package main.java.com.example.marketplace.seller.model;

import main.java.com.example.marketplace.shared.enums.ItemType;

import java.util.ArrayList;
import java.util.List;

public final class Catalog {

    private List<Product> productList = new ArrayList<>();
    private int totalItems;
    private int totalFood;
    private int totalMisc;

    public Catalog() {
        this.productList = new ArrayList<>();
        this.totalItems = 0;
        this.totalFood = 0;
        this.totalMisc = 0;
    }

    public void updateCatalog() {

        this.totalFood = 0;
        this.totalItems = 0;

        for (Product product : productList) {
            if (product.getType() == ItemType.FOOD)
                this.totalFood++;
            else
                this.totalMisc++;
        }
        setTotalItems();
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

    // Setters

    public void setTotalItems() {
        this.totalItems += this.totalFood + this.totalMisc;
    }

    public void setTotalFood(int totalFood) {
        this.totalFood += totalFood;
        setTotalItems();
    }

    public void setTotalMisc(int totalMisc) {
        this.totalMisc += totalMisc;
        setTotalItems();
    }
}
