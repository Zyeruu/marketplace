package main.java.com.example.marketplace.user.store.controller;

import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.user.store.dto.StoreResponse;
import main.java.com.example.marketplace.user.store.service.StoreService;
import main.java.com.example.marketplace.user.store.view.StoreView;

public final class StoreController {

    private final StoreView view;
    private final StoreService service;
    private final Session session;

    public StoreController(StoreView view, StoreService service, Session session) {
        this.view = view;
        this.service = service;
        this.session = session;
    }

    public void printStore() {

        String email = session.getLoggedUserEmail();

        try {
            StoreResponse storeResponse = service.findByEmail(email);
            view.printStore(storeResponse);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void changeStoreName() {

        String email = session.getLoggedUserEmail();
        String password = view.getPassword();

        try {
            service.verifyPassword(email, password);

            String storeName = view.getStoreName();
            storeName = service.normalizeAndValidateStoreName(storeName);

            service.verifyStoreName(email, storeName);
            service.updateStoreNameAndProducts(email, storeName);
            session.setLoggedUserStoreName(storeName);
            view.printMessage("[*] Store name successfully changed!");
        }
        catch (NotFoundException | IllegalArgumentException | AlreadyExistsException e) {
            view.printMessage(e.getMessage());
        }
    }
}
