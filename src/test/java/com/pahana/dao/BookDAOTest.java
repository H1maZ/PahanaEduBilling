package com.pahana.dao;

import com.pahana.dto.BookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for BookDAO
 * Tests all main database operations with mocked dependencies
 */
@DisplayName("BookDAO Unit Tests")
class BookDAOTest {

    @Mock
    private BookDAO bookDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should add book successfully")
    void testAddBook_Success() {
        // Arrange
        BookDTO book = createTestBook();
        
        // Act
        boolean result = bookDAO.addBook(book);
        
        // Assert
        assertTrue(result);
        
        // Verify book was added
        BookDTO retrievedBook = bookDAO.getBookById(book.getBookId());
        assertNotNull(retrievedBook);
        assertEquals(book.getTitle(), retrievedBook.getTitle());
        assertEquals(book.getAuthor(), retrievedBook.getAuthor());
        assertEquals(book.getPrice(), retrievedBook.getPrice(), 0.01);
        assertEquals(book.getStock(), retrievedBook.getStock());
    }

    @Test
    @DisplayName("Should get book by ID successfully")
    void testGetBookById_Success() {
        // Arrange
        BookDTO book = createTestBook();
        bookDAO.addBook(book);
        
        // Act
        BookDTO result = bookDAO.getBookById(book.getBookId());
        
        // Assert
        assertNotNull(result);
        assertEquals(book.getBookId(), result.getBookId());
        assertEquals(book.getTitle(), result.getTitle());
        assertEquals(book.getAuthor(), result.getAuthor());
        assertEquals(book.getPrice(), result.getPrice(), 0.01);
        assertEquals(book.getStock(), result.getStock());
    }

    @Test
    @DisplayName("Should return null when book not found")
    void testGetBookById_NotFound() {
        // Arrange
        int nonExistentId = 999;
        
        // Act
        BookDTO result = bookDAO.getBookById(nonExistentId);
        
        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("Should get all books successfully")
    void testGetAllBooks_Success() {
        // Arrange
        BookDTO book1 = createTestBook();
        BookDTO book2 = createTestBook();
        book2.setBookId(2);
        book2.setTitle("Test Book 2");
        
        bookDAO.addBook(book1);
        bookDAO.addBook(book2);
        
        // Act
        List<BookDTO> result = bookDAO.getAllBooks();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.size() >= 2);
        
        // Verify both books are in the list
        boolean foundBook1 = result.stream().anyMatch(b -> b.getBookId() == book1.getBookId());
        boolean foundBook2 = result.stream().anyMatch(b -> b.getBookId() == book2.getBookId());
        
        assertTrue(foundBook1);
        assertTrue(foundBook2);
    }

    @Test
    @DisplayName("Should update book successfully")
    void testUpdateBook_Success() {
        // Arrange
        BookDTO book = createTestBook();
        bookDAO.addBook(book);
        
        // Update book details
        book.setTitle("Updated Test Book");
        book.setAuthor("Updated Test Author");
        book.setPrice(29.99);
        book.setStock(15);
        
        // Act
        boolean result = bookDAO.updateBook(book);
        
        // Assert
        assertTrue(result);
        
        // Verify book was updated
        BookDTO updatedBook = bookDAO.getBookById(book.getBookId());
        assertNotNull(updatedBook);
        assertEquals("Updated Test Book", updatedBook.getTitle());
        assertEquals("Updated Test Author", updatedBook.getAuthor());
        assertEquals(29.99, updatedBook.getPrice(), 0.01);
        assertEquals(15, updatedBook.getStock());
    }

