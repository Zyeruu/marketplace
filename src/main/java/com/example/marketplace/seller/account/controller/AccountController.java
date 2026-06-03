package main.java.com.example.marketplace.seller.account.controller;

import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.seller.account.repository.AccountRepository;
import main.java.com.example.marketplace.seller.account.view.AccountView;
import main.java.com.example.marketplace.shared.session.SellerSession;

public final class AccountController {

    private AccountRepository repository;
    private AccountView view;

    public void deleteAccount(String email) {

        String password = view.getPassword();

        try {
            repository.deleteAccount(email, password);
            SellerSession.logout();
            view.printMessage("Your account has been successfully deleted!");
        }
        catch (NotFoundException e) {
            view.printException(e);
        }
    }
}
