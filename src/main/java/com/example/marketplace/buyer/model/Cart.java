package main.java.com.example.marketplace.buyer.model;

import main.java.com.example.marketplace.shared.enums.ItemType;

import java.util.ArrayList;
import java.util.List;

public final class Cart {

    private final List<CartItem> cartItemsList = new ArrayList<>();
    private float totalCost = 0;
    private float shipping = 0;
    private int totalItems = 0;
    private int totalFood = 0;
    private int totalMisc = 0;

    public void updateCart() {

        this.totalFood = 0;
        this.totalMisc = 0;

        for (CartItem item : cartItemsList) {
            if (item.getType() == ItemType.FOOD)
                this.totalFood++;
            else
                this.totalMisc++;
        }
        setTotalItems();
        updateTotalCost();
    }

    public void updateTotalCost() {

        float totalCostFood = 0;
        float totalCostMisc = 0;
        this.shipping = 0;

        for (CartItem item : cartItemsList) {
            if (item.getType() == ItemType.FOOD)
                totalCostFood = item.getQuantity() * item.getUnitPrice();
            else
                totalCostMisc = item.getQuantity() * item.getUnitPrice();
        }

        if (totalCostFood < 249.9)
            this.shipping += 19.9F;
        if (totalCostMisc < 79.9)
            this.shipping += 19.9F;
        this.totalCost = totalCostFood + totalCostMisc + this.shipping;
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

    public float getTotalCost() {
        return totalCost;
    }

    public float getShipping() {
        return shipping;
    }

    // Setters
    public void setTotalItems() {
        this.totalItems = this.totalFood + this.totalMisc;
    }

    public void setTotalFood(int totalFood) {
        this.totalFood += totalFood;
        setTotalItems();
    }

    public void setTotalMisc(int totalMisc) {
        this.totalMisc = totalMisc;
        setTotalItems();
    }
}
