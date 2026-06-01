package main.java.com.example.marketplace.database;

import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.seller.model.Seller;

import java.util.ArrayList;
import java.util.List;

public final class DataBase {

    // List of registered users and their respective database
    private static List<Buyer> buyerList = new ArrayList<>();
    private static List<Seller> sellerList = new ArrayList<>();

    public static boolean isBuyerListEmpty() {

        return buyerList.isEmpty();
    }

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

    public static boolean isSellerListEmpty() {

        return sellerList.isEmpty();
    }

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
