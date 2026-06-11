package main.java.com.example.marketplace.seller.model;

import main.java.com.example.marketplace.checkout.model.TaxReceipt;

import java.util.ArrayList;
import java.util.List;

public final class SalesMenu {

    private final List<TaxReceipt> taxReceiptsList = new ArrayList<>();
    private float revenue = 0;

    public void updateRevenue() {

        this.revenue = 0;

        taxReceiptsList.stream()
                .flatMap(taxReceipt -> taxReceipt.getOrderedProductList().stream())
                .forEach(product -> this.revenue += product.getTotalCost());
    }

    // Getters
    public List<TaxReceipt> getTaxReceiptList() {
        return taxReceiptsList;
    }

    public float getRevenue() {
        return revenue;
    }

    // Setters
    public void setTaxReceiptList(TaxReceipt taxReceipt) {
        this.taxReceiptsList.add(taxReceipt);
        updateRevenue();
    }
}
