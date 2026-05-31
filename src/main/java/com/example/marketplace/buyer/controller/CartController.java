package main.java.com.example.marketplace.buyer.controller;

import main.java.com.example.marketplace.buyer.dto.CartResponse;
import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.buyer.repository.CartRepository;

public final class CartController {

    private Buyer buyer;
    private CartRepository repository;

    public CartResponse findByEmail(String email) {

        CartResponse cartResponse = repository.findByEmail(email);

        if (cartResponse == null)
            System.out.println("User not found!");

        return cartResponse;
    }
}
