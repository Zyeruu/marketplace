package main.java.com.example.marketplace.seller.auth.repository;

import main.java.com.example.marketplace.seller.auth.dto.SellerAuthRequest;
import main.java.com.example.marketplace.seller.auth.dto.SellerAuthResponse;
import main.java.com.example.marketplace.seller.model.Seller;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;

public final class SellerAuthRepository {

    public void save(Seller seller) {

        if (DataBase.existsSellerByEmail(seller.getEmail()))
            throw new AlreadyExistsException("[!] E-mail already registered.");

        if (DataBase.existsSellerByStoreName(seller.getStore().getName()))
            throw new AlreadyExistsException("[!] Store name already registered.");

        DataBase.saveSeller(seller);
    }

    public SellerAuthResponse login(SellerAuthRequest user) {

        if (!DataBase.existsSellerByEmailAndPassword(user.getEmail(), user.getPassword()))
            throw new NotFoundException("[!] Incorrect e-mail or password.");

        Seller seller = DataBase.findSellerByEmailAndPassword(user.getEmail(), user.getPassword());

        return new SellerAuthResponse(seller.getStore().getName(), seller.getStore().getCnpj());
    }
}
