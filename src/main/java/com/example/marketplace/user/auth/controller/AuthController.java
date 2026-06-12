package main.java.com.example.marketplace.user.auth.controller;

import main.java.com.example.marketplace.shared.utils.Normalizer;
import main.java.com.example.marketplace.user.auth.dto.AuthRequest;
import main.java.com.example.marketplace.user.auth.repository.AuthRepository;
import main.java.com.example.marketplace.user.auth.view.AuthView;
import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.shared.utils.Validator;

public final class AuthController {

    private final AuthView view;
    private final AuthRepository repository;
    private final Session session;

    public AuthController(AuthView view, AuthRepository repository, Session session) {
        this.view = view;
        this.repository = repository;
        this.session = session;
    }

    public void login() {

        AuthRequest userRequest = view.collectEmailAndPassword();

        try {
            Validator.isValidEmail(userRequest.getEmail());
            Validator.isValidPassword(userRequest.getPassword());

            User user = repository.login(userRequest);

            if (user.getStore() == null)
                session.login(user.getEmail());
            else
                session.login(user.getEmail(), user.getCnpj(), user.getStoreName());

            view.printMessage("[✓] You are now logged in.");
        }
        catch (IllegalArgumentException | NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void register() {

        AuthRequest userRequest = view.collectRegistrationData();
        userRequest.setName(Normalizer.normalizeName(userRequest.getName()));

        try {
            Validator.isValidEmail(userRequest.getEmail());
            Validator.isValidPassword(userRequest.getPassword());
            Validator.isValidUserName(userRequest.getName());

            User user = new User(userRequest.getName(), userRequest.getEmail(), userRequest.getPassword());
            repository.save(user);

            session.login(user.getEmail());
            view.printMessage("[+] Account successfully created!");
        }
        catch (IllegalArgumentException | AlreadyExistsException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void logout() {

        session.logout();
        view.printMessage("[*] You are now logged out.");
    }
}