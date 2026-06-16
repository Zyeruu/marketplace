package main.java.com.example.marketplace.user.store.model;

import main.java.com.example.marketplace.shared.enums.ProductType;
import main.java.com.example.marketplace.shared.model.Review;

import java.util.List;

public final class Product {

    private String name;
    private final String id;
    private String storeName;
    private ProductType type;
    private String brand;
    private float unitPrice;
    private float weight;
    private int stock;
    private Integer warranty;
    private boolean available;
    private final Reputation reputation;

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
        this.warranty = null;
        this.available = true;
        this.reputation = new Reputation();
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
        this.available = true;
        this.reputation = new Reputation();
    }

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
        this.available = product.available;
        this.reputation = new Reputation(product.getReputation());
    }

    public void updateReputation() {
        reputation.updateReputation();
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

    public Integer getWarranty() {
        return warranty;
    }

    public boolean isAvailable() {
        return available;
    }

    public Reputation getReputation() {
        return reputation;
    }

    public List<Review> getReviewList() {
        return reputation.getReviewList();
    }

    public float getReputationRating() {
        return reputation.getReputationRating();
    }

    public int getTotalReviews() {
        return reputation.getTotalReviews();
    }

    public int getTotalRating() {
        return reputation.getTotalRating();
    }

    public int getTotal1Star() {
        return reputation.getTotal1Star();
    }

    public int getTotal2Stars() {
        return reputation.getTotal2Stars();
    }

    public int getTotal3Stars() {
        return reputation.getTotal3Stars();
    }

    public int getTotal4Stars() {
        return reputation.getTotal4Stars();
    }

    public int getTotal5Stars() {
        return reputation.getTotal5Stars();
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

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
