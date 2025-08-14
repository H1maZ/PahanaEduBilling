package com.pahana.controller;

import com.pahana.dao.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int bookCount = 0;
        int customerCount = 0;

        try (Connection conn = DBConnection.getConnection()) {
            // Count books
            String sqlBooks = "SELECT COUNT(*) FROM books";
            try (PreparedStatement ps = conn.prepareStatement(sqlBooks)) {
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    bookCount = rs.getInt(1);
                }
            }

            // Count customers
            String sqlCustomers = "SELECT COUNT(*) FROM customers";
            try (PreparedStatement ps = conn.prepareStatement(sqlCustomers)) {
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    customerCount = rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Pass data to JSP
        req.setAttribute("bookCount", bookCount);
        req.setAttribute("customerCount", customerCount);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
