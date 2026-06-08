package main.java.com.example.marketplace.buyer.model;

import main.java.com.example.marketplace.shared.enums.ProductType;

import java.util.ArrayList;
import java.util.List;

public final class Cart {

    private final List<CartProduct> cartProductList = new ArrayList<>();
    private float totalCost = 0;
    private float shipping = 0;
    private int totalProducts = 0;
    private int totalFood = 0;
    private int totalMisc = 0;

    public void updateCart() {

        this.totalFood = 0;
        this.totalMisc = 0;

        for (CartProduct product : cartProductList) {
            if (product.getType() == ProductType.FOOD)
                this.totalFood++;
            else
                this.totalMisc++;
        }

        this.totalProducts = this.totalFood + this.totalMisc;
        updateTotalCost();
    }

    public void updateTotalCost() {

        float totalCostFood = 0;
        float totalCostMisc = 0;
        this.shipping = 0;

        for (CartProduct product : cartProductList) {
            if (product.getType() == ProductType.FOOD)
                totalCostFood += product.getQuantity() * product.getUnitPrice();
            else
                totalCostMisc += product.getQuantity() * product.getUnitPrice();
        }

        if (totalCostFood > 0 && totalCostFood < 249.9)
            this.shipping += 19.9F;
        if (totalCostMisc > 0 && totalCostMisc < 79.9)
            this.shipping += 19.9F;

        this.totalCost = totalCostFood + totalCostMisc + this.shipping;
    }

    // Getters
    public List<CartProduct> getCartProductList() {
        return cartProductList;
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

    public float getTotalCost() {
        return totalCost;
    }

    public float getShipping() {
        return shipping;
    }
}
