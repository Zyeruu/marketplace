package main.java.com.example.marketplace.database;

import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.buyer.model.CartItem;
import main.java.com.example.marketplace.seller.dto.CatalogRequest;
import main.java.com.example.marketplace.seller.model.Product;
import main.java.com.example.marketplace.seller.model.Seller;

import java.util.ArrayList;
import java.util.List;

public final class DataBase {

    // List of registered users and their respective database
    private static List<Buyer> buyerList = new ArrayList<>();
    private static List<Seller> sellerList = new ArrayList<>();


    // =============================================| BUYER METHODS |=============================================

    public static boolean isBuyerListEmpty() {
        return buyerList.isEmpty();
    }

    // ---------------------------------------------| AUTH | ACCOUNT |--------------------------------------------

    public static void saveBuyer(Buyer buyer) {
        buyerList.add(buyer);
    }

    public static void deleteBuyer(Buyer buyer) {
        buyerList.remove(buyer);
    }


    // ---------------------------------------------| CART METHODS |---------------------------------------------

    public static void addItemToCart(String itemId, int quantity, String email, String cnpj) {

        Buyer buyer = findBuyerByEmail(email);
        Seller seller = findSellerByCnpj(cnpj);

        for (Product p : seller.getStore().getCatalog().getProductList())
            if (p.getId().equals(itemId)) {

                CartItem item = new CartItem(p.getName(), itemId, p.getStoreName(), p.getType(), p.getUnitPrice(), p.getWeight(), quantity);
                buyer.getCart().getCartItemsList().add(item);
                buyer.getCart().updateCart();
            }
    }

    public static void addItemToCart(String itemId, int quantity, String email) {

        Buyer buyer = findBuyerByEmail(email);

        for (CartItem item : buyer.getCart().getCartItemsList())
            if (item.getId().equals(itemId))
                item.setQuantity(item.getQuantity() + quantity);
    }

    public static void removeItemFromCart(String buyerEmail, String itemId, int quantity) {

        Buyer buyer = findBuyerByEmail(buyerEmail);

        for (CartItem item : buyer.getCart().getCartItemsList())
            if (item.getId().equals(itemId))
                if (quantity >= item.getQuantity()) {
                    buyer.getCart().getCartItemsList().remove(item);
                    buyer.getCart().updateCart();
                }
                else
                    item.setQuantity(item.getQuantity() - quantity);
    }

    public static boolean existsCartItemByEmailAndId(String buyerEmail, String id) {

        Buyer buyer = findBuyerByEmail(buyerEmail);

        for (CartItem item : buyer.getCart().getCartItemsList())
            if (item.getId().equals(id))
                return true;
        return false;
    }

    public static int findCartItemQuantityByEmailAndId(String buyerEmail, String id) {

        Buyer buyer = findBuyerByEmail(buyerEmail);

        for (CartItem item : buyer.getCart().getCartItemsList())
            if (item.getId().equals(id))
                return item.getQuantity();
        return 0;
    }


    // ---------------------------------------------| OTHERS METHODS |---------------------------------------------

    public static boolean existsBuyerByEmail(String email) {

        for (Buyer buyer : buyerList)
            if (buyer.getEmail().equals(email))
                return true;
        return false;
    }

    public static Buyer findBuyerByEmail(String email) {

        for (Buyer buyer : buyerList)
            if (buyer.getEmail().equals(email))
                return buyer;
        return null;
    }

    public static boolean existsBuyerByEmailAndPassword(String email, String password) {

        for (Buyer buyer : buyerList)
            if (buyer.getEmail().equals(email))
                return buyer.getPassword().equals(password);
        return false;
    }


    // =============================================| SELLER METHODS |=============================================

    public static boolean isSellerListEmpty() {
        return sellerList.isEmpty();
    }

    // ----------------------------------------------| AUTH | ACCOUNT |----------------------------------------------

    public static void saveSeller(Seller seller) {
        sellerList.add(seller);
    }

    public static void deleteSeller(Seller seller) {
        sellerList.remove(seller);
    }

    // ----------------------------------------------| CATALOG METHODS |-------------------------------------------

    public static boolean existsCatalogItemByIdAndCnpj(String itemId, String cnpj) {

        Seller seller = findSellerByCnpj(cnpj);

        for (Product product : seller.getStore().getCatalog().getProductList())
            if (product.getId().equals(itemId))
                return true;
        return false;
    }

