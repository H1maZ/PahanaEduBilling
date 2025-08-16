package com.pahana.service;

import com.pahana.dto.CartItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive JUnit tests for CartService
 * Tests all main functions including adding, removing items and calculating totals
 */
@DisplayName("CartService Tests")
class CartServiceTest {

    private CartService cartService;
    
    @BeforeEach
    void setUp() {
        cartService = new CartService();
    }
    
    @Test
    @DisplayName("Should add item to empty cart")
    void testAddToCart_EmptyCart() {
        // Arrange
        List<CartItemDTO> cart = new ArrayList<>();
        CartItemDTO newItem = new CartItemDTO(1, "Test Book", 25.99, 2);
        
        // Act
        List<CartItemDTO> result = cartService.addToCart(cart, newItem);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(newItem, result.get(0));
        assertEquals(1, result.get(0).getBookId());
        assertEquals("Test Book", result.get(0).getTitle());
        assertEquals(25.99, result.get(0).getPrice(), 0.01);
        assertEquals(2, result.get(0).getQuantity());
    }
    
    @Test
    @DisplayName("Should add item to existing cart")
    void testAddToCart_ExistingCart() {
        // Arrange
        List<CartItemDTO> cart = new ArrayList<>();
        CartItemDTO existingItem = new CartItemDTO(1, "Test Book 1", 25.99, 1);
        cart.add(existingItem);
        
        CartItemDTO newItem = new CartItemDTO(2, "Test Book 2", 19.99, 3);
        
        // Act
        List<CartItemDTO> result = cartService.addToCart(cart, newItem);
        
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(existingItem, result.get(0));
        assertEquals(newItem, result.get(1));
    }
    
    @Test
    @DisplayName("Should update quantity when adding existing book")
    void testAddToCart_ExistingBook() {
        // Arrange
        List<CartItemDTO> cart = new ArrayList<>();
        CartItemDTO existingItem = new CartItemDTO(1, "Test Book", 25.99, 2);
        cart.add(existingItem);
        
        CartItemDTO newItem = new CartItemDTO(1, "Test Book", 25.99, 3);
        
        // Act
        List<CartItemDTO> result = cartService.addToCart(cart, newItem);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(5, result.get(0).getQuantity()); // 2 + 3 = 5
        assertEquals(1, result.get(0).getBookId());
        assertEquals("Test Book", result.get(0).getTitle());
        assertEquals(25.99, result.get(0).getPrice(), 0.01);
    }
    
    @Test
    @DisplayName("Should handle null cart by creating new cart")
    void testAddToCart_NullCart() {
        // Arrange
        List<CartItemDTO> cart = null;
        CartItemDTO newItem = new CartItemDTO(1, "Test Book", 25.99, 1);
        
        // Act
        List<CartItemDTO> result = cartService.addToCart(cart, newItem);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(newItem, result.get(0));
    }
    
    @Test
    @DisplayName("Should remove item from cart successfully")
    void testRemoveFromCart_Success() {
        // Arrange
        List<CartItemDTO> cart = new ArrayList<>();
        CartItemDTO item1 = new CartItemDTO(1, "Test Book 1", 25.99, 2);
        CartItemDTO item2 = new CartItemDTO(2, "Test Book 2", 19.99, 1);
        cart.add(item1);
        cart.add(item2);
        
        // Act
        List<CartItemDTO> result = cartService.removeFromCart(cart, 1);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(item2, result.get(0));
        assertEquals(2, result.get(0).getBookId());
    }
    
    @Test
    @DisplayName("Should remove multiple items with same book ID")
    void testRemoveFromCart_MultipleItems() {
        // Arrange
        List<CartItemDTO> cart = new ArrayList<>();
        CartItemDTO item1 = new CartItemDTO(1, "Test Book 1", 25.99, 2);
        CartItemDTO item2 = new CartItemDTO(1, "Test Book 1", 25.99, 1); // Same book ID
        CartItemDTO item3 = new CartItemDTO(2, "Test Book 2", 19.99, 1);
        cart.add(item1);
        cart.add(item2);
        cart.add(item3);
        
        // Act
        List<CartItemDTO> result = cartService.removeFromCart(cart, 1);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(item3, result.get(0));
        assertEquals(2, result.get(0).getBookId());
    }
    
    @Test
    @DisplayName("Should handle removing non-existent book ID")
    void testRemoveFromCart_NonExistentBook() {
        // Arrange
        List<CartItemDTO> cart = new ArrayList<>();
        CartItemDTO item = new CartItemDTO(1, "Test Book", 25.99, 2);
        cart.add(item);
        
        // Act
        List<CartItemDTO> result = cartService.removeFromCart(cart, 999);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(item, result.get(0));
    }
    
    @Test
    @DisplayName("Should handle null cart in remove operation")
    void testRemoveFromCart_NullCart() {
        // Arrange
        List<CartItemDTO> cart = null;
        
        // Act
        List<CartItemDTO> result = cartService.removeFromCart(cart, 1);
        
        // Assert
        assertNull(result);
    }
    
    @Test
    @DisplayName("Should calculate total for single item")
    void testCalculateTotal_SingleItem() {
        // Arrange
        List<CartItemDTO> cart = new ArrayList<>();
        CartItemDTO item = new CartItemDTO(1, "Test Book", 25.99, 2);
        cart.add(item);
        
        // Act
        double result = cartService.calculateTotal(cart);
        
        // Assert
        assertEquals(51.98, result, 0.01); // 25.99 * 2
    }
    
