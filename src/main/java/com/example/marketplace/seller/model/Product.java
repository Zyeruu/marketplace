package main.java.com.example.marketplace.seller.model;

import main.java.com.example.marketplace.shared.enums.ProductType;

public final class Product {

    private final String name;
    private final String id;
    private String storeName;
    private final ProductType type;
    private final String brand;
    private float unitPrice;
    private final float weight;
    private int stock;
    private final int warranty;

    // Food type
    public Product(String name, String id, String storeName, float unitPrice, float weight, int stock) {
        this.name = name;
        this.id = id;
        this.storeName = storeName;
        this.type = ProductType.FOOD;
        this.brand = null;
        this.unitPrice = unitPrice;
        this.weight = weight;
        this.stock = stock;
        this.warranty = 0;
    }

    // Miscellaneous type
    public Product(String name, String id, String storeName, String brand, float unitPrice, float weight, int stock, int warranty) {
        this.name = name;
        this.id = id;
        this.storeName = storeName;
        this.type = ProductType.MISCELLANEOUS;
        this.brand = brand;
        this.unitPrice = unitPrice;
        this.weight = weight;
        this.stock = stock;
        this.warranty = warranty;
    }

    // Creates a copy of Product using a pointer to an existing Product
    public Product(Product product) {
        this.name = product.name;
        this.id = product.id;
        this.storeName = product.storeName;
        this.type = product.type;
        this.brand = product.brand;
        this.unitPrice = product.unitPrice;
        this.weight = product.weight;
        this.stock = product.stock;
        this.warranty = product.warranty;
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

    public int getStock() {
        return stock;
    }

    public int getWarranty() {
        return warranty;
    }

    // Setters
    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
