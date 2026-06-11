package main.java.com.example.marketplace.buyer.controller;

import main.java.com.example.marketplace.buyer.dto.CartRequest;
import main.java.com.example.marketplace.buyer.dto.CartResponse;
import main.java.com.example.marketplace.buyer.model.CartProduct;
import main.java.com.example.marketplace.buyer.repository.CartRepository;
import main.java.com.example.marketplace.buyer.view.CartView;
import main.java.com.example.marketplace.exceptions.EmptyCartException;
import main.java.com.example.marketplace.exceptions.InsufficientStockException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.exceptions.ProductSelectionStateException;
import main.java.com.example.marketplace.shared.enums.ProductType;

public final class CartController {

    private final CartView view ;
    private final CartRepository repository;

    public CartController(CartView view, CartRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void printCart() {

        try {
            CartResponse cartResponse = repository.findByEmail();
            view.printBuyerCart(cartResponse);
        }
        catch (NotFoundException | EmptyCartException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void printSelected() {

        try {
            CartResponse cartResponse = repository.findByEmailAndSelected();
            view.printBuyerCart(cartResponse);
        }
        catch (NotFoundException | EmptyCartException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void printDeselected() {

        try {
            CartResponse cartResponse = repository.findByEmailAndDeselected();
            view.printBuyerCart(cartResponse);
        }
        catch (NotFoundException | EmptyCartException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void printCartByProductType() {

        ProductType productType = view.getProductType();

        try {
            CartResponse cartResponse = repository.findByEmailAndProductType(productType);
            view.printBuyerCart(cartResponse);
        }
        catch (NotFoundException | EmptyCartException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void printCartByProductName() {

        String productName = view.getProductName();

        try {
            CartResponse cartResponse = repository.findByEmailAndProductName(productName);
            view.printBuyerCart(cartResponse);
        }
        catch (NotFoundException | EmptyCartException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void printAllProductDetails() {

        String productId = view.getProductId();

        try {
            CartProduct cartProduct = repository.findByEmailAndProductId(productId);
            view.printCartProduct(cartProduct);
        }
        catch (NotFoundException | EmptyCartException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void addProduct() {

        CartRequest cartRequest = view.getProductData();

        try {
            repository.addProduct(cartRequest);
            view.printMessage("[+] Product added.");
        }
        catch (NotFoundException | InsufficientStockException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void removeProduct() {

        CartRequest cartRequest = view.getProductData();

        try {
            repository.removeProduct(cartRequest);
            view.printMessage("[-] Product removed.");
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void selectProduct() {

        String productId = view.getProductId();

        try {
            repository.selectProduct(productId);
            view.printMessage("[+] Product selected.");
        }
        catch (NotFoundException | EmptyCartException | ProductSelectionStateException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void deselectProduct() {

        String productId = view.getProductId();

        try {
            repository.deselectProduct(productId);
            view.printMessage("[-] Product deselected.");
        }
        catch (NotFoundException | EmptyCartException | ProductSelectionStateException e) {
            view.printMessage(e.getMessage());
        }
    }
}
