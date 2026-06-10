package main.java.com.example.marketplace.seller.account.repository;

import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.seller.account.dto.SellerAccountResponse;
import main.java.com.example.marketplace.seller.model.Product;
import main.java.com.example.marketplace.seller.model.Seller;
import main.java.com.example.marketplace.shared.session.Session;

public final class SellerAccountRepository {

    private final DataBase dataBase;
    private final Session session;

    public SellerAccountRepository(DataBase dataBase, Session session) {
        this.dataBase = dataBase;
        this.session = session;
    }

    public SellerAccountResponse findByEmail() {

        String email = session.getEmail();
        Seller seller = dataBase.findSellerByEmail(email);

        if (seller == null)
            throw new NotFoundException("[!] User not found.");

        String name = seller.getName();
        int passwordSize = seller.getPassword().length();
        String storeName = seller.getStoreName();
        String cnpj = seller.getCnpj();

        return new SellerAccountResponse(name, email, passwordSize, storeName, cnpj);
    }

    public void updateEmail(String newEmail) {

        String currentEmail = session.getEmail();
        Seller seller = dataBase.findSellerByEmail(currentEmail);

        if (seller == null)
            throw new NotFoundException("[!] User not found.");

        if (currentEmail.equals(newEmail))
            throw new IllegalArgumentException("[!] The new e-mail must be different from the current e-mail.");

        if (dataBase.existsSellerByEmail(newEmail))
            throw new AlreadyExistsException("[!] E-mail already registered.");

        seller.setEmail(newEmail);
    }

    public void updatePassword(String newPassword) {

        String email = session.getEmail();
        Seller seller = dataBase.findSellerByEmail(email);

        if (seller == null)
            throw new NotFoundException("[!] User not found.");

        if (seller.getPassword().equals(newPassword))
            throw new IllegalArgumentException("[!] New password must be different from the current password.");

        seller.setPassword(newPassword);
    }

    public void deleteAccount(String password) {

        String email = session.getEmail();
        Seller seller = dataBase.findSellerByEmail(email);

        if (seller == null)
            throw new NotFoundException("[!] User not found.");

        if (!seller.getPassword().equals(password))
            throw new NotFoundException("[!] Incorrect password.");

        for (Product product : seller.getProductList())
            dataBase.deleteFromProductList(product);

        dataBase.deleteSeller(seller);
    }

    public void verifyPassword(String password) {

        String email = session.getEmail();
        Seller seller = dataBase.findSellerByEmail(email);

        if (seller == null)
            throw new NotFoundException("[!] User not found.");

        if (!seller.getPassword().equals(password))
            throw new NotFoundException("Incorrect password.");
    }
}
