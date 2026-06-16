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
            return new CartResponse(cartProductListCopy, totalCost, shipping, null, totalFood, null);
        }
        else {
            int totalMisc = user.getCartTotalMisc();
            return new CartResponse(cartProductListCopy, totalCost, shipping, null, null, totalMisc);
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
            if (product.getName().toLowerCase().contains(productName.toLowerCase())) {

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

        if (user == null) {
            session.updateLastProductViewed(null);
            throw new NotFoundException("[!] User not found.");
        }

        if (user.getCartProductList().isEmpty()) {
            session.updateLastProductViewed(null);
            throw new EmptyCartException("[!] Your cart is empty.");
        }

        CartProduct cartProduct = user.getCartProductList().stream()
                .filter(product -> product.getId().equals(productId))
                .map(CartProduct::new)
                .findFirst()
                .orElse(null);

        if (cartProduct == null) {
            session.updateLastProductViewed(null);
            throw new NotFoundException("[!] Product with ID \"" + productId + "\" not found.");
        }

        session.updateLastProductViewed(productId);

        return cartProduct;
    }

    public boolean existsReviewByProductId(String productId) {

        return dataBase.getProductList().stream()
                .filter(Product::isAvailable)
                .filter(product -> product.getId().equals(productId))
                .anyMatch(product -> !product.getReviewList().isEmpty());
    }

    public void addProduct(CartRequest cartRequest) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        String productId = cartRequest.id();
        int quantToBeAdded = cartRequest.quantity();

        Product catalogProduct = dataBase.findAvailableProductById(productId);

        if (catalogProduct == null)
            throw new NotFoundException("[!] Product with ID \"" + productId + "\" not found.");

        if (user.getStore() != null)
            if (user.getCatalogProductList().stream().anyMatch(product -> product.getId().equals(productId)))
                throw new OwnProductException("[!] You cannot add your own product.");

        if (quantToBeAdded > catalogProduct.getStock())
            throw new InsufficientStockException("[!] Insufficient stock for the requested quantity.");

        CartProduct cartProduct = user.getCartProductList().stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartProduct != null) {

            if (cartProduct.getQuantity() + cartRequest.quantity() > catalogProduct.getStock())
                throw new InsufficientStockException("[!] Insufficient stock for the requested quantity.");

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

    public void addProductByStoreNameAndProductId(CartRequest cartRequest) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        User seller = dataBase.findSellerByStoreName(session.getLastStoreViewed());

        if (seller == null)
            throw new NotFoundException("[!] Seller named \"" + session.getLastStoreViewed() + "\" not found.");

        if (seller.getStore() == null)
            throw new NotFoundException("[!] " + session.getLastStoreViewed() + "'s catalog could not be found.");

        if (seller.getCatalogProductList().isEmpty())
            throw new EmptyCatalogException(session.getLastStoreViewed() + " has no products listed.");

        String productId = cartRequest.id();
        int quantToBeAdded = cartRequest.quantity();

        Product catalogProduct = seller.getCatalogProductList().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (catalogProduct == null)
            throw new NotFoundException("Product with ID \"" + productId + "\" not found in " + session.getLastStoreViewed() + "'s catalog.");

        if (quantToBeAdded > catalogProduct.getStock())
            throw new InsufficientStockException("[!] Insufficient stock for the requested quantity.");

        CartProduct cartProduct = user.getCartProductList().stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartProduct != null) {

            if (cartProduct.getQuantity() + cartRequest.quantity() > catalogProduct.getStock())
                throw new InsufficientStockException("[!] Insufficient stock for the requested quantity.");

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

        if (user.getCartProductList().isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

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
