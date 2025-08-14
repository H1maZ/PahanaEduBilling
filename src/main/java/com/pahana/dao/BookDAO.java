package com.pahana.dao;

import com.pahana.dto.BookDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // Add a new book to the database
    public boolean addBook(BookDTO book) {
        String sql = "INSERT INTO books (title, author, price, stock, image_data, image_type) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setDouble(3, book.getPrice());
            stmt.setInt(4, book.getStock());
            stmt.setBytes(5, book.getImageData());
            stmt.setString(6, book.getImageType());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Error while adding book: " + e.getMessage());
            return false;
        }
    }

    // Retrieve all books
    public List<BookDTO> getAllBooks() {
        List<BookDTO> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                BookDTO book = new BookDTO();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setPrice(rs.getDouble("price"));
                book.setStock(rs.getInt("stock"));
                book.setImageData(rs.getBytes("image_data"));
                book.setImageType(rs.getString("image_type"));

                books.add(book);
            }

        } catch (SQLException e) {
            System.err.println("Error while retrieving books: " + e.getMessage());
        }

        return books;
    }

    // Get a book by ID
    public BookDTO getBookById(int id) {
        String sql = "SELECT book_id, title, author, price, stock, image_data, image_type FROM books WHERE book_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BookDTO b = new BookDTO();
                    b.setBookId(rs.getInt("book_id"));
                    b.setTitle(rs.getString("title"));
                    b.setAuthor(rs.getString("author"));
                    b.setPrice(rs.getDouble("price"));
                    b.setStock(rs.getInt("stock"));
                    b.setImageData(rs.getBytes("image_data"));
                    b.setImageType(rs.getString("image_type"));
                    return b;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update an existing book
    public boolean updateBook(BookDTO book) {
        String sql = "UPDATE books SET title = ?, author = ?, price = ?, stock = ?, image_data = ?, image_type = ? WHERE book_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setDouble(3, book.getPrice());
            stmt.setInt(4, book.getStock());
            stmt.setBytes(5, book.getImageData());
            stmt.setString(6, book.getImageType());
            stmt.setInt(7, book.getBookId());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Error while updating book: " + e.getMessage());
            return false;
        }
    }

    // Delete a book by ID
    public boolean deleteBook(int bookId) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // First delete related invoice items
                String deleteInvoiceItemsSql = "DELETE FROM invoice_items WHERE book_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(deleteInvoiceItemsSql)) {
                    stmt.setInt(1, bookId);
                    stmt.executeUpdate();
                }
                
                // Then delete the book
                String deleteBookSql = "DELETE FROM books WHERE book_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(deleteBookSql)) {
                    stmt.setInt(1, bookId);
                    int rows = stmt.executeUpdate();
                    
                    if (rows > 0) {
                        conn.commit();
                        return true;
                    } else {
                        conn.rollback();
                        return false;
                    }
                }
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Error while deleting book: " + e.getMessage());
                return false;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println("Error while deleting book: " + e.getMessage());
            return false;
        }
    }
}
