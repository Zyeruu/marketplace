package main.java.com.example.marketplace.checkout.repository;

import main.java.com.example.marketplace.exceptions.*;
import main.java.com.example.marketplace.user.model.Cart;
import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.user.model.CartProduct;
import main.java.com.example.marketplace.checkout.model.OrderedProduct;
import main.java.com.example.marketplace.checkout.model.PaymentMethod;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.user.store.model.Product;
import main.java.com.example.marketplace.shared.utils.IdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class CheckoutRepository {

    private final DataBase dataBase;

    public CheckoutRepository(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public Product findProductById(String productId) {
        return dataBase.findAvailableProductById(productId);
    }

    public void saveOrder(User buyer, PaymentMethod paymentMethod) {

        List<CartProduct> selectedProducts = buyer.getCartProductList().stream()
                .filter(CartProduct::isSelected)
                .collect(Collectors.toList());

        List<String> storeNames = new ArrayList<>();

        for (CartProduct product : selectedProducts)
            if (!storeNames.contains(product.getStoreName()))
                storeNames.add(product.getStoreName());

        for (String name : storeNames) {

            User seller = dataBase.findSellerByStoreName(name);

            List<OrderedProduct> buyerOrderedProductList = selectedProducts.stream()
                    .filter(p -> p.getStoreName().equals(name))
                    .map(p -> new OrderedProduct(p.getName(), p.getId(), seller.getCnpj(), p.getType(), p.getQuantity(), p.getUnitPrice()))
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

            TaxReceipt buyerTaxReceipt = new TaxReceipt(orderId, name, seller.getCnpj(), buyer.getName(), paymentMethod,
                    buyerTotalCost, shipping, buyerOrderedProductList);

            TaxReceipt sellerTaxReceipt = new TaxReceipt(orderId, name, seller.getCnpj(), buyer.getName(), paymentMethod,
                    sellerRevenue, null,  sellerOrderedProductList);

            for (OrderedProduct product : buyerOrderedProductList)
                buyer.getOrdersMenuOrderedProductList().add(product);

            for (OrderedProduct product : sellerOrderedProductList)
                seller.getSalesMenuOrderedProductList().add(product);

            buyer.getOrdersMenuTaxReceiptList().add(buyerTaxReceipt);
            seller.getSalesMenuTaxReceiptList().add(sellerTaxReceipt);
        }
    }

    public void updateCartAndCatalog(Cart cart) {

        List<CartProduct> cartProducts = cart.getProductList().stream()
                .filter(CartProduct::isSelected)
                .collect(Collectors.toList());

        for (int i = 0; i < cartProducts.size(); i++) {

            User seller = dataBase.findSellerByStoreName(cartProducts.get(i).getStoreName());

            if (seller == null)
                throw new NotFoundException("[!] Seller not found.");

            List<Product> catalogProducts = seller.getCatalogProductList();

            for (int j = 0; j < catalogProducts.size(); j++) {
                if (catalogProducts.get(j).getId().equals(cartProducts.get(i).getId())) {

                    if (cartProducts.get(i).getQuantity() == catalogProducts.get(j).getStock()) {

                        catalogProducts.get(j).setStock(0);
                        catalogProducts.get(j).setAvailable(false);
                    }
                    else
                        catalogProducts.get(j).setStock(catalogProducts.get(j).getStock() - cartProducts.get(i).getQuantity());

                    cart.getProductList().remove(cartProducts.get(i));
                    break;
                }
            }

            seller.updateCatalog();
        }

        cart.updateCart();
    }

    public void updateOutdatedCart(Cart cart) {

        List<CartProduct> cartProductList = cart.getProductList();

        for (int i = 0; i < cartProductList.size(); i++) {

            Product product = dataBase.findAvailableProductById(cartProductList.get(i).getId());

            if (product == null) {
                cartProductList.remove(i);
                i--;
                continue;
            }

            if (!cartProductList.get(i).getName().equals(product.getName()))
                cartProductList.get(i).setName(product.getName());

            if (cartProductList.get(i).getType() != product.getType())
                cartProductList.get(i).setType(product.getType());

            if (cartProductList.get(i).getBrand() != null)
                if (cartProductList.get(i).getBrand().equals(product.getBrand()))
                    cartProductList.get(i).setBrand(product.getBrand());

            if (cartProductList.get(i).getUnitPrice() != product.getUnitPrice())
                cartProductList.get(i).setUnitPrice(product.getUnitPrice());

            if (cartProductList.get(i).getWeight() != product.getWeight())
                cartProductList.get(i).setWeight(product.getWeight());

            if (cartProductList.get(i).getQuantity() > product.getStock())
                cartProductList.get(i).setQuantity(product.getStock());

            if (cartProductList.get(i).getWarranty() != null)
                if (cartProductList.get(i).getWarranty().equals(product.getWarranty()))
                    cartProductList.get(i).setWarranty(product.getWarranty());

            if (!cartProductList.get(i).getStoreName().equals(product.getStoreName()))
                cartProductList.get(i).setStoreName(product.getStoreName());
        }
    }
}
