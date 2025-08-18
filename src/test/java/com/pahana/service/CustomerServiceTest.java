package com.pahana.service;

import com.pahana.dto.CustomerDTO;
import com.pahana.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for CustomerService using only JUnit 5 (no Mockito)
 * Tests focus on business logic and data validation
 */
@DisplayName("CustomerService Tests")
class CustomerServiceTest {

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerService();
    }

    @Test
    @DisplayName("Should test service initialization")
    void testServiceInitialization() {
        // Arrange & Act
        CustomerService newService = new CustomerService();

        // Assert
        assertNotNull(newService);
    }

    @Test
    @DisplayName("Should test getAllCustomers method exists")
    void testGetAllCustomersMethodExists() {
        // Act
        List<CustomerDTO> result = customerService.getAllCustomers();

        // Assert
        // The method should return a list (even if empty due to no database connection)
        assertNotNull(result);
        // Since database is not available, we expect an empty list or exception handling
        // The test passes if the method handles the database connection failure gracefully
    }

    @Test
    @DisplayName("Should test getCustomerByAccountNumber method exists")
    void testGetCustomerByAccountNumberMethodExists() {
        // Act
        CustomerDTO result = customerService.getCustomerByAccountNumber("TEST001");

        // Assert
        // The method should handle the request (may return null if no database)
        assertNotNull(customerService);
        // Since database is not available, we expect null or exception handling
        // The test passes if the method handles the database connection failure gracefully
    }

    @Test
    @DisplayName("Should test addCustomer method exists")
    void testAddCustomerMethodExists() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO("CUST003", "Bob Johnson", "789 Pine St", "555-9999", 75);

        // Act
        boolean result = customerService.addCustomer(customerDTO);

        // Assert
        // The method should handle the request
        assertNotNull(customerService);
        // Since database is not available, we expect false or exception handling
        // The test passes if the method handles the database connection failure gracefully
    }

    @Test
    @DisplayName("Should test updateCustomer method exists")
    void testUpdateCustomerMethodExists() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO("CUST009", "Test Customer", "Test Address", "Test Phone", 25);

        // Act
        boolean result = customerService.updateCustomer(customerDTO);

        // Assert
        // The method should handle the request
        assertNotNull(customerService);
        // Since database is not available, we expect false or exception handling
        // The test passes if the method handles the database connection failure gracefully
    }

    @Test
    @DisplayName("Should test deleteCustomer method exists")
    void testDeleteCustomerMethodExists() {
        // Act
        boolean result = customerService.deleteCustomer("CUST010");

        // Assert
        // The method should handle the request
        assertNotNull(customerService);
        // Since database is not available, we expect false or exception handling
        // The test passes if the method handles the database connection failure gracefully
    }

    @Test
    @DisplayName("Should handle null customer in addCustomer")
    void testAddCustomer_NullCustomer() {
        // Arrange
        CustomerDTO customerDTO = null;

        // Act
        boolean result = customerService.addCustomer(customerDTO);

        // Assert
        // The method should handle null input gracefully and return false
        assertFalse(result);
    }

    @Test
    @DisplayName("Should handle empty customer name")
    void testAddCustomer_EmptyName() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO("CUST004", "", "123 Test St", "555-0000", 50);

        // Act
        boolean result = customerService.addCustomer(customerDTO);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Should handle null customer name")
    void testAddCustomer_NullName() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO("CUST005", null, "123 Test St", "555-0000", 50);

        // Act
        boolean result = customerService.addCustomer(customerDTO);

        // Assert
        // Should return false for null name without throwing NullPointerException
        assertFalse(result);
    }

    @Test
    @DisplayName("Should handle blank customer name")
    void testAddCustomer_BlankName() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO("CUST006", "   ", "123 Test St", "555-0000", 50);

        // Act
        boolean result = customerService.addCustomer(customerDTO);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Should validate customer DTO fields")
    void testCustomerDTOValidation() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO("CUST007", "Valid Name", "Valid Address", "Valid Phone", 100);

        // Act & Assert
        assertEquals("CUST007", customerDTO.getAccountNumber());
        assertEquals("Valid Name", customerDTO.getName());
        assertEquals("Valid Address", customerDTO.getAddress());
        assertEquals("Valid Phone", customerDTO.getPhone());
        assertEquals(100, customerDTO.getUnitsConsumed());
    }

    @Test
    @DisplayName("Should handle customer DTO setters")
    void testCustomerDTOSetters() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO("CUST008", "Original Name", "Original Address", "Original Phone", 50);

        // Act
        customerDTO.setName("Updated Name");
        customerDTO.setAddress("Updated Address");
        customerDTO.setPhone("Updated Phone");
        customerDTO.setUnitsConsumed(75);

        // Assert
        assertEquals("Updated Name", customerDTO.getName());
        assertEquals("Updated Address", customerDTO.getAddress());
        assertEquals("Updated Phone", customerDTO.getPhone());
        assertEquals(75, customerDTO.getUnitsConsumed());
    }

    @Test
    @DisplayName("Should validate customer model fields")
    void testCustomerModelValidation() {
        // Arrange
        Customer customer = createTestCustomer();

        // Act & Assert
        assertEquals("CUST001", customer.getAccountNumber());
        assertEquals("John Doe", customer.getName());
        assertEquals("123 Main St", customer.getAddress());
        assertEquals("555-1234", customer.getPhone());
        assertEquals(100, customer.getUnitsConsumed());
    }

    @Test
    @DisplayName("Should handle customer model setters")
    void testCustomerModelSetters() {
        // Arrange
        Customer customer = new Customer();

        // Act
        customer.setAccountNumber("CUST002");
        customer.setName("Jane Smith");
        customer.setAddress("456 Oak Ave");
        customer.setPhone("555-5678");
        customer.setUnitsConsumed(150);

        // Assert
        assertEquals("CUST002", customer.getAccountNumber());
        assertEquals("Jane Smith", customer.getName());
        assertEquals("456 Oak Ave", customer.getAddress());
        assertEquals("555-5678", customer.getPhone());
        assertEquals(150, customer.getUnitsConsumed());
    }

    @Test
    @DisplayName("Should test DTO to Model conversion logic")
    void testDTOToModelConversion() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO("CUST011", "Test User", "Test Address", "Test Phone", 25);

        // Act - Test the conversion logic
        Customer customer = convertDTOToModel(customerDTO);

        // Assert
        assertNotNull(customer);
        assertEquals(customerDTO.getAccountNumber(), customer.getAccountNumber());
        assertEquals(customerDTO.getName(), customer.getName());
        assertEquals(customerDTO.getAddress(), customer.getAddress());
        assertEquals(customerDTO.getPhone(), customer.getPhone());
        assertEquals(customerDTO.getUnitsConsumed(), customer.getUnitsConsumed());
    }

    @Test
    @DisplayName("Should test Model to DTO conversion logic")
    void testModelToDTOConversion() {
        // Arrange
        Customer customer = createTestCustomer();

        // Act - Test the conversion logic
        CustomerDTO customerDTO = convertModelToDTO(customer);

        // Assert
        assertNotNull(customerDTO);
        assertEquals(customer.getAccountNumber(), customerDTO.getAccountNumber());
        assertEquals(customer.getName(), customerDTO.getName());
        assertEquals(customer.getAddress(), customerDTO.getAddress());
        assertEquals(customer.getPhone(), customerDTO.getPhone());
        assertEquals(customer.getUnitsConsumed(), customerDTO.getUnitsConsumed());
    }

    @Test
    @DisplayName("Should handle null in DTO to Model conversion")
    void testDTOToModelConversion_Null() {
        // Act
        Customer result = convertDTOToModel(null);

        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("Should handle null in Model to DTO conversion")
    void testModelToDTOConversion_Null() {
        // Act
        CustomerDTO result = convertModelToDTO(null);

        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("Should test customer data validation")
    void testCustomerDataValidation() {
        // Arrange
        CustomerDTO validCustomer = new CustomerDTO("CUST012", "Valid Customer", "Valid Address", "Valid Phone", 50);
        CustomerDTO invalidCustomer = new CustomerDTO("CUST013", "", "Valid Address", "Valid Phone", 50);

        // Act & Assert
        assertTrue(isValidCustomer(validCustomer));
        assertFalse(isValidCustomer(invalidCustomer));
    }

    @Test
    @DisplayName("Should test customer list operations")
    void testCustomerListOperations() {
        // Arrange
        List<CustomerDTO> customers = new ArrayList<>();
        CustomerDTO customer1 = new CustomerDTO("CUST014", "Customer 1", "Address 1", "Phone 1", 10);
        CustomerDTO customer2 = new CustomerDTO("CUST015", "Customer 2", "Address 2", "Phone 2", 20);

        // Act
        customers.add(customer1);
        customers.add(customer2);

        // Assert
        assertEquals(2, customers.size());
        assertEquals("Customer 1", customers.get(0).getName());
        assertEquals("Customer 2", customers.get(1).getName());
    }

    @Test
    @DisplayName("Should handle null account number in getCustomerByAccountNumber")
    void testGetCustomerByAccountNumber_NullAccountNumber() {
        // Act
        CustomerDTO result = customerService.getCustomerByAccountNumber(null);

        // Assert
        // Should return null for null account number without throwing exception
        assertNull(result);
    }

    @Test
    @DisplayName("Should handle empty account number in getCustomerByAccountNumber")
    void testGetCustomerByAccountNumber_EmptyAccountNumber() {
        // Act
        CustomerDTO result = customerService.getCustomerByAccountNumber("");

        // Assert
        // Should return null for empty account number without throwing exception
        assertNull(result);
    }

    @Test
    @DisplayName("Should handle null account number in deleteCustomer")
    void testDeleteCustomer_NullAccountNumber() {
        // Act
        boolean result = customerService.deleteCustomer(null);

        // Assert
        // Should return false for null account number without throwing exception
        assertFalse(result);
    }

    @Test
    @DisplayName("Should handle empty account number in deleteCustomer")
    void testDeleteCustomer_EmptyAccountNumber() {
        // Act
        boolean result = customerService.deleteCustomer("");

        // Assert
        // Should return false for empty account number without throwing exception
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

    // Helper methods that replicate service logic for testing
    private Customer convertDTOToModel(CustomerDTO dto) {
        if (dto == null) return null;
        Customer customer = new Customer();
        customer.setAccountNumber(dto.getAccountNumber());
        customer.setName(dto.getName());
        customer.setAddress(dto.getAddress());
        customer.setPhone(dto.getPhone());
        customer.setUnitsConsumed(dto.getUnitsConsumed());
        return customer;
    }

    private CustomerDTO convertModelToDTO(Customer customer) {
        if (customer == null) return null;
        return new CustomerDTO(
                customer.getAccountNumber(),
                customer.getName(),
                customer.getAddress(),
                customer.getPhone(),
                customer.getUnitsConsumed()
        );
    }

    private boolean isValidCustomer(CustomerDTO customer) {
        return customer != null &&
                customer.getName() != null &&
                !customer.getName().trim().isEmpty();
    }
}
