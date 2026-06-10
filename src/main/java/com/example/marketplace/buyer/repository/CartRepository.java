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

        List<CartProduct> cartProductListPointer = buyer.getCartProductList();

        if (cartProductListPointer.isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

        List<CartProduct> cartProductListCopy = new ArrayList<>();

        for (CartProduct product : cartProductListPointer)
            cartProductListCopy.add(new CartProduct(product));

        float totalCost = buyer.getCartTotalCost();
        int totalProducts = buyer.getCartTotalProducts();
        int totalFood = buyer.getCartTotalFood();
        int totalMisc = buyer.getCartTotalMisc();

        return new CartResponse(cartProductListCopy, totalCost,totalProducts, totalFood, totalMisc);
    }

    public CartResponse findByEmailAndProductType(ProductType productType) {

        String email = session.getEmail();
        Buyer buyer = dataBase.findBuyerByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] Logged-in user does not exist.");

        List<CartProduct> cartProductListPointer = buyer.getCartProductList();

        if (cartProductListPointer.isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

        List<CartProduct> cartProductListCopy = new ArrayList<>();

        float totalCost = 0;

        for (CartProduct product : cartProductListPointer) {
            if (product.getType() == productType) {
                cartProductListCopy.add(new CartProduct(product));
                totalCost += product.getUnitPrice() * product.getQuantity();
            }
        }

        if (cartProductListCopy.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        if (productType == ProductType.FOOD) {
            int totalFood = buyer.getCartTotalFood();
            return new CartResponse(cartProductListCopy, totalCost, 0, totalFood, 0);
        }
        else {
            int totalMisc = buyer.getCartTotalMisc();
            return new CartResponse(cartProductListCopy, totalCost, 0, 0, totalMisc);
        }
    }

    public CartResponse findByEmailAndProductName(String productName) {

        String email = session.getEmail();
        Buyer buyer = dataBase.findBuyerByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] User not found.");

        List<CartProduct> cartProductListPointer = buyer.getCartProductList();

        if (cartProductListPointer.isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

        List<CartProduct> cartProductListCopy = new ArrayList<>();

        float totalCost = 0;
        int totalProducts;
        int totalFood = 0;
        int totalMisc = 0;

        for (CartProduct product : cartProductListPointer) {
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

        return new CartResponse(cartProductListCopy, totalCost, totalProducts, totalFood, totalMisc);
    }

    public CartProduct findByEmailAndProductId(String productId) {

        String email = session.getEmail();
        Buyer buyer = dataBase.findBuyerByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] User not found.");

        List<CartProduct> cartProductListPointer = buyer.getCartProductList();

        if (cartProductListPointer.isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

        CartProduct cartProduct = null;

        for (CartProduct product : cartProductListPointer)
            if (product.getId().equals(productId)) {
                cartProduct = new CartProduct(product);
                break;
            }

        if (cartProduct == null)
            throw new NotFoundException("[!] The product with ID \"" + productId + "\" was not found.");

        return cartProduct;
    }

    public void addProduct(CartRequest cartRequest) {

        String email = session.getEmail();
        Buyer buyer = dataBase.findBuyerByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] User not found.");

        String productId = cartRequest.getId();
        int quantToBeAdded = cartRequest.getQuantity();
        Product catalogProduct = dataBase.findProductById(productId);

        if (catalogProduct == null)
            throw new NotFoundException("[!] The product with ID \"" + productId + "\" was not found.");

        if (quantToBeAdded > catalogProduct.getStock())
            throw new InsufficientStockException("[!] There is not enough stock to meet the requested quantity.");

        CartProduct cartProduct = findCartProductById(buyer, productId);

        if (cartProduct != null) {

            if (cartProduct.getQuantity() + cartRequest.getQuantity() > catalogProduct.getStock())
                throw new InsufficientStockException("[!] Insufficient stock for this quantity.");

            cartProduct.setQuantity(cartProduct.getQuantity() + cartRequest.getQuantity());
            return;
        }

        buyer.getCartProductList().add(new CartProduct(catalogProduct.getName(), productId, catalogProduct.getStoreName(),
                catalogProduct.getType(), catalogProduct.getBrand(), catalogProduct.getUnitPrice(), catalogProduct.getWeight(),
                cartRequest.getQuantity(), catalogProduct.getWarranty()));
    }

    public void removeProduct(CartRequest cartRequest) {

        String email = session.getEmail();
        Buyer buyer = dataBase.findBuyerByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] User not found.");

        CartProduct cartProduct = findCartProductById(buyer, cartRequest.getId());

        if (cartProduct == null)
            throw new NotFoundException("[!] The product with ID \"" + cartRequest.getId() + "\" was not found.");

        if (cartProduct.getQuantity() == cartRequest.getQuantity())
            buyer.getCartProductList().remove(cartProduct);
        else
            cartProduct.setQuantity(cartProduct.getQuantity() - cartRequest.getQuantity());
    }

    public CartProduct findCartProductById(Buyer buyer, String productId) {

        for (CartProduct product : buyer.getCartProductList())
            if (product.getId().equals(productId))
                return product;
        return null;
    }
}
