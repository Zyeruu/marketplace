package main.java.com.example.marketplace.buyer.repository;

import main.java.com.example.marketplace.buyer.dto.CartRequest;
import main.java.com.example.marketplace.buyer.dto.CartResponse;
import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.buyer.model.CartItem;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.EmptyCartException;
import main.java.com.example.marketplace.exceptions.InsufficientStockException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.session.BuyerSession;
import main.java.com.example.marketplace.shared.session.SellerSession;

import java.util.ArrayList;
import java.util.List;

public final class CartRepository {

    public CartResponse findByEmail() {

        String email = BuyerSession.getEmail();

        if (!DataBase.existsBuyerByEmail(email))
            throw new NotFoundException("Logged-in user does not exist!");

        Buyer buyer = DataBase.findBuyerByEmail(email);

        List<CartItem> cartItemsListPointer = buyer.getCart().getCartItemsList();

        if (cartItemsListPointer.isEmpty())
            throw new EmptyCartException("Your cart is empty.");

        List<CartItem> cartItemsListCopy = new ArrayList<>();

        // Copies the items from cartItemsListPointer to cartItemsListCopy
        for (CartItem cartItem : cartItemsListPointer)
            cartItemsListCopy.add(new CartItem(cartItem));

        int totalItems = buyer.getCart().getTotalItems();
        int totalFood = buyer.getCart().getTotalFood();
        int totalMisc = buyer.getCart().getTotalMisc();

        return new CartResponse(cartItemsListCopy, totalItems, totalFood, totalMisc);
    }

    public void addItem(CartRequest cartRequest) {

        String email = BuyerSession.getEmail();
        String cnpj = SellerSession.getCnpj();
        String itemId = cartRequest.getId();
        int quantToBeAdded = cartRequest.getQuantity();

        if (!DataBase.existsByCnpj(cnpj))
            throw new NotFoundException("Seller catalog not found.");

        if (!DataBase.existsItemByIdAndCnpj(itemId, cnpj))
            throw new NotFoundException("The item with ID \"" + itemId + "\" was not found.");

        int catalogItemQuant = DataBase.getCatalogItemQuantityByIdAndCnpj(itemId, cnpj);

        if (DataBase.existsCartItemByEmailAndId(email, itemId)) {

            int cartItemQuant = DataBase.findCartItemQuantityByEmailAndId(email, itemId);

            if (cartItemQuant + quantToBeAdded > catalogItemQuant)
                throw new InsufficientStockException("Insufficient stock for this quantity.");

            DataBase.addItemToCart(itemId, quantToBeAdded, email);
            return;
        }

        if (quantToBeAdded > catalogItemQuant)
            throw new InsufficientStockException("There is not enough stock to meet the requested quantity.");

        DataBase.addItemToCart(itemId, quantToBeAdded, email, cnpj);
    }

    public void removeItem(CartRequest cartRequest) {

        String email = BuyerSession.getEmail();
        String itemId = cartRequest.getId();
        int quantToBeRemoved = cartRequest.getQuantity();

        if (!DataBase.existsCartItemByEmailAndId(email, itemId))
            throw new NotFoundException("The item with ID \"" + itemId + "\" was not found.");

        DataBase.removeItemFromCart(email, itemId, quantToBeRemoved);
    }
}
