package main.java.com.example.marketplace.buyer.account.repository;

import main.java.com.example.marketplace.buyer.account.dto.BuyerAccountResponse;
import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.session.BuyerSession;

public final class BuyerAccountRepository {

    public void deleteAccount(String password) {

        String email = BuyerSession.getEmail();

        if (!DataBase.existsBuyerByEmailAndPassword(email, password))
            throw new NotFoundException("Incorrect password.");

        Buyer buyer = DataBase.findBuyerByEmail(email);
        DataBase.deleteBuyer(buyer);
    }

    public BuyerAccountResponse findByEmail() {

        String email = BuyerSession.getEmail();

        if (!DataBase.existsBuyerByEmail(email))
            throw new NotFoundException("Logged-in user does not exist.");

        Buyer buyer = DataBase.findBuyerByEmail(email);
        String name = buyer.getName();
        int passwordSize = buyer.getPassword().length();

        return new BuyerAccountResponse(name, email, passwordSize);
    }
}
