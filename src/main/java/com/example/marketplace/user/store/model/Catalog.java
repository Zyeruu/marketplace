package main.java.com.example.marketplace.user.store.model;

import main.java.com.example.marketplace.shared.enums.ProductType;

import java.util.ArrayList;
import java.util.List;

public final class Catalog {

    private final List<Product> productList = new ArrayList<>();
    private int totalProducts = 0;
    private int totalFood = 0;
    private int totalMisc = 0;
    private int availableTotalProducts = 0;
    private int availableTotalFood = 0;
    private int availableTotalMisc = 0;
    private int unavailableTotalProducts = 0;
    private int unavailableTotalFood = 0;
    private int unavailableTotalMisc = 0;

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

    public void updateAvailableCatalog() {

        this.availableTotalFood = 0;
        this.availableTotalMisc = 0;

        for (Product product : productList) {
            if (product.isAvailable())
                if (product.getType() == ProductType.FOOD)
                    this.availableTotalFood++;
                else
                    this.availableTotalMisc++;
        }

        this.availableTotalProducts = this.availableTotalFood + this.availableTotalMisc;
    }

    public void updateUnavailableCatalog() {

        this.unavailableTotalFood = 0;
        this.unavailableTotalMisc = 0;

        for (Product product : productList) {
            if (!product.isAvailable())
                if (product.getType() == ProductType.FOOD)
                    this.unavailableTotalFood++;
                else
                    this.unavailableTotalMisc++;
        }

        this.unavailableTotalProducts = this.unavailableTotalFood + this.unavailableTotalMisc;
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

    public int getAvailableTotalProducts() {
        return availableTotalProducts;
    }

    public int getAvailableTotalFood() {
        return availableTotalFood;
    }

    public int getAvailableTotalMisc() {
        return availableTotalMisc;
    }

    public int getUnavailableTotalProducts() {
        return unavailableTotalProducts;
    }

    public int getUnavailableTotalFood() {
        return unavailableTotalFood;
    }

    public int getUnavailableTotalMisc() {
        return unavailableTotalMisc;
    }
}
