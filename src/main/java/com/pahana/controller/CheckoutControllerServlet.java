package com.pahana.controller;

import com.pahana.dto.CustomerDTO;
import com.pahana.dto.CartItemDTO;
import com.pahana.dto.InvoiceDTO;
import com.pahana.service.CustomerService;
import com.pahana.service.BillingService;
import com.pahana.dao.InvoiceDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutControllerServlet extends HttpServlet {

    private final CustomerService customerService = new CustomerService();
    private final BillingService billingService = new BillingService();
    private final InvoiceDAO invoiceDAO = new InvoiceDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedIn") == null) {
            response.sendRedirect("login");
            return;
        }

        List<CustomerDTO> customers = customerService.getAllCustomers();
        request.setAttribute("customers", customers);
        request.getRequestDispatcher("checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedIn") == null) {
            response.sendRedirect("login");
            return;
        }

        String customerId = request.getParameter("customerId");
        List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");

        System.out.println("=== Checkout Debug Info ===");
        System.out.println("Customer ID: " + customerId);
        System.out.println("Cart size: " + (cart != null ? cart.size() : "null"));

        if (cart == null || cart.isEmpty()) {
            System.err.println("❌ Cart is empty or null");
            response.sendRedirect("cart?error=Cart is empty");
            return;
        }

        if (customerId == null || customerId.trim().isEmpty()) {
            System.err.println("❌ Customer ID is missing");
            response.sendRedirect("checkout?error=Please select a customer");
            return;
        }

        // Test database connection first
        if (!invoiceDAO.testConnection()) {
            System.err.println("❌ Database connection test failed");
            response.sendRedirect("checkout?error=Database connection failed");
            return;
        }

        // Get customer details
        CustomerDTO customer = customerService.getCustomerByAccountNumber(customerId);
        if (customer == null) {
            System.err.println("❌ Customer not found: " + customerId);
            response.sendRedirect("checkout?error=Customer not found");
            return;
        }

        System.out.println("✅ Customer found: " + customer.getName());

        // Validate cart items
        for (CartItemDTO item : cart) {
            if (item.getBookId() <= 0 || item.getQuantity() <= 0) {
                System.err.println("❌ Invalid cart item: " + item);
                response.sendRedirect("checkout?error=Invalid cart items detected");
                return;
            }
        }

        // Create invoice
        System.out.println("🔄 Creating invoice...");
        InvoiceDTO invoice = billingService.createInvoice(customer, cart);
        
        if (invoice == null) {
            System.err.println("❌ Invoice creation failed");
            response.sendRedirect("checkout?error=Failed to create invoice - check server logs");
            return;
        }

        System.out.println("✅ Invoice created successfully: " + invoice.getInvoiceId());

        // Clear cart after successful billing
        session.removeAttribute("cart");
        System.out.println("✅ Cart cleared from session");

        // Pass invoice to bill page
        request.setAttribute("invoice", invoice);
        request.getRequestDispatcher("bill.jsp").forward(request, response);
    }
}
