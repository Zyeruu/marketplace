package main.java.com.example.marketplace.user.store.controller;

import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.EmptyCatalogException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.enums.ProductType;
import main.java.com.example.marketplace.user.store.dto.CatalogRequest;
import main.java.com.example.marketplace.user.store.dto.CatalogResponse;
import main.java.com.example.marketplace.user.store.model.Product;
import main.java.com.example.marketplace.user.store.repository.CatalogRepository;
import main.java.com.example.marketplace.user.store.view.CatalogView;

public final class CatalogController {

    private final CatalogView view;
    private final CatalogRepository repository;

    public CatalogController(CatalogView view, CatalogRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void printCatalog() {

        try {
            CatalogResponse catalogResponse = repository.findByEmail();
            view.printCatalog(catalogResponse);
        }
        catch (NotFoundException | EmptyCatalogException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void printCatalogByProductType() {

        ProductType productType = view.getProductType();

        try {
            CatalogResponse catalogResponse = repository.findByEmailAndProductType(productType);
            view.printCatalog(catalogResponse);
        }
        catch (NotFoundException | EmptyCatalogException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void printCatalogByProductName() {

        String productName = view.getProductName();

        try {
            CatalogResponse catalogResponse = repository.findByEmailAndProductName(productName);
            view.printCatalog(catalogResponse);
        }
        catch (NotFoundException | EmptyCatalogException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void printAllProductDetails() {

        String productId = view.getProductId();

        try {
            Product product = repository.findByEmailAndProductId(productId);
            view.printCatalogProduct(product);
        }
        catch (NotFoundException | EmptyCatalogException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void addProductToCatalog() {

        Product product = view.getProductData();

        try {
            repository.saveProduct(product);
            view.printMessage("[+] Product added to catalog.");
        }
        catch (NotFoundException | AlreadyExistsException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void removeCatalogProduct() {

        String productId = view.getProductId();

        try {
            repository.removeProduct(productId);
            view.printMessage("[-] Product deleted.");
        }
        catch (NotFoundException | EmptyCatalogException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void updateCatalogProduct() {

        view.printMessage("[1] Update stock\n[2] Update price");
        int choice = view.readChoice();

        switch (choice) {
            case 1 -> updateCatalogProductStock();
            case 2 -> updateCatalogProductPrice();
        }
    }

    public void updateCatalogProductStock() {

        CatalogRequest catalogRequest = view.getProductIdAndStock();

        try {
            repository.updateProductStock(catalogRequest);
            view.printMessage("[*] Stock updated.");
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void updateCatalogProductPrice() {

        CatalogRequest catalogRequest = view.getProductIdAndPrice();

        try {
            repository.updateProductPrice(catalogRequest);
            view.printMessage("[*] Price updated.");
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }
}
