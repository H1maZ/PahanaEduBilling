package com.pahana.dto;

import java.time.LocalDateTime;
import java.util.List;

public class InvoiceDTO {
    private String invoiceId;
    private String customerId;
    private String customerName;
    private LocalDateTime invoiceDate;
    private double totalAmount;
    private String paymentStatus;
    private List<InvoiceItemDTO> items;
    
    // Constructors
    public InvoiceDTO() {}
    
    public InvoiceDTO(String invoiceId, String customerId, String customerName, 
                     LocalDateTime invoiceDate, double totalAmount) {
        this.invoiceId = invoiceId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.invoiceDate = invoiceDate;
        this.totalAmount = totalAmount;
        this.paymentStatus = "PAID"; // Default status
    }
    
    // Getters and Setters
    public String getInvoiceId() {
        return invoiceId;
    }
    
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }
    
    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    public List<InvoiceItemDTO> getItems() {
        return items;
    }
    
    public void setItems(List<InvoiceItemDTO> items) {
        this.items = items;
    }
}
