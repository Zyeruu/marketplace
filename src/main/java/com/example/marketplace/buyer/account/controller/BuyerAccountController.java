package main.java.com.example.marketplace.buyer.account.controller;

import main.java.com.example.marketplace.buyer.account.dto.BuyerAccountResponse;
import main.java.com.example.marketplace.buyer.account.repository.BuyerAccountRepository;
import main.java.com.example.marketplace.buyer.account.view.BuyerAccountView;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.session.BuyerSession;

public final class BuyerAccountController {

    private final BuyerAccountRepository repository = new BuyerAccountRepository();
    private final BuyerAccountView view = new BuyerAccountView();

    public void printBuyer() {

        try {
            BuyerAccountResponse accountResponse = repository.findByEmail();
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
