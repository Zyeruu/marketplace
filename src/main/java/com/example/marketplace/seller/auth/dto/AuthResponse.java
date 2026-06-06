package main.java.com.example.marketplace.seller.auth.dto;

public final class AuthResponse {

    private final String cnpj;
    private final String storeName;

    public AuthResponse(String cnpj, String storeName) {
        this.cnpj = cnpj;
        this.storeName = storeName;
    }

    // Getters
    public String getCnpj() {
        return cnpj;
    }

    public String getStoreName() {
        return storeName;
    }
}
