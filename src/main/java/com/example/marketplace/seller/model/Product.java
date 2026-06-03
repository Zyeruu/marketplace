package main.java.com.example.marketplace.seller.model;

import main.java.com.example.marketplace.shared.ItemType;

public final class Product {

    private String name;
    private String id;
    private String storeName;
    private ItemType type;
    private String brand;
    private float unitPrice;
    private float weight;
    private int stock;
    private int warranty;

    // Food type
    public Product(String name, String id, String storeName, float unitPrice, float weight, int stock) {
        this.name = name;
        this.id = id;
        this.storeName = storeName;
        this.type = ItemType.FOOD;
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
        this.type = ItemType.MISCELLANEOUS;
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

    public ItemType getType() {
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
}
