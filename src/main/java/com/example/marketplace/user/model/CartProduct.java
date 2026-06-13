package main.java.com.example.marketplace.user.model;

import main.java.com.example.marketplace.shared.enums.ProductType;

public final class CartProduct {

    private String name;
    private final String id;
    private String storeName;
    private ProductType type;
    private String brand;
    private float unitPrice;
    private float weight;
    private int quantity;
    private int warranty;
    private boolean selected;

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
        this.selected = true;
    }

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
        this.selected = cartProductPointer.isSelected();
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

    public boolean isSelected() {
        return selected;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
