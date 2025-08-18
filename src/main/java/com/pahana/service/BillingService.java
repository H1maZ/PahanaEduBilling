package com.pahana.service;

import com.pahana.dao.BookDAO;
import com.pahana.dao.InvoiceDAO;
import com.pahana.dto.CartItemDTO;
import com.pahana.dto.CustomerDTO;
import com.pahana.dto.InvoiceDTO;
import com.pahana.dto.InvoiceItemDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BillingService {
    
    private final BookDAO bookDAO = new BookDAO();
    private final InvoiceDAO invoiceDAO = new InvoiceDAO();
    
    public InvoiceDTO createInvoice(CustomerDTO customer, List<CartItemDTO> cartItems) {
        System.out.println("=== BillingService.createInvoice() ===");
        
        if (cartItems == null || cartItems.isEmpty()) {
            System.err.println("❌ Cart items is null or empty");
            return null;
        }
        
        System.out.println("✅ Cart items count: " + cartItems.size());
        
        // Create invoice
        InvoiceDTO invoice = new InvoiceDTO();
        String invoiceId = generateInvoiceId();
        invoice.setInvoiceId(invoiceId);
        invoice.setCustomerId(customer.getAccountNumber());
        invoice.setCustomerName(customer.getName());
        invoice.setInvoiceDate(LocalDateTime.now());
        double totalAmount = calculateTotal(cartItems);
        invoice.setTotalAmount(totalAmount);
        invoice.setPaymentStatus("PAID"); // Set default payment status
        
        System.out.println("✅ Invoice object created:");
        System.out.println("  - Invoice ID: " + invoiceId);
        System.out.println("  - Customer: " + customer.getName());
        System.out.println("  - Total Amount: " + totalAmount);
        System.out.println("  - Payment Status: " + invoice.getPaymentStatus());
        
        // Create invoice items
        List<InvoiceItemDTO> invoiceItems = new ArrayList<>();
        for (CartItemDTO cartItem : cartItems) {
            InvoiceItemDTO invoiceItem = new InvoiceItemDTO();
            invoiceItem.setBookId(cartItem.getBookId());
            invoiceItem.setBookTitle(cartItem.getTitle());
            invoiceItem.setQuantity(cartItem.getQuantity());
            invoiceItem.setUnitPrice(cartItem.getPrice());
            invoiceItem.setTotalPrice(cartItem.getPrice() * cartItem.getQuantity());
            invoiceItems.add(invoiceItem);
            
            System.out.println("✅ Invoice item created: " + cartItem.getTitle() + " x" + cartItem.getQuantity());
        }
        invoice.setItems(invoiceItems);
        
        // Save invoice to database
        System.out.println("🔄 Saving invoice to database...");
        if (invoiceDAO.saveInvoice(invoice)) {
            System.out.println("✅ Invoice saved successfully to database");
            
            // Update stock levels
            System.out.println("🔄 Updating stock levels...");
            updateStockLevels(cartItems);
            System.out.println("✅ Stock levels updated");
            
            return invoice;
        } else {
            System.err.println("❌ Failed to save invoice to database");
            return null;
        }
    }
    
    // Get all invoices for billing history
    public List<InvoiceDTO> getAllInvoices() {
        return invoiceDAO.getAllInvoices();
    }
    
    // Get invoice by ID for reprinting
    public InvoiceDTO getInvoiceById(String invoiceId) {
        return invoiceDAO.getInvoiceById(invoiceId);
    }
    
    // Get invoices by customer
    public List<InvoiceDTO> getInvoicesByCustomer(String customerId) {
        return invoiceDAO.getInvoicesByCustomer(customerId);
    }
    
    private String generateInvoiceId() {
        return "INV-" + System.currentTimeMillis();
    }
    
    private double calculateTotal(List<CartItemDTO> cartItems) {
        return cartItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
    
    private void updateStockLevels(List<CartItemDTO> cartItems) {
        for (CartItemDTO item : cartItems) {
            // Get current book
            var book = bookDAO.getBookById(item.getBookId());
            if (book != null) {
                // Update stock
                int newStock = book.getStock() - item.getQuantity();
                if (newStock >= 0) {
                    book.setStock(newStock);
                    boolean updated = bookDAO.updateBook(book);
                    if (updated) {
                        System.out.println("✅ Stock updated for " + book.getTitle() + ": " + newStock);
                    } else {
                        System.err.println("❌ Failed to update stock for " + book.getTitle());
                    }
                } else {
                    System.err.println("❌ Insufficient stock for " + book.getTitle());
                }
            } else {
                System.err.println("❌ Book not found for ID: " + item.getBookId());
            }
        }
    }
}
