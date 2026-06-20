package main.java.com.example.marketplace.user.account.service;

import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.utils.Normalizer;
import main.java.com.example.marketplace.shared.utils.Validator;
import main.java.com.example.marketplace.user.account.dto.AccountResponse;
import main.java.com.example.marketplace.user.account.repository.AccountRepository;
import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.user.store.model.Store;

public final class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public User findUserByEmail(String email) {

        User user = repository.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        return user;
    }

    public User findSellerByStoreName(String storeName) {

        User seller = repository.findSellerByStoreName(storeName);

        if (seller == null)
            throw new NotFoundException("[!] Seller not found.");

        return seller;
    }

    public User findSellerByCnpj(String cnpj) {
        return repository.findSellerByCnpj(cnpj);
    }

    public AccountResponse findProfileByEmail(String email) {

        User user = findUserByEmail(email);
        return new AccountResponse(user.getName(), user.getEmail(), user.getPassword().length());
    }

    public void saveStore(String email, Store store) {

        User user = findUserByEmail(email);
        repository.saveStore(user, store);
    }

    public void deleteAccount(String email) {

        User user = findUserByEmail(email);
        repository.deleteAccount(user);
    }

    public void deleteStore(String email) {

        User user = findUserByEmail(email);
        repository.deleteStore(user);
    }

    public String normalizeAndValidateStoreName(String storeName) {

        storeName = Normalizer.normalizeStoreName(storeName);
        Validator.isValidStoreName(storeName);
        return storeName;
    }

    public void validateAndUpdateEmail(String currentEmail, String newEmail) {

        User user = findUserByEmail(currentEmail);

        Validator.isValidEmail(newEmail);

        if (repository.existsUserByEmail(newEmail))
            throw new AlreadyExistsException("[!] E-mail already registered.");

        if (currentEmail.equals(newEmail))
            throw new IllegalArgumentException("[!] The new e-mail must be different from the current e-mail.");

        if (user.getPassword().equalsIgnoreCase(newEmail))
            throw new IllegalArgumentException("[!] Your e-mail cannot be the same as your password address.");

        repository.updateEmail(user, newEmail);
    }

    public void validateAndUpdatePassword(String email, String password) {

        User user = findUserByEmail(email);

        Validator.isValidPassword(password);

        if (user.getPassword().equals(password))
            throw new IllegalArgumentException("[!] The new password must be different from the current password.");

        if (user.getEmail().equalsIgnoreCase(password))
            throw new IllegalArgumentException("[!] Your password cannot be the same as your e-mail address.");

        repository.updatePassword(user, password);
    }

    public void verifyStoreName(String storeName) {

        if (repository.verifyStoreName(storeName))
            throw new AlreadyExistsException("[!] Store name already registered.");
    }

    public void verifyPassword(String email, String password) {

        User user = findUserByEmail(email);

        if (!user.getPassword().equals(password))
            throw new NotFoundException("[!] Incorrect password.");
    }
}
