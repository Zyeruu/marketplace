package main.java.com.example.marketplace.buyer.auth.controller;

import main.java.com.example.marketplace.buyer.auth.dto.BuyerAuthRequest;
import main.java.com.example.marketplace.buyer.auth.repository.BuyerAuthRepository;
import main.java.com.example.marketplace.buyer.auth.view.BuyerAuthView;
import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.session.BuyerSession;
import main.java.com.example.marketplace.shared.utils.Validator;

import java.text.Normalizer;

public final class BuyerAuthController {

    private final BuyerAuthRepository repository = new BuyerAuthRepository();
    private final BuyerAuthView view = new BuyerAuthView();

    public void register() {

        BuyerAuthRequest user = view.collectRegistrationData();
        user.setName(normalizeName(user.getName()));

        try {
            Validator.isValidEmail(user.getEmail());
            Validator.isValidPassword(user.getPassword());
            Validator.isValidUserName(user.getName());
            Buyer buyer = new Buyer(user.getName(), user.getEmail(), user.getPassword());
            repository.save(buyer);
            BuyerSession.login(user.getEmail());
            view.printMessage("[+] Account successfully created!");
        }
        catch (IllegalArgumentException | AlreadyExistsException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void login() {

        BuyerAuthRequest user = view.collectEmailAndPassword();

        try {
            Validator.isValidEmail(user.getEmail());
            Validator.isValidPassword(user.getPassword());
            repository.login(user);
            BuyerSession.login(user.getEmail());
            view.printMessage("[✓] You are now logged in.");
        }
        catch (IllegalArgumentException | NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public String normalizeName(String userName) {

        userName = Normalizer.normalize(userName, Normalizer.Form.NFD);
        return userName.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").toUpperCase();
    }
}