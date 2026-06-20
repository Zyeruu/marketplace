package main.java.com.example.marketplace.user.model;

import main.java.com.example.marketplace.shared.enums.ProductType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Cart {

    private final List<CartProduct> productList;
    private float totalCost = 0;
    private float shipping = 0;
    private int totalProducts = 0;
    private int totalFood = 0;
    private int totalMisc = 0;

    public Cart() {
        this.productList = new ArrayList<>();
        this.totalCost = 0;
        this.shipping = 0;
        this.totalProducts = 0;
        this.totalFood = 0;
        this.totalMisc = 0;
    }

    public Cart(Cart cart) {
        this.productList = cart.productList.stream()
                .map(CartProduct::new).collect(Collectors.toList());
        this.totalCost = cart.totalCost;
        this.shipping = cart.shipping;
        this.totalProducts = cart.totalProducts;
        this.totalFood = cart.totalFood;
        this.totalMisc = cart.totalMisc;
    }

    public Cart(List<CartProduct> productList, float totalCost, float shipping, int totalProducts, int totalFood, int totalMisc) {
        this.productList = productList;
        this.totalCost = totalCost;
        this.shipping = shipping;
        this.totalProducts = totalProducts;
        this.totalFood = totalFood;
        this.totalMisc = totalMisc;
    }

    public void updateCart() {

        this.totalFood = 0;
        this.totalMisc = 0;

        for (CartProduct product : productList) {
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
        shipping = 0;

        for (CartProduct product : productList) {
            if (product.getType() == ProductType.FOOD)
                totalCostFood += product.getQuantity() * product.getUnitPrice();
            else
                totalCostMisc += product.getQuantity() * product.getUnitPrice();
        }

        if (totalCostFood > 0 && totalCostFood < 249.9)
            shipping += 19.9F;
        if (totalCostMisc > 0 && totalCostMisc < 79.9)
            shipping += 19.9F;

        totalCost = totalCostFood + totalCostMisc + shipping;
    }

    // Getters
    public List<CartProduct> getProductList() {
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

    public float getTotalCost() {
        updateCart();
        return totalCost;
    }

    public float getSelectedTotalCost() {

        float totalCostFood = 0;
        float totalCostMisc = 0;

        for (CartProduct product : productList) {
            if (product.isSelected()) {
                if (product.getType() == ProductType.FOOD)
                    totalCostFood += product.getQuantity() * product.getUnitPrice();
                else
                    totalCostMisc += product.getQuantity() * product.getUnitPrice();
            }
        }

        return totalCost = totalCostFood + totalCostMisc + getSelectedShipping();
    }

    public float getSelectedShipping() {

        float totalCostFood = 0;
        float totalCostMisc = 0;
        float shipping = 0;

        for (CartProduct product : productList) {
            if(product.isSelected())
                if (product.getType() == ProductType.FOOD)
                    totalCostFood += product.getQuantity() * product.getUnitPrice();
                else
                    totalCostMisc += product.getQuantity() * product.getUnitPrice();
        }

        if (totalCostFood > 0 && totalCostFood < 249.9)
            shipping += 19.9F;
        if (totalCostMisc > 0 && totalCostMisc < 79.9)
            shipping += 19.9F;

        return shipping;
    }

    public float getDeselectedShipping() {

        float totalCostFood = 0;
        float totalCostMisc = 0;
        float shipping = 0;

        for (CartProduct product : productList) {
            if(!product.isSelected())
                if (product.getType() == ProductType.FOOD)
                    totalCostFood += product.getQuantity() * product.getUnitPrice();
                else
                    totalCostMisc += product.getQuantity() * product.getUnitPrice();
        }

        if (totalCostFood > 0 && totalCostFood < 249.9)
            shipping += 19.9F;
        if (totalCostMisc > 0 && totalCostMisc < 79.9)
            shipping += 19.9F;

        return shipping;
    }

    public float getShipping() {
        return shipping;
    }
}
