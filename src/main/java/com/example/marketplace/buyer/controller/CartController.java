package main.java.com.example.marketplace.buyer.controller;

import main.java.com.example.marketplace.buyer.dto.CartRequest;
import main.java.com.example.marketplace.buyer.dto.CartResponse;
import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.buyer.repository.CartRepository;
import main.java.com.example.marketplace.buyer.view.CartView;
import main.java.com.example.marketplace.exceptions.InsufficientStockException;
import main.java.com.example.marketplace.exceptions.NotFoundException;

public final class CartController {

    private Buyer buyer;
    private CartRepository repository;
    private CartView view;

    public void printCart() {

        try {
            CartResponse cartResponse = repository.findByEmail();
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void addItem() {

        CartRequest cartRequest = view.getItemData();

        try {
            repository.addItem(cartRequest);
            view.printMessage("Item added.");
        }
        catch (NotFoundException | InsufficientStockException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void removeItem() {

        CartRequest cartRequest = view.getItemData();

        try {
            repository.removeItem(cartRequest);
            view.printMessage("Item removed.");
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }
}
