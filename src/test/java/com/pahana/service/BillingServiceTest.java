package com.pahana.service;

import com.pahana.dao.BookDAO;
import com.pahana.dao.InvoiceDAO;
import com.pahana.dto.BookDTO;
import com.pahana.dto.CartItemDTO;
import com.pahana.dto.CustomerDTO;
import com.pahana.dto.InvoiceDTO;
import com.pahana.dto.InvoiceItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Comprehensive JUnit tests for BillingService
 * Tests all main functions including invoice creation, stock updates, and error handling
 */
@DisplayName("BillingService Tests")
class BillingServiceTest {

    @Mock
    private BookDAO bookDAO;
    
    @Mock
    private InvoiceDAO invoiceDAO;
    
    private BillingService billingService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        billingService = new BillingService();
    }
    
    @Test
    @DisplayName("Should create invoice successfully with valid cart items")
    void testCreateInvoice_Success() {
        // Arrange
        CustomerDTO customer = createTestCustomer();
        List<CartItemDTO> cartItems = createTestCartItems();
        
        when(invoiceDAO.saveInvoice(any(InvoiceDTO.class))).thenReturn(true);
        when(bookDAO.getBookById(anyInt())).thenReturn(createTestBook());
        when(bookDAO.updateBook(any(BookDTO.class))).thenReturn(true);
        
        // Act
        InvoiceDTO result = billingService.createInvoice(customer, cartItems);
        
        // Assert
        assertNotNull(result);
        assertEquals(customer.getAccountNumber(), result.getCustomerId());
        assertEquals(customer.getName(), result.getCustomerName());
        assertEquals("PAID", result.getPaymentStatus());
        assertTrue(result.getTotalAmount() > 0);
        assertNotNull(result.getInvoiceId());
        assertTrue(result.getInvoiceId().startsWith("INV-"));
        assertEquals(2, result.getItems().size());
        
        // Verify interactions
        verify(invoiceDAO, times(1)).saveInvoice(any(InvoiceDTO.class));
        verify(bookDAO, times(2)).getBookById(anyInt());
        verify(bookDAO, times(2)).updateBook(any(BookDTO.class));
    }
    
    @Test
    @DisplayName("Should return null when cart items is null")
    void testCreateInvoice_NullCartItems() {
        // Arrange
        CustomerDTO customer = createTestCustomer();
        
        // Act
        InvoiceDTO result = billingService.createInvoice(customer, null);
        
        // Assert
        assertNull(result);
        verify(invoiceDAO, never()).saveInvoice(any(InvoiceDTO.class));
    }
    
    @Test
    @DisplayName("Should return null when cart items is empty")
    void testCreateInvoice_EmptyCartItems() {
        // Arrange
        CustomerDTO customer = createTestCustomer();
        List<CartItemDTO> cartItems = new ArrayList<>();
        
        // Act
        InvoiceDTO result = billingService.createInvoice(customer, cartItems);
        
        // Assert
        assertNull(result);
        verify(invoiceDAO, never()).saveInvoice(any(InvoiceDTO.class));
    }
    
    @Test
    @DisplayName("Should return null when invoice save fails")
    void testCreateInvoice_SaveFailure() {
        // Arrange
        CustomerDTO customer = createTestCustomer();
        List<CartItemDTO> cartItems = createTestCartItems();
        
        when(invoiceDAO.saveInvoice(any(InvoiceDTO.class))).thenReturn(false);
        
        // Act
        InvoiceDTO result = billingService.createInvoice(customer, cartItems);
        
        // Assert
        assertNull(result);
        verify(invoiceDAO, times(1)).saveInvoice(any(InvoiceDTO.class));
        verify(bookDAO, never()).updateBook(any(BookDTO.class));
    }
    
    @Test
    @DisplayName("Should calculate total amount correctly")
    void testCalculateTotal() {
        // Arrange
        List<CartItemDTO> cartItems = createTestCartItems();
        // Book 1: $25.99 * 2 = $51.98
        // Book 2: $19.99 * 1 = $19.99
        // Total: $71.97
        
        // Act
        InvoiceDTO result = billingService.createInvoice(createTestCustomer(), cartItems);
        
        // Assert
        assertNotNull(result);
        assertEquals(71.97, result.getTotalAmount(), 0.01);
    }
    
    @Test
    @DisplayName("Should generate unique invoice ID")
    void testGenerateInvoiceId() {
        // Arrange
        CustomerDTO customer = createTestCustomer();
        List<CartItemDTO> cartItems = createTestCartItems();
        
        when(invoiceDAO.saveInvoice(any(InvoiceDTO.class))).thenReturn(true);
        when(bookDAO.getBookById(anyInt())).thenReturn(createTestBook());
        when(bookDAO.updateBook(any(BookDTO.class))).thenReturn(true);
        
        // Act
        InvoiceDTO result1 = billingService.createInvoice(customer, cartItems);
        InvoiceDTO result2 = billingService.createInvoice(customer, cartItems);
        
        // Assert
        assertNotNull(result1.getInvoiceId());
        assertNotNull(result2.getInvoiceId());
        assertNotEquals(result1.getInvoiceId(), result2.getInvoiceId());
        assertTrue(result1.getInvoiceId().startsWith("INV-"));
        assertTrue(result2.getInvoiceId().startsWith("INV-"));
    }
    
    @Test
    @DisplayName("Should create invoice items correctly")
    void testCreateInvoiceItems() {
        // Arrange
        CustomerDTO customer = createTestCustomer();
        List<CartItemDTO> cartItems = createTestCartItems();
        
        when(invoiceDAO.saveInvoice(any(InvoiceDTO.class))).thenReturn(true);
        when(bookDAO.getBookById(anyInt())).thenReturn(createTestBook());
        when(bookDAO.updateBook(any(BookDTO.class))).thenReturn(true);
        
        // Act
        InvoiceDTO result = billingService.createInvoice(customer, cartItems);
        
        // Assert
        assertNotNull(result);
        assertEquals(2, result.getItems().size());
        
        InvoiceItemDTO item1 = result.getItems().get(0);
        assertEquals(1, item1.getBookId());
        assertEquals("Test Book 1", item1.getBookTitle());
        assertEquals(2, item1.getQuantity());
        assertEquals(25.99, item1.getUnitPrice(), 0.01);
        assertEquals(51.98, item1.getTotalPrice(), 0.01);
        
        InvoiceItemDTO item2 = result.getItems().get(1);
        assertEquals(2, item2.getBookId());
        assertEquals("Test Book 2", item2.getBookTitle());
        assertEquals(1, item2.getQuantity());
        assertEquals(19.99, item2.getUnitPrice(), 0.01);
        assertEquals(19.99, item2.getTotalPrice(), 0.01);
    }
    
    @Test
    @DisplayName("Should update stock levels correctly")
    void testUpdateStockLevels() {
        // Arrange
        CustomerDTO customer = createTestCustomer();
        List<CartItemDTO> cartItems = createTestCartItems();
        BookDTO book = createTestBook();
        book.setStock(10);
        
        when(invoiceDAO.saveInvoice(any(InvoiceDTO.class))).thenReturn(true);
        when(bookDAO.getBookById(1)).thenReturn(book);
        when(bookDAO.getBookById(2)).thenReturn(book);
        when(bookDAO.updateBook(any(BookDTO.class))).thenReturn(true);
        
        // Act
        InvoiceDTO result = billingService.createInvoice(customer, cartItems);
        
        // Assert
        assertNotNull(result);
        verify(bookDAO, times(2)).updateBook(any(BookDTO.class));
        
        // Verify stock was reduced correctly
        verify(bookDAO).updateBook(argThat(updatedBook -> 
            updatedBook.getStock() == 8 || updatedBook.getStock() == 9
        ));
    }
    
    @Test
    @DisplayName("Should handle book not found during stock update")
    void testUpdateStockLevels_BookNotFound() {
        // Arrange
        CustomerDTO customer = createTestCustomer();
        List<CartItemDTO> cartItems = createTestCartItems();
        
        when(invoiceDAO.saveInvoice(any(InvoiceDTO.class))).thenReturn(true);
        when(bookDAO.getBookById(anyInt())).thenReturn(null);
        
        // Act
        InvoiceDTO result = billingService.createInvoice(customer, cartItems);
        
        // Assert
        assertNotNull(result);
        verify(bookDAO, times(2)).getBookById(anyInt());
        verify(bookDAO, never()).updateBook(any(BookDTO.class));
    }
    
    @Test
    @DisplayName("Should handle insufficient stock")
    void testUpdateStockLevels_InsufficientStock() {
        // Arrange
        CustomerDTO customer = createTestCustomer();
        List<CartItemDTO> cartItems = createTestCartItems();
        BookDTO book = createTestBook();
        book.setStock(1); // Insufficient for quantity 2
        
        when(invoiceDAO.saveInvoice(any(InvoiceDTO.class))).thenReturn(true);
        when(bookDAO.getBookById(anyInt())).thenReturn(book);
        
        // Act
        InvoiceDTO result = billingService.createInvoice(customer, cartItems);
        
        // Assert
        assertNotNull(result);
        verify(bookDAO, times(2)).getBookById(anyInt());
        verify(bookDAO, never()).updateBook(any(BookDTO.class));
    }
    
    @Test
    @DisplayName("Should get all invoices")
    void testGetAllInvoices() {
        // Arrange
        List<InvoiceDTO> expectedInvoices = createTestInvoices();
        when(invoiceDAO.getAllInvoices()).thenReturn(expectedInvoices);
        
        // Act
        List<InvoiceDTO> result = billingService.getAllInvoices();
        
        // Assert
        assertNotNull(result);
        assertEquals(expectedInvoices.size(), result.size());
        verify(invoiceDAO, times(1)).getAllInvoices();
    }
    
    @Test
    @DisplayName("Should get invoice by ID")
    void testGetInvoiceById() {
        // Arrange
        String invoiceId = "INV-123456";
        InvoiceDTO expectedInvoice = createTestInvoice();
        when(invoiceDAO.getInvoiceById(invoiceId)).thenReturn(expectedInvoice);
        
        // Act
        InvoiceDTO result = billingService.getInvoiceById(invoiceId);
        
        // Assert
        assertNotNull(result);
        assertEquals(expectedInvoice, result);
        verify(invoiceDAO, times(1)).getInvoiceById(invoiceId);
    }
    
    @Test
    @DisplayName("Should get invoices by customer")
    void testGetInvoicesByCustomer() {
        // Arrange
        String customerId = "CUST001";
        List<InvoiceDTO> expectedInvoices = createTestInvoices();
        when(invoiceDAO.getInvoicesByCustomer(customerId)).thenReturn(expectedInvoices);
        
        // Act
        List<InvoiceDTO> result = billingService.getInvoicesByCustomer(customerId);
        
        // Assert
        assertNotNull(result);
        assertEquals(expectedInvoices.size(), result.size());
        verify(invoiceDAO, times(1)).getInvoicesByCustomer(customerId);
    }
    
    // Helper methods to create test data
    private CustomerDTO createTestCustomer() {
        return new CustomerDTO("CUST001", "John Doe", "123 Main St", "555-1234", 100);
    }
    
    private List<CartItemDTO> createTestCartItems() {
        List<CartItemDTO> cartItems = new ArrayList<>();
        
        CartItemDTO item1 = new CartItemDTO(1, "Test Book 1", 25.99, 2);
        cartItems.add(item1);
        
        CartItemDTO item2 = new CartItemDTO(2, "Test Book 2", 19.99, 1);
        cartItems.add(item2);
        
        return cartItems;
    }
    
    private BookDTO createTestBook() {
        BookDTO book = new BookDTO();
        book.setBookId(1);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPrice(25.99);
        book.setStock(10);
        return book;
    }
    
    private InvoiceDTO createTestInvoice() {
        InvoiceDTO invoice = new InvoiceDTO();
        invoice.setInvoiceId("INV-123456");
        invoice.setCustomerId("CUST001");
        invoice.setCustomerName("John Doe");
        invoice.setInvoiceDate(LocalDateTime.now());
        invoice.setTotalAmount(71.97);
        invoice.setPaymentStatus("PAID");
        invoice.setItems(createTestInvoiceItems());
        return invoice;
    }
    
    private List<InvoiceItemDTO> createTestInvoiceItems() {
        List<InvoiceItemDTO> items = new ArrayList<>();
        
        InvoiceItemDTO item1 = new InvoiceItemDTO();
        item1.setBookId(1);
        item1.setBookTitle("Test Book 1");
        item1.setQuantity(2);
        item1.setUnitPrice(25.99);
        item1.setTotalPrice(51.98);
        items.add(item1);
        
        InvoiceItemDTO item2 = new InvoiceItemDTO();
        item2.setBookId(2);
        item2.setBookTitle("Test Book 2");
        item2.setQuantity(1);
        item2.setUnitPrice(19.99);
        item2.setTotalPrice(19.99);
        items.add(item2);
        
        return items;
    }
    
    private List<InvoiceDTO> createTestInvoices() {
        List<InvoiceDTO> invoices = new ArrayList<>();
        invoices.add(createTestInvoice());
        return invoices;
    }
}
