package main.java.com.example.marketplace.checkout.controller;

import main.java.com.example.marketplace.exceptions.EmptyCartException;
import main.java.com.example.marketplace.exceptions.InsufficientStockException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.checkout.dto.CheckoutRequest;
import main.java.com.example.marketplace.checkout.model.PaymentMethod;
import main.java.com.example.marketplace.checkout.repository.CheckoutRepository;
import main.java.com.example.marketplace.checkout.view.CheckoutView;
import main.java.com.example.marketplace.exceptions.OutdatedPriceException;

public final class CheckoutController {

    CheckoutRepository repository;
    CheckoutView view;

    public void checkout() {

        try {
            repository.verifyCart();
            PaymentMethod paymentMethod = view.selectPaymentMethod();
            CheckoutRequest checkoutRequest = repository.getTotalCostAndShipping();
            checkoutRequest.setPaymentMethod(paymentMethod);
            view.printMessage("Processing payment of R$" + checkoutRequest.getTotalCost() + "...");
            view.printMessage("Payment confirmed!");
            repository.confirmOrder(checkoutRequest);
            view.printMessage("Purchase confirmed!");
        }
        catch (EmptyCartException | NotFoundException | InsufficientStockException | OutdatedPriceException e) {
            repository.updateCart();
            view.printMessage(e.getMessage());
        }
    }
}
