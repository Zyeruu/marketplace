package main.java.com.example.marketplace.user.service;

import main.java.com.example.marketplace.exceptions.*;
import main.java.com.example.marketplace.shared.enums.ProductType;
import main.java.com.example.marketplace.user.account.service.AccountService;
import main.java.com.example.marketplace.user.model.Cart;
import main.java.com.example.marketplace.user.model.CartProduct;
import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.user.repository.CartRepository;
import main.java.com.example.marketplace.user.store.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public final class CartService {

    private final CartRepository repository;
    private final AccountService accountService;

    public CartService(CartRepository repository, AccountService accountService) {
        this.repository = repository;
        this.accountService = accountService;
    }

    public User findUserByEmail(String email) {
        return accountService.findUserByEmail(email);
    }

    public User findSellerByStoreName(String storeName) {
        return accountService.findSellerByStoreName(storeName);
    }

    public Cart findCartByEmail(String email) {

        User user = accountService.findUserByEmail(email);

        if (user.getCartProductList().isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

        user.updateCart();
        return user.getCart();
    }

    public Cart findAndCopyCartByEmail(String email) {

        User user = accountService.findUserByEmail(email);

        if (user.getCartProductList().isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

        user.updateCart();
        return new Cart(user.getCart());
    }

    public Cart findSelectedByEmail(String email) {

        Cart cart = findAndCopyCartByEmail(email);

        List<CartProduct> cartProductListCopy = cart.getProductList().stream()
                .filter(CartProduct::isSelected)
                .map(CartProduct::new)
                .collect(Collectors.toList());

        if (cartProductListCopy.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        float shipping = cart.getSelectedShipping();

        float totalCost = shipping + (float) cartProductListCopy.stream()
                .mapToDouble(product -> product.getUnitPrice() * product.getQuantity())
                .sum();

        int totalFood = (int) cartProductListCopy.stream()
                .filter(product -> product.getType() == ProductType.FOOD)
                .count();

        int totalMisc = (int) cartProductListCopy.stream()
                .filter(product -> product.getType() == ProductType.MISCELLANEOUS)
                .count();

        return new Cart(cartProductListCopy, totalCost, shipping, totalFood + totalMisc, totalFood, totalMisc);
    }

    public Cart findDeselectedByEmail(String email) {

        Cart cart = findAndCopyCartByEmail(email);

        List<CartProduct> cartProductListCopy = cart.getProductList().stream()
                .filter(product -> !product.isSelected())
                .map(CartProduct::new)
                .collect(Collectors.toList());

        if (cartProductListCopy.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        float shipping = cart.getSelectedShipping();

        float totalCost = shipping + (float) cartProductListCopy.stream()
                .mapToDouble(product -> product.getUnitPrice() * product.getQuantity())
                .sum();

        int totalFood = (int) cartProductListCopy.stream()
                .filter(product -> product.getType() == ProductType.FOOD)
                .count();

        int totalMisc = (int) cartProductListCopy.stream()
                .filter(product -> product.getType() == ProductType.MISCELLANEOUS)
                .count();

        return new Cart(cartProductListCopy, totalCost, shipping, totalFood + totalMisc, totalFood, totalMisc);
    }

    public Cart findByEmailAndProductType(String email, ProductType productType) {

        Cart cart = repository.findByEmailAndProductType(email, productType);

        if (cart.getProductList().isEmpty())
            throw new EmptyCartException("[!] No results were found.");

        return cart;
    }

    public Cart findByEmailAndProductName(String email, String productName) {

        Cart cart = repository.findByEmailAndProductName(email, productName);

        if (cart.getProductList().isEmpty())
            throw new EmptyCartException("[!] No results were found.");

        return cart;
    }

    public CartProduct findByEmailAndProductId(String email, String productId) {

        CartProduct cartProduct = repository.findByEmailAndProductId(email, productId);

        if (cartProduct == null)
            throw new NotFoundException("[!] Product with ID \"" + productId + "\" not found.");

        return cartProduct;
    }

    public boolean existsReviewByProductId(String productId) {
        return repository.existsReviewByProductId(productId);
    }

    public void findAndVerifyAndAddProductByIdAndQuantity(String email, String productId, int quantity) {

        Product catalogProduct = repository.findAvailableProductById(productId);

        if (catalogProduct == null)
            throw new NotFoundException("[!] Product with ID \"" + productId + "\" not found.");

        User user = findUserByEmail(email);

        if (user.getStore() != null)
            if (user.getCatalogProductList().stream().anyMatch(product -> product.getId().equals(productId)))
                throw new OwnProductException("[!] You cannot add your own product.");

        if (quantity > catalogProduct.getStock())
            throw new InsufficientStockException("[!] Insufficient stock for the requested quantity.");

        CartProduct cartProduct = user.getCartProductList().stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartProduct != null) {
            if (cartProduct.getQuantity() + quantity > catalogProduct.getStock())
                throw new InsufficientStockException("[!] Insufficient stock for the requested quantity.");

            repository.addProduct(cartProduct, quantity);
        }
        else {
            CartProduct newProduct = new CartProduct(catalogProduct.getName(), productId, catalogProduct.getStoreName(),
                    catalogProduct.getType(), catalogProduct.getBrand(), catalogProduct.getUnitPrice(),
                    catalogProduct.getWeight(), quantity, catalogProduct.getWarranty());

            repository.addProduct(user.getCart(), newProduct);
        }
    }

    public void verifyLastStoreViewedAndAddProduct(String email, String storeName, String productId, int quantity) {

        User seller = findSellerByStoreName(storeName);

        if (seller.getStore() == null)
            throw new NotFoundException("[!] " + storeName + "'s catalog could not be found.");

        if (seller.getCatalogProductList().isEmpty())
            throw new EmptyCatalogException(storeName + " has no products listed.");

        Product catalogProduct = seller.getCatalogProductList().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (catalogProduct == null)
            throw new NotFoundException("[!] Product with ID \"" + productId + "\" not found in " + storeName + "'s catalog.");

        if (quantity > catalogProduct.getStock())
            throw new InsufficientStockException("[!] Insufficient stock for the requested quantity.");

        Cart cart = findCartByEmail(email);

        CartProduct cartProduct = cart.getProductList().stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartProduct != null) {
            if (cartProduct.getQuantity() + quantity > catalogProduct.getStock())
                throw new InsufficientStockException("[!] Insufficient stock for the requested quantity.");

            repository.addProduct(cartProduct, quantity);
        }
        else {
            CartProduct newProduct = new CartProduct(catalogProduct.getName(), productId, catalogProduct.getStoreName(),
                    catalogProduct.getType(), catalogProduct.getBrand(), catalogProduct.getUnitPrice(),
                    catalogProduct.getWeight(), quantity, catalogProduct.getWarranty());

            repository.addProduct(cart, newProduct);
        }
    }

    public void verifyCartAndRemoveProduct(String email, String productId, int quantity) {

        Cart cart = findCartByEmail(email);

        CartProduct cartProduct = cart.getProductList().stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartProduct == null)
            throw new NotFoundException("[!] Product with ID \"" + productId + "\" not found.");

        if (cartProduct.getQuantity() == quantity)
            repository.removeProduct(cart, cartProduct);
        else
            repository.removeProduct(cartProduct, quantity);
    }

    public void verifyCartAndSelectProduct(String email, String productId) {

        Cart cart = findCartByEmail(email);
        CartProduct cartProduct = verifyCartAndFindProductById(cart, productId);

        if (cartProduct.isSelected())
            throw new ProductSelectionStateException("[!] Product already selected.");

        repository.updateSelectedStatus(cartProduct, true);
    }

    public void verifyCartAndDeselectProduct(String email, String productId) {

        Cart cart = findCartByEmail(email);
        CartProduct cartProduct = verifyCartAndFindProductById(cart, productId);

        if (!cartProduct.isSelected())
            throw new ProductSelectionStateException("[!] Product already deselected.");

        repository.updateSelectedStatus(cartProduct, false);
    }

    public void verifyCartAndSelectAll(String email) {

        Cart cart = findCartByEmail(email);

        if (cart.getProductList().stream().allMatch(CartProduct::isSelected))
            throw new IllegalArgumentException("[!] All products are already selected.");

        repository.updateSelectedStatus(cart, true);
    }

    public void verifyCartAndDeselectAll(String email) {

        Cart cart = findCartByEmail(email);

        if (cart.getProductList().stream().noneMatch(CartProduct::isSelected))
            throw new IllegalArgumentException("[!] All products are already deselected.");

        repository.updateSelectedStatus(cart, false);
    }

    public CartProduct verifyCartAndFindProductById(Cart cart, String productId) {

        CartProduct cartProduct = cart.getProductList().stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartProduct == null)
            throw new NotFoundException("[!] Product with ID \"" + productId + "\" not found.");

        return cartProduct;
    }
}
