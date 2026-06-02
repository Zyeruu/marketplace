package main.java.com.example.marketplace.seller.model;

import main.java.com.example.marketplace.payment.model.TaxReceipt;

import java.util.ArrayList;
import java.util.List;

public final class SalesMenu {

    private List<TaxReceipt> taxReceiptsList;
    private int totalSales;
    private int totalItemsSold;

    public SalesMenu() {
        this.taxReceiptsList = new ArrayList<>();
        this.totalSales = 0;
        this.totalItemsSold = 0;
    }

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
