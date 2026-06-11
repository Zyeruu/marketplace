package main.java.com.example.marketplace.buyer.model;

import main.java.com.example.marketplace.shared.enums.ProductType;

public final class CartProduct {

    private final String name;
    private final String id;
    private final String storeName;
    private final ProductType type;
    private final String brand;
    private float unitPrice;
    private final float weight;
    private int quantity;
    private final int warranty;

    public CartProduct(String name, String id, String storeName, ProductType type, String brand, float unitPrice, float weight, int quantity, int warranty) {
        this.name = name;
        this.id = id;
        this.storeName = storeName;
        this.type = type;
        this.brand = brand;
        this.unitPrice = unitPrice;
        this.weight = weight;
        this.quantity = quantity;
        this.warranty = warranty;
    }

    // Create a CartProduct copy
    public CartProduct(CartProduct cartProductPointer) {
        this.name = cartProductPointer.getName();
        this.id = cartProductPointer.getId();
        this.storeName = cartProductPointer.getStoreName();
        this.type = cartProductPointer.getType();
        this.brand = cartProductPointer.getBrand();
        this.unitPrice = cartProductPointer.getUnitPrice();
        this.weight = cartProductPointer.getWeight();
        this.quantity = cartProductPointer.getQuantity();
        this.warranty = cartProductPointer.getWarranty();
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

    public String getBrand() {
        return brand;
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

    public int getWarranty() {
        return warranty;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
