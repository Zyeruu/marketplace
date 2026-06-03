package main.java.com.example.marketplace.buyer.dto;

import main.java.com.example.marketplace.buyer.model.CartItem;

import java.util.List;

public final class CartResponse {

    private List<CartItem> cartItems;
    private int totalItems;
    private int totalFood;
    private int totalMisc;

    public CartResponse(List<CartItem> cartItems, int totalItems, int totalFood, int totalMisc) {
        this.cartItems = cartItems;
        this.totalItems = totalItems;
        this.totalFood = totalFood;
        this.totalMisc = totalMisc;
    }

    // Getters
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int getTotalFood() {
        return totalFood;
    }

    public int getTotalMisc() {
        return totalMisc;
    }
}
