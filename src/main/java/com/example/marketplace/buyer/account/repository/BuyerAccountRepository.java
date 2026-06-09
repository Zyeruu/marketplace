package main.java.com.example.marketplace.buyer.account.repository;

import main.java.com.example.marketplace.buyer.account.dto.BuyerAccountResponse;
import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.session.BuyerSession;

public final class BuyerAccountRepository {

    public BuyerAccountResponse findByEmail() {

        String email = BuyerSession.getEmail();

        if (!DataBase.existsBuyerByEmail(email))
            throw new NotFoundException("[!] Logged-in user does not exist.");

        Buyer buyer = DataBase.findBuyerByEmail(email);
        String name = buyer.getName();
        int passwordSize = buyer.getPassword().length();

        return new BuyerAccountResponse(name, email, passwordSize);
    }

    public void updateEmail(String newEmail) {

        String currentEmail = BuyerSession.getEmail();

        if (currentEmail.equals(newEmail))
            throw new IllegalArgumentException("[!] The new e-mail must be different from the current e-mail.");

        DataBase.updateBuyerEmail(currentEmail, newEmail);
    }

    public void updatePassword(String newPassword) {

        String email = BuyerSession.getEmail();

        if (DataBase.isSameBuyerPassword(email, newPassword))
            throw new IllegalArgumentException("[!] The new password must be different from the current password.");

        DataBase.updateBuyerPassword(email, newPassword);
    }

    public void deleteAccount(String password) {

        String email = BuyerSession.getEmail();

        if (!DataBase.existsBuyerByEmailAndPassword(email, password))
            throw new NotFoundException("[!] Incorrect password.");

        Buyer buyer = DataBase.findBuyerByEmail(email);
        DataBase.deleteBuyer(buyer);
    }

    public void verifyPassword(String password) {

        String email = BuyerSession.getEmail();

        if (!DataBase.existsBuyerByEmailAndPassword(email, password))
            throw new NotFoundException("Incorrect password.");
    }
}
