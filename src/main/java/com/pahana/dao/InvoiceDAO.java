package com.pahana.dao;

import com.pahana.dto.InvoiceDTO;
import com.pahana.dto.InvoiceItemDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {

    // Save invoice to database
    public boolean saveInvoice(InvoiceDTO invoice) {
        String sql = "INSERT INTO invoices (invoice_id, customer_id, customer_name, invoice_date, total_amount, payment_status) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, invoice.getInvoiceId());
            stmt.setString(2, invoice.getCustomerId());
            stmt.setString(3, invoice.getCustomerName());
            stmt.setTimestamp(4, Timestamp.valueOf(invoice.getInvoiceDate()));
            stmt.setDouble(5, invoice.getTotalAmount());
            stmt.setString(6, invoice.getPaymentStatus() != null ? invoice.getPaymentStatus() : "PAID");
            
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                // Save invoice items
                return saveInvoiceItems(invoice);
            }
            return false;
            
        } catch (SQLException e) {
            System.err.println("Error while saving invoice: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Save invoice items to database
    private boolean saveInvoiceItems(InvoiceDTO invoice) {
        String sql = "INSERT INTO invoice_items (invoice_id, book_id, book_title, quantity, unit_price, total_price) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Disable auto-commit for transaction
            conn.setAutoCommit(false);
            
            try {
                for (InvoiceItemDTO item : invoice.getItems()) {
                    stmt.setString(1, invoice.getInvoiceId());
                    stmt.setInt(2, item.getBookId());
                    stmt.setString(3, item.getBookTitle());
                    stmt.setInt(4, item.getQuantity());
                    stmt.setDouble(5, item.getUnitPrice());
                    stmt.setDouble(6, item.getTotalPrice());
                    stmt.addBatch();
                }
                
                int[] results = stmt.executeBatch();
                conn.commit();
                
                // Check if all batch operations were successful
                for (int result : results) {
                    if (result < 0) {
                        System.err.println("Batch operation failed with result: " + result);
                        return false;
                    }
                }
                
                return results.length > 0;
                
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
            
        } catch (SQLException e) {
            System.err.println("Error while saving invoice items: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Get all invoices
    public List<InvoiceDTO> getAllInvoices() {
        List<InvoiceDTO> invoices = new ArrayList<>();
        String sql = "SELECT * FROM invoices ORDER BY invoice_date DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                InvoiceDTO invoice = new InvoiceDTO();
                invoice.setInvoiceId(rs.getString("invoice_id"));
                invoice.setCustomerId(rs.getString("customer_id"));
                invoice.setCustomerName(rs.getString("customer_name"));
                invoice.setInvoiceDate(rs.getTimestamp("invoice_date").toLocalDateTime());
                invoice.setTotalAmount(rs.getDouble("total_amount"));
                invoice.setPaymentStatus(rs.getString("payment_status"));
                
                // Get invoice items
                invoice.setItems(getInvoiceItems(invoice.getInvoiceId()));
                
                invoices.add(invoice);
            }
            
        } catch (SQLException e) {
            System.err.println("Error while retrieving invoices: " + e.getMessage());
            e.printStackTrace();
        }
        
        return invoices;
    }
    
    // Get invoice by ID
    public InvoiceDTO getInvoiceById(String invoiceId) {
        String sql = "SELECT * FROM invoices WHERE invoice_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, invoiceId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                InvoiceDTO invoice = new InvoiceDTO();
                invoice.setInvoiceId(rs.getString("invoice_id"));
                invoice.setCustomerId(rs.getString("customer_id"));
                invoice.setCustomerName(rs.getString("customer_name"));
                invoice.setInvoiceDate(rs.getTimestamp("invoice_date").toLocalDateTime());
                invoice.setTotalAmount(rs.getDouble("total_amount"));
                invoice.setPaymentStatus(rs.getString("payment_status"));
                
                // Get invoice items
                invoice.setItems(getInvoiceItems(invoiceId));
                
                return invoice;
            }
            
        } catch (SQLException e) {
            System.err.println("Error while retrieving invoice: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    // Get invoice items by invoice ID
    private List<InvoiceItemDTO> getInvoiceItems(String invoiceId) {
        List<InvoiceItemDTO> items = new ArrayList<>();
        String sql = "SELECT * FROM invoice_items WHERE invoice_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, invoiceId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                InvoiceItemDTO item = new InvoiceItemDTO();
                item.setBookId(rs.getInt("book_id"));
                item.setBookTitle(rs.getString("book_title"));
                item.setQuantity(rs.getInt("quantity"));
                item.setUnitPrice(rs.getDouble("unit_price"));
                item.setTotalPrice(rs.getDouble("total_price"));
                
                items.add(item);
            }
            
        } catch (SQLException e) {
            System.err.println("Error while retrieving invoice items: " + e.getMessage());
            e.printStackTrace();
        }
        
        return items;
    }
    
    // Get invoices by customer
    public List<InvoiceDTO> getInvoicesByCustomer(String customerId) {
        List<InvoiceDTO> invoices = new ArrayList<>();
        String sql = "SELECT * FROM invoices WHERE customer_id = ? ORDER BY invoice_date DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, customerId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                InvoiceDTO invoice = new InvoiceDTO();
                invoice.setInvoiceId(rs.getString("invoice_id"));
                invoice.setCustomerId(rs.getString("customer_id"));
                invoice.setCustomerName(rs.getString("customer_name"));
                invoice.setInvoiceDate(rs.getTimestamp("invoice_date").toLocalDateTime());
                invoice.setTotalAmount(rs.getDouble("total_amount"));
                invoice.setPaymentStatus(rs.getString("payment_status"));
                
                // Get invoice items
                invoice.setItems(getInvoiceItems(invoice.getInvoiceId()));
                
                invoices.add(invoice);
            }
            
        } catch (SQLException e) {
            System.err.println("Error while retrieving customer invoices: " + e.getMessage());
            e.printStackTrace();
        }
        
        return invoices;
    }
    
    // Test database connection and table structure
    public boolean testConnection() {
        try (Connection conn = DBConnection.getConnection()) {
            // Test if invoices table exists
            String checkTableSql = "SELECT COUNT(*) FROM invoices";
            try (PreparedStatement stmt = conn.prepareStatement(checkTableSql)) {
                stmt.executeQuery();
                System.out.println("✅ Invoices table exists and is accessible");
            }
            
            // Test if invoice_items table exists
            String checkItemsTableSql = "SELECT COUNT(*) FROM invoice_items";
            try (PreparedStatement stmt = conn.prepareStatement(checkItemsTableSql)) {
                stmt.executeQuery();
                System.out.println("✅ Invoice_items table exists and is accessible");
            }
            
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Database connection test failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
