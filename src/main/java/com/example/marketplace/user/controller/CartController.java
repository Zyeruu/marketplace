package main.java.com.example.marketplace.user.controller;

import main.java.com.example.marketplace.exceptions.*;
import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.user.model.Cart;
import main.java.com.example.marketplace.user.model.CartProduct;
import main.java.com.example.marketplace.user.service.CartService;
import main.java.com.example.marketplace.user.view.CartView;
import main.java.com.example.marketplace.shared.enums.ProductType;

public final class CartController {

    private final CartView view ;
    private final CartService service;
    private final Session session;

    public CartController(CartView view, CartService service, Session session) {
        this.view = view;
        this.service = service;
        this.session = session;
    }

    public boolean printCart() {

        String email = session.getLoggedUserEmail();

        try {
            Cart cart = service.findAndCopyCartByEmail(email);
            view.printBuyerCart(cart);
            return true;
        }
        catch (NotFoundException | EmptyCartException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean printSelected() {

        String email = session.getLoggedUserEmail();

        try {
            Cart cart = service.findSelectedByEmail(email);
            view.printBuyerCart(cart);
            return true;
        }
        catch (NotFoundException | EmptyCartException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean printDeselected() {

        String email = session.getLoggedUserEmail();

        try {
            Cart cart = service.findDeselectedByEmail(email);
            view.printBuyerCart(cart);
            return true;
        }
        catch (NotFoundException | EmptyCartException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean printCartByProductType() {

        String email = session.getLoggedUserEmail();
        ProductType productType = view.getProductType();

        try {
            Cart cart = service.findByEmailAndProductType(email, productType);
            view.printBuyerCart(cart);
            return true;
        }
        catch (NotFoundException | EmptyCartException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean printCartByProductName() {

        String email = session.getLoggedUserEmail();
        String productName = view.getProductName();

        try {
            Cart cart = service.findByEmailAndProductName(email, productName);
            view.printBuyerCart(cart);
            return true;
        }
        catch (NotFoundException | EmptyCartException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean printAllProductDetails() {

        String email = session.getLoggedUserEmail();
        String productId = view.getProductId();

        try {
            CartProduct cartProduct = service.findByEmailAndProductId(email, productId);
            view.printCartProduct(cartProduct);

            return service.existsReviewByProductId(productId);
        }
        catch (NotFoundException | EmptyCartException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public void addProduct() {

        String email = session.getLoggedUserEmail();
        String productId = view.getProductId();
        int quantity = view.getProductQuantity();

        try {
            service.findAndVerifyAndAddProductByIdAndQuantity(email, productId, quantity);
            view.printMessage("[+] Product added.");
        }
        catch (NotFoundException | InsufficientStockException | OwnProductException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void addProductByStoreNameAndProductId() {

        String email = session.getLoggedUserEmail();
        String storeName = session.getLastStoreViewed();;
        String productId = view.getProductId();
        int quantity = view.getProductQuantity();

        try {
            service.verifyLastStoreViewedAndAddProduct(email, storeName, productId, quantity);
            view.printMessage("[+] Product added.");
        }
        catch (NotFoundException | InsufficientStockException | EmptyCatalogException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void removeProduct() {

        String email = session.getLoggedUserEmail();
        String productId = view.getProductId();
        int quantity = view.getProductQuantity();

        try {
            service.verifyCartAndRemoveProduct(email, productId, quantity);
            view.printMessage("[-] Product removed.");
        }
        catch (NotFoundException | EmptyCartException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void selectProduct() {

        String email = session.getLoggedUserEmail();
        String productId = view.getProductId();

        try {
            service.verifyCartAndSelectProduct(email, productId);
            view.printMessage("[+] Product selected.");
        }
        catch (NotFoundException | EmptyCartException | ProductSelectionStateException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void deselectProduct() {

        String email = session.getLoggedUserEmail();
        String productId = view.getProductId();

        try {
            service.verifyCartAndDeselectProduct(email, productId);
            view.printMessage("[-] Product deselected.");
        }
        catch (NotFoundException | EmptyCartException | ProductSelectionStateException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void selectAll() {

        String email = session.getLoggedUserEmail();

        try {
            service.verifyCartAndSelectAll(email);
            view.printMessage("[+] All products were selected.");
        }
        catch (NotFoundException | IllegalArgumentException | EmptyCartException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void deselectAll() {

        String email = session.getLoggedUserEmail();

        try {
            service.verifyCartAndDeselectAll(email);
            view.printMessage("[-] All products were deselected.");
        }
        catch (NotFoundException | IllegalArgumentException | EmptyCartException e) {
            view.printMessage(e.getMessage());
        }
    }
}
