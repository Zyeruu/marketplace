package main.java.com.example.marketplace.buyer.model;

import java.util.ArrayList;
import java.util.List;

public final class Cart {

    private List<CartItem> cartItemsList = new ArrayList<>();
    private int totalItems;
    private int totalFood;
    private int totalMisc;

    public Cart() {
        this.cartItemsList = new ArrayList<>();
        this.totalItems = 0;
        this.totalFood = 0;
        this.totalMisc = 0;
    }

    // Getters
    public List<CartItem> getCartItemsList() {
        return cartItemsList;
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

    // Setters
    public void setTotalItems() {
        this.totalItems = this.totalFood + this.totalMisc;
    }

    public void setTotalFood(int totalFood) {
        this.totalFood += totalFood;
    }

    public void setTotalMisc(int totalMisc) {
        this.totalMisc = totalMisc;
    }
}
