package main.java.com.example.marketplace.buyer.auth.repository;

import main.java.com.example.marketplace.buyer.auth.dto.BuyerAuthRequest;
import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;

public final class BuyerAuthRepository {

    private final DataBase dataBase;

    public BuyerAuthRepository(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public void save(Buyer buyer) {

        if (dataBase.existsBuyerByEmail(buyer.getEmail()))
            throw new AlreadyExistsException("[!] E-mail already registered.");

        dataBase.saveBuyer(buyer);
    }

    public void login(BuyerAuthRequest user) {

        if (!dataBase.existsBuyerByEmailAndPassword(user.getEmail(), user.getPassword()))
            throw new NotFoundException("[!] Incorrect e-mail or password.");
    }
}
