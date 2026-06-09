package main.java.com.example.marketplace.buyer.auth.dto;

public final class BuyerAuthRequest {

    private final String email;
    private final String password;
    private String name;

    public BuyerAuthRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
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

    // Setters
    public void setName(String name) {
        this.name = name;
    }
}
