package com.pahana.controller;

import com.pahana.dao.CustomerDAO;
import com.pahana.model.Customer;
import com.pahana.dao.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String action = req.getParameter("action"); // "add", "update", or "delete"
        String message = null;
        String error = null;

        try (Connection connection = DBConnection.getConnection()) {
            CustomerDAO customerDAO = new CustomerDAO(connection);
            String accountNumber = req.getParameter("accountNumber");

            if ("delete".equalsIgnoreCase(action)) {
                boolean deleted = customerDAO.deleteCustomer(accountNumber);
                if (deleted) {
                    message = "Customer deleted successfully!";
                } else {
                    error = "Failed to delete customer.";
                }

            } else {
                // Add or Update
                String name = req.getParameter("name");
                String address = req.getParameter("address");
                String phone = req.getParameter("phone");
                int unitsConsumed = 0;

                try {
                    unitsConsumed = Integer.parseInt(req.getParameter("unitsConsumed"));
                } catch (NumberFormatException e) {
                    error = "Invalid input for units consumed.";
                }

                if (error == null) {
                    Customer customer = new Customer(accountNumber, name, address, phone, unitsConsumed);

                    if ("update".equalsIgnoreCase(action)) {
                        boolean updated = customerDAO.updateCustomer(customer);
                        if (updated) {
                            message = "Customer updated successfully!";
                        } else {
                            error = "Failed to update customer.";
                        }
                    } else if ("add".equalsIgnoreCase(action)) {
                        boolean added = customerDAO.addCustomer(customer);
                        if (added) {
                            message = "Customer added successfully!";
                        } else {
                            error = "Failed to add customer.";
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            error = "Database error: " + e.getMessage();
        }

        // Redirect to the servlet URL /customers to reload customer list with messages
        String redirectUrl = "customers";

        if (message != null) {
            redirectUrl += "?message=" + java.net.URLEncoder.encode(message, "UTF-8");
        } else if (error != null) {
            redirectUrl += "?error=" + java.net.URLEncoder.encode(error, "UTF-8");
        }

        resp.sendRedirect(redirectUrl);
    }
}
