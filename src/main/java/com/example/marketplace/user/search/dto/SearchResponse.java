package main.java.com.example.marketplace.user.search.dto;

import main.java.com.example.marketplace.user.store.model.Product;

import java.util.List;

public record SearchResponse(List<Product> catalog, int totalProducts, int totalFoods, int totalMisc) {

}
