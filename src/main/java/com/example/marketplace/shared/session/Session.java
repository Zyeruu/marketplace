package main.java.com.example.marketplace.shared.session;

public final class Session {

    private String email;
    private String cnpj;
    private String storeName;
    private boolean buyerLogged;
    private boolean sellerLogged;

    public Session() {
        this.email = null;
        this.cnpj = null;
        this.storeName = null;
        this.buyerLogged = false;
        this.sellerLogged = false;
    }

    public void login(String email) {
        this.email = email;
        buyerLogged = true;
        sellerLogged = false;
    }
    public void login(String email, String cnpj, String storeName) {
        this.email = email;
        this.cnpj = cnpj;
        this.storeName = storeName;
        this.sellerLogged = true;
        this.buyerLogged = false;
    }

    public void logout() {
        this.email = null;
        this.cnpj = null;
        this.storeName = null;
        this.buyerLogged = false;
        this.sellerLogged = false;
    }

    // Getters
    public String getCnpj() {
        return cnpj;
    }

    public String getEmail() {
        return email;
    }

    public String getStoreName() {
        return storeName;
    }

    public boolean isBuyerLogged() {
        return buyerLogged;
    }

    public boolean isSellerLogged() {
        return sellerLogged;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }
}
