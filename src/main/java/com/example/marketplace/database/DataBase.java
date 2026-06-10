package main.java.com.example.marketplace.database;

import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.seller.model.Product;
import main.java.com.example.marketplace.seller.model.Seller;
import main.java.com.example.marketplace.shared.enums.ProductType;

import java.util.ArrayList;
import java.util.List;

public final class DataBase {

    // List of registered users and their respective database
    private final List<Buyer> buyerList = new ArrayList<>();
    private final List<Seller> sellerList = new ArrayList<>();
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

        for (Buyer buyer : buyerList)
            if (buyer.getEmail().equals(email))
                return true;
        return false;
    }

    public Buyer findBuyerByEmail(String email) {

        for (Buyer buyer : buyerList)
            if (buyer.getEmail().equals(email))
                return buyer;
        return null;
    }

    public boolean existsBuyerByEmailAndPassword(String email, String password) {

        for (Buyer buyer : buyerList)
            if (buyer.getEmail().equals(email))
                return buyer.getPassword().equals(password);
        return false;
    }


    // ==============================================| SELLER METHODS |==============================================

    // ----------------------------------------------| AUTH | ACCOUNT |----------------------------------------------

    public void saveSeller(Seller seller) {
        sellerList.add(seller);
    }

    public void deleteSeller(Seller seller) {
        sellerList.remove(seller);
    }


    // ----------------------------------------------| OTHERS METHODS |----------------------------------------------

    public boolean existsSellerByEmail(String email) {

        for (Seller seller : sellerList)
            if (seller.getEmail().equals(email))
                return true;
        return false;
    }

    public Seller findSellerByEmail(String email) {

        for (Seller seller : sellerList)
            if (seller.getEmail().equals(email))
                return seller;
        return null;
    }

    public boolean existsSellerByStoreName(String storeName) {

        for (Seller seller : sellerList)
            if (seller.getStoreName().equals(storeName))
                return true;
        return false;
    }


    // --------------------------------------------| CHECKOUT METHODS |--------------------------------------------

    public Seller findSellerByStoreName(String storeName) {

        for (Seller seller : sellerList)
            if (seller.getStoreName().equals(storeName))
                return seller;
        return null;
    }


    // ---------------------------------------------| PRODUCT LIST |--------------------------------------------

    public boolean isProductListEmpty() {
        return productList.isEmpty();
    }

    public void addToProductList(Product product) {
        productList.add(product);
    }

    public void deleteFromProductList(Product product) {
        productList.remove(product);
    }

    public boolean existsProductByName(String productName) {

        for (Product product : productList)
            if (product.getName().equalsIgnoreCase(productName))
                return true;
        return false;
    }

    public boolean existsProductByType(ProductType productType) {

        for (Product product : productList)
            if (product.getType() == productType)
                return true;
        return false;
    }

    public Product findProductById(String id) {

        for (Product product : productList)
            if (product.getId().equals(id))
                return product;
        return null;
    }

    public List<Product> getProductList() {
        return productList;
    }
}
