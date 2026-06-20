package main.java.com.example.marketplace.user.store.repository;

import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.user.store.model.Product;

public final class StoreRepository {

    private final DataBase dataBase;

    public StoreRepository(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public void updateStoreNameAndProducts(String email, String storeName) {

        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        user.setStoreName(storeName);

        if (!user.getCatalogProductList().isEmpty())
            for (Product product : user.getCatalogProductList())
                product.setStoreName(user.getStoreName());
    }

    public boolean existsStoreByStoreName(String storeName) {
        return dataBase.existsSellerByStoreNameIgnoreCase(storeName);
    }
}
