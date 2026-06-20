package main.java.com.example.marketplace.user.auth.service;

import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.utils.Validator;
import main.java.com.example.marketplace.user.auth.dto.AuthRequest;
import main.java.com.example.marketplace.user.auth.repository.AuthRepository;
import main.java.com.example.marketplace.user.model.User;

public final class AuthService {

    private final AuthRepository repository;

    public AuthService(AuthRepository repository) {
        this.repository = repository;
    }

    public void validateEmailAndPassword(AuthRequest request) {

        Validator.isValidEmail(request.getEmail());
        Validator.isValidPassword(request.getPassword());
    }

    public void verifyEmailAndSave(User user) {

        if (repository.existsByEmail(user.getEmail()))
             throw new AlreadyExistsException("E-mail already registered.");

        if (user.getPassword().equalsIgnoreCase(user.getEmail()))
            throw new IllegalArgumentException("[!] Your password cannot be the same as your e-mail address.");

        repository.save(user);
    }

    public User verifyEmailAndPassword(AuthRequest request) {

        User user = repository.findByEmailAndPassword(request);

        if (user == null)
            throw new NotFoundException("[!] Invalid e-mail or password.");

        return user;
    }

    public void validateEmailAndPasswordAndUserName(AuthRequest request) {

        Validator.isValidEmail(request.getEmail());
        Validator.isValidPassword(request.getPassword());
        Validator.isValidUserName(request.getName());
    }
}
