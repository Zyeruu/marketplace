package main.java.com.example.marketplace.database;

import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.seller.model.Product;
import main.java.com.example.marketplace.seller.model.Seller;

import java.util.ArrayList;
import java.util.List;

public final class DataBase {

    // List of registered users and their respective database
    private final List<Buyer> buyerList = new ArrayList<>();
    private final List<Seller> sellerList = new ArrayList<>();
    // List of products from all sellers
    private final List<Product> productList = new ArrayList<>();


    // =============================================| BUYER METHODS |=============================================

    // ---------------------------------------------| AUTH | ACCOUNT |--------------------------------------------

    public void saveBuyer(Buyer buyer) {
        buyerList.add(buyer);
    }

    public void deleteBuyer(Buyer buyer) {
        buyerList.remove(buyer);
    }

    // ---------------------------------------------| OTHERS METHODS |---------------------------------------------

    public boolean existsBuyerByEmail(String email) {

        return buyerList.stream()
                .anyMatch(buyer -> buyer.getEmail().equals(email));
    }

    public Buyer findBuyerByEmail(String email) {

        return buyerList.stream()
                .filter(buyer -> buyer.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public boolean existsBuyerByEmailAndPassword(String email, String password) {

        return buyerList.stream()
                .anyMatch(buyer -> buyer.getEmail().equals(email) && buyer.getPassword().equals(password));
    }


    // ==============================================| SELLER METHODS |==============================================

    // ----------------------------------------------| AUTH | ACCOUNT |----------------------------------------------

    public void saveSeller(Seller seller) {
        sellerList.add(seller);
    }

    public void deleteSeller(Seller seller) {
        sellerList.remove(seller);
    }


    // --------------------------------------------| CHECKOUT METHODS |--------------------------------------------

    public Seller findSellerByStoreName(String storeName) {

        return sellerList.stream()
                .filter(seller -> seller.getStoreName().equals(storeName))
                .findFirst()
                .orElse(null);
    }


    // ----------------------------------------------| OTHERS METHODS |----------------------------------------------

    public boolean existsSellerByEmail(String email) {

        return sellerList.stream()
                .anyMatch(seller -> seller.getEmail().equals(email));
    }

    public Seller findSellerByEmail(String email) {

        return sellerList.stream()
                .filter(seller -> seller.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public boolean existsSellerByStoreName(String storeName) {

        return sellerList.stream()
                .anyMatch(seller -> seller.getStoreName().equals(storeName));
    }


    // ---------------------------------------------| PRODUCT LIST |--------------------------------------------

    public void addToProductList(Product product) {
        productList.add(product);
    }

    public void deleteFromProductList(Product product) {
        productList.remove(product);
    }

    public Product findProductById(String productId) {

        return productList.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public List<Product> getProductList() {
        return productList;
    }
}
