package main.java.com.example.marketplace.checkout.repository;

import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.buyer.model.CartProduct;
import main.java.com.example.marketplace.checkout.model.OrderedProduct;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.EmptyCartException;
import main.java.com.example.marketplace.exceptions.InsufficientStockException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.checkout.dto.CheckoutRequest;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.exceptions.OutdatedPriceException;
import main.java.com.example.marketplace.seller.model.Product;
import main.java.com.example.marketplace.seller.model.Seller;
import main.java.com.example.marketplace.shared.session.BuyerSession;

import java.util.ArrayList;
import java.util.List;

public final class CheckoutRepository {

    public void verifyCart() {

        String email = BuyerSession.getEmail();

        if (DataBase.isCartEmptyByEmail(email))
            throw new EmptyCartException("[!] Your cart is empty.");

        List<CartProduct> cartProductList = DataBase.findCartProductListByEmail(email);

        for (CartProduct item : cartProductList) {

            if (!DataBase.existsProductById(item.getId()))
                throw new NotFoundException("[!] Your cart contained product(s) that were unavailable. Your cart has been updated.");

            Product product = DataBase.findProductById(item.getId());

            if (item.getQuantity() > product.getStock())
                throw new InsufficientStockException("[!] Your cart contained more product(s) than were available. Your cart has been updated.");

            if (product.getUnitPrice() != item.getUnitPrice())
                throw new OutdatedPriceException("[!] Your cart contained product(s) with outdated prices. Your cart has been updated.");
        }
    }

    public void saveOrder(CheckoutRequest checkoutRequest) {

        String buyerEmail = BuyerSession.getEmail();
        Buyer buyer = DataBase.findBuyerByEmail(buyerEmail);

        List<CartProduct> cartProductList = DataBase.findCartProductListByEmail(buyerEmail);
        List<String> storeNames = getStoreNames(cartProductList);

        for (String name : storeNames) {

            Seller seller = DataBase.findSellerByStoreName(name);

            List<OrderedProduct> buyerOrderedProductList = new ArrayList<>();
            List<OrderedProduct> sellerOrderedProductList = new ArrayList<>();

            for (CartProduct item : cartProductList)
                if (item.getStoreName().equals(name)) {
                    buyerOrderedProductList.add(new OrderedProduct(item.getName(), item.getId(), item.getType(), item.getQuantity(), item.getUnitPrice()));
                    sellerOrderedProductList.add(new OrderedProduct(item.getName(), item.getId(), item.getType(), item.getQuantity(), item.getUnitPrice()));
                }

            TaxReceipt buyerTaxReceipt = new TaxReceipt(name, buyer.getName(), checkoutRequest, sellerOrderedProductList);
            TaxReceipt sellerTaxReceipt = new TaxReceipt(name, buyer.getName(), checkoutRequest, sellerOrderedProductList);
            buyer.getOrdersMenu().setTaxReceiptList(buyerTaxReceipt);
            seller.getStore().getSalesMenu().setTaxReceiptsList(sellerTaxReceipt);
        }

        updateBuyerCartAndSellerCatalog();
    }

    public CheckoutRequest getTotalCostAndShipping() {

        String email = BuyerSession.getEmail();
        Buyer buyer = DataBase.findBuyerByEmail(email);

        buyer.getCart().updateCart();
        return new CheckoutRequest(buyer.getCart().getTotalCost(), buyer.getCart().getShipping());
    }

    public void updateBuyerCartAndSellerCatalog() {

        String email = BuyerSession.getEmail();
        Buyer buyer = DataBase.findBuyerByEmail(email);
        List<CartProduct> cart = DataBase.findCartProductListByEmail(email);

        for (CartProduct item : cart) {
            List<Product> products = DataBase.findCatalogProductListByStoreName(item.getStoreName());
            for (Product product : products)
                if (product.getId().equals(item.getId()))
                    DataBase.removeProductFromCatalog(item.getStoreName(), product, item.getQuantity());
        }

        cart.clear();
        buyer.getCart().updateCart();
    }

    public void updateCart() {

        String email = BuyerSession.getEmail();
        List<CartProduct> cartProductList = DataBase.findCartProductListByEmail(email);

        for (int i = 0; i < cartProductList.size(); i++) {

            if (!DataBase.existsProductById(cartProductList.get(i).getId())) {
                cartProductList.remove(i);
                i--;
                continue;
            }

            Product product = DataBase.findProductById(cartProductList.get(i).getId());

            if (cartProductList.get(i).getQuantity() > product.getStock())
                cartProductList.get(i).setQuantity(product.getStock());

            if (cartProductList.get(i).getUnitPrice() != product.getUnitPrice())
                cartProductList.get(i).setUnitPrice(product.getUnitPrice());
        }
    }

    public List<String> getStoreNames(List<CartProduct> cartProductList) {

        List<String> storeNames = new ArrayList<>();

        for (CartProduct product : cartProductList)
            if (!storeNames.contains(product.getStoreName()))
                storeNames.add(product.getStoreName());
        return storeNames;
    }
}
