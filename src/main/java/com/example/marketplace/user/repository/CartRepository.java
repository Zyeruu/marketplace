package main.java.com.example.marketplace.user.repository;

import main.java.com.example.marketplace.exceptions.*;
import main.java.com.example.marketplace.user.dto.CartRequest;
import main.java.com.example.marketplace.user.dto.CartResponse;
import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.user.model.CartProduct;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.user.store.model.Product;
import main.java.com.example.marketplace.shared.enums.ProductType;
import main.java.com.example.marketplace.shared.session.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class CartRepository {

    private final DataBase dataBase;
    private final Session session;

    public CartRepository(DataBase dataBase, Session session) {
        this.dataBase = dataBase;
        this.session = session;
    }

    public CartResponse findByEmail() {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getCartProductList().isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

        List<CartProduct> cartProductListCopy = user.getCartProductList().stream()
                .map(CartProduct::new)
                .collect(Collectors.toList());

        float shipping = user.getSelectedShipping();
        float totalCost = user.getSelectedTotalCost();
        int totalProducts = user.getCartTotalProducts();
        int totalFood = user.getCartTotalFood();
        int totalMisc = user.getCartTotalMisc();

        return new CartResponse(cartProductListCopy, totalCost, shipping, totalProducts, totalFood, totalMisc);
    }

    public CartResponse findByEmailAndSelected() {

        String email = session.getEmail();;
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getCartProductList().isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

        List<CartProduct> cartProductListCopy = user.getCartProductList().stream()
                .filter(CartProduct::isSelected)
                .map(CartProduct::new)
                .collect(Collectors.toList());

        if (cartProductListCopy.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        float shipping = user.getSelectedShipping();

        float totalCost = shipping + (float) cartProductListCopy.stream()
                .mapToDouble(product -> product.getUnitPrice() * product.getQuantity())
                .sum();

        int totalFood = (int) cartProductListCopy.stream()
                .filter(product -> product.getType() == ProductType.FOOD)
                .count();

        int totalMisc = (int) cartProductListCopy.stream()
                .filter(product -> product.getType() == ProductType.MISCELLANEOUS)
                .count();

        return new CartResponse(cartProductListCopy, totalCost, shipping, totalFood + totalMisc, totalFood, totalMisc);
    }

    public CartResponse findByEmailAndDeselected() {

        String email = session.getEmail();;
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getCartProductList().isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

        List<CartProduct> cartProductListCopy = user.getCartProductList().stream()
                .filter(product -> !product.isSelected())
                .map(CartProduct::new)
                .collect(Collectors.toList());

        if (cartProductListCopy.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        float shipping = user.getDeselectedShipping();

        float totalCost = shipping + (float) cartProductListCopy.stream()
                .mapToDouble(product -> product.getUnitPrice() * product.getQuantity())
                .sum();

        int totalFood = (int) cartProductListCopy.stream()
                .filter(product -> product.getType() == ProductType.FOOD)
                .count();

        int totalMisc = (int) cartProductListCopy.stream()
                .filter(product -> product.getType() == ProductType.MISCELLANEOUS)
                .count();

        return new CartResponse(cartProductListCopy, totalCost, shipping, totalFood + totalMisc, totalFood, totalMisc);
    }

    public CartResponse findByEmailAndProductType(ProductType productType) {

        String email = session.getEmail();
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

        if (cartProductListCopy.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        if (productType == ProductType.FOOD) {
            int totalFood = user.getCartTotalFood();
            return new CartResponse(cartProductListCopy, totalCost, shipping, 0, totalFood, 0);
        }
        else {
            int totalMisc = user.getCartTotalMisc();
            return new CartResponse(cartProductListCopy, totalCost, shipping, 0, 0, totalMisc);
        }
    }

    public CartResponse findByEmailAndProductName(String productName) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getCartProductList().isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

        List<CartProduct> cartProductListCopy = new ArrayList<>();

        float totalCost = 0;
        int totalProducts;
        int totalFood = 0;
        int totalMisc = 0;

        for (CartProduct product : user.getCartProductList()) {
            if (product.getName().equalsIgnoreCase(productName)) {

                cartProductListCopy.add(new CartProduct(product));
                totalCost += product.getUnitPrice() * product.getQuantity();

                if (product.getType() == ProductType.FOOD)
                    totalFood++;
                else
                    totalMisc++;
            }
        }

        totalProducts = totalFood + totalMisc;

        if (cartProductListCopy.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        float shipping = user.getCartShipping();

        return new CartResponse(cartProductListCopy, totalCost, shipping, totalProducts, totalFood, totalMisc);
    }

    public CartProduct findByEmailAndProductId(String productId) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getCartProductList().isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

        CartProduct cartProduct = null;

        cartProduct = user.getCartProductList().stream()
                .filter(product -> product.getId().equals(productId))
                .map(CartProduct::new)
                .findFirst()
                .orElse(null);

        if (cartProduct == null)
            throw new NotFoundException("[!] Product with ID \"" + productId + "\" not found.");

        return cartProduct;
    }

    public void addProduct(CartRequest cartRequest) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        String productId = cartRequest.id();
        int quantToBeAdded = cartRequest.quantity();
        Product catalogProduct = dataBase.findProductById(productId);

        if (catalogProduct == null)
            throw new NotFoundException("[!] Product with ID \"" + productId + "\" not found.");

        if (user.getStore() != null)
            if (user.getCatalogProductList().stream().anyMatch(product -> product.getId().equals(productId)))
                throw new OwnProductException("[!] You cannot add your own product.");

        if (quantToBeAdded > catalogProduct.getStock())
            throw new InsufficientStockException("[!] Insufficient stock for the requested stock.");

        CartProduct cartProduct = user.getCartProductList().stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartProduct != null) {

            if (cartProduct.getQuantity() + cartRequest.quantity() > catalogProduct.getStock())
                throw new InsufficientStockException("[!] Insufficient stock for the requested stock.");

            cartProduct.setQuantity(cartProduct.getQuantity() + cartRequest.quantity());
            return;
        }

        user.getCartProductList().add(new CartProduct(
                catalogProduct.getName(),
                productId,
                catalogProduct.getStoreName(),
                catalogProduct.getType(),
                catalogProduct.getBrand(),
                catalogProduct.getUnitPrice(),
                catalogProduct.getWeight(),
                cartRequest.quantity(),
                catalogProduct.getWarranty()));

        user.updateCart();
    }

    public void removeProduct(CartRequest cartRequest) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        CartProduct cartProduct = user.getCartProductList().stream()
                .filter(product -> product.getId().equals(cartRequest.id()))
                .findFirst()
                .orElse(null);

        if (cartProduct == null)
            throw new NotFoundException("[!] Product with ID \"" + cartRequest.id() + "\" not found.");

        if (cartProduct.getQuantity() == cartRequest.quantity())
            user.getCartProductList().remove(cartProduct);
        else
            cartProduct.setQuantity(cartProduct.getQuantity() - cartRequest.quantity());

        user.updateCart();
    }

    public void selectProduct(String productId) {

        String email = session.getEmail();;
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getCartProductList().isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

        CartProduct cartProduct = user.getCartProductList().stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartProduct == null)
            throw new NotFoundException("[!] Product with ID \"" + productId + "\" not found.");

        if (cartProduct.isSelected())
            throw new ProductSelectionStateException("[!] Product already selected.");

        cartProduct.setSelected(true);
    }

    public void deselectProduct(String productId) {

        String email = session.getEmail();;
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getCartProductList().isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

        CartProduct cartProduct = user.getCartProductList().stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartProduct == null)
            throw new NotFoundException("[!] Product with ID \"" + productId + "\" not found.");

        if (!cartProduct.isSelected())
            throw new ProductSelectionStateException("[!] Product already deselected.");

        cartProduct.setSelected(false);
    }
}
