package main.java.com.example.marketplace.checkout.repository;

import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.buyer.model.CartProduct;
import main.java.com.example.marketplace.checkout.model.OrderedProduct;
import main.java.com.example.marketplace.checkout.model.PaymentMethod;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.EmptyCartException;
import main.java.com.example.marketplace.exceptions.InsufficientStockException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.exceptions.OutdatedPriceException;
import main.java.com.example.marketplace.seller.model.Product;
import main.java.com.example.marketplace.seller.model.Seller;
import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.shared.utils.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public final class CheckoutRepository {

    private final DataBase dataBase;
    private final Session session;

    public CheckoutRepository(DataBase dataBase, Session session) {
        this.dataBase = dataBase;
        this.session = session;
    }

    public void verifyCart() {

        String email = session.getEmail();
        Buyer buyer = dataBase.findBuyerByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] User not found.");

        if (buyer.getCartProductList().isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

        List<CartProduct> cartProductList = buyer.getCartProductList();

        for (CartProduct item : cartProductList) {

            Product product = dataBase.findProductById(item.getId());

            if (product == null)
                throw new NotFoundException("[!] Your cart contained product(s) that were unavailable. Your cart has been updated.");

            if (item.getQuantity() > product.getStock())
                throw new InsufficientStockException("[!] Your cart contained more product(s) than were available. Your cart has been updated.");

            if (product.getUnitPrice() != item.getUnitPrice())
                throw new OutdatedPriceException("[!] Your cart contained product(s) with outdated prices. Your cart has been updated.");
        }
    }

    public void saveOrder(PaymentMethod paymentMethod) {

        String buyerEmail = session.getEmail();
        Buyer buyer = dataBase.findBuyerByEmail(buyerEmail);

        if (buyer == null)
            throw new NotFoundException("[!] User not found.");

        List<CartProduct> cartProductList = buyer.getCartProductList();
        List<String> storeNames = getStoreNames(cartProductList);

        for (String name : storeNames) {

            Seller seller = dataBase.findSellerByStoreName(name);

            List<OrderedProduct> buyerOrderedProductList = new ArrayList<>();
            List<OrderedProduct> sellerOrderedProductList = new ArrayList<>();

            float totalCost = 0;

            for (CartProduct product : cartProductList)
                if (product.getStoreName().equals(name)) {

                    totalCost += product.getUnitPrice() * product.getQuantity();

                    buyerOrderedProductList.add(new OrderedProduct(product.getName(), product.getId(), product.getType(), product.getQuantity(), product.getUnitPrice()));
                    sellerOrderedProductList.add(new OrderedProduct(product.getName(), product.getId(), product.getType(), product.getQuantity(), product.getUnitPrice()));
                }

            String orderId = IdGenerator.generateOrderId();

            TaxReceipt buyerTaxReceipt = new TaxReceipt(orderId, name, buyer.getName(), paymentMethod, totalCost, buyerOrderedProductList);
            TaxReceipt sellerTaxReceipt = new TaxReceipt(orderId, name, buyer.getName(), paymentMethod, totalCost, sellerOrderedProductList);

            buyer.setTaxReceiptList(buyerTaxReceipt);
            seller.setTaxReceiptList(sellerTaxReceipt);
        }
    }

    public float getTotalCost() {

        String email = session.getEmail();
        Buyer buyer = dataBase.findBuyerByEmail(email);

        buyer.updateCart();
        return buyer.getCartTotalCost();
    }

    public void updateBuyerCartAndSellerCatalog() {

        String email = session.getEmail();
        Buyer buyer = dataBase.findBuyerByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] User not found.");

        List<CartProduct> cart = buyer.getCartProductList();

        for (int i = 0; i < cart.size(); i++) {

            Seller seller = dataBase.findSellerByStoreName(cart.get(i).getStoreName());

            if (seller == null)
                throw new NotFoundException("[!] Seller not found.");

            List<Product> products = seller.getProductList();

            for (int j = 0; j < products.size(); j++) {
                if (products.get(j).getId().equals(cart.get(i).getId())) {

                    if (cart.get(i).getQuantity() == products.get(j).getStock()) {
                        seller.getProductList().remove(products.get(j));
                        j--;
                    }
                    else
                        products.get(j).setStock(products.get(j).getStock() - cart.get(i).getQuantity());
                }
            }

            seller.updateCatalog();
        }

        cart.clear();
        buyer.updateCart();
    }

    public void updateCart() {

        String email = session.getEmail();
        Buyer buyer = dataBase.findBuyerByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] User not found.");

        List<CartProduct> cartProductList = buyer.getCartProductList();

        for (int i = 0; i < cartProductList.size(); i++) {

            Product product = dataBase.findProductById(cartProductList.get(i).getId());

            if (product == null) {
                cartProductList.remove(i);
                i--;
                continue;
            }

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
