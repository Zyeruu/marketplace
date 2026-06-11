package main.java.com.example.marketplace.buyer.model;

import main.java.com.example.marketplace.checkout.model.TaxReceipt;

import java.util.List;

public final class Buyer {

    private final String name;
    private String email;
    private String password;
    private final Cart cart;
    private final OrdersMenu ordersMenu;

    public Buyer(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cart = new Cart();
        this.ordersMenu = new OrdersMenu();
    }

    public void updateCart() {
        cart.updateCart();
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

    public List<CartProduct> getCartProductList() {
        return cart.getCartProductList();
    }

    public List<TaxReceipt> getTaxReceiptList() {
        return ordersMenu.getTaxReceiptList();
    }

    public float getCartTotalCost() {
        return cart.getTotalCost();
    }

    public float getCartShipping() {
        return cart.getShipping();
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

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTaxReceiptList(TaxReceipt taxReceipt) {
        ordersMenu.setTaxReceiptList(taxReceipt);
    }
}
