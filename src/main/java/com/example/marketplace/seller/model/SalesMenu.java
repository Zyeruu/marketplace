package main.java.com.example.marketplace.seller.model;

import main.java.com.example.marketplace.checkout.model.OrderedItem;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;

import java.util.ArrayList;
import java.util.List;

public final class SalesMenu {

    private List<TaxReceipt> taxReceiptsList;
    private int totalSales;
    private float income;

    public SalesMenu() {
        this.taxReceiptsList = new ArrayList<>();
        this.totalSales = 0;
        this.income = 0;
    }

    public void updateIncome() {

        this.income = 0;

        for (TaxReceipt receipt : taxReceiptsList)
            for (OrderedItem item : receipt.getOrderedItemsList())
                this.income += item.getTotalCost();
    }

    // Getters
    public List<TaxReceipt> getTaxReceiptsList() {
        return taxReceiptsList;
    }

    public int getTotalSales() {
        return totalSales;
    }

    // Setters
    public void setTaxReceiptsList(TaxReceipt taxReceipt) {
        this.taxReceiptsList.add(taxReceipt);
        updateIncome();
        setTotalSales();
    }

    public void setTotalSales() {
        this.totalSales++;
    }
}
