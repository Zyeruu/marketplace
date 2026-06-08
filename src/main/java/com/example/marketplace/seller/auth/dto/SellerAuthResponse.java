package main.java.com.example.marketplace.seller.auth.dto;

public final class SellerAuthResponse {

    private final String cnpj;
    private final String storeName;

    public SellerAuthResponse(String storeName, String cnpj) {
        this.storeName = storeName;
        this.cnpj = cnpj;
    }

    // Getters
    public String getCnpj() {
        return cnpj;
    }

    public String getStoreName() {
        return storeName;
    }
}
