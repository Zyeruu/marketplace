package main.java.com.example.marketplace.user.store.repository;

import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.user.store.dto.StoreResponse;
import main.java.com.example.marketplace.user.store.model.Product;

public class StoreRepository {

    private final DataBase dataBase;
    private final Session session;

    public StoreRepository(DataBase dataBase, Session session) {
        this.dataBase = dataBase;
        this.session = session;
    }

    public StoreResponse findByEmail() {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        String storeName = user.getStoreName();
        String cnpj = user.getCnpj();
        int totalSales = user.getSalesMenuTaxReceiptList().size();
        float totalRevenue = user.getTotalRevenue();

        return new StoreResponse(storeName, cnpj, totalSales, totalRevenue);
    }

    public void updateStoreName(String storeName) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        user.setStoreName(storeName);
    }

    public void verifyStoreName(String storeName) {

        if (dataBase.existsSellerByStoreName(storeName))
            throw new AlreadyExistsException("[!] Store name already registered.");
    }

    public void verifyPassword(String password) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found");

        if (!user.getPassword().equals(password))
            throw new NotFoundException("[!] Incorrect password.");
    }

    public void updateProductStoreName() {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (!user.getCatalogProductList().isEmpty())
            for (Product product : user.getCatalogProductList())
                product.setStoreName(user.getStoreName());
    }
}
