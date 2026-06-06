package main.java.com.example.marketplace.buyer.account.controller;

import main.java.com.example.marketplace.buyer.account.dto.AccountResponse;
import main.java.com.example.marketplace.buyer.account.repository.AccountRepository;
import main.java.com.example.marketplace.buyer.account.view.AccountView;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.session.BuyerSession;

public final class AccountController {

    private AccountRepository repository;
    private AccountView view;

    public void printBuyer() {

        try {
            AccountResponse accountResponse = repository.findByEmail();
            view.printBuyerProfile(accountResponse);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void deleteAccount() {

        String password = view.getPassword();

        try {
            repository.deleteAccount(password);
            BuyerSession.logout();
            view.printMessage("Your account has been successfully deleted!");
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }
}
