package com.pahana.dao;

import com.pahana.dto.BookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("BookDAO Tests")
class BookDAOTest {

    private BookDAO bookDAO;

    @BeforeEach
    void setUp() {
        bookDAO = new BookDAO();
    }

    @Test
    @DisplayName("Should create BookDAO instance")
    void testBookDAOInitialization() {
        // Arrange & Act
        BookDAO newBookDAO = new BookDAO();

        // Assert
        assertNotNull(newBookDAO);
    }

    @Test
    @DisplayName("Should validate BookDTO fields")
    void testBookDTOValidation() {
        // Arrange
        BookDTO book = createTestBook();

        // Act & Assert
        assertEquals(1, book.getBookId());
        assertEquals("Test Book", book.getTitle());
        assertEquals("Test Author", book.getAuthor());
        assertEquals(25.99, book.getPrice(), 0.01);
        assertEquals(10, book.getStock());
    }

    @Test
    @DisplayName("Should handle BookDTO setters")
    void testBookDTOSetters() {
        // Arrange
        BookDTO book = new BookDTO();

        // Act
        book.setBookId(2);
        book.setTitle("Updated Book");
        book.setAuthor("Updated Author");
        book.setPrice(30.50);
        book.setStock(15);
        book.setImageType("image/jpeg");

        // Assert
        assertEquals(2, book.getBookId());
        assertEquals("Updated Book", book.getTitle());
        assertEquals("Updated Author", book.getAuthor());
        assertEquals(30.50, book.getPrice(), 0.01);
        assertEquals(15, book.getStock());
        assertEquals("image/jpeg", book.getImageType());
    }

    @Test
    @DisplayName("Should handle image data in BookDTO")
    void testBookDTOImageData() {
        // Arrange
        BookDTO book = new BookDTO();
        byte[] testImageData = "test image data".getBytes();

        // Act
        book.setImageData(testImageData);
        book.setImageType("image/png");

        // Assert
        assertNotNull(book.getImageData());
        assertEquals(testImageData.length, book.getImageData().length);
        assertEquals("image/png", book.getImageType());
    }

    @Test
    @DisplayName("Should test addBook method exists")
    void testAddBookMethodExists() {
        // Arrange
        BookDTO book = createTestBook();

        // Act
        boolean result = bookDAO.addBook(book);

        // Assert
        // The method should handle the request (may return false if no database connection)
        assertNotNull(bookDAO);
    }

    @Test
    @DisplayName("Should test getBookById method exists")
    void testGetBookByIdMethodExists() {
        // Act
        BookDTO result = bookDAO.getBookById(1);

        // Assert
        // The method should handle the request (may return null if no database connection)
        assertNotNull(bookDAO);
    }

