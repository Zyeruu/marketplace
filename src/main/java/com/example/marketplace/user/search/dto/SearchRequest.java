package main.java.com.example.marketplace.user.search.dto;

import main.java.com.example.marketplace.shared.enums.ProductType;

public final class SearchRequest {

    private final String productName;
    private final ProductType productType;

    public SearchRequest(String productName, ProductType productType) {
        this.productName = productName;
        this.productType = productType;
    }

    // Getters
    public String getProductName() {
        return productName;
    }

    public ProductType getProductType() {
        return productType;
    }
}
