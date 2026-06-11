package main.java.com.example.marketplace.seller.dto;

import main.java.com.example.marketplace.checkout.model.TaxReceipt;

import java.util.List;

public class SalesMenuResponse {

    private final List<TaxReceipt> taxReceiptList;
    private final float revenue;

    public SalesMenuResponse(List<TaxReceipt> taxReceiptList, float revenue) {
        this.taxReceiptList = taxReceiptList;
        this.revenue = revenue;
    }

    // Getters
    public List<TaxReceipt> getTaxReceiptList() {
        return taxReceiptList;
    }

    public float getRevenue() {
        return revenue;
    }
}
