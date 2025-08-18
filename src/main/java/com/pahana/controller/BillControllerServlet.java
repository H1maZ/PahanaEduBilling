package com.pahana.controller;

import com.pahana.dto.InvoiceDTO;
import com.pahana.service.BillingService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/bill")
public class BillControllerServlet extends HttpServlet {

    private final BillingService billingService = new BillingService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedIn") == null) {
            response.sendRedirect("login");
            return;
        }

        String invoiceId = request.getParameter("invoiceId");
        
        if (invoiceId == null || invoiceId.trim().isEmpty()) {
            response.sendRedirect("billing-history?error=Invoice ID is required");
            return;
        }

        // Get invoice from database
        InvoiceDTO invoice = billingService.getInvoiceById(invoiceId);
        
        if (invoice == null) {
            response.sendRedirect("billing-history?error=Invoice not found");
            return;
        }

        // Set invoice in request attributes
        request.setAttribute("invoice", invoice);
        
        // Forward to bill.jsp
        request.getRequestDispatcher("bill.jsp").forward(request, response);
    }
}
