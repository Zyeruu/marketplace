package main.java.com.example.marketplace.buyer.account.repository;

import main.java.com.example.marketplace.buyer.account.dto.AccountResponse;
import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.NotFoundException;

public final class AccountRepository {

    public void deleteAccount(String email, String password) {

        if (!DataBase.existsBuyerByEmailAndPassword(email, password))
            throw new NotFoundException("Incorrect password.");

        Buyer buyer = DataBase.findBuyerByEmail(email);
        DataBase.deleteBuyer(buyer);
    }

    public AccountResponse findByEmail(String email) {

        if (!DataBase.existsBuyerByEmail(email))
            throw new NotFoundException("Logged-in user does not exist.");

        Buyer buyer = DataBase.findBuyerByEmail(email);
        String name = buyer.getName();
        int passwordSize = buyer.getPassword().length();

        return new AccountResponse(name, email, passwordSize);
    }
}
