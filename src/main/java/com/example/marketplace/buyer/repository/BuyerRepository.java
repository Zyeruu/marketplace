package main.java.com.example.marketplace.buyer.repository;

import main.java.com.example.marketplace.buyer.dto.BuyerResponse;
import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.NotFoundException;

public final class BuyerRepository {

    public BuyerResponse findByEmail(String email) {

        if (!DataBase.existsBuyerByEmail(email))
            throw new NotFoundException("Logged-in user does not exist!");

        Buyer buyer = DataBase.findBuyerByEmail(email);
        String name = buyer.getName();
        int passwordSize = buyer.getPassword().length();

        return new BuyerResponse(name, email, passwordSize);
    }
}