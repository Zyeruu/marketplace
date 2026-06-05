package main.java.com.example.marketplace.buyer.model;

public final class Buyer {

    private final String name;
    private final String email;
    private final String password;
    private final Cart cart;
    private final OrdersMenu ordersMenu;

    public Buyer(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cart = new Cart();
        this.ordersMenu = new OrdersMenu();
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

    public Cart getCart() {
        return cart;
    }

    public OrdersMenu getOrdersMenu() {
        return ordersMenu;
    }
}
