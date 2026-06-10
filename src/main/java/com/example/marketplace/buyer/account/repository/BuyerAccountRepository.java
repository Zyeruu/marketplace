package main.java.com.example.marketplace.buyer.account.repository;

import main.java.com.example.marketplace.buyer.account.dto.BuyerAccountResponse;
import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.session.Session;

public final class BuyerAccountRepository {

    private final DataBase dataBase;
    private final Session session;

    public BuyerAccountRepository(DataBase dataBase, Session session) {
        this.dataBase = dataBase;
        this.session = session;
    }

    public BuyerAccountResponse findByEmail() {

        String email = session.getEmail();

        Buyer buyer = dataBase.findBuyerByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] User not found.");

        String name = buyer.getName();
        int passwordSize = buyer.getPassword().length();

        return new BuyerAccountResponse(name, email, passwordSize);
    }

    public void updateEmail(String newEmail) {

        String currentEmail = session.getEmail();
        Buyer buyer = dataBase.findBuyerByEmail(currentEmail);

        if (buyer == null)
            throw new NotFoundException("[!] User not found.");

        if (currentEmail.equals(newEmail))
            throw new IllegalArgumentException("[!] The new e-mail must be different from the current e-mail.");

        if (dataBase.existsBuyerByEmail(newEmail))
            throw new AlreadyExistsException("[!] E-mail already registered.");

        buyer.setEmail(newEmail);
    }

    public void updatePassword(String newPassword) {

        String email = session.getEmail();
        Buyer buyer = dataBase.findBuyerByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] User not found.");

        if (buyer.getPassword().equals(newPassword))
            throw new IllegalArgumentException("[!] New password must be different from the current password.");

        buyer.setPassword(newPassword);
    }

    public void deleteAccount(String password) {

        String email = session.getEmail();
        Buyer buyer = dataBase.findBuyerByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] User not found.");

        if (!buyer.getPassword().equals(password))
            throw new NotFoundException("[!] Incorrect password.");

        dataBase.deleteBuyer(buyer);
    }

    public void verifyPassword(String password) {

        String email = session.getEmail();
        Buyer buyer = dataBase.findBuyerByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] User not found");

        if (!buyer.getPassword().equals(password))
            throw new NotFoundException("[!] Incorrect password.");
    }
}
