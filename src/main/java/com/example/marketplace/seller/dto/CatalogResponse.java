package main.java.com.example.marketplace.seller.dto;

import main.java.com.example.marketplace.seller.model.Product;

import java.util.List;

public final class CatalogResponse {

    private List<Product> productList;
    private int totalItems;
    private int totalFood;
    private int totalMisc;

    public CatalogResponse(List<Product> productList, int totalItems, int totalFood, int totalMisc) {
        this.productList = productList;
        this.totalItems = totalItems;
        this.totalFood = totalFood;
        this.totalMisc = totalMisc;
    }

    // Getters

    public List<Product> getProductList() {
        return productList;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int getTotalFood() {
        return totalFood;
    }

    public int getTotalMisc() {
        return totalMisc;
    }
}
