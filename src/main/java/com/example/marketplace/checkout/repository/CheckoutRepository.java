package main.java.com.example.marketplace.checkout.repository;

import main.java.com.example.marketplace.exceptions.*;
import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.user.model.CartProduct;
import main.java.com.example.marketplace.checkout.model.OrderedProduct;
import main.java.com.example.marketplace.checkout.model.PaymentMethod;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.user.store.model.Product;
import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.shared.utils.IdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class CheckoutRepository {

    private final DataBase dataBase;
    private final Session session;

    public CheckoutRepository(DataBase dataBase, Session session) {
        this.dataBase = dataBase;
        this.session = session;
    }

    public void verifyCart() {

        String email = session.getEmail();
        User buyer = dataBase.findUserByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] User not found.");

        if (buyer.getCartProductList().isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

        if (buyer.getCartProductList().stream().noneMatch(CartProduct::isSelected))
            throw new NotFoundException("[!] You have no products selected.");

        List<CartProduct> cartProductList = buyer.getCartProductList();

        for (CartProduct cartProduct : cartProductList) {

            if (cartProduct.isSelected()) {
                Product product = dataBase.findProductById(cartProduct.getId());

                if (product == null)
                    throw new NotFoundException("[!] Your cart contained product(s) that were unavailable. Your cart has been updated.");

                if (!cartProduct.getName().equals(product.getName()))
                    throw new OutdatedProductException("[!] Your car contained product(s) with outdated name(s). Your cart has been updated.");

                if (cartProduct.getType() != product.getType())
                    throw new OutdatedProductException("[!] Your cart contained product(s) with outdated type(s). Your cart has been updated.");

                if (!cartProduct.getBrand().equals(product.getBrand()))
                    throw new OutdatedProductException("[!] Your cart contained product(s) with outdated brand(s). Your cart has been updated.");

                if (cartProduct.getUnitPrice() != product.getUnitPrice())
                    throw new OutdatedProductException("[!] Your cart contained product(s) with outdated price(s). Your cart has been updated.");

                if (cartProduct.getWeight() != product.getWeight())
                    throw new OutdatedProductException("[!] Your cart contained product(s) with outdated weight(s). Your cart has been updated.");

                if (cartProduct.getQuantity() > product.getStock())
                    throw new InsufficientStockException("[!] Your cart contained more product(s) than were available. Your cart has been updated.");

                if (cartProduct.getWarranty() != product.getWarranty())
                    throw new OutdatedProductException("[!] Your cart contained product(s) with an outdated warranty(ies). Your cart has been updated.");

                if (!cartProduct.getStoreName().equals(product.getStoreName()))
                    throw new OutdatedProductException("[!] Your cart contained product(s) with an outdated store name(s). Your cart has been updated.");
            }
        }
    }

    public void saveOrder(PaymentMethod paymentMethod) {

        String buyerEmail = session.getEmail();
        User buyer = dataBase.findUserByEmail(buyerEmail);

        if (buyer == null)
            throw new NotFoundException("[!] User not found.");

        List<CartProduct> selectedProducts = buyer.getCartProductList().stream()
                .filter(CartProduct::isSelected)
                .collect(Collectors.toList());

        List<String> storeNames = getStoreNames(selectedProducts);

        for (String name : storeNames) {

            User seller = dataBase.findSellerByStoreName(name);

            List<OrderedProduct> buyerOrderedProductList = selectedProducts.stream()
                    .filter(p -> p.getStoreName().equals(name))
                    .map(p -> new OrderedProduct(p.getName(), p.getId(), p.getType(), p.getQuantity(), p.getUnitPrice()))
                    .collect(Collectors.toList());

            List<OrderedProduct> sellerOrderedProductList = selectedProducts.stream()
                    .filter(p -> p.getStoreName().equals(name))
                    .map(p -> new OrderedProduct(p.getName(), p.getId(), p.getType(), p.getQuantity(), p.getUnitPrice()))
                    .collect(Collectors.toList());

            float sellerRevenue = (float) selectedProducts.stream()
                    .filter(p -> p.getStoreName().equals(name))
                    .mapToDouble(p -> p.getUnitPrice() * p.getQuantity())
                    .sum();

            String orderId = IdGenerator.generateOrderId();
            float shipping = buyer.getSelectedShipping() / storeNames.size();
            float buyerTotalCost = sellerRevenue + shipping;

            TaxReceipt buyerTaxReceipt = new TaxReceipt(orderId, name, buyer.getName(), paymentMethod, buyerTotalCost, shipping, buyerOrderedProductList);
            TaxReceipt sellerTaxReceipt = new TaxReceipt(orderId, name, buyer.getName(), paymentMethod, sellerRevenue, 0,  sellerOrderedProductList);

            buyer.setOrdersMenuTaxReceiptList(buyerTaxReceipt);
            seller.setSalesMenuTaxReceiptList(sellerTaxReceipt);
        }
    }

    public float getTotalCost() {

        String email = session.getEmail();
        User buyer = dataBase.findUserByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] User not found.");

        buyer.updateCart();
        return buyer.getSelectedTotalCost();
    }

    public void updateBuyerCartAndSellerCatalog() {

        String email = session.getEmail();
        User buyer = dataBase.findUserByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] User not found.");

        List<CartProduct> cart = buyer.getCartProductList().stream()
                .filter(CartProduct::isSelected)
                .collect(Collectors.toList());

        for (int i = 0; i < cart.size(); i++) {

            User seller = dataBase.findSellerByStoreName(cart.get(i).getStoreName());

            if (seller == null)
                throw new NotFoundException("[!] Seller not found.");

            List<Product> products = seller.getCatalogProductList();

            for (int j = 0; j < products.size(); j++) {
                if (products.get(j).getId().equals(cart.get(i).getId())) {

                    if (cart.get(i).getQuantity() == products.get(j).getStock()) {

                        dataBase.deleteFromProductList(products.get(j));
                        products.remove(products.get(j));
                    }
                    else
                        products.get(j).setStock(products.get(j).getStock() - cart.get(i).getQuantity());

                    buyer.getCartProductList().remove(cart.get(i));
                    break;
                }
            }

            seller.updateCatalog();
        }

        buyer.updateCart();
    }

    public void updateCart() {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        List<CartProduct> cartProductList = user.getCartProductList();

        for (int i = 0; i < cartProductList.size(); i++) {

            Product product = dataBase.findProductById(cartProductList.get(i).getId());

            if (product == null) {
                cartProductList.remove(i);
                i--;
                continue;
            }

            if (!cartProductList.get(i).getName().equals(product.getName()))
                cartProductList.get(i).setName(product.getName());

            if (cartProductList.get(i).getType() != product.getType())
                cartProductList.get(i).setType(product.getType());

            if (cartProductList.get(i).getBrand().equals(product.getBrand()))
                cartProductList.get(i).setBrand(product.getBrand());

            if (cartProductList.get(i).getUnitPrice() != product.getUnitPrice())
                cartProductList.get(i).setUnitPrice(product.getUnitPrice());

            if (cartProductList.get(i).getWeight() != product.getWeight())
                cartProductList.get(i).setWeight(product.getWeight());

            if (cartProductList.get(i).getQuantity() > product.getStock())
                cartProductList.get(i).setQuantity(product.getStock());

            if (cartProductList.get(i).getWarranty() != product.getWarranty())
                cartProductList.get(i).setWarranty(product.getWarranty());

            if (!cartProductList.get(i).getStoreName().equals(product.getStoreName()))
                cartProductList.get(i).setStoreName(product.getStoreName());
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
