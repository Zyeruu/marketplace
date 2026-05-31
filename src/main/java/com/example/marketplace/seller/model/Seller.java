package main.java.com.example.marketplace.seller.model;

public final class Seller {

    private String name;
    private String email;
    private String password;
    private Store store;

    public Seller(String name, String email, String password, Store store) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.store = store;
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


}
