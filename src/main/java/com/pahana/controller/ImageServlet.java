package com.pahana.controller;

import com.pahana.dao.BookDAO;
import com.pahana.dto.BookDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/book-image")
public class ImageServlet extends HttpServlet {
    
    private final BookDAO bookDAO = new BookDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String bookIdParam = request.getParameter("id");
        
        if (bookIdParam == null || bookIdParam.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Book ID is required");
            return;
        }
        
        try {
            int bookId = Integer.parseInt(bookIdParam);
            BookDTO book = bookDAO.getBookById(bookId);
            
            if (book == null || book.getImageData() == null) {
                // Return a default image or 404
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
                return;
            }
            
            // Set content type
            String contentType = book.getImageType();
            if (contentType == null || contentType.trim().isEmpty()) {
                contentType = "image/jpeg"; // Default to JPEG
            }
            response.setContentType(contentType);
            
            // Set cache headers
            response.setHeader("Cache-Control", "public, max-age=31536000");
            response.setHeader("Expires", "Thu, 31 Dec 2024 23:59:59 GMT");
            
            // Write image data
            try (OutputStream out = response.getOutputStream()) {
                out.write(book.getImageData());
                out.flush();
            }
            
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid book ID");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving image");
        }
    }
}
