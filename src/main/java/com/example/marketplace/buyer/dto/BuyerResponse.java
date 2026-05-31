package main.java.com.example.marketplace.buyer.dto;

public class BuyerResponse {

    private String name;
    private String email;
    private int passwordSize;

    public BuyerResponse(String name, String email, int password) {
        this.name = name;
        this.email = email;
        this.passwordSize = password;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getPasswordSize() {
        return passwordSize;
    }
}
