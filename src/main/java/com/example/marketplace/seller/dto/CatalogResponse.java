package main.java.com.example.marketplace.seller.dto;

import main.java.com.example.marketplace.seller.model.Product;

import java.util.List;

public final class CatalogResponse {

    private final List<Product> productList;
    private final int totalProduct;
    private final int totalFood;
    private final int totalMisc;

    public CatalogResponse(List<Product> productList, int totalProduct, int totalFood, int totalMisc) {
        this.productList = productList;
        this.totalProduct = totalProduct;
        this.totalFood = totalFood;
        this.totalMisc = totalMisc;
    }

    // Getters

    public List<Product> getProductList() {
        return productList;
    }

    public int getTotalProduct() {
        return totalProduct;
    }

    public int getTotalFood() {
        return totalFood;
    }

    public int getTotalMisc() {
        return totalMisc;
    }
}
