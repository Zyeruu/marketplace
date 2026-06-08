package main.java.com.example.marketplace.seller.account.dto;

public final class SellerAccountResponse {

    private final String name;
    private final String email;
    private final int passwordSize;
    private final String storeName;
    private final String cnpj;

    public SellerAccountResponse(String name, String email, int passwordSize, String storeName, String cnpj) {
        this.name = name;
        this.email = email;
        this.passwordSize = passwordSize;
        this.storeName = storeName;
        this.cnpj = cnpj;
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

    public String getStoreName() {
        return storeName;
    }

    public String getCnpj() {
        return cnpj;
    }
}
