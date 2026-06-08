package main.java.com.example.marketplace.buyer.repository;

import main.java.com.example.marketplace.buyer.dto.CartRequest;
import main.java.com.example.marketplace.buyer.dto.CartResponse;
import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.buyer.model.CartProduct;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.EmptyCartException;
import main.java.com.example.marketplace.exceptions.InsufficientStockException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.enums.ProductType;
import main.java.com.example.marketplace.shared.session.BuyerSession;

import java.util.ArrayList;
import java.util.List;

public final class CartRepository {

    public CartResponse findByEmail() {

        String email = BuyerSession.getEmail();
        Buyer buyer = DataBase.findBuyerByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] Logged-in user does not exist!");

        List<CartProduct> cartProductListPointer = DataBase.findCartProductListByEmail(email);

        if (cartProductListPointer.isEmpty())
            throw new EmptyCartException("[!] Your cart is empty.");

        List<CartProduct> cartProductListCopy = new ArrayList<>();

        for (CartProduct product : cartProductListPointer)
            cartProductListCopy.add(new CartProduct(product));

        float totalCost = buyer.getCart().getTotalCost();
        int totalProducts = buyer.getCart().getTotalProducts();
        int totalFood = buyer.getCart().getTotalFood();
        int totalMisc = buyer.getCart().getTotalMisc();

        return new CartResponse(cartProductListCopy, totalCost,totalProducts, totalFood, totalMisc);
    }

    public CartResponse findByEmailAndProductType(ProductType productType) {

        String email = BuyerSession.getEmail();
        Buyer buyer = DataBase.findBuyerByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] Logged-in user does not exist.");

        List<CartProduct> cartProductListPointer = DataBase.findCartProductListByEmail(email);

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
            int totalFood = buyer.getCart().getTotalFood();
            return new CartResponse(cartProductListCopy, totalCost, 0, totalFood, 0);
        }
        else {
            int totalMisc = buyer.getCart().getTotalMisc();
            return new CartResponse(cartProductListCopy, totalCost, 0, 0, totalMisc);
        }
    }

    public CartResponse findByEmailAndProductName(String productName) {

        String email = BuyerSession.getEmail();
        Buyer buyer = DataBase.findBuyerByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] Logged-in user does not exist!");

        List<CartProduct> cartProductListPointer = DataBase.findCartProductListByEmail(email);

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

        String email = BuyerSession.getEmail();
        Buyer buyer = DataBase.findBuyerByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] Logged-in user does not exist!");

        List<CartProduct> cartProductListPointer = DataBase.findCartProductListByEmail(email);

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

        String email = BuyerSession.getEmail();
        String productId = cartRequest.getId();
        int quantToBeAdded = cartRequest.getQuantity();

        if (!DataBase.existsProductById(productId))
            throw new NotFoundException("[!] The product with ID \"" + productId + "\" was not found.");

        int productStock = DataBase.getProductStockById(productId);

        if (DataBase.existsCartProductByEmailAndId(email, productId)) {

            int cartProductQuant = DataBase.findCartProductQuantityByEmailAndId(email, productId);

            if (cartProductQuant + quantToBeAdded > productStock)
                throw new InsufficientStockException("[!] Insufficient stock for this quantity.");

            DataBase.updateProductQuantity(email, productId, quantToBeAdded);
            return;
        }

        if (quantToBeAdded > productStock)
            throw new InsufficientStockException("[!] There is not enough stock to meet the requested quantity.");

        DataBase.addProductToCart(email, productId, quantToBeAdded);
    }

    public void removeProduct(CartRequest cartRequest) {

        String email = BuyerSession.getEmail();
        String productId = cartRequest.getId();
        int quantToBeRemoved = cartRequest.getQuantity();

        if (!DataBase.existsCartProductByEmailAndId(email, productId))
            throw new NotFoundException("[!] The product with ID \"" + productId + "\" was not found.");

        DataBase.removeProductFromCart(email, productId, quantToBeRemoved);
    }
}
