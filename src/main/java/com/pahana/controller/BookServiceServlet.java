package com.pahana.controller;

import com.pahana.dao.BookDAO;
import com.pahana.dto.BookDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@WebServlet("/books")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 5,   // 5 MB
    maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
public class BookServiceServlet extends HttpServlet {

    private final BookDAO bookDAO = new BookDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {

            if ("add".equalsIgnoreCase(action)) {
                String title = request.getParameter("title");
                String author = request.getParameter("author");
                double price = Double.parseDouble(request.getParameter("price"));
                int stock = Integer.parseInt(request.getParameter("stock"));

                BookDTO book = new BookDTO();
                book.setTitle(title);
                book.setAuthor(author);
                book.setPrice(price);
                book.setStock(stock);

                // Handle image upload
                Part filePart = request.getPart("image");
                if (filePart != null && filePart.getSize() > 0) {
                    String fileName = filePart.getSubmittedFileName();
                    String contentType = filePart.getContentType();
                    
                    // Validate file type
                    if (contentType != null && (contentType.startsWith("image/"))) {
                        try (InputStream inputStream = filePart.getInputStream()) {
                            byte[] imageData = inputStream.readAllBytes();
                            book.setImageData(imageData);
                            book.setImageType(contentType);
                        }
                    } else {
                        request.getSession().setAttribute("error", "Please upload a valid image file.");
                        response.sendRedirect("add-book.jsp");
                        return;
                    }
                }

                boolean success = bookDAO.addBook(book);

                if (success) {
                    request.getSession().setAttribute("message", "Book added successfully!");
                    response.sendRedirect("books");
                } else {
                    request.getSession().setAttribute("error", "Failed to add book.");
                    response.sendRedirect("add-book.jsp");
                }
            } else if ("update".equalsIgnoreCase(action)) {
                int bookId = Integer.parseInt(request.getParameter("bookId"));
                String title = request.getParameter("title");
                String author = request.getParameter("author");
                double price = Double.parseDouble(request.getParameter("price"));
                int stock = Integer.parseInt(request.getParameter("stock"));

                BookDTO book = new BookDTO();
                book.setBookId(bookId);
                book.setTitle(title);
                book.setAuthor(author);
                book.setPrice(price);
                book.setStock(stock);

                // Handle image upload for update
                Part filePart = request.getPart("image");
                if (filePart != null && filePart.getSize() > 0) {
                    String fileName = filePart.getSubmittedFileName();
                    String contentType = filePart.getContentType();
                    
                    // Validate file type
                    if (contentType != null && (contentType.startsWith("image/"))) {
                        try (InputStream inputStream = filePart.getInputStream()) {
                            byte[] imageData = inputStream.readAllBytes();
                            book.setImageData(imageData);
                            book.setImageType(contentType);
                        }
                    } else {
                        request.getSession().setAttribute("error", "Please upload a valid image file.");
                        response.sendRedirect("edit-book.jsp?bookId=" + bookId);
                        return;
                    }
                } else {
                    // Keep existing image if no new image is uploaded
                    BookDTO existingBook = bookDAO.getBookById(bookId);
                    if (existingBook != null) {
                        book.setImageData(existingBook.getImageData());
                        book.setImageType(existingBook.getImageType());
                    }
                }

                boolean success = bookDAO.updateBook(book);

                if (success) {
                    request.getSession().setAttribute("message", "Book updated successfully!");
                    response.sendRedirect("books");
                } else {
                    request.getSession().setAttribute("error", "Failed to update book.");
                    response.sendRedirect("edit-book.jsp?bookId=" + bookId);
                }
            } else if ("delete".equalsIgnoreCase(action)) {
                int bookId = Integer.parseInt(request.getParameter("bookId"));
                boolean success = bookDAO.deleteBook(bookId);

                if (success) {
                    request.getSession().setAttribute("message", "Book deleted successfully!");
                } else {
                    request.getSession().setAttribute("error", "Failed to delete book.");
                }
                response.sendRedirect("books");
            } else {
                response.sendRedirect("books");
            }

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Invalid input.");
            response.sendRedirect("books");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<BookDTO> books = bookDAO.getAllBooks();
        request.setAttribute("books", books);
        request.getRequestDispatcher("book-list.jsp").forward(request, response);
    }
}
