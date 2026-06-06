package main.java.com.example.marketplace.buyer.model;

import main.java.com.example.marketplace.checkout.model.TaxReceipt;

import java.util.ArrayList;
import java.util.List;

public final class OrdersMenu {

    private final List<TaxReceipt> taxReceiptList = new ArrayList<>();
    private int totalOrders = 0;

    // Getters
    public List<TaxReceipt> getTaxReceiptList() {
        return taxReceiptList;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    // Setters
    public void setTaxReceiptList(TaxReceipt taxReceipt) {
        this.taxReceiptList.add(taxReceipt);
        setTotalOrders();
    }

    public void setTotalOrders() {
        this.totalOrders++;
    }
}
