package main.java.com.example.marketplace.buyer.model;

import main.java.com.example.marketplace.shared.enums.ProductType;

public final class CartProduct {

    private final String name;
    private final String id;
    private final String storeName;
    private final ProductType type;
    private float unitPrice;
    private float weight;
    private int quantity;

    public CartProduct(String name, String id, String storeName, ProductType type, float unitPrice, float weight, int quantity) {
        this.name = name;
        this.id = id;
        this.storeName = storeName;
        this.type = type;
        this.unitPrice = unitPrice;
        this.weight = weight;
        this.quantity = quantity;
    }

    // Create a CartProduct copy
    public CartProduct(CartProduct cartProductPointer) {
        this.name = cartProductPointer.name;
        this.id = cartProductPointer.id;
        this.storeName = cartProductPointer.storeName;
        this.type = cartProductPointer.type;
        this.unitPrice = cartProductPointer.unitPrice;
        this.weight = cartProductPointer.weight;
        this.quantity = cartProductPointer.quantity;
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

    public ProductType getType() {
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

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
