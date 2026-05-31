package main.java.com.example.marketplace.buyer.controller;

import main.java.com.example.marketplace.buyer.dto.BuyerResponse;
import main.java.com.example.marketplace.buyer.repository.BuyerRepository;

public final class BuyerController {

    private BuyerRepository repository;

    public BuyerResponse searchUser(String email) {

        BuyerResponse buyerResponse = repository.emailExists(email);

        if (buyerResponse == null) {
            System.out.println("Profile not found!");
            return null;
        }
        return buyerResponse;
    }
}
