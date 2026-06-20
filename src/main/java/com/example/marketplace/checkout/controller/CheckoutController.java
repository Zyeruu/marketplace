package main.java.com.example.marketplace.checkout.controller;

import main.java.com.example.marketplace.checkout.service.CheckoutService;
import main.java.com.example.marketplace.exceptions.*;
import main.java.com.example.marketplace.checkout.model.PaymentMethod;
import main.java.com.example.marketplace.checkout.view.CheckoutView;
import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.shared.utils.Formatter;
import main.java.com.example.marketplace.user.model.User;

public final class CheckoutController {

    private final CheckoutView view;
    private final CheckoutService service;
    private final Session session;

    public CheckoutController(CheckoutView view, CheckoutService service, Session session) {
        this.view = view;
        this.service = service;
        this.session = session;
    }

    public void checkout() {

        String email = session.getLoggedUserEmail();

        try {
            User buyer = service.findUserByEmail(email);
            service.verifyCart(buyer.getCart());
            PaymentMethod paymentMethod = view.selectPaymentMethod();
            view.printMessage("[*] Processing payment of R$" + Formatter.formatFloat(buyer.getCartTotalCost()) + "...");
            view.printMessage("[✓] Payment confirmed!");
            service.saveOrder(buyer, paymentMethod);
            service.updateCartAndCatalog(buyer.getCart());
            view.printMessage("[✓] Purchase confirmed!");
        }
        catch (NotFoundException | EmptyCartException | InsufficientStockException | OutdatedProductException e) {
            User buyer = service.findUserByEmail(email);
            service.updateOutdatedCart(buyer.getCart());
            view.printMessage(e.getMessage());
        }
        catch (RuntimeException e) {
            view.printMessage("[!] An unexpected error occurred.");
        }
    }
}
