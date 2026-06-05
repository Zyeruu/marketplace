package main.java.com.example.marketplace.seller.auth.repository;

import main.java.com.example.marketplace.seller.auth.dto.AuthRequest;
import main.java.com.example.marketplace.seller.model.Seller;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;

public final class AuthRepository {

    public void save(Seller seller) {

        if (!DataBase.existsSellerByEmail(seller.getEmail()))
            throw new AlreadyExistsException("The email address entered is already registered.");

        DataBase.saveSeller(seller);
    }

    public void login(AuthRequest user) {

        if (!DataBase.existsSellerByEmailAndPassword(user.getEmail(), user.getPassword()))
            throw new NotFoundException("Invalid email or password.");
    }

    public int searchNumberOfSellers() {

        return DataBase.getNumberOfSellers();
    }
}
