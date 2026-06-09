package main.java.com.example.marketplace.seller.account.repository;

import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.seller.account.dto.SellerAccountResponse;
import main.java.com.example.marketplace.seller.model.Product;
import main.java.com.example.marketplace.seller.model.Seller;
import main.java.com.example.marketplace.shared.session.SellerSession;

public final class SellerAccountRepository {

    public SellerAccountResponse findByEmail() {

        String email = SellerSession.getEmail();

        if (!DataBase.existsSellerByEmail(email))
            throw new NotFoundException("[!] Logged-in user does not exist.");

        Seller seller = DataBase.findSellerByEmail(email);

        String name = seller.getName();
        int passwordSize = seller.getPassword().length();
        String storeName = seller.getStore().getName();
        String cnpj = seller.getStore().getCnpj();

        return new SellerAccountResponse(name, email, passwordSize, storeName, cnpj);
    }

    public void updateEmail(String newEmail) {

        String currentEmail = SellerSession.getEmail();

        if (currentEmail.equals(newEmail))
            throw new IllegalArgumentException("[!] The new e-mail must be different from the current e-mail.");

        if (DataBase.existsSellerByEmail(newEmail))
            throw new AlreadyExistsException("[!] E-mail already registered.");

        DataBase.updateSellerEmail(currentEmail, newEmail);
    }

    public void updatePassword(String newPassword) {

        String email = SellerSession.getEmail();

        if (DataBase.isSameSellerPassword(email, newPassword))
            throw new IllegalArgumentException("[!] The new password must be different from the current password.");

        DataBase.updateSellerPassword(email, newPassword);
    }

    public void deleteAccount(String password) {

        String email = SellerSession.getEmail();

        if (!DataBase.existsSellerByEmailAndPassword(email, password))
            throw new NotFoundException("[!] Incorrect password.");

        Seller seller = DataBase.findSellerByEmail(email);

        for (Product product : seller.getStore().getCatalog().getProductList())
            DataBase.removeFromProductList(product);

        DataBase.deleteSeller(seller);
    }

    public void verifyPassword(String password) {

        String email = SellerSession.getEmail();

        if (!DataBase.existsSellerByEmailAndPassword(email, password))
            throw new NotFoundException("Incorrect password.");
    }
}
