package main.java.com.example.marketplace.checkout.service;

import main.java.com.example.marketplace.checkout.model.PaymentMethod;
import main.java.com.example.marketplace.checkout.repository.CheckoutRepository;
import main.java.com.example.marketplace.exceptions.EmptyCartException;
import main.java.com.example.marketplace.exceptions.InsufficientStockException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.exceptions.OutdatedProductException;
import main.java.com.example.marketplace.user.account.service.AccountService;
import main.java.com.example.marketplace.user.model.Cart;
import main.java.com.example.marketplace.user.model.CartProduct;
import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.user.store.model.Product;

public final class CheckoutService {

    private final CheckoutRepository repository;
    private final AccountService accountService;

    public CheckoutService(CheckoutRepository repository, AccountService accountService) {
        this.repository = repository;
        this.accountService = accountService;
    }

    public User findUserByEmail(String email) {
        return accountService.findUserByEmail(email);
    }

    public void verifyCart(Cart cart) {

        if (cart.getProductList().isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

        if (cart.getProductList().stream().noneMatch(CartProduct::isSelected))
            throw new NotFoundException("[!] You have no products selected.");

        for (CartProduct cartProduct : cart.getProductList()) {

            if (cartProduct.isSelected()) {
                Product product = repository.findProductById(cartProduct.getId());

                if (product == null)
                    throw new NotFoundException("[!] Your cart contained product(s) that were unavailable. Your cart has been updated.");

                if (!cartProduct.getName().equals(product.getName()))
                    throw new OutdatedProductException("[!] Your cart contained product(s) with outdated name(s). Your cart has been updated.");

                if (cartProduct.getType() != product.getType())
                    throw new OutdatedProductException("[!] Your cart contained product(s) with outdated type(s). Your cart has been updated.");

                if (cartProduct.getBrand() != null)
                    if (!cartProduct.getBrand().equals(product.getBrand()))
                        throw new OutdatedProductException("[!] Your cart contained product(s) with outdated brand(s). Your cart has been updated.");

                if (cartProduct.getUnitPrice() != product.getUnitPrice())
                    throw new OutdatedProductException("[!] Your cart contained product(s) with outdated price(s). Your cart has been updated.");

                if (cartProduct.getWeight() != product.getWeight())
                    throw new OutdatedProductException("[!] Your cart contained product(s) with outdated weight(s). Your cart has been updated.");

                if (cartProduct.getQuantity() > product.getStock())
                    throw new InsufficientStockException("[!] Your cart contained more product(s) than were available. Your cart has been updated.");

                if (cartProduct.getWarranty() != null)
                    if (!cartProduct.getWarranty().equals(product.getWarranty()))
                        throw new OutdatedProductException("[!] Your cart contained product(s) with an outdated warranty(ies). Your cart has been updated.");

                if (!cartProduct.getStoreName().equals(product.getStoreName()))
                    throw new OutdatedProductException("[!] Your cart contained product(s) with an outdated store name(s). Your cart has been updated.");
            }
        }
    }

    public void saveOrder(User buyer, PaymentMethod paymentMethod) {
        repository.saveOrder(buyer, paymentMethod);
    }

    public void updateCartAndCatalog(Cart cart) {
        repository.updateCartAndCatalog(cart);
    }

    public void updateOutdatedCart(Cart cart) {
        repository.updateOutdatedCart(cart);
    }
}
