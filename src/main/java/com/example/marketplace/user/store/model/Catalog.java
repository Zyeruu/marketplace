package main.java.com.example.marketplace.user.store.model;

import main.java.com.example.marketplace.shared.enums.ProductType;

import java.util.ArrayList;
import java.util.List;

public final class Catalog {

    private final List<Product> productList = new ArrayList<>();
    private int totalProducts = 0;
    private int totalFood = 0;
    private int totalMisc = 0;

    public void updateCatalog() {

        this.totalFood = 0;
        this.totalMisc = 0;

        for (Product product : productList) {
            if (product.getType() == ProductType.FOOD)
                this.totalFood++;
            else
                this.totalMisc++;
        }

        this.totalProducts = this.totalFood + this.totalMisc;
    }

    // Getters
    public List<Product> getProductList() {
        return productList;
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    public int getTotalFood() {
        return totalFood;
    }

    public int getTotalMisc() {
        return totalMisc;
    }
}
