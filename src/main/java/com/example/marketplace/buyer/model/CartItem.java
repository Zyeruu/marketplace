package main.java.com.example.marketplace.buyer.model;

import main.java.com.example.marketplace.shared.ItemType;

public final class CartItem {

    private String name;
    private String id;
    private String storeName;
    private ItemType type;
    private float unitPrice;
    private float weight;
    private int quantity;

    public CartItem(String name, String id, String storeName, ItemType type, float unitPrice, float weight, int quantity) {
        this.name = name;
        this.id = id;
        this.storeName = storeName;
        this.type = type;
        this.unitPrice = unitPrice;
        this.weight = weight;
        this.quantity = quantity;
    }

    // Create a CartItem copy
    public CartItem(CartItem cartItemPointer) {
        this.name = cartItemPointer.name;
        this.id = cartItemPointer.id;
        this.storeName = cartItemPointer.storeName;
        this.type = cartItemPointer.type;
        this.unitPrice = cartItemPointer.unitPrice;
        this.weight = cartItemPointer.weight;
        this.quantity = cartItemPointer.quantity;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getStoreName() {
        return storeName;
    }

    public ItemType getType() {
        return type;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public float getWeight() {
        return weight;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
