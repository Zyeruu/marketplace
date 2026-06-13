package main.java.com.example.marketplace.user.store.controller;

import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.utils.Normalizer;
import main.java.com.example.marketplace.shared.utils.Validator;
import main.java.com.example.marketplace.user.store.dto.StoreResponse;
import main.java.com.example.marketplace.user.store.repository.StoreRepository;
import main.java.com.example.marketplace.user.store.view.StoreView;

public final class StoreController {

    private final StoreView view;
    private final StoreRepository repository;

    public StoreController(StoreView view, StoreRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void printStore() {

        try {
            StoreResponse storeResponse = repository.findByEmail();
            view.printStore(storeResponse);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void changeStoreName() {

        String password = view.getPassword();

        try {
            repository.verifyPassword(password);

            String storeName = view.getStoreName();
            storeName = Normalizer.normalizeStoreName(storeName);
            Validator.isValidStoreName(storeName);
            repository.updateStoreName(storeName);
            repository.updateProductStoreName();
            view.printMessage("[*] Store name successfully changed!");
        }
        catch (NotFoundException | IllegalArgumentException | AlreadyExistsException e) {
            view.printMessage(e.getMessage());
        }
    }
}
