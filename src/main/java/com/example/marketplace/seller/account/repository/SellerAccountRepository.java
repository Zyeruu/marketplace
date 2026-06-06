package main.java.com.example.marketplace.seller.account.repository;

import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.seller.account.dto.SellerAccountResponse;
import main.java.com.example.marketplace.seller.model.Seller;
import main.java.com.example.marketplace.shared.session.SellerSession;

public final class SellerAccountRepository {

    public SellerAccountResponse findByEmail() {

        String email = SellerSession.getEmail();

        if (!DataBase.existsSellerByEmail(email))
            throw new NotFoundException("Logged-in user does not exist.");

        Seller seller = DataBase.findSellerByEmail(email);
        String name = seller.getName();
        int passwordSize = seller.getPassword().length();

        return new SellerAccountResponse(name, email, passwordSize);
    }

    public void deleteAccount(String password) {

        String email = SellerSession.getEmail();

        if (!DataBase.existsSellerByEmailAndPassword(email, password))
            throw new NotFoundException("Incorrect password.");

        Seller seller = DataBase.findSellerByEmail(email);
        DataBase.deleteSeller(seller);
    }
}
