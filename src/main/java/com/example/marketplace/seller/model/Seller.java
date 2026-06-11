package main.java.com.example.marketplace.seller.model;

import main.java.com.example.marketplace.checkout.model.TaxReceipt;

import java.util.List;

public final class Seller {

    private final String name;
    private String email;
    private String password;
    private final Store store;

    public Seller(String name, String email, String password, Store store) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.store = store;
    }

    public void updateCatalog() {
        store.getCatalog().updateCatalog();
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Store getStore() {
        return store;
    }

    public List<Product> getProductList() {
        return store.getCatalog().getProductList();
    }

    public List<TaxReceipt> getTaxReceiptList() {
        return store.getSalesMenu().getTaxReceiptList();
    }

    public String getCnpj() {
        return store.getCnpj();
    }

    public String getStoreName() {
        return store.getName();
    }

    public int getTotalProducts() {
        return store.getCatalog().getTotalProducts();
    }

    public int getTotalFood() {
        return store.getCatalog().getTotalFood();
    }

    public int getTotalMisc() {
        return store.getCatalog().getTotalMisc();
    }

    public float getTotalRevenue() {
        return store.getSalesMenu().getRevenue();
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTaxReceiptList(TaxReceipt taxReceipt) {
        store.getSalesMenu().setTaxReceiptList(taxReceipt);
    }
}
