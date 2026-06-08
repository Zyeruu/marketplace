package main.java.com.example.marketplace.checkout.controller;

import main.java.com.example.marketplace.exceptions.EmptyCartException;
import main.java.com.example.marketplace.exceptions.InsufficientStockException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.checkout.model.PaymentMethod;
import main.java.com.example.marketplace.checkout.repository.CheckoutRepository;
import main.java.com.example.marketplace.checkout.view.CheckoutView;
import main.java.com.example.marketplace.exceptions.OutdatedPriceException;

public final class CheckoutController {

    private final CheckoutRepository repository = new CheckoutRepository();
    private final CheckoutView view = new CheckoutView();

    public void checkout() {

        try {
            repository.verifyCart();
            PaymentMethod paymentMethod = view.selectPaymentMethod();
            float totalCost = repository.getTotalCost();
            view.printMessage("Processing payment of R$" + formatFloat(totalCost) + "...");
            view.printMessage("Payment confirmed!");
            repository.saveOrder(paymentMethod);
            view.printMessage("Purchase confirmed!");
        }
        catch (EmptyCartException | NotFoundException | InsufficientStockException | OutdatedPriceException e) {
            repository.updateCart();
            view.printMessage(e.getMessage());
        }
    }

    public String formatFloat(float value) {
        return String.format("%.2f", value);
    }
}
