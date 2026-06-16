package main.java.com.example.marketplace.user.model;

import main.java.com.example.marketplace.checkout.model.OrderedProduct;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.shared.model.Review;
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

    public int getTotalReviews() {
        return store.getReputation().getTotalReviews();
    }

    public int getTotalRating() {
        return store.getReputation().getTotalRating();
    }

    public float getReputationRating() {
        return store.getReputation().getReputationRating();
    }

    // Setters
    public void setStore(Store store) {
        this.store = store;
    }

    public void setTotalReviews(int totalReviews) {
        store.getReputation().setTotalReviews(totalReviews);
    }

    public void setTotalRating(int totalRating) {
        this.store.getReputation().setTotalRating(totalRating);
    }

    public void setReputationRating(float reputation) {
        this.store.getReputation().setReputationRating(reputation);
    }

    // ================================================| CATALOG |================================================

    public void updateCatalog() {
        store.getCatalog().updateCatalog();
    }

    public void updateAvailableCatalog() {
        store.getCatalog().updateAvailableCatalog();
    }

    public void updateUnavailableCatalog() {
        store.getCatalog().updateUnavailableCatalog();
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

    public int getCatalogAvailableTotalProducts() {
        return store.getCatalog().getAvailableTotalProducts();
    }

    public int getCatalogAvailableTotalFood() {
        return store.getCatalog().getAvailableTotalFood();
    }

    public int getCatalogAvailableTotalMisc() {
        return store.getCatalog().getAvailableTotalMisc();
    }

    public int getCatalogUnavailableTotalProducts() {
        return store.getCatalog().getUnavailableTotalProducts();
    }

    public int getCatalogUnavailableTotalFood() {
        return store.getCatalog().getUnavailableTotalFood();
    }

    public int getCatalogUnavailableTotalMisc() {
        return store.getCatalog().getUnavailableTotalMisc();
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
