package main.java.com.example.marketplace.user.account.controller;

import main.java.com.example.marketplace.shared.utils.IdGenerator;
import main.java.com.example.marketplace.user.account.service.AccountService;
import main.java.com.example.marketplace.user.store.model.Store;
import main.java.com.example.marketplace.user.account.dto.AccountResponse;
import main.java.com.example.marketplace.user.account.view.AccountView;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.session.Session;

public final class AccountController {

    private final AccountView view;
    private final AccountService service;
    private final Session session;

    public AccountController(AccountView view, AccountService service, Session session) {
        this.view = view;
        this.service = service;
        this.session = session;
    }

    public void printProfile() {

        String email = session.getLoggedUserEmail();

        try {
            AccountResponse accountResponse = service.findProfileByEmail(email);
            view.printBuyerProfile(accountResponse);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void createStore() {

        String email = session.getLoggedUserEmail();
        String storeName = view.getStoreName();

        try {
            storeName = service.normalizeAndValidateStoreName(storeName);
            service.verifyStoreName(storeName);

            String cnpj = IdGenerator.generateCnpj();
            Store store = new Store(storeName, cnpj);

            service.saveStore(email, store);
            session.updateHasStore(storeName, cnpj, true);

            view.printMessage("[+] Store successfully created!");
        }
        catch (NotFoundException | IllegalArgumentException | AlreadyExistsException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void deleteStore() {

        String email = session.getLoggedUserEmail();
        String password = view.getPassword();

        try {
            service.verifyPassword(email, password);

            if (view.getFinalDecision()) {
                service.deleteStore(email);
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

        String email = session.getLoggedUserEmail();
        String password = view.getPassword();

        try {
            service.verifyPassword(email, password);

            String newEmail = view.getEmail();
            service.validateAndUpdateEmail(email, newEmail);

            session.setLoggedUserEmail(newEmail);
            view.printMessage("[*] E-mail successfully changed!");
        }
        catch (NotFoundException | IllegalArgumentException | AlreadyExistsException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void changePassword() {

        String email = session.getLoggedUserEmail();
        String password = view.getPassword();

        try {
            service.verifyPassword(email, password);

            String newPassword = view.getNewPassword();
            service.validateAndUpdatePassword(email, newPassword);

            view.printMessage("[*] Password successfully changed!");
        }
        catch (NotFoundException | IllegalArgumentException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void deleteAccount() {

        String email = session.getLoggedUserEmail();
        String password = view.getPassword();

        try {
            service.verifyPassword(email, password);

            service.deleteAccount(email);
            session.logout();

            view.printMessage("[-] Your account has been successfully deleted!");
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }
}
