package main.java.com.example.marketplace.user.model;

import main.java.com.example.marketplace.checkout.model.OrderedProduct;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.shared.model.Review;
import main.java.com.example.marketplace.user.store.model.Catalog;
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

    public Cart getCart() {
        return cart;
    }

    public List<CartProduct> getCartProductList() {
        return cart.getProductList();
    }

    public float getCartTotalCost() {
        return cart.getTotalCost();
    }

    public float getCartShipping() {
        return cart.getShipping();
    }

    public float getSelectedShipping() {
        return cart.getSelectedShipping();
    }

    public int getCartTotalFood() {
        return cart.getTotalFood();
    }

    public int getCartTotalMisc() {
        return cart.getTotalMisc();
    }

    // =================================================| STORE |=================================================

    public void updateReputation() {
        store.getReputation().updateReputation();
    }

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

    public List<Review> getStoreReviewList() {
        return store.getReputation().getReviewList();
    }

    public float getReputationRating() {
        return store.getReputation().getReputationRating();
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

    public Catalog getCatalog() {
        return store.getCatalog();
    }
    public List<Product> getCatalogProductList() {
        return store.getCatalog().getProductList();
    }

    public int getCatalogAvailableTotalProducts() {
        return store.getCatalog().getAvailableTotalProducts();
    }

    // ==============================================| ORDERS MENU |==============================================

    // Getters
    public List<TaxReceipt> getOrdersMenuTaxReceiptList() {
        return ordersMenu.getTaxReceiptList();
    }

    public List<OrderedProduct> getOrdersMenuOrderedProductList() {
        return ordersMenu.getOrderedProductList();
    }

    public List<Review> getOrdersMenuReviewList() {
        return ordersMenu.getReviewList();
    }

    // ==============================================| SALES MENU |===============================================

    // Getters
    public List<TaxReceipt> getSalesMenuTaxReceiptList() {
        return store.getSalesMenu().getTaxReceiptList();
    }

    public List<OrderedProduct> getSalesMenuOrderedProductList() {
        return store.getSalesMenu().getOrderedProductList();
    }

    public float getTotalRevenue() {
        return store.getSalesMenu().getRevenue();
    }
}
