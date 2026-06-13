package main.java.com.example.marketplace.user.dto;

import main.java.com.example.marketplace.user.model.CartProduct;

import java.util.List;

public record CartResponse(List<CartProduct> cartProducts, float totalCost, float shipping, Integer totalProducts,
                           Integer totalFood, Integer totalMisc) {

}
