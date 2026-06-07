package main.java.com.example.marketplace.buyer.dto;

import main.java.com.example.marketplace.buyer.model.CartProduct;

import java.util.List;

public final class CartResponse {

    private final List<CartProduct> cartProducts;
    private final int totalProducts;
    private final int totalFood;
    private final int totalMisc;

    public CartResponse(List<CartProduct> cartProducts, int totalProducts, int totalFood, int totalMisc) {
        this.cartProducts = cartProducts;
        this.totalProducts = totalProducts;
        this.totalFood = totalFood;
        this.totalMisc = totalMisc;
    }

    // Getters
    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    public int getTotalFood() {
        return totalFood;
    }

    public int getTotalMisc() {
        return totalMisc;
    }
}
