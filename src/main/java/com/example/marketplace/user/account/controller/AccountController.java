package main.java.com.example.marketplace.user.account.controller;

import main.java.com.example.marketplace.shared.utils.IdGenerator;
import main.java.com.example.marketplace.shared.utils.Normalizer;
import main.java.com.example.marketplace.user.store.model.Store;
import main.java.com.example.marketplace.user.account.dto.AccountResponse;
import main.java.com.example.marketplace.user.account.repository.AccountRepository;
import main.java.com.example.marketplace.user.account.view.AccountView;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.shared.utils.Validator;

public final class AccountController {

    private final AccountView view;
    private final AccountRepository repository;
    private final Session session;

    public AccountController(AccountView view, AccountRepository repository, Session session) {
        this.view = view;
        this.repository = repository;
        this.session = session;
    }

    public void printProfile() {

        try {
            AccountResponse accountResponse = repository.findByEmail();
            view.printBuyerProfile(accountResponse);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void createStore() {

        String storeName = view.getStoreName();
        storeName = Normalizer.normalizeName(storeName);

        try {
            Validator.isValidStoreName(storeName);
            repository.verifyStoreName(storeName);

            String cnpj = IdGenerator.generateCnpj();
            Store store = new Store(storeName, cnpj);

            repository.saveStore(store);
            session.updateHasStore(storeName, cnpj, true);
            view.printMessage("[+] Store successfully created!");
        }
        catch (NotFoundException | IllegalArgumentException | AlreadyExistsException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void deleteStore() {

        String password = view.getPassword();

        try {
            repository.verifyPassword(password);

            if (view.getFinalResponse()) {
                repository.deleteStore();
                session.updateHasStore(null, null, false);
                view.printMessage("[-] Store successfully deleted!");
            }
            else
                view.printMessage("[X] Action canceled.");
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void changeEmail() {

        String password = view.getPassword();

        try {
            repository.verifyPassword(password);
            String newEmail = view.getEmail();
            Validator.isValidEmail(newEmail);
            repository.updateEmail(newEmail);
            session.setEmail(newEmail);
            view.printMessage("[*] E-mail successfully changed!");
        }
        catch (NotFoundException | IllegalArgumentException | AlreadyExistsException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void changePassword() {

        String password = view.getPassword();

        try {
            repository.verifyPassword(password);
            String newPassword = view.getNewPassword();
            Validator.isValidPassword(newPassword);
            repository.updatePassword(newPassword);
            view.printMessage("[*] Password successfully changed!");
        }
        catch (NotFoundException | IllegalArgumentException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void deleteAccount() {

        String password = view.getPassword();

        try {
            repository.deleteAccount(password);
            session.logout();
            view.printMessage("[-] Your account has been successfully deleted!");
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }
}