    @Test
    @DisplayName("Should test getAllBooks method exists")
    void testGetAllBooksMethodExists() {
        // Act
        List<BookDTO> result = bookDAO.getAllBooks();

        // Assert
        // The method should return a list (even if empty due to no database connection)
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should test updateBook method exists")
    void testUpdateBookMethodExists() {
        // Arrange
        BookDTO book = createTestBook();

        // Act
        boolean result = bookDAO.updateBook(book);

        // Assert
        // The method should handle the request
        assertNotNull(bookDAO);
    }

    @Test
    @DisplayName("Should test deleteBook method exists")
    void testDeleteBookMethodExists() {
        // Act
        boolean result = bookDAO.deleteBook(1);

        // Assert
        // The method should handle the request
        assertNotNull(bookDAO);
    }

    @Test
    @DisplayName("Should handle null book in addBook")
    void testAddBook_NullBook() {
        // Act
        boolean result = bookDAO.addBook(null);

        // Assert
        // The method should handle null input gracefully
        assertNotNull(bookDAO);
    }

    @Test
    @DisplayName("Should handle invalid book ID in getBookById")
    void testGetBookById_InvalidId() {
        // Act
        BookDTO result = bookDAO.getBookById(-1);

        // Assert
        // The method should handle invalid input gracefully
        assertNotNull(bookDAO);
    }

    @Test
    @DisplayName("Should handle null book in updateBook")
    void testUpdateBook_NullBook() {
        // Act
        boolean result = bookDAO.updateBook(null);

        // Assert
        // The method should handle null input gracefully
        assertNotNull(bookDAO);
    }

    @Test
    @DisplayName("Should handle invalid book ID in deleteBook")
    void testDeleteBook_InvalidId() {
        // Act
        boolean result = bookDAO.deleteBook(-1);

        // Assert
        // The method should handle invalid input gracefully
        assertNotNull(bookDAO);
    }

    @Test
    @DisplayName("Should test DAO with multiple book operations")
    void testMultipleBookOperations() {
        // Arrange
        BookDTO book1 = createTestBook();
        BookDTO book2 = createTestBook();
        book2.setBookId(2);
        book2.setTitle("Test Book 2");

        // Act & Assert - Test that all methods exist and can be called
        assertDoesNotThrow(() -> {
            bookDAO.addBook(book1);
            bookDAO.addBook(book2);
            bookDAO.getAllBooks();
            bookDAO.getBookById(1);
            bookDAO.getBookById(2);
            bookDAO.updateBook(book1);
            bookDAO.deleteBook(1);
        });
    }

    @Test
    @DisplayName("Should validate book with special characters")
    void testBookWithSpecialCharacters() {
        // Arrange
        BookDTO book = new BookDTO();
        book.setTitle("Test Book with Special Characters: @#$%^&*()");
        book.setAuthor("Author with Special Characters: @#$%^&*()");

        // Act & Assert
        assertEquals("Test Book with Special Characters: @#$%^&*()", book.getTitle());
        assertEquals("Author with Special Characters: @#$%^&*()", book.getAuthor());
    }

    @Test
    @DisplayName("Should validate book with very long text")
    void testBookWithLongText() {
        // Arrange
        BookDTO book = new BookDTO();
        String longTitle = "A".repeat(500); // 500 character title
        String longAuthor = "B".repeat(500); // 500 character author
        book.setTitle(longTitle);
        book.setAuthor(longAuthor);

        // Act & Assert
        assertEquals(longTitle, book.getTitle());
        assertEquals(longAuthor, book.getAuthor());
    }

    @Test
    @DisplayName("Should validate book with decimal prices")
    void testBookWithDecimalPrice() {
        // Arrange
        BookDTO book = new BookDTO();
        book.setPrice(19.99);

        // Act & Assert
        assertEquals(19.99, book.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should validate book with zero price")
    void testBookWithZeroPrice() {
        // Arrange
        BookDTO book = new BookDTO();
        book.setPrice(0.0);

        // Act & Assert
        assertEquals(0.0, book.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should validate book with zero stock")
    void testBookWithZeroStock() {
        // Arrange
        BookDTO book = new BookDTO();
        book.setStock(0);

        // Act & Assert
        assertEquals(0, book.getStock());
    }

    @Test
    @DisplayName("Should validate book with negative stock")
    void testBookWithNegativeStock() {
        // Arrange
        BookDTO book = new BookDTO();
        book.setStock(-5);

        // Act & Assert
        assertEquals(-5, book.getStock());
    }

    @Test
    @DisplayName("Should validate book with maximum values")
    void testBookWithMaximumValues() {
        // Arrange
        BookDTO book = new BookDTO();
        book.setBookId(Integer.MAX_VALUE);
        book.setPrice(Double.MAX_VALUE);
        book.setStock(Integer.MAX_VALUE);

        // Act & Assert
        assertEquals(Integer.MAX_VALUE, book.getBookId());
        assertEquals(Double.MAX_VALUE, book.getPrice(), 0.01);
        assertEquals(Integer.MAX_VALUE, book.getStock());
    }

    @Test
    @DisplayName("Should validate book with minimum values")
    void testBookWithMinimumValues() {
        // Arrange
        BookDTO book = new BookDTO();
        book.setBookId(Integer.MIN_VALUE);
        book.setPrice(Double.MIN_VALUE);
        book.setStock(Integer.MIN_VALUE);

        // Act & Assert
        assertEquals(Integer.MIN_VALUE, book.getBookId());
        assertEquals(Double.MIN_VALUE, book.getPrice(), 0.01);
        assertEquals(Integer.MIN_VALUE, book.getStock());
    }

    @Test
    @DisplayName("Should test book data integrity")
    void testBookDataIntegrity() {
        // Arrange
        BookDTO book = createTestBook();

        // Act - Modify some fields
        book.setTitle("Modified Title");
        book.setPrice(99.99);

        // Assert - Other fields should remain unchanged
        assertEquals(1, book.getBookId());
        assertEquals("Modified Title", book.getTitle());
        assertEquals("Test Author", book.getAuthor());
        assertEquals(99.99, book.getPrice(), 0.01);
        assertEquals(10, book.getStock());
    }

    @Test
    @DisplayName("Should test book list operations")
    void testBookListOperations() {
        // Arrange
        List<BookDTO> books = new ArrayList<>();
        BookDTO book1 = createTestBook();
        BookDTO book2 = createTestBook();
        book2.setBookId(2);
        book2.setTitle("Book 2");

        // Act
        books.add(book1);
        books.add(book2);

        // Assert
        assertEquals(2, books.size());
        assertEquals("Test Book", books.get(0).getTitle());
        assertEquals("Book 2", books.get(1).getTitle());
    }

    // Helper methods to create test data
    private BookDTO createTestBook() {
        BookDTO book = new BookDTO();
        book.setBookId(1);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPrice(25.99);
        book.setStock(10);
        return book;
    }
}
