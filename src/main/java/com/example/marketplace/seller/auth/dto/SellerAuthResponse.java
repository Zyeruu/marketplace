package main.java.com.example.marketplace.seller.auth.dto;

public final class SellerAuthResponse {

    private final String cnpj;
    private final String storeName;

    public SellerAuthResponse(String cnpj, String storeName) {
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
