package com.pahana.dao;

import com.pahana.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    private final Connection connection;

    // Constructor to accept the DB connection
    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    // Add customer
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (accountNumber, name, address, phone, unitsConsumed) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, customer.getAccountNumber());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getPhone());
            stmt.setInt(5, customer.getUnitsConsumed());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get customer by account number (accountNumber is treated as String)
    public Customer getCustomerByAccountNumber(String accountNumber) {
        String sql = "SELECT accountNumber, name, address, phone, unitsConsumed FROM customers WHERE accountNumber = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Customer customer = new Customer();
                    customer.setAccountNumber(rs.getString("accountNumber"));
                    customer.setName(rs.getString("name"));
                    customer.setAddress(rs.getString("address"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setUnitsConsumed(rs.getInt("unitsConsumed"));
                    return customer;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update customer
    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET name = ?, address = ?, phone = ?, unitsConsumed = ? WHERE accountNumber = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getAddress());
            stmt.setString(3, customer.getPhone());
            stmt.setInt(4, customer.getUnitsConsumed());
            stmt.setString(5, customer.getAccountNumber());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete customer
    public boolean deleteCustomer(String accountNumber) {
        try (Connection conn = connection) {
            conn.setAutoCommit(false);
            try {
                // First delete related invoice items for invoices of this customer
                String deleteInvoiceItemsSql = "DELETE ii FROM invoice_items ii " +
                                             "INNER JOIN invoices i ON ii.invoice_id = i.invoice_id " +
                                             "WHERE i.customer_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(deleteInvoiceItemsSql)) {
                    stmt.setString(1, accountNumber);
                    stmt.executeUpdate();
                }
                
                // Then delete invoices for this customer
                String deleteInvoicesSql = "DELETE FROM invoices WHERE customer_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(deleteInvoicesSql)) {
                    stmt.setString(1, accountNumber);
                    stmt.executeUpdate();
                }
                
                // Finally delete the customer
                String deleteCustomerSql = "DELETE FROM customers WHERE accountNumber = ?";
                try (PreparedStatement stmt = conn.prepareStatement(deleteCustomerSql)) {
                    stmt.setString(1, accountNumber);
                    int rows = stmt.executeUpdate();
                    
                    if (rows > 0) {
                        conn.commit();
                        return true;
                    } else {
                        conn.rollback();
                        return false;
                    }
                }
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT accountNumber, name, address, phone, unitsConsumed FROM customers";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setAccountNumber(rs.getString("accountNumber"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setPhone(rs.getString("phone"));
                customer.setUnitsConsumed(rs.getInt("unitsConsumed"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

}
