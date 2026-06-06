package main.java.com.example.marketplace.checkout.repository;

import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.buyer.model.CartItem;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.EmptyCartException;
import main.java.com.example.marketplace.exceptions.InsufficientStockException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.checkout.dto.CheckoutRequest;
import main.java.com.example.marketplace.checkout.model.OrderedItem;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.exceptions.OutdatedPriceException;
import main.java.com.example.marketplace.seller.model.Product;
import main.java.com.example.marketplace.seller.model.Seller;
import main.java.com.example.marketplace.shared.session.BuyerSession;
import main.java.com.example.marketplace.shared.session.SellerSession;

import java.util.ArrayList;
import java.util.List;

public final class CheckoutRepository {

    public void verifyCart() {

        String email = BuyerSession.getEmail();

        if (DataBase.isCartEmptyByEmail(email))
            throw new EmptyCartException("Your cart is empty.");

        List<CartItem> cartItemList = DataBase.findCartItemListByEmail(email);

        for (CartItem item : cartItemList) {

            Seller seller = DataBase.findSellerByStoreName(item.getStoreName());

            if (seller == null)
                throw new NotFoundException("Your cart contained item(s) from a seller who is no longer registered. " +
                        "The item(s) has/have been removed from your cart.");

            Product product = DataBase.findCatalogItemByStoreNameAndId(item.getStoreName(), item.getId());

            if (product == null)
                throw new NotFoundException("Your cart contained item(s) that are out of stock. Your cart has been updated.");

            if (item.getQuantity() > product.getStock())
                throw new InsufficientStockException("Your cart contained more items than were available. Your cart has been updated.");

            if (product.getUnitPrice() != item.getUnitPrice())
                throw new OutdatedPriceException("Your cart contained item(s) with outdated prices. Your cart has been updated.");
        }
    }

    public void confirmOrder(CheckoutRequest checkoutRequest) {

        String buyerEmail = BuyerSession.getEmail();
        String sellerEmail = SellerSession.getEmail();

        Buyer buyer = DataBase.findBuyerByEmail(buyerEmail);
        Seller seller = DataBase.findSellerByEmail(sellerEmail);

        List<CartItem> cartItemList = DataBase.findCartItemListByEmail(buyerEmail);
        List<OrderedItem> orderedItemList = new ArrayList<>();

        for (CartItem item : cartItemList)
            orderedItemList.add(new OrderedItem(item.getName(), item.getId(), item.getType(), item.getQuantity(), item.getUnitPrice()));

        TaxReceipt taxReceipt = new TaxReceipt(buyer.getName(), seller.getName(), checkoutRequest, orderedItemList);

        saveOrder(taxReceipt);
        updateBuyerCartAndSellerCatalog(buyerEmail);
    }

    public void saveOrder(TaxReceipt taxReceipt) {

        String buyerEmail = BuyerSession.getEmail();
        String sellerEmail = SellerSession.getEmail();

        Buyer buyer = DataBase.findBuyerByEmail(buyerEmail);
        Seller seller = DataBase.findSellerByEmail(sellerEmail);

        buyer.getOrdersMenu().setTaxReceiptList(taxReceipt);
        seller.getStore().getSalesMenu().setTaxReceiptsList(taxReceipt);
    }

    public CheckoutRequest getTotalCostAndShipping() {

        String email = BuyerSession.getEmail();
        Buyer buyer = DataBase.findBuyerByEmail(email);

        buyer.getCart().updateCart();
        return new CheckoutRequest(buyer.getCart().getTotalCost(), buyer.getCart().getShipping());
    }

    public void updateBuyerCartAndSellerCatalog(String buyerEmail) {

        Buyer buyer = DataBase.findBuyerByEmail(buyerEmail);
        List<CartItem> items = DataBase.findCartItemListByEmail(buyerEmail);

        for (CartItem item : items) {
            List<Product> products = DataBase.findCatalogItemListByStoreName(item.getStoreName());
            for (Product product : products)
                if (product.getId().equals(item.getId()))
                    DataBase.removeItemFromCatalog(item.getStoreName(), product, item.getQuantity());
        }
        items.clear();
        buyer.getCart().updateCart();
    }

    public void updateCart() {

        String email = BuyerSession.getEmail();
        List<CartItem> cartItemList = DataBase.findCartItemListByEmail(email);

        for (int i = 0; i < cartItemList.size(); i++) {

            Seller seller = DataBase.findSellerByStoreName(cartItemList.get(i).getStoreName());

            if (seller == null) {
                cartItemList.remove(i);
                i--;
                continue;
            }

            Product product = DataBase.findCatalogItemByStoreNameAndId(cartItemList.get(i).getStoreName(), cartItemList.get(i).getId());

            if (product == null) {
                cartItemList.remove(i);
                i--;
                continue;
            }

            if (cartItemList.get(i).getQuantity() > product.getStock())
                cartItemList.get(i).setQuantity(product.getStock());

            if (cartItemList.get(i).getUnitPrice() != product.getUnitPrice())
                cartItemList.get(i).setUnitPrice(product.getUnitPrice());
        }
    }
}
