package main.java.com.example.marketplace.seller.auth.dto;

public final class SellerAuthRequest {

    private String email;
    private String password;
    private String name;
    private String storeName;

    public SellerAuthRequest(String email, String password, String name, String storeName) {
        this.email = email.toLowerCase();
        this.password = password;
        this.name = name;
        this.storeName = storeName;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getStoreName() {
        return storeName;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
