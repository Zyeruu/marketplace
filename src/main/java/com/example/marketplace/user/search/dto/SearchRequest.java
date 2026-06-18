package main.java.com.example.marketplace.user.search.dto;

import main.java.com.example.marketplace.shared.enums.ProductType;

public record SearchRequest(String productName, ProductType productType) {

}
