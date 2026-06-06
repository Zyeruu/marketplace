package main.java.com.example.marketplace.buyer.auth.repository;

import main.java.com.example.marketplace.buyer.auth.dto.BuyerAuthRequest;
import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;

public final class BuyerAuthRepository {

    public void save(Buyer buyer) {

        if (!DataBase.existsBuyerByEmail(buyer.getEmail()))
            throw new AlreadyExistsException("The email address entered is already registered.");

        DataBase.saveBuyer(buyer);
    }

    public void login(BuyerAuthRequest user) {

        if (!DataBase.existsBuyerByEmailAndPassword(user.getEmail(), user.getPassword()))
            throw new NotFoundException("Invalid email or password.");
    }
}
