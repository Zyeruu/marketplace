package main.java.com.example.marketplace.database;

import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.buyer.model.CartItem;
import main.java.com.example.marketplace.seller.model.Product;
import main.java.com.example.marketplace.seller.model.Seller;
import main.java.com.example.marketplace.shared.ItemType;

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
        Seller seller = findByCnpj(cnpj);

        for (Product product : seller.getStore().getCatalog().getProductList())
            if (product.getId().equals(itemId)) {

                CartItem item = new CartItem(product.getName(), itemId, product.getStoreName(), product.getType(), product.getUnitPrice(), product.getWeight(), quantity);
                buyer.getCart().getCartItemsList().add(item);

                if (item.getType() == ItemType.FOOD)
                    buyer.getCart().setTotalFood(quantity);
                else
                    buyer.getCart().setTotalMisc(quantity);
                buyer.getCart().setTotalItems();
            }
    }

    public static void addItemToCart(String itemId, int quantity, String email) {

        Buyer buyer = findBuyerByEmail(email);

        for (CartItem item : buyer.getCart().getCartItemsList())
            if (item.getId().equals(itemId)) {

                item.setQuantity(item.getQuantity() + quantity);

                if (item.getType() == ItemType.FOOD)
                    buyer.getCart().setTotalFood(quantity);
                else
                    buyer.getCart().setTotalMisc(quantity);
            }
        buyer.getCart().setTotalItems();
    }

    public static void removeItemFromCart(String buyerEmail, String itemId, int quantity) {

        Buyer buyer = findBuyerByEmail(buyerEmail);

        for (CartItem item : buyer.getCart().getCartItemsList())
            if (item.getId().equals(itemId))
                if (quantity >= item.getQuantity()) {

                    buyer.getCart().getCartItemsList().remove(item);
                    if (item.getType() == ItemType.FOOD)
                        buyer.getCart().setTotalFood(item.getQuantity());
                    else
                        buyer.getCart().setTotalMisc(item.getQuantity());
                }
                else {
                    item.setQuantity(item.getQuantity() - quantity);
                    if (item.getType() == ItemType.FOOD)
                        buyer.getCart().setTotalFood(quantity);
                    else
                        buyer.getCart().setTotalMisc(quantity);
                }
        buyer.getCart().setTotalItems();
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

    public static Seller findByCnpj(String cnpj) {

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

    public static int findNumberOfSellers() {
        return sellerList.size();
    }

    public static boolean existsItemByIdAndCnpj(String id, String cnpj) {

        for (Seller seller : sellerList)
            if (seller.getStore().getCnpj().equals(cnpj))
                for (Product product : seller.getStore().getCatalog().getProductList())
                    if (product.getId().equals(id))
                        return true;
        return false;
    }

    public static int findCatalogItemQuantityByIdAndCnpj(String id, String cnpj) {

        for (Seller seller : sellerList)
            if (seller.getStore().getCnpj().equals(cnpj))
                for (Product product : seller.getStore().getCatalog().getProductList())
                    if (product.getId().equals(id))
                        return product.getStock();
        return 0;
    }

    // Getters
    public static List<Buyer> getBuyerList() {
        return buyerList;
    }

    public static List<Seller> getSellerList() {
        return sellerList;
    }

    // Setters
    public static void setBuyerList(List<Buyer> buyerList) {
        DataBase.buyerList = buyerList;
    }

    public static void setSellerList(List<Seller> sellerList) {
        DataBase.sellerList = sellerList;
    }
}
