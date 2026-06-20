package main.java.com.example.marketplace.user.store.model;

import main.java.com.example.marketplace.checkout.model.OrderedProduct;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;

import java.util.ArrayList;
import java.util.List;

public final class SalesMenu {

    private final List<TaxReceipt> taxReceiptList = new ArrayList<>();
    private final List<OrderedProduct> orderedProductList = new ArrayList<>();
    private float revenue = 0;

    public void updateRevenue() {

        this.revenue = 0;

        taxReceiptList.stream()
                .flatMap(taxReceipt -> taxReceipt.getOrderedProductList().stream())
                .forEach(product -> this.revenue += product.getTotalCost());
    }

    // Getters
    public List<TaxReceipt> getTaxReceiptList() {
        return taxReceiptList;
    }

    public List<OrderedProduct> getOrderedProductList() {
        return orderedProductList;
    }

    public float getRevenue() {
        updateRevenue();
        return revenue;
    }
}
