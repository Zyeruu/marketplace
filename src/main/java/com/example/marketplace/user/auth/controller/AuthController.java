package main.java.com.example.marketplace.user.auth.controller;

import main.java.com.example.marketplace.shared.utils.Normalizer;
import main.java.com.example.marketplace.user.auth.dto.AuthRequest;
import main.java.com.example.marketplace.user.auth.service.AuthService;
import main.java.com.example.marketplace.user.auth.view.AuthView;
import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.session.Session;

public final class AuthController {

    private final AuthView view;
    private final AuthService service;
    private final Session session;

    public AuthController(AuthView view, AuthService service, Session session) {
        this.view = view;
        this.service = service;
        this.session = session;
    }

    public void login() {

        AuthRequest request = view.collectEmailAndPassword();

        try {
            service.validateEmailAndPassword(request);
            User user = service.verifyEmailAndPassword(request);

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

        AuthRequest request = view.collectRegistrationData();
        request.setName(Normalizer.normalizeName(request.getName()));

        try {
            service.validateEmailAndPasswordAndUserName(request);

            User user = new User(request.getName(), request.getEmail(), request.getPassword());
            service.verifyEmailAndSave(user);

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