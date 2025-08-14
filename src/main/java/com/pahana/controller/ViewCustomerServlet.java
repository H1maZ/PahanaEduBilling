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
    import java.util.List;

    @WebServlet("/customers")
    public class ViewCustomerServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            try (Connection connection = DBConnection.getConnection()) {
                CustomerDAO customerDAO = new CustomerDAO(connection);

                List<Customer> customers = customerDAO.getAllCustomers();
                req.setAttribute("customerList", customers);

                req.getRequestDispatcher("customerList.jsp").forward(req, resp);
            } catch (SQLException e) {
                e.printStackTrace();
                resp.getWriter().println("<h3>Database error: " + e.getMessage() + "</h3>");
            }
        }
    }

