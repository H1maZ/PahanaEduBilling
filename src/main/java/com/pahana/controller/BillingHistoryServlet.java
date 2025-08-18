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
import java.util.List;

@WebServlet("/billing-history")
public class BillingHistoryServlet extends HttpServlet {

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

        String action = request.getParameter("action");
        String invoiceId = request.getParameter("invoiceId");

        if ("view".equals(action) && invoiceId != null) {
            // View specific invoice for reprinting
            InvoiceDTO invoice = billingService.getInvoiceById(invoiceId);
            if (invoice != null) {
                request.setAttribute("invoice", invoice);
                request.getRequestDispatcher("bill.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Invoice not found");
                response.sendRedirect("billing-history");
            }
        } else {
            // Show billing history list
            List<InvoiceDTO> invoices = billingService.getAllInvoices();
            request.setAttribute("invoices", invoices);
            request.getRequestDispatcher("billing-history.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
