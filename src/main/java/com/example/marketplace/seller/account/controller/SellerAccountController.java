package main.java.com.example.marketplace.seller.account.controller;

import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.seller.account.dto.SellerAccountResponse;
import main.java.com.example.marketplace.seller.account.repository.SellerAccountRepository;
import main.java.com.example.marketplace.seller.account.view.SellerAccountView;
import main.java.com.example.marketplace.shared.session.SellerSession;

public final class SellerAccountController {

    private final SellerAccountRepository repository = new SellerAccountRepository();
    private final SellerAccountView view = new SellerAccountView();

    public void printSeller() {

        try {
            SellerAccountResponse accountResponse = repository.findByEmail();
            view.printSellerProfile(accountResponse);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void deleteAccount() {

        String password = view.getPassword();

        try {
            repository.deleteAccount(password);
            SellerSession.logout();
            view.printMessage("Your account has been successfully deleted!");
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }
}