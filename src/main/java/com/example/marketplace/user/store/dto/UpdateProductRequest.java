package main.java.com.example.marketplace.user.store.dto;

import main.java.com.example.marketplace.shared.enums.ProductType;

public final class UpdateProductRequest {

    private final String id;
    private final String name;
    private final ProductType type;
    private final String brand;
    private final Float unitPrice;
    private final Float weight;
    private final Integer stock;
    private final Integer warranty;
    private final Boolean available;

    public UpdateProductRequest(String id, String name, ProductType type, String brand, Float unitPrice, Float weight, Integer stock, Integer warranty, Boolean available) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.unitPrice = unitPrice;
        this.weight = weight;
        this.stock = stock;
        this.warranty = warranty;
        this.available = available;
    }

    public static UpdateProductRequest withName(String id, String name) {
        return new UpdateProductRequest(id, name, null, null, null, null, null, null, null);
    }

    public static UpdateProductRequest withType(String id, ProductType type) {
        return new UpdateProductRequest(id, null, type, null, null, null, null, null, null);
    }

    public static UpdateProductRequest withBrand(String id, String brand) {
        return new UpdateProductRequest(id, null, null, brand, null, null, null, null, null);
    }

    public static UpdateProductRequest withPrice(String id, float unitPrice) {
        return new UpdateProductRequest(id, null, null, null, unitPrice, null, null, null, null);
    }

    public static UpdateProductRequest withWeight(String id, float weight) {
        return new UpdateProductRequest(id, null, null, null, null, weight, null, null, null);
    }

    public static UpdateProductRequest withStock(String id, int stock) {
        return new UpdateProductRequest(id, null, null, null, null, null, stock, null, null);
    }

    public static UpdateProductRequest withWarranty(String id, int warranty) {
        return new UpdateProductRequest(id, null, null, null, null, null, null, warranty, null);
    }

    public static UpdateProductRequest withAvailable(String id, boolean available) {
        return new UpdateProductRequest(id, null, null, null, null, null, null, null, available);
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ProductType getType() {
        return type;
    }

    public String getBrand() {
        return brand;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public Float getWeight() {
        return weight;
    }

    public Integer getStock() {
        return stock;
    }

    public Integer getWarranty() {
        return warranty;
    }

    public Boolean getAvailable() {
        return available;
    }
}
