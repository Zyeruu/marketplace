package main.java.com.example.marketplace.seller.account.repository;

import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.seller.model.Seller;

public final class AccountRepository {

    public void deleteAccount(String email, String password) {

        if (!DataBase.existsSellerByEmailAndPassword(email, password))
            throw new NotFoundException("Incorrect password.");

        Seller seller = DataBase.findSellerByEmail(email);
        DataBase.deleteSeller(seller);
    }
}
