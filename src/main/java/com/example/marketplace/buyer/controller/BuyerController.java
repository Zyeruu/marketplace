package main.java.com.example.marketplace.buyer.controller;

import main.java.com.example.marketplace.buyer.dto.BuyerResponse;
import main.java.com.example.marketplace.buyer.repository.BuyerRepository;
import main.java.com.example.marketplace.buyer.view.BuyerView;
import main.java.com.example.marketplace.exceptions.NotFoundException;

public final class BuyerController {

    private BuyerRepository repository;
    private BuyerView view;

    public void printBuyer(String email) {

        try {
            BuyerResponse buyerResponse = repository.findByEmail(email);
            view.printBuyerProfile(buyerResponse);
        }
        catch (NotFoundException e) {
            view.printException(e);
        }
    }
}
