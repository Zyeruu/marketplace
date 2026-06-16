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

        availableTotalFood = 0;
        availableTotalMisc = 0;
        unavailableTotalFood = 0;
        unavailableTotalMisc = 0;

        for (Product product : productList) {
            if (product.getType() == ProductType.FOOD) {
                if (product.isAvailable()) {
                    availableTotalFood++;
                }
                else
                    unavailableTotalFood++;
            }
            else {
                if (product.isAvailable()) {
                    availableTotalMisc++;
                }
                else
                    unavailableTotalMisc++;
            }
        }

        availableTotalProducts = availableTotalFood + availableTotalMisc;
        unavailableTotalProducts = unavailableTotalFood + unavailableTotalMisc;
        totalProducts = availableTotalProducts + unavailableTotalProducts;
        totalFood = availableTotalFood + unavailableTotalFood;
        totalMisc = availableTotalMisc + unavailableTotalMisc;
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
