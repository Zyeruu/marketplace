package main.java.com.example.marketplace.seller.model;

import java.util.ArrayList;
import java.util.List;

public final class Catalog {

    private List<Product> productList = new ArrayList<>();
    private int totalItems;
    private int totalFood;
    private int totalMisc;

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
