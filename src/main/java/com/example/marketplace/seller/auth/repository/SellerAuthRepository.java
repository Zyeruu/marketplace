package main.java.com.example.marketplace.seller.auth.repository;

import main.java.com.example.marketplace.seller.auth.dto.SellerAuthRequest;
import main.java.com.example.marketplace.seller.auth.dto.SellerAuthResponse;
import main.java.com.example.marketplace.seller.model.Seller;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;

public final class SellerAuthRepository {

    private final DataBase dataBase;

    public SellerAuthRepository(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public void save(Seller seller) {

        if (dataBase.existsSellerByEmail(seller.getEmail()))
            throw new AlreadyExistsException("[!] E-mail already registered.");

        if (dataBase.existsSellerByStoreName(seller.getStore().getName()))
            throw new AlreadyExistsException("[!] Store name already registered.");

        dataBase.saveSeller(seller);
    }

    public SellerAuthResponse login(SellerAuthRequest user) {

        Seller seller = dataBase.findSellerByEmail(user.getEmail());

        if (seller == null)
            throw new NotFoundException("[!] Incorrect e-mail or password.");

        if (!seller.getPassword().equals(user.getPassword()))
            throw new NotFoundException("[!] Incorrect e-mail or password.");

        return new SellerAuthResponse(seller.getStoreName(), seller.getCnpj());
    }
}
