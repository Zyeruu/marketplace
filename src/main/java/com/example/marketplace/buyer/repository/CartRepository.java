package main.java.com.example.marketplace.buyer.repository;

import main.java.com.example.marketplace.buyer.dto.CartResponse;
import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.buyer.model.CartItem;
import main.java.com.example.marketplace.database.DataBase;

import java.util.ArrayList;
import java.util.List;

public final class CartRepository {

    public CartResponse findByEmail(String email) {

        List<Buyer> buyerList = DataBase.getBuyerList();

        if (!buyerList.isEmpty())
            for (Buyer buyer : buyerList)
                if (buyer.getEmail().equals(email)) {

                    List<CartItem> cartItemsListPointer = buyer.getCart().getCartItemsList();
                    List<CartItem> cartItemsListCopy = new ArrayList<>();

                    int totalItems = buyer.getCart().getTotalItems();
                    int totalFood = buyer.getCart().getTotalFood();
                    int totalMisc = buyer.getCart().getTotalMisc();

                    if (!cartItemsListPointer.isEmpty())
                        // Copies the items from cartItemsListPointer to cartItemsListCopy
                        for (CartItem cartItem : cartItemsListPointer)
                            cartItemsListCopy.add(new CartItem(cartItem));

                    return new CartResponse(cartItemsListCopy, totalItems, totalFood, totalMisc);
                }
        return null;
    }
}
