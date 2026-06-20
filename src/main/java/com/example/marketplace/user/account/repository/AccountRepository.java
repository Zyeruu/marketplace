package main.java.com.example.marketplace.user.account.repository;

import main.java.com.example.marketplace.user.store.model.Product;
import main.java.com.example.marketplace.user.store.model.Store;
import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.database.DataBase;

public final class AccountRepository {

    private final DataBase dataBase;

    public AccountRepository(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public User findUserByEmail(String email) {
        return dataBase.findUserByEmail(email);
    }

    public User findSellerByStoreName(String storeName) {
        return dataBase.findSellerByStoreName(storeName);
    }

    public User findSellerByCnpj(String cnpj) {
        return dataBase.findSellerByCnpj(cnpj);
    }

    public boolean existsUserByEmail(String email) {
        return dataBase.existsUserByEmail(email);
    }

    public void deleteAccount(User user) {
        dataBase.deleteUser(user);
    }

    public void updateEmail(User user, String newEmail) {
        user.setEmail(newEmail);
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
    }

    public void saveStore(User user, Store store) {
        user.setStore(store);
    }

    public void deleteStore(User user) {

        for (Product product : user.getCatalogProductList())
            dataBase.deleteFromProductList(product);

        user.setStore(null);
    }

    public boolean verifyStoreName(String storeName) {
        return dataBase.existsSellerByStoreNameIgnoreCase(storeName);
    }
}
