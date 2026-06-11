package main.java.com.example.marketplace.buyer.repository;

import main.java.com.example.marketplace.buyer.dto.CartRequest;
import main.java.com.example.marketplace.buyer.dto.CartResponse;
import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.buyer.model.CartProduct;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.EmptyCartException;
import main.java.com.example.marketplace.exceptions.InsufficientStockException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.seller.model.Product;
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
        Buyer buyer = dataBase.findBuyerByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] User not found.");

        if (buyer.getCartProductList().isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

        List<CartProduct> cartProductListCopy = buyer.getCartProductList().stream()
                .map(CartProduct::new)
                .collect(Collectors.toList());

        float totalCost = buyer.getCartTotalCost();
        float shipping = buyer.getCartShipping();
        int totalProducts = buyer.getCartTotalProducts();
        int totalFood = buyer.getCartTotalFood();
        int totalMisc = buyer.getCartTotalMisc();

        return new CartResponse(cartProductListCopy, totalCost, shipping, totalProducts, totalFood, totalMisc);
    }

    public CartResponse findByEmailAndProductType(ProductType productType) {

        String email = session.getEmail();
        Buyer buyer = dataBase.findBuyerByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] User not found.");

        if (buyer.getCartProductList().isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

        List<CartProduct> cartProductListCopy = new ArrayList<>();

        float totalCost = 0;

        for (CartProduct product : buyer.getCartProductList()) {
            if (product.getType() == productType) {

                cartProductListCopy.add(new CartProduct(product));
                totalCost += product.getUnitPrice() * product.getQuantity();
            }
        }

        float shipping = buyer.getCartShipping();

        if (cartProductListCopy.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        if (productType == ProductType.FOOD) {
            int totalFood = buyer.getCartTotalFood();
            return new CartResponse(cartProductListCopy, totalCost, shipping, 0, totalFood, 0);
        }
        else {
            int totalMisc = buyer.getCartTotalMisc();
            return new CartResponse(cartProductListCopy, totalCost, shipping, 0, 0, totalMisc);
        }
    }

    public CartResponse findByEmailAndProductName(String productName) {

        String email = session.getEmail();
        Buyer buyer = dataBase.findBuyerByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] User not found.");

        if (buyer.getCartProductList().isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

        List<CartProduct> cartProductListCopy = new ArrayList<>();

        float totalCost = 0;
        int totalProducts;
        int totalFood = 0;
        int totalMisc = 0;

        for (CartProduct product : buyer.getCartProductList()) {
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

        float shipping = buyer.getCartShipping();

        return new CartResponse(cartProductListCopy, totalCost, shipping, totalProducts, totalFood, totalMisc);
    }

    public CartProduct findByEmailAndProductId(String productId) {

        String email = session.getEmail();
        Buyer buyer = dataBase.findBuyerByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] User not found.");

        if (buyer.getCartProductList().isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

        CartProduct cartProduct = null;

        cartProduct = buyer.getCartProductList().stream()
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
        Buyer buyer = dataBase.findBuyerByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] User not found.");

        String productId = cartRequest.id();
        int quantToBeAdded = cartRequest.quantity();
        Product catalogProduct = dataBase.findProductById(productId);

        if (catalogProduct == null)
            throw new NotFoundException("[!] Product with ID \"" + productId + "\" not found.");

        if (quantToBeAdded > catalogProduct.getStock())
            throw new InsufficientStockException("[!] Insufficient stock for the requested quantity.");

        CartProduct cartProduct = buyer.getCartProductList().stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartProduct != null) {

            if (cartProduct.getQuantity() + cartRequest.quantity() > catalogProduct.getStock())
                throw new InsufficientStockException("[!] Insufficient stock for the requested quantity.");

            cartProduct.setQuantity(cartProduct.getQuantity() + cartRequest.quantity());
            return;
        }

        buyer.getCartProductList().add(new CartProduct(
                catalogProduct.getName(),
                productId,
                catalogProduct.getStoreName(),
                catalogProduct.getType(),
                catalogProduct.getBrand(),
                catalogProduct.getUnitPrice(),
                catalogProduct.getWeight(),
                cartRequest.quantity(),
                catalogProduct.getWarranty()));

        buyer.updateCart();
    }

    public void removeProduct(CartRequest cartRequest) {

        String email = session.getEmail();
        Buyer buyer = dataBase.findBuyerByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] User not found.");

        CartProduct cartProduct = buyer.getCartProductList().stream()
                .filter(product -> product.getId().equals(cartRequest.id()))
                .findFirst()
                .orElse(null);

        if (cartProduct == null)
            throw new NotFoundException("[!] Product with ID \"" + cartRequest.id() + "\" not found.");

        if (cartProduct.getQuantity() == cartRequest.quantity())
            buyer.getCartProductList().remove(cartProduct);
        else
            cartProduct.setQuantity(cartProduct.getQuantity() - cartRequest.quantity());

        buyer.updateCart();
    }
}
