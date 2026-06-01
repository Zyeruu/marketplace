package main.java.com.example.marketplace.buyer.repository;

import main.java.com.example.marketplace.buyer.dto.CartResponse;
import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.buyer.model.CartItem;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public final class CartRepository {

    public CartResponse findByEmail(String email) {

        if (!DataBase.existsBuyerByEmail(email))
            throw new NotFoundException("Logged-in user does not exist!");

        Buyer buyer = DataBase.findBuyerByEmail(email);

        List<CartItem> cartItemsListPointer = buyer.getCart().getCartItemsList();
        List<CartItem> cartItemsListCopy = new ArrayList<>();

        // Copies the items from cartItemsListPointer to cartItemsListCopy
        for (CartItem cartItem : cartItemsListPointer)
            cartItemsListCopy.add(new CartItem(cartItem));

        int totalItems = buyer.getCart().getTotalItems();
        int totalFood = buyer.getCart().getTotalFood();
        int totalMisc = buyer.getCart().getTotalMisc();

        return new CartResponse(cartItemsListCopy, totalItems, totalFood, totalMisc);
    }
}
