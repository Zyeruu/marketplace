package main.java.com.example.marketplace.user.store.service;

import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.utils.Normalizer;
import main.java.com.example.marketplace.shared.utils.Validator;
import main.java.com.example.marketplace.user.account.service.AccountService;
import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.user.store.dto.StoreResponse;
import main.java.com.example.marketplace.user.store.repository.StoreRepository;

public final class StoreService {

    private final StoreRepository repository;
    private final AccountService accountService;

    public StoreService(StoreRepository repository, AccountService accountService) {
        this.repository = repository;
        this.accountService = accountService;
    }

    public StoreResponse findByEmail(String email) {

        User user = accountService.findUserByEmail(email);

        return new StoreResponse(user.getStoreName(), user.getCnpj(),
                user.getSalesMenuTaxReceiptList().size(), user.getTotalRevenue());
    }

    public void verifyPassword(String email, String password) {

        User user = accountService.findUserByEmail(email);

        if (user.getPassword().equals(password))
            throw new NotFoundException("[!] Invalid password.");
    }

    public void verifyStoreName(String email, String storeName) {

        User user = accountService.findUserByEmail(email);

        if (user.getStoreName().equalsIgnoreCase(storeName)) {
            if (user.getStoreName().equals(storeName))
                throw new IllegalArgumentException("[!] The new name must be different from the current name.");
        }
        else
            if (repository.existsStoreByStoreName(storeName))
                throw new AlreadyExistsException("[!] Store name already registered.");
    }

    public void updateStoreNameAndProducts(String email, String storeName) {
        repository.updateStoreNameAndProducts(email, storeName);
    }

    public String normalizeAndValidateStoreName(String storeName) {

        storeName = Normalizer.normalizeStoreName(storeName);
        Validator.isValidStoreName(storeName);
        return storeName;
    }
}
