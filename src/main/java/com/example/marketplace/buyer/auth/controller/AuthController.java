package main.java.com.example.marketplace.buyer.auth.controller;

import main.java.com.example.marketplace.buyer.auth.dto.AuthRequest;
import main.java.com.example.marketplace.buyer.auth.repository.AuthRepository;
import main.java.com.example.marketplace.buyer.auth.view.AuthView;
import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.Validator;
import main.java.com.example.marketplace.shared.session.SellerSession;

import java.text.Normalizer;

public final class AuthController {

    private AuthRepository repository;
    private AuthView view;

    public void register() {

        AuthRequest user = view.collectRegistrationData();
        user.setName(normalizeUserName(user.getName()));

        try {
            Validator.isValidEmail(user.getEmail());
            Validator.isValidPassword(user.getPassword());
            Validator.isValidUserName(user.getName());
            Buyer buyer = new Buyer(user.getName(), user.getEmail(), user.getPassword());
            repository.save(buyer);
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
            SellerSession.login(user.getEmail());
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
}