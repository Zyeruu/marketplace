package main.java.com.example.marketplace.seller.auth.controller;

import main.java.com.example.marketplace.seller.auth.dto.AuthRequest;
import main.java.com.example.marketplace.seller.auth.repository.AuthRepository;
import main.java.com.example.marketplace.seller.auth.view.AuthView;
import main.java.com.example.marketplace.seller.model.Seller;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.seller.model.Store;
import main.java.com.example.marketplace.shared.Validator;
import main.java.com.example.marketplace.shared.session.BuyerSession;
import main.java.com.example.marketplace.shared.session.SellerSession;

import java.text.Normalizer;

public final class AuthController {

    private AuthRepository repository;
    private AuthView view;

    public void register() {

        AuthRequest user = view.collectRegistrationData();
        user.setName(normalizeUserName(user.getName()));
        user.setStoreName(normalizeUserName(user.getStoreName()));

        try {
            Validator.isValidEmail(user.getEmail());
            Validator.isValidPassword(user.getPassword());
            Validator.isValidUserName(user.getName());
            Validator.isValidStoreName(user.getStoreName());

            String cnpj = generateCnpj();
            Store store = new Store(user.getStoreName(), cnpj);
            Seller seller = new Seller(user.getName(), user.getEmail(), user.getPassword(), store);
            repository.save(seller);
            view.printMessage("Account created successfully!");
        }
        catch (IllegalArgumentException e) {
            view.printException(e.getMessage());
        }
    }

    public void login() {

        AuthRequest user = view.collectEmailAndPassword();

        try {
            Validator.isValidEmail(user.getEmail());
            Validator.isValidPassword(user.getPassword());
            repository.login(user);
            BuyerSession.login(user.getEmail());
            view.printMessage("You are now logged in.");
        }
        catch (IllegalArgumentException | NotFoundException e) {
            view.printException(e.getMessage());
        }
    }

    public void logout() {
        SellerSession.logout();
        view.printMessage("You are now logged out.");
    }

    public String normalizeUserName(String userName) {

        userName = Normalizer.normalize(userName, Normalizer.Form.NFD);
        return userName.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").toUpperCase();
    }

    public String generateCnpj() {

        int numberOfSellers = repository.searchNumberOfSellers();

        return String.format("%014d", numberOfSellers)
                .replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
    }
}
