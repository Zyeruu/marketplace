package main.java.com.example.marketplace.buyer.controller;

import main.java.com.example.marketplace.buyer.dto.CartResponse;
import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.buyer.repository.CartRepository;
import main.java.com.example.marketplace.buyer.view.CartView;
import main.java.com.example.marketplace.exceptions.NotFoundException;

public final class CartController {

    private Buyer buyer;
    private CartRepository repository;
    private CartView view;

    public void printCart(String email) {

        try {
            CartResponse cartResponse = repository.findByEmail(email);

            if (!cartResponse.getCartItems().isEmpty())
                view.printMessage("Your cart is empty!");

            view.printBuyerCart(cartResponse);
        }
        catch (NotFoundException e) {
            view.printException(e);
        }
    }
}