    @Test
    @DisplayName("Should return false when updating non-existent book")
    void testUpdateBook_NotFound() {
        // Arrange
        BookDTO book = createTestBook();
        book.setBookId(999); // Non-existent ID
        
        // Act
        boolean result = bookDAO.updateBook(book);
        
        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Should delete book successfully")
    void testDeleteBook_Success() {
        // Arrange
        BookDTO book = createTestBook();
        bookDAO.addBook(book);
        
        // Act
        boolean result = bookDAO.deleteBook(book.getBookId());
        
        // Assert
        assertTrue(result);
        
        // Verify book was deleted
        BookDTO deletedBook = bookDAO.getBookById(book.getBookId());
        assertNull(deletedBook);
    }

    @Test
    @DisplayName("Should return false when deleting non-existent book")
    void testDeleteBook_NotFound() {
        // Arrange
        int nonExistentId = 999;
        
        // Act
        boolean result = bookDAO.deleteBook(nonExistentId);
        
        // Assert
        assertFalse(result);
    }



    @Test
    @DisplayName("Should handle book with image data")
    void testBookWithImageData() {
        // Arrange
        BookDTO book = createTestBook();
        byte[] imageData = "test image data".getBytes();
        book.setImageData(imageData);
        book.setImageType("image/jpeg");
        
        // Act
        boolean result = bookDAO.addBook(book);
        
        // Assert
        assertTrue(result);
        
        // Verify book with image was added
        BookDTO retrievedBook = bookDAO.getBookById(book.getBookId());
        assertNotNull(retrievedBook);
        assertNotNull(retrievedBook.getImageData());
        assertEquals("image/jpeg", retrievedBook.getImageType());
        assertArrayEquals(imageData, retrievedBook.getImageData());
    }

    @Test
    @DisplayName("Should handle large image data")
    void testBookWithLargeImageData() {
        // Arrange
        BookDTO book = createTestBook();
        byte[] largeImageData = new byte[1024 * 1024]; // 1MB
        for (int i = 0; i < largeImageData.length; i++) {
            largeImageData[i] = (byte) (i % 256);
        }
        book.setImageData(largeImageData);
        book.setImageType("image/png");
        
        // Act
        boolean result = bookDAO.addBook(book);
        
        // Assert
        assertTrue(result);
        
        // Verify large image was stored
        BookDTO retrievedBook = bookDAO.getBookById(book.getBookId());
        assertNotNull(retrievedBook);
        assertNotNull(retrievedBook.getImageData());
        assertEquals(largeImageData.length, retrievedBook.getImageData().length);
    }

    @Test
    @DisplayName("Should handle books with special characters in title")
    void testBookWithSpecialCharacters() {
        // Arrange
        BookDTO book = createTestBook();
        book.setTitle("Test Book with Special Characters: @#$%^&*()");
        book.setAuthor("Author with Special Characters: @#$%^&*()");
        
        // Act
        boolean result = bookDAO.addBook(book);
        
        // Assert
        assertTrue(result);
        
        // Verify special characters were preserved
        BookDTO retrievedBook = bookDAO.getBookById(book.getBookId());
        assertNotNull(retrievedBook);
        assertEquals("Test Book with Special Characters: @#$%^&*()", retrievedBook.getTitle());
        assertEquals("Author with Special Characters: @#$%^&*()", retrievedBook.getAuthor());
    }

    @Test
    @DisplayName("Should handle books with very long title and author")
    void testBookWithLongText() {
        // Arrange
        BookDTO book = createTestBook();
        String longTitle = "A".repeat(500); // 500 character title
        String longAuthor = "B".repeat(500); // 500 character author
        book.setTitle(longTitle);
        book.setAuthor(longAuthor);
        
        // Act
        boolean result = bookDAO.addBook(book);
        
        // Assert
        assertTrue(result);
        
        // Verify long text was preserved
        BookDTO retrievedBook = bookDAO.getBookById(book.getBookId());
        assertNotNull(retrievedBook);
        assertEquals(longTitle, retrievedBook.getTitle());
        assertEquals(longAuthor, retrievedBook.getAuthor());
    }

    @Test
    @DisplayName("Should handle books with decimal prices")
    void testBookWithDecimalPrice() {
        // Arrange
        BookDTO book = createTestBook();
        book.setPrice(19.99);
        
        // Act
        boolean result = bookDAO.addBook(book);
        
        // Assert
        assertTrue(result);
        
        // Verify decimal price was preserved
        BookDTO retrievedBook = bookDAO.getBookById(book.getBookId());
        assertNotNull(retrievedBook);
        assertEquals(19.99, retrievedBook.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should handle books with zero price")
    void testBookWithZeroPrice() {
        // Arrange
        BookDTO book = createTestBook();
        book.setPrice(0.0);
        
        // Act
        boolean result = bookDAO.addBook(book);
        
        // Assert
        assertTrue(result);
        
        // Verify zero price was preserved
        BookDTO retrievedBook = bookDAO.getBookById(book.getBookId());
        assertNotNull(retrievedBook);
        assertEquals(0.0, retrievedBook.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should handle books with zero stock")
    void testBookWithZeroStock() {
        // Arrange
        BookDTO book = createTestBook();
        book.setStock(0);
        
        // Act
        boolean result = bookDAO.addBook(book);
        
        // Assert
        assertTrue(result);
        
        // Verify zero stock was preserved
        BookDTO retrievedBook = bookDAO.getBookById(book.getBookId());
        assertNotNull(retrievedBook);
        assertEquals(0, retrievedBook.getStock());
    }

    // Helper methods
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
