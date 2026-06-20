package main.java.com.example.marketplace.user.repository;

import main.java.com.example.marketplace.exceptions.*;
import main.java.com.example.marketplace.user.model.Cart;
import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.user.model.CartProduct;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.user.store.model.Product;
import main.java.com.example.marketplace.shared.enums.ProductType;

import java.util.ArrayList;
import java.util.List;

public final class CartRepository {

    private final DataBase dataBase;

    public CartRepository(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public Cart findAllByEmail(String email) {

        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        return new Cart(user.getCart());
    }

    public Cart findByEmailAndProductType(String email, ProductType productType) {

        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getCartProductList().isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

        List<CartProduct> cartProductListCopy = new ArrayList<>();

        float totalCost = 0;

        for (CartProduct product : user.getCartProductList()) {
            if (product.getType() == productType) {

                cartProductListCopy.add(new CartProduct(product));
                totalCost += product.getUnitPrice() * product.getQuantity();
            }
        }

        float shipping = user.getCartShipping();

        if (productType == ProductType.FOOD) {
            int totalFood = user.getCartTotalFood();
            return new Cart(cartProductListCopy, totalCost, shipping, 0, totalFood, 0);
        }
        else {
            int totalMisc = user.getCartTotalMisc();
            return new Cart(cartProductListCopy, totalCost, shipping, 0, 0, totalMisc);
        }
    }

    public Cart findByEmailAndProductName(String email, String productName) {

        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getCartProductList().isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

        List<CartProduct> cartProductListCopy = new ArrayList<>();

        float totalCost = 0;
        int totalFood = 0;
        int totalMisc = 0;

        for (CartProduct product : user.getCartProductList()) {
            if (product.getName().toLowerCase().contains(productName.toLowerCase())) {

                cartProductListCopy.add(new CartProduct(product));
                totalCost += product.getUnitPrice() * product.getQuantity();

                if (product.getType() == ProductType.FOOD)
                    totalFood++;
                else
                    totalMisc++;
            }
        }

        int totalProducts = totalFood + totalMisc;
        float shipping = user.getCartShipping();

        return new Cart(cartProductListCopy, totalCost, shipping, totalProducts, totalFood, totalMisc);
    }

    public CartProduct findByEmailAndProductId(String email, String productId) {

        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getCartProductList().isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

        return user.getCartProductList().stream()
                .filter(product -> product.getId().equals(productId))
                .map(CartProduct::new)
                .findFirst()
                .orElse(null);
    }

    public boolean existsReviewByProductId(String productId) {

        return dataBase.getProductList().stream()
                .filter(Product::isAvailable)
                .filter(product -> product.getId().equals(productId))
                .anyMatch(product -> !product.getReviewList().isEmpty());
    }

    public void addProduct(Cart cart, CartProduct product) {
        cart.getProductList().add(product);
        cart.updateCart();
    }

    public void addProduct(CartProduct product, int quantity) {
        product.setQuantity(product.getQuantity() + quantity);
    }

    public void removeProduct(Cart cart, CartProduct cartProduct) {
        cart.getProductList().remove(cartProduct);
        cart.updateCart();
    }

    public void removeProduct(CartProduct cartProduct, int quantity) {
        cartProduct.setQuantity(cartProduct.getQuantity() - quantity);
    }

    public void selectProduct(CartProduct cartProduct) {
        cartProduct.setSelected(true);
    }

    public void deselectProduct(CartProduct cartProduct) {
        cartProduct.setSelected(false);
    }

    public void selectAll(Cart cart) {

        for (CartProduct product : cart.getProductList())
            product.setSelected(true);
    }

    public void deselectAll(Cart cart) {

        for (CartProduct product : cart.getProductList())
            product.setSelected(false);
    }

    public Product findAvailableProductById(String productId) {
        return dataBase.findAvailableProductById(productId);
    }
}
