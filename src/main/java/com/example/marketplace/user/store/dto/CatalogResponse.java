package main.java.com.example.marketplace.user.store.dto;

import main.java.com.example.marketplace.user.store.model.Product;

import java.util.List;

public record CatalogResponse(List<Product> productList, int totalProduct, int totalFood, int totalMisc) {

}
