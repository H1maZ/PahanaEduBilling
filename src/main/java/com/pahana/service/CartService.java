package com.pahana.service;

import com.pahana.dto.CartItemDTO;
import java.util.ArrayList;
import java.util.List;

public class CartService {

    public List<CartItemDTO> addToCart(List<CartItemDTO> cart, CartItemDTO item) {
        if (cart == null) {
            cart = new ArrayList<>();
        }
        // If book already exists, increase quantity
        for (CartItemDTO cartItem : cart) {
            if (cartItem.getBookId() == item.getBookId()) {
                cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
                return cart;
            }
        }
        cart.add(item);
        return cart;
    }

    public List<CartItemDTO> removeFromCart(List<CartItemDTO> cart, int bookId) {
        if (cart != null) {
            cart.removeIf(item -> item.getBookId() == bookId);
        }
        return cart;
    }

    public double calculateTotal(List<CartItemDTO> cart) {
        if (cart == null) return 0;
        return cart.stream().mapToDouble(CartItemDTO::getTotalPrice).sum();
    }
}
