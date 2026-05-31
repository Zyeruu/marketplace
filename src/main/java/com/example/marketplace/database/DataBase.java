package main.java.com.example.marketplace.database;

import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.seller.model.Seller;

import java.util.ArrayList;
import java.util.List;

public final class DataBase {

    // List of registered users and their respective database
    private static List<Buyer> buyerList = new ArrayList<>();
    private static List<Seller> sellerList = new ArrayList<>();

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
