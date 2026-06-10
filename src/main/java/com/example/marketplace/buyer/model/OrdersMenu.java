package main.java.com.example.marketplace.buyer.model;

import main.java.com.example.marketplace.checkout.model.TaxReceipt;

import java.util.ArrayList;
import java.util.List;

public final class OrdersMenu {

    private final List<TaxReceipt> taxReceiptList = new ArrayList<>();

    // Getters
    public List<TaxReceipt> getTaxReceiptList() {
        return taxReceiptList;
    }

    // Setters
    public void setTaxReceiptList(TaxReceipt taxReceipt) {
        this.taxReceiptList.add(taxReceipt);
    }
}
