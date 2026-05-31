package main.java.com.example.marketplace.seller.model;

import main.java.com.example.marketplace.shared.ItemType;

public final class Product {

    private String name;
    private String id;
    private ItemType type;
    private String brand;
    private float unitPrice;
    private float weight;
    private int stock;
    private int warranty;

    // Food type
    public Product(String name, String id, float unitPrice, float weight, int stock) {
        this.name = name;
        this.id = id;
        this.type = ItemType.FOOD;
        this.brand = null;
        this.unitPrice = unitPrice;
        this.weight = weight;
        this.stock = stock;
        this.warranty = 0;
    }

    // Miscellaneous type
    public Product(String name, String id, String brand, float unitPrice, float weight, int stock, int warranty) {
        this.name = name;
        this.id = id;
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
