package com.pahana.model;

import com.pahana.dto.BookDTO;

public class CartItem {
    private BookDTO book;
    private int quantity;

    public CartItem(BookDTO book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public BookDTO getBook() {
        return book;
    }
    public void setBook(BookDTO book) {
        this.book = book;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getTotalPrice() {
        return book.getPrice() * quantity;
    }
}
