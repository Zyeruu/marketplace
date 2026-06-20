package main.java.com.example.marketplace.database;

import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.user.store.model.Product;

import java.util.ArrayList;
import java.util.List;

public final class DataBase {

    // List of registered users and their respective database
    private final List<User> userList = new ArrayList<>();
    // List of products from all sellers
    private final List<Product> productList = new ArrayList<>();


    // ================================================| USER METHODS |================================================

    // ------------------------------------------------| AUTH | ACCOUNT |-----------------------------------------------

    public void saveUser(User user) {
        userList.add(user);
    }

    public void deleteUser(User user) {
        userList.remove(user);
    }

    // ------------------------------------------------| OTHERS METHODS |-----------------------------------------------

    public boolean existsUserByEmail(String email) {

        return userList.stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    public User findUserByEmail(String email) {

        return userList.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public List<User> getUserList() {
        return userList;
    }

    public boolean existsUserByEmailAndPassword(String email, String password) {

        return userList.stream()
                .anyMatch(user -> user.getEmail().equals(email) && user.getPassword().equals(password));
    }

    public User findUserByEmailAndPassword(String email, String password) {

        return userList.stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst().orElse(null);
    }


    // ================================================| SELLER METHODS |===============================================

    // -----------------------------------------------| CHECKOUT METHODS |----------------------------------------------

    public User findSellerByStoreName(String storeName) {

        return userList.stream()
                .filter(user -> user.getStore() != null)
                .filter(seller -> seller.getStoreName().equalsIgnoreCase(storeName))
                .findFirst()
                .orElse(null);
    }


    // ------------------------------------------------| OTHERS METHODS |-----------------------------------------------


    public boolean existsSellerByStoreNameIgnoreCase(String storeName) {

        return userList.stream()
                .filter(seller -> seller.getStore() != null)
                .anyMatch(user -> user.getStoreName().equalsIgnoreCase(storeName));
    }

    public User findSellerByStoreNameIgnoreCase(String storeName) {

        return userList.stream()
                .filter(seller -> seller.getStore() != null)
                .filter(user -> user.getStoreName().equalsIgnoreCase(storeName))
                .findFirst()
                .orElse(null);
    }

    public User findSellerByCnpj(String cnpj) {

        return userList.stream()
                .filter(seller -> seller.getStore() != null)
                .filter(seller -> seller.getCnpj().equals(cnpj)).findFirst().orElse(null);
    }

    // =================================================| PRODUCT LIST |================================================

    public void addToProductList(Product product) {
        productList.add(product);
    }

    public void deleteFromProductList(Product product) {
        productList.remove(product);
    }

    public Product findAvailableProductById(String productId) {

        return productList.stream()
                .filter(Product::isAvailable)
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElse(null);
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
