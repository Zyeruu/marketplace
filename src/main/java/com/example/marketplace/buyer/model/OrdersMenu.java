package main.java.com.example.marketplace.buyer.model;

import main.java.com.example.marketplace.checkout.model.TaxReceipt;

import java.util.ArrayList;
import java.util.List;

public final class OrdersMenu {

    private final List<TaxReceipt> buyerTaxReceipts = new ArrayList<>();
    private int totalOrders = 0;

    // Setters
    public void setBuyerTaxReceipts(TaxReceipt taxReceipt) {
        this.buyerTaxReceipts.add(taxReceipt);
        setTotalOrders();
    }

    public void setTotalOrders() {
        this.totalOrders++;
    }
}
