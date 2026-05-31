package main.java.com.example.marketplace.buyer.model;

import main.java.com.example.marketplace.shared.ItemType;

public final class CartItem {

    private String name;
    private ItemType type;
    private float unitPrice;
    private float weight;
    private int quantity;

    public CartItem(String name, ItemType type, float unitPrice, float weight, int quantity) {
        this.name = name;
        this.type = type;
        this.unitPrice = unitPrice;
        this.weight = weight;
        this.quantity = quantity;
    }

    // Create a CartItem copy
    public CartItem(CartItem cartItemPointer) {
        this.name = cartItemPointer.name;
        this.type = cartItemPointer.type;
        this.unitPrice = cartItemPointer.unitPrice;
        this.weight = cartItemPointer.weight;
        this.quantity = cartItemPointer.quantity;
    }

    // Getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
