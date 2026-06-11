package main.java.com.example.marketplace.seller.dto;

import main.java.com.example.marketplace.seller.model.Product;

import java.util.List;

public record CatalogResponse(List<Product> productList, int totalProduct, int totalFood, int totalMisc) {

}
