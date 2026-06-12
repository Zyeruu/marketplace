package main.java.com.example.marketplace.user.account.repository;

import main.java.com.example.marketplace.user.store.model.Product;
import main.java.com.example.marketplace.user.store.model.Store;
import main.java.com.example.marketplace.user.account.dto.AccountResponse;
import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.session.Session;

public final class AccountRepository {

    private final DataBase dataBase;
    private final Session session;

    public AccountRepository(DataBase dataBase, Session session) {
        this.dataBase = dataBase;
        this.session = session;
    }

    public AccountResponse findByEmail() {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        String name = user.getName();
        int passwordSize = user.getPassword().length();

        return new AccountResponse(name, email, passwordSize);
    }

    public void saveStore(Store store) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        user.setStore(store);
    }

    public void deleteStore() {

        String email = session.getEmail();;
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        for (Product product : user.getCatalogProductList())
            dataBase.deleteFromProductList(product);

        user.setStore(null);
    }

    public void updateEmail(String newEmail) {

        String currentEmail = session.getEmail();
        User user = dataBase.findUserByEmail(currentEmail);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (currentEmail.equals(newEmail))
            throw new IllegalArgumentException("[!] The new e-mail must be different from the current e-mail.");

        if (dataBase.existsUserByEmail(newEmail))
            throw new AlreadyExistsException("[!] E-mail already registered.");

        user.setEmail(newEmail);
    }

    public void updatePassword(String newPassword) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getPassword().equals(newPassword))
            throw new IllegalArgumentException("[!] The new password must be different from the current password.");

        user.setPassword(newPassword);
    }

    public void deleteAccount(String password) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (!user.getPassword().equals(password))
            throw new NotFoundException("[!] Incorrect password.");

        dataBase.deleteUser(user);
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
}
