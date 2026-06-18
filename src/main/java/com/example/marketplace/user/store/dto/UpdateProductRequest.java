package main.java.com.example.marketplace.user.store.dto;

import main.java.com.example.marketplace.shared.enums.ProductType;

public record UpdateProductRequest(String id, String name, ProductType type, String brand, Float unitPrice,
                                   Float weight, Integer stock, Integer warranty, Boolean available) {

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
}
