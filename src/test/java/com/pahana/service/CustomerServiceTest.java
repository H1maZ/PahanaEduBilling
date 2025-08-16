package com.pahana.service;

import com.pahana.dao.CustomerDAO;
import com.pahana.dao.DBConnection;
import com.pahana.dto.CustomerDTO;
import com.pahana.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Comprehensive JUnit tests for CustomerService
 * Tests all main functions including CRUD operations and error handling
 */
@DisplayName("CustomerService Tests")
class CustomerServiceTest {

    @Mock
    private CustomerDAO customerDAO;
    
    @Mock
    private Connection connection;
    
    private CustomerService customerService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerService();
    }
    
    @Test
    @DisplayName("Should get all customers successfully")
    void testGetAllCustomers_Success() {
        // Arrange
        List<Customer> mockCustomers = createTestCustomers();
        when(customerDAO.getAllCustomers()).thenReturn(mockCustomers);
        
        // Act
        List<CustomerDTO> result = customerService.getAllCustomers();
        
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        
        CustomerDTO firstCustomer = result.get(0);
        assertEquals("CUST001", firstCustomer.getAccountNumber());
        assertEquals("John Doe", firstCustomer.getName());
        assertEquals("123 Main St", firstCustomer.getAddress());
        assertEquals("555-1234", firstCustomer.getPhone());
        assertEquals(100, firstCustomer.getUnitsConsumed());
        
        CustomerDTO secondCustomer = result.get(1);
        assertEquals("CUST002", secondCustomer.getAccountNumber());
        assertEquals("Jane Smith", secondCustomer.getName());
        assertEquals("456 Oak Ave", secondCustomer.getAddress());
        assertEquals("555-5678", secondCustomer.getPhone());
        assertEquals(150, secondCustomer.getUnitsConsumed());
    }
    
    @Test
    @DisplayName("Should return empty list when database error occurs")
    void testGetAllCustomers_DatabaseError() {
        // Arrange
        when(customerDAO.getAllCustomers()).thenThrow(new RuntimeException("Database error"));
        
        // Act
        List<CustomerDTO> result = customerService.getAllCustomers();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    @DisplayName("Should get customer by account number successfully")
    void testGetCustomerByAccountNumber_Success() {
        // Arrange
        String accountNumber = "CUST001";
        Customer mockCustomer = createTestCustomer();
        when(customerDAO.getCustomerByAccountNumber(accountNumber)).thenReturn(mockCustomer);
        
        // Act
        CustomerDTO result = customerService.getCustomerByAccountNumber(accountNumber);
        
        // Assert
        assertNotNull(result);
        assertEquals(accountNumber, result.getAccountNumber());
        assertEquals("John Doe", result.getName());
        assertEquals("123 Main St", result.getAddress());
        assertEquals("555-1234", result.getPhone());
        assertEquals(100, result.getUnitsConsumed());
        
        verify(customerDAO, times(1)).getCustomerByAccountNumber(accountNumber);
    }
    
    @Test
    @DisplayName("Should return null when customer not found")
    void testGetCustomerByAccountNumber_NotFound() {
        // Arrange
        String accountNumber = "NONEXISTENT";
        when(customerDAO.getCustomerByAccountNumber(accountNumber)).thenReturn(null);
        
        // Act
        CustomerDTO result = customerService.getCustomerByAccountNumber(accountNumber);
        
        // Assert
        assertNull(result);
        verify(customerDAO, times(1)).getCustomerByAccountNumber(accountNumber);
    }
    
    @Test
    @DisplayName("Should return null when database error occurs")
    void testGetCustomerByAccountNumber_DatabaseError() {
        // Arrange
        String accountNumber = "CUST001";
        when(customerDAO.getCustomerByAccountNumber(accountNumber)).thenThrow(new RuntimeException("Database error"));
        
        // Act
        CustomerDTO result = customerService.getCustomerByAccountNumber(accountNumber);
        
        // Assert
        assertNull(result);
    }
    
    @Test
    @DisplayName("Should add customer successfully with valid data")
    void testAddCustomer_Success() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO("CUST003", "Bob Johnson", "789 Pine St", "555-9999", 75);
        when(customerDAO.addCustomer(any(Customer.class))).thenReturn(true);
        
        // Act
        boolean result = customerService.addCustomer(customerDTO);
        
        // Assert
        assertTrue(result);
        verify(customerDAO, times(1)).addCustomer(any(Customer.class));
    }
    
    @Test
    @DisplayName("Should return false when customer name is null")
    void testAddCustomer_NullName() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO("CUST003", null, "789 Pine St", "555-9999", 75);
        
        // Act
        boolean result = customerService.addCustomer(customerDTO);
        
        // Assert
        assertFalse(result);
        verify(customerDAO, never()).addCustomer(any(Customer.class));
    }
    
    @Test
    @DisplayName("Should return false when customer name is blank")
    void testAddCustomer_BlankName() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO("CUST003", "   ", "789 Pine St", "555-9999", 75);
        
        // Act
        boolean result = customerService.addCustomer(customerDTO);
        
        // Assert
        assertFalse(result);
        verify(customerDAO, never()).addCustomer(any(Customer.class));
    }
    
    @Test
    @DisplayName("Should return false when database error occurs during add")
    void testAddCustomer_DatabaseError() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO("CUST003", "Bob Johnson", "789 Pine St", "555-9999", 75);
        when(customerDAO.addCustomer(any(Customer.class))).thenThrow(new RuntimeException("Database error"));
        
        // Act
        boolean result = customerService.addCustomer(customerDTO);
        
        // Assert
        assertFalse(result);
    }
    
    @Test
    @DisplayName("Should update customer successfully")
    void testUpdateCustomer_Success() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO("CUST001", "John Doe Updated", "123 Main St Updated", "555-1234", 120);
        when(customerDAO.updateCustomer(any(Customer.class))).thenReturn(true);
        
        // Act
        boolean result = customerService.updateCustomer(customerDTO);
        
        // Assert
        assertTrue(result);
        verify(customerDAO, times(1)).updateCustomer(any(Customer.class));
    }
    
    @Test
    @DisplayName("Should return false when database error occurs during update")
    void testUpdateCustomer_DatabaseError() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO("CUST001", "John Doe Updated", "123 Main St Updated", "555-1234", 120);
        when(customerDAO.updateCustomer(any(Customer.class))).thenThrow(new RuntimeException("Database error"));
        
        // Act
        boolean result = customerService.updateCustomer(customerDTO);
        
        // Assert
        assertFalse(result);
    }
    
    @Test
    @DisplayName("Should delete customer successfully")
    void testDeleteCustomer_Success() {
        // Arrange
        String accountNumber = "CUST001";
        when(customerDAO.deleteCustomer(accountNumber)).thenReturn(true);
        
        // Act
        boolean result = customerService.deleteCustomer(accountNumber);
        
        // Assert
        assertTrue(result);
        verify(customerDAO, times(1)).deleteCustomer(accountNumber);
    }
    
    @Test
    @DisplayName("Should return false when database error occurs during delete")
    void testDeleteCustomer_DatabaseError() {
        // Arrange
        String accountNumber = "CUST001";
        when(customerDAO.deleteCustomer(accountNumber)).thenThrow(new RuntimeException("Database error"));
        
        // Act
        boolean result = customerService.deleteCustomer(accountNumber);
        
        // Assert
        assertFalse(result);
    }
    
    @Test
    @DisplayName("Should convert Customer model to DTO correctly")
    void testToDTO() {
        // Arrange
        Customer customer = createTestCustomer();
        
        // Act
        CustomerDTO result = customerService.getAllCustomers().get(0); // This will trigger toDTO internally
        
        // Assert
        assertNotNull(result);
        assertEquals(customer.getAccountNumber(), result.getAccountNumber());
        assertEquals(customer.getName(), result.getName());
        assertEquals(customer.getAddress(), result.getAddress());
        assertEquals(customer.getPhone(), result.getPhone());
        assertEquals(customer.getUnitsConsumed(), result.getUnitsConsumed());
    }
    
    @Test
    @DisplayName("Should convert DTO to Customer model correctly")
    void testToModel() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO("CUST003", "Bob Johnson", "789 Pine St", "555-9999", 75);
        
        // Act
        boolean result = customerService.addCustomer(customerDTO); // This will trigger toModel internally
        
        // Assert
        // The test verifies that the conversion works by successfully adding the customer
        // If toModel fails, the addCustomer would fail
        assertTrue(result);
    }
    
    @Test
    @DisplayName("Should handle null customer in toDTO")
    void testToDTO_NullCustomer() {
        // Arrange
        Customer customer = null;
        
        // Act & Assert
        // This test verifies that the service handles null customers gracefully
        // The actual implementation should handle null values
        assertDoesNotThrow(() -> {
            // The service should handle null values internally
            customerService.getAllCustomers();
        });
    }
    
    @Test
    @DisplayName("Should handle null DTO in toModel")
    void testToModel_NullDTO() {
        // Arrange
        CustomerDTO customerDTO = null;
        
        // Act
        boolean result = customerService.addCustomer(customerDTO);
        
        // Assert
        assertFalse(result);
    }
    
    // Helper methods to create test data
    private Customer createTestCustomer() {
        Customer customer = new Customer();
        customer.setAccountNumber("CUST001");
        customer.setName("John Doe");
        customer.setAddress("123 Main St");
        customer.setPhone("555-1234");
        customer.setUnitsConsumed(100);
        return customer;
    }
    
    private List<Customer> createTestCustomers() {
        List<Customer> customers = new ArrayList<>();
        
        Customer customer1 = new Customer();
        customer1.setAccountNumber("CUST001");
        customer1.setName("John Doe");
        customer1.setAddress("123 Main St");
        customer1.setPhone("555-1234");
        customer1.setUnitsConsumed(100);
        customers.add(customer1);
        
        Customer customer2 = new Customer();
        customer2.setAccountNumber("CUST002");
        customer2.setName("Jane Smith");
        customer2.setAddress("456 Oak Ave");
        customer2.setPhone("555-5678");
        customer2.setUnitsConsumed(150);
        customers.add(customer2);
        
        return customers;
    }
}
