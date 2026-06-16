package main.java.com.example.marketplace.user.controller;

import main.java.com.example.marketplace.exceptions.*;
import main.java.com.example.marketplace.user.dto.CartRequest;
import main.java.com.example.marketplace.user.dto.CartResponse;
import main.java.com.example.marketplace.user.model.CartProduct;
import main.java.com.example.marketplace.user.repository.CartRepository;
import main.java.com.example.marketplace.user.view.CartView;
import main.java.com.example.marketplace.shared.enums.ProductType;

public final class CartController {

    private final CartView view ;
    private final CartRepository repository;

    public CartController(CartView view, CartRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public boolean printCart() {

        try {
            CartResponse cartResponse = repository.findByEmail();
            view.printBuyerCart(cartResponse);
            return true;
        }
        catch (NotFoundException | EmptyCartException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean printSelected() {

        try {
            CartResponse cartResponse = repository.findByEmailAndSelected();
            view.printBuyerCart(cartResponse);
            return true;
        }
        catch (NotFoundException | EmptyCartException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean printDeselected() {

        try {
            CartResponse cartResponse = repository.findByEmailAndDeselected();
            view.printBuyerCart(cartResponse);
            return true;
        }
        catch (NotFoundException | EmptyCartException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean printCartByProductType() {

        ProductType productType = view.getProductType();

        try {
            CartResponse cartResponse = repository.findByEmailAndProductType(productType);
            view.printBuyerCart(cartResponse);
            return true;
        }
        catch (NotFoundException | EmptyCartException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean printCartByProductName() {

        String productName = view.getProductName();

        try {
            CartResponse cartResponse = repository.findByEmailAndProductName(productName);
            view.printBuyerCart(cartResponse);
            return true;
        }
        catch (NotFoundException | EmptyCartException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean printAllProductDetails() {

        String productId = view.getProductId();

        try {
            CartProduct cartProduct = repository.findByEmailAndProductId(productId);
            view.printCartProduct(cartProduct);

            return repository.existsReviewByProductId(productId);
        }
        catch (NotFoundException | EmptyCartException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public void addProduct() {

        CartRequest cartRequest = view.getProductData();

        try {
            repository.addProduct(cartRequest);
            view.printMessage("[+] Product added.");
        }
        catch (NotFoundException | InsufficientStockException | OwnProductException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void addProductByStoreNameAndProductId() {

        CartRequest cartRequest = view.getProductData();

        try {
            repository.addProductByStoreNameAndProductId(cartRequest);
            view.printMessage("[+] Product added.");
        }
        catch (NotFoundException | InsufficientStockException | EmptyCatalogException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void removeProduct() {

        CartRequest cartRequest = view.getProductData();

        try {
            repository.removeProduct(cartRequest);
            view.printMessage("[-] Product removed.");
        }
        catch (NotFoundException | EmptyCartException e) {
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
