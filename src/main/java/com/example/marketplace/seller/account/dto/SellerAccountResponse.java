package main.java.com.example.marketplace.seller.account.dto;

public class SellerAccountResponse {

    private final String name;
    private final String email;
    private final int passwordSize;

    public SellerAccountResponse(String name, String email, int password) {
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