    @Test
    @DisplayName("Should calculate total for multiple items")
    void testCalculateTotal_MultipleItems() {
        // Arrange
        List<CartItemDTO> cart = new ArrayList<>();
        CartItemDTO item1 = new CartItemDTO(1, "Test Book 1", 25.99, 2);
        CartItemDTO item2 = new CartItemDTO(2, "Test Book 2", 19.99, 1);
        cart.add(item1);
        cart.add(item2);
        
        // Act
        double result = cartService.calculateTotal(cart);
        
        // Assert
        assertEquals(71.97, result, 0.01); // (25.99 * 2) + (19.99 * 1) = 51.98 + 19.99 = 71.97
    }
    
    @Test
    @DisplayName("Should return zero for empty cart")
    void testCalculateTotal_EmptyCart() {
        // Arrange
        List<CartItemDTO> cart = new ArrayList<>();
        
        // Act
        double result = cartService.calculateTotal(cart);
        
        // Assert
        assertEquals(0.0, result, 0.01);
    }
    
    @Test
    @DisplayName("Should return zero for null cart")
    void testCalculateTotal_NullCart() {
        // Arrange
        List<CartItemDTO> cart = null;
        
        // Act
        double result = cartService.calculateTotal(cart);
        
        // Assert
        assertEquals(0.0, result, 0.01);
    }
    
    @Test
    @DisplayName("Should handle zero quantity items")
    void testCalculateTotal_ZeroQuantity() {
        // Arrange
        List<CartItemDTO> cart = new ArrayList<>();
        CartItemDTO item = new CartItemDTO(1, "Test Book", 25.99, 0);
        cart.add(item);
        
        // Act
        double result = cartService.calculateTotal(cart);
        
        // Assert
        assertEquals(0.0, result, 0.01);
    }
    
    @Test
    @DisplayName("Should handle zero price items")
    void testCalculateTotal_ZeroPrice() {
        // Arrange
        List<CartItemDTO> cart = new ArrayList<>();
        CartItemDTO item = new CartItemDTO(1, "Free Book", 0.0, 2);
        cart.add(item);
        
        // Act
        double result = cartService.calculateTotal(cart);
        
        // Assert
        assertEquals(0.0, result, 0.01);
    }
    
    @Test
    @DisplayName("Should handle decimal quantities")
    void testCalculateTotal_DecimalQuantities() {
        // Arrange
        List<CartItemDTO> cart = new ArrayList<>();
        CartItemDTO item = new CartItemDTO(1, "Test Book", 10.50, 3);
        cart.add(item);
        
        // Act
        double result = cartService.calculateTotal(cart);
        
        // Assert
        assertEquals(31.50, result, 0.01); // 10.50 * 3
    }
    
    @Test
    @DisplayName("Should handle large quantities")
    void testCalculateTotal_LargeQuantities() {
        // Arrange
        List<CartItemDTO> cart = new ArrayList<>();
        CartItemDTO item = new CartItemDTO(1, "Test Book", 1.99, 1000);
        cart.add(item);
        
        // Act
        double result = cartService.calculateTotal(cart);
        
        // Assert
        assertEquals(1990.0, result, 0.01); // 1.99 * 1000
    }
    
    @Test
    @DisplayName("Should maintain cart integrity after operations")
    void testCartIntegrity() {
        // Arrange
        List<CartItemDTO> cart = new ArrayList<>();
        CartItemDTO item1 = new CartItemDTO(1, "Test Book 1", 25.99, 2);
        CartItemDTO item2 = new CartItemDTO(2, "Test Book 2", 19.99, 1);
        
        // Act - Add items
        cart = cartService.addToCart(cart, item1);
        cart = cartService.addToCart(cart, item2);
        
        // Assert - Check cart state
        assertEquals(2, cart.size());
        assertEquals(1, cart.get(0).getBookId());
        assertEquals(2, cart.get(1).getBookId());
        
        // Act - Remove item
        cart = cartService.removeFromCart(cart, 1);
        
        // Assert - Check cart state after removal
        assertEquals(1, cart.size());
        assertEquals(2, cart.get(0).getBookId());
        
        // Act - Calculate total
        double total = cartService.calculateTotal(cart);
        
        // Assert - Check total
        assertEquals(19.99, total, 0.01);
    }
    
    @Test
    @DisplayName("Should handle edge case with maximum integer quantity")
    void testCalculateTotal_MaxQuantity() {
        // Arrange
        List<CartItemDTO> cart = new ArrayList<>();
        CartItemDTO item = new CartItemDTO(1, "Test Book", 1.0, Integer.MAX_VALUE);
        cart.add(item);
        
        // Act
        double result = cartService.calculateTotal(cart);
        
        // Assert
        assertEquals((double) Integer.MAX_VALUE, result, 0.01);
    }
    
    @Test
    @DisplayName("Should handle edge case with maximum double price")
    void testCalculateTotal_MaxPrice() {
        // Arrange
        List<CartItemDTO> cart = new ArrayList<>();
        CartItemDTO item = new CartItemDTO(1, "Test Book", Double.MAX_VALUE, 1);
        cart.add(item);
        
        // Act
        double result = cartService.calculateTotal(cart);
        
        // Assert
        assertEquals(Double.MAX_VALUE, result, 0.01);
    }
}
