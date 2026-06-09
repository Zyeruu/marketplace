package main.java.com.example.marketplace.seller.auth.controller;

import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.seller.auth.dto.SellerAuthRequest;
import main.java.com.example.marketplace.seller.auth.dto.SellerAuthResponse;
import main.java.com.example.marketplace.seller.auth.repository.SellerAuthRepository;
import main.java.com.example.marketplace.seller.auth.view.SellerAuthView;
import main.java.com.example.marketplace.seller.model.Seller;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.seller.model.Store;
import main.java.com.example.marketplace.shared.utils.IdGenerator;
import main.java.com.example.marketplace.shared.utils.Validator;
import main.java.com.example.marketplace.shared.session.SellerSession;

import java.text.Normalizer;

public final class SellerAuthController {

    private final SellerAuthRepository repository = new SellerAuthRepository();
    private final SellerAuthView view = new SellerAuthView();

    public void register() {

        SellerAuthRequest user = view.collectRegistrationData();
        user.setName(normalizeName(user.getName()));
        user.setStoreName(normalizeName(user.getStoreName()));

        try {
            Validator.isValidEmail(user.getEmail());
            Validator.isValidPassword(user.getPassword());
            Validator.isValidUserName(user.getName());
            Validator.isValidStoreName(user.getStoreName());

            String cnpj = IdGenerator.generateCnpj();
            Store store = new Store(user.getStoreName(), cnpj);
            Seller seller = new Seller(user.getName(), user.getEmail(), user.getPassword(), store);
            repository.save(seller);
            SellerSession.login(user.getEmail(), cnpj, store.getName());
            view.printMessage("[+] Account successfully created!");
        }
        catch (IllegalArgumentException | AlreadyExistsException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void login() {

        SellerAuthRequest user = view.collectEmailAndPassword();

        try {
            Validator.isValidEmail(user.getEmail());
            Validator.isValidPassword(user.getPassword());
            SellerAuthResponse authResponse = repository.login(user);
            SellerSession.login(user.getEmail(), authResponse.getCnpj(), authResponse.getStoreName());
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