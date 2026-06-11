package main.java.com.example.marketplace.buyer.dto;

import main.java.com.example.marketplace.buyer.model.CartProduct;

import java.util.List;

public record CartResponse(List<CartProduct> cartProducts, float totalCost, float shipping, int totalProducts,
                           int totalFood, int totalMisc) {

}
