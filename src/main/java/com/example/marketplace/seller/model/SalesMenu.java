package main.java.com.example.marketplace.seller.model;

import main.java.com.example.marketplace.payment.model.TaxReceipt;

import java.util.ArrayList;
import java.util.List;

public final class SalesMenu {

    private List<TaxReceipt> taxReceiptsList = new ArrayList<>();
    private int totalSales;
    private int totalItemsSold;

    // Getters
    public List<TaxReceipt> getTaxReceiptsList() {
        return taxReceiptsList;
    }

    public int getTotalItemsSold() {
        return totalItemsSold;
    }

    public int getTotalSales() {
        return totalSales;
    }

    // Setters
    public void setTotalItemsSold(int totalItemsSold) {
        this.totalItemsSold = totalItemsSold;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }
}
