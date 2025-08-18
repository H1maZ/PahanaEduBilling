package com.pahana.controller;

import com.pahana.dto.CartItemDTO;
import com.pahana.service.CartService;
import com.pahana.dao.BookDAO;
import com.pahana.dto.BookDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/cart")
public class CartControllerServlet extends HttpServlet {

    private final CartService cartService = new CartService();
    private final BookDAO bookDAO = new BookDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");

        if (cart == null) {
            cart = new java.util.ArrayList<>();
        }

        try {
            if ("add".equals(action)) {
                int bookId = Integer.parseInt(request.getParameter("bookId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                if (quantity <= 0) {
                    session.setAttribute("error", "Quantity must be greater than zero.");
                    response.sendRedirect("books");
                    return;
                }

                BookDTO book = bookDAO.getBookById(bookId);
                if (book == null) {
                    session.setAttribute("error", "Book not found.");
                    response.sendRedirect("books");
                    return;
                }

                CartItemDTO newItem = new CartItemDTO(book.getBookId(), book.getTitle(), book.getPrice(), quantity);
                cart = cartService.addToCart(cart, newItem);
                session.setAttribute("cart", cart);
                session.setAttribute("message", "Book added to cart!");
                response.sendRedirect("books");
                return;

            } else if ("update".equals(action)) {
                int bookId = Integer.parseInt(request.getParameter("bookId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                if (quantity <= 0) {
                    // Remove item if quantity is 0 or negative
                    cart = cartService.removeFromCart(cart, bookId);
                    session.setAttribute("message", "Item removed from cart.");
                } else {
                    // Update quantity
                    for (CartItemDTO item : cart) {
                        if (item.getBookId() == bookId) {
                            item.setQuantity(quantity);
                            break;
                        }
                    }
                    session.setAttribute("message", "Cart updated successfully!");
                }
                session.setAttribute("cart", cart);
                response.sendRedirect("cart");
                return;

            } else if ("remove".equals(action)) {
                int bookId = Integer.parseInt(request.getParameter("bookId"));
                cart = cartService.removeFromCart(cart, bookId);
                session.setAttribute("cart", cart);
                session.setAttribute("message", "Item removed from cart.");
                response.sendRedirect("cart");
                return;

            } else if ("increase".equals(action)) {
                int bookId = Integer.parseInt(request.getParameter("bookId"));
                for (CartItemDTO item : cart) {
                    if (item.getBookId() == bookId) {
                        item.setQuantity(item.getQuantity() + 1);
                        break;
                    }
                }
                session.setAttribute("cart", cart);
                response.sendRedirect("cart");
                return;

            } else if ("decrease".equals(action)) {
                int bookId = Integer.parseInt(request.getParameter("bookId"));
                for (CartItemDTO item : cart) {
                    if (item.getBookId() == bookId) {
                        if (item.getQuantity() > 1) {
                            item.setQuantity(item.getQuantity() - 1);
                        } else {
                            // Remove item if quantity goes below 1
                            cart = cartService.removeFromCart(cart, bookId);
                        }
                        break;
                    }
                }
                session.setAttribute("cart", cart);
                response.sendRedirect("cart");
                return;
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Invalid input.");
            response.sendRedirect("books");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }
}