    public static boolean existsCatalogItemByNameAndCnpj(String itemName, String cnpj) {

        Seller seller = findSellerByCnpj(cnpj);

        for (Product product : seller.getStore().getCatalog().getProductList())
            if (product.getName().equalsIgnoreCase(itemName))
                return true;
        return false;
    }

    public static void addToCatalog(Product product, String cnpj) {

        Seller seller = findSellerByCnpj(cnpj);

        seller.getStore().getCatalog().getProductList().add(product);
        seller.getStore().getCatalog().updateCatalog();
    }

    public static void removeCatalogItem(String itemId, String cnpj) {

        Seller seller = findSellerByCnpj(cnpj);

        for (Product product : seller.getStore().getCatalog().getProductList())
            if (product.getId().equals(itemId))
                seller.getStore().getCatalog().getProductList().remove(product);
    }

    public static void updateStock(CatalogRequest catalogRequest, String cnpj) {

        Seller seller = findSellerByCnpj(cnpj);

        for (Product product : seller.getStore().getCatalog().getProductList())
            if (product.getId().equals(catalogRequest.getId()))
                product.setStock(catalogRequest.getQuantity());
    }

    // ---------------------------------------------| OTHERS METHODS |---------------------------------------------

    public static boolean existsSellerByEmail(String email) {

        for (Seller seller : sellerList)
            if (seller.getEmail().equals(email))
                return true;
        return false;
    }

    public static Seller findSellerByEmail(String email) {

        for (Seller seller : sellerList)
            if (seller.getEmail().equals(email))
                return seller;
        return null;
    }

    public static boolean existsByCnpj(String cnpj) {

        for (Seller seller : sellerList)
            if (seller.getStore().getCnpj().equals(cnpj))
                return true;
        return false;
    }

    public static Seller findSellerByCnpj(String cnpj) {

        for (Seller seller : sellerList)
            if (seller.getStore().getCnpj().equals(cnpj))
                return seller;
        return null;
    }

    public static boolean existsSellerByEmailAndPassword(String email, String password) {

        for (Seller seller : sellerList)
            if (seller.getEmail().equals(email))
                return seller.getPassword().equals(password);
        return false;
    }

    public static int getNumberOfSellers() {
        return sellerList.size();
    }

    public static boolean existsItemByIdAndCnpj(String id, String cnpj) {

        Seller seller = findSellerByCnpj(cnpj);

        for (Product product : seller.getStore().getCatalog().getProductList())
            if (product.getId().equals(id))
                return true;
        return false;
    }

    public static int getCatalogItemQuantityByIdAndCnpj(String id, String cnpj) {

        Seller seller = findSellerByCnpj(cnpj);

        for (Product product : seller.getStore().getCatalog().getProductList())
            if (product.getId().equals(id))
                return product.getStock();
        return -1;
    }

    // --------------------------------------------| CHECKOUT METHODS |--------------------------------------------

    public static boolean isCartEmptyByEmail(String email) {

        Buyer buyer = findBuyerByEmail(email);
        return buyer.getCart().getCartItemsList().isEmpty();
    }

    public static List<CartItem> findCartItemListByEmail(String email) {

        Buyer buyer = findBuyerByEmail(email);
        return buyer.getCart().getCartItemsList();
    }

    public static Seller findSellerByStoreName(String storeName) {

        for (Seller seller : sellerList)
            if (seller.getStore().getName().equals(storeName))
                return seller;
        return null;
    }

    public static Product findCatalogItemByStoreNameAndId(String storeName, String itemId) {

        Seller seller = findSellerByStoreName(storeName);

        for (Product item : seller.getStore().getCatalog().getProductList())
            if (item.getType().equals(itemId))
                return item;
        return null;
    }

    public static List<Product> findCatalogItemListByStoreName(String storeName) {

        Seller seller = findSellerByStoreName(storeName);
        return seller.getStore().getCatalog().getProductList();
    }

    public static void removeItemFromCatalog(String storeName, Product product, int quantity) {

        Seller seller = findSellerByStoreName(storeName);

        if (quantity == product.getStock())
            seller.getStore().getCatalog().getProductList().remove(product);
        else
            product.updateStock(product.getStock() - quantity);
        seller.getStore().getCatalog().updateCatalog();
    }
}
