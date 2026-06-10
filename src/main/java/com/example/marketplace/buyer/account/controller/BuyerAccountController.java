package main.java.com.example.marketplace.buyer.account.controller;

import main.java.com.example.marketplace.buyer.account.dto.BuyerAccountResponse;
import main.java.com.example.marketplace.buyer.account.repository.BuyerAccountRepository;
import main.java.com.example.marketplace.buyer.account.view.BuyerAccountView;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.shared.utils.Validator;

public final class BuyerAccountController {

    private final BuyerAccountView view;
    private final BuyerAccountRepository repository;
    private final Session session;

    public BuyerAccountController(BuyerAccountView view, BuyerAccountRepository repository, Session session) {
        this.view = view;
        this.repository = repository;
        this.session = session;
    }

    public void printBuyer() {

        try {
            BuyerAccountResponse accountResponse = repository.findByEmail();
            view.printBuyerProfile(accountResponse);
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
