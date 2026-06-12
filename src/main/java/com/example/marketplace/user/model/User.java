package main.java.com.example.marketplace.user.model;

import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.user.store.model.Product;
import main.java.com.example.marketplace.user.store.model.Store;

import java.util.List;

public final class User {

    private final String name;
    private String email;
    private String password;
    private Store store;
    private final Cart cart;
    private final OrdersMenu ordersMenu;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.store = null;
        this.password = password;
        this.cart = new Cart();
        this.ordersMenu = new OrdersMenu();
    }

    // ================================================| GENERAL |===============================================

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

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStoreName(String storeName) {
        store.setStoreName(storeName);
    }


    // =================================================| CART |=================================================

    public void updateCart() {
        cart.updateCart();
    }

    // Getters
    public List<CartProduct> getCartProductList() {
        return cart.getCartProductList();
    }

    public float getCartTotalCost() {
        return cart.getTotalCost();
    }

    public float getSelectedTotalCost() {
        return cart.getSelectedTotalCost();
    }

    public float getCartShipping() {
        return cart.getShipping();
    }

    public float getSelectedShipping() {
        return cart.getSelectedShipping();
    }

    public float getDeselectedShipping() {
        return cart.getDeselectedShipping();
    }

    public int getCartTotalProducts() {
        return cart.getTotalProducts();
    }

    public int getCartTotalFood() {
        return cart.getTotalFood();
    }

    public int getCartTotalMisc() {
        return cart.getTotalMisc();
    }

    // =================================================| STORE |=================================================

    // Getters
    public Store getStore() {
        return store;
    }

    public String getCnpj() {
        return store.getCnpj();
    }

    public String getStoreName() {
        return store.getName();
    }

    // Setters
    public void setStore(Store store) {
        this.store = store;
    }

    // ================================================| CATALOG |================================================

    public void updateCatalog() {
        store.getCatalog().updateCatalog();
    }

    // Getters
    public List<Product> getCatalogProductList() {
        return store.getCatalog().getProductList();
    }

    public int getCatalogTotalProducts() {
        return store.getCatalog().getTotalProducts();
    }

    public int getCatalogTotalFood() {
        return store.getCatalog().getTotalFood();
    }

    public int getCatalogTotalMisc() {
        return store.getCatalog().getTotalMisc();
    }

    // ==============================================| ORDERS MENU |==============================================

    // Getters
    public List<TaxReceipt> getOrdersMenuTaxReceiptList() {
        return ordersMenu.getTaxReceiptList();
    }

    // Setters
    public void setOrdersMenuTaxReceiptList(TaxReceipt taxReceipt) {
        ordersMenu.setTaxReceiptList(taxReceipt);
    }

    // ==============================================| SALES MENU |===============================================

    // Getters
    public List<TaxReceipt> getSalesMenuTaxReceiptList() {
        return store.getSalesMenu().getTaxReceiptList();
    }

    public float getTotalRevenue() {
        return store.getSalesMenu().getRevenue();
    }

    // Setters
    public void setSalesMenuTaxReceiptList(TaxReceipt taxReceipt) {
        store.getSalesMenu().setTaxReceiptList(taxReceipt);
    }
}
