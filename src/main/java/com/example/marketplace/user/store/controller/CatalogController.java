package main.java.com.example.marketplace.user.store.controller;

import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.EmptyCatalogException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.enums.ProductType;
import main.java.com.example.marketplace.shared.utils.Normalizer;
import main.java.com.example.marketplace.shared.utils.Validator;
import main.java.com.example.marketplace.user.store.dto.UpdateProductRequest;
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
            product.setName(Normalizer.normalizeName(product.getName()));
            Validator.isValidProductName(product.getName());

            if (product.getType() == ProductType.MISCELLANEOUS) {
                product.setBrand(Normalizer.normalizeBrandName(product.getBrand()));
                Validator.isValidBrandName(product.getBrand());
            }

            repository.saveProduct(product);
            view.printMessage("[+] Product added to catalog.");
        }
        catch (NotFoundException | AlreadyExistsException | IllegalArgumentException e) {
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

        view.printMessageWithOutLn("-------| UPDATE A PRODUCT |--------");
        view.printMessageWithOutLn("[1] Update name\n[2] Update type\n[3] Update brand\n[4] Update price\n[5] Update weight\n[6] Update stock\n[7] Update warranty");
        view.printMessageWithOutLn("-----------------------------------");
        int choice = view.readChoice();

        switch (choice) {
            case 1 -> updateProductName();
            case 2 -> updateProductType();
            case 3 -> updateProductBrand();
            case 4 -> updateProductPrice();
            case 5 -> updateProductWeight();
            case 6 -> updateProductStock();
            case 7 -> updateProductWarranty();
        }
    }

    public void updateProductName() {

        String productId = view.getProductId();
        String productName = view.getProductName();

        try {
            productName = Normalizer.normalizeName(productName);
            Validator.isValidProductName(productName);

            UpdateProductRequest updateProductRequest = UpdateProductRequest.withName(productId,productName);

            repository.updateProductName(updateProductRequest);
            view.printMessage("[*] Name updated.");
        }
        catch (NotFoundException | IllegalArgumentException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void updateProductType() {

        String productId = view.getProductId();
        ProductType productType = view.getProductType();
        UpdateProductRequest updateProductRequest = UpdateProductRequest.withType(productId, productType);

        try {
            repository.updateProductType(updateProductRequest);
            view.printMessage("[*] Type updated.");
        }
        catch (NotFoundException | IllegalArgumentException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void updateProductBrand() {

        String productId = view.getProductId();
        String productBrandName = view.getProductBrand();

        try {
            productBrandName = Normalizer.normalizeBrandName(productBrandName);
            Validator.isValidBrandName(productBrandName);

            UpdateProductRequest updateProductRequest = UpdateProductRequest.withBrand(productId, productBrandName);

            repository.updateProductBrand(updateProductRequest);
            view.printMessage("[*] Brand updated.");
        }
        catch (NotFoundException | IllegalArgumentException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void updateProductPrice() {

        String productId = view.getProductId();
        float productPrice = view.getProductPrice();
        UpdateProductRequest updateProductRequest = UpdateProductRequest.withPrice(productId, productPrice);

        try {
            repository.updateProductPrice(updateProductRequest);
            view.printMessage("[*] Price updated.");
        }
        catch (NotFoundException | IllegalArgumentException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void updateProductWeight() {

        String productId = view.getProductId();
        float productWeight = view.getProductWeight();
        UpdateProductRequest updateProductRequest = UpdateProductRequest.withWeight(productId, productWeight);

        try {
            repository.updateProductWeight(updateProductRequest);
            view.printMessage("[*] Weight updated.");
        }
        catch (NotFoundException | IllegalArgumentException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void updateProductStock() {


        String productId = view.getProductId();
        int productStock = view.getProductStock();
        UpdateProductRequest updateProductRequest = UpdateProductRequest.withStock(productId, productStock);

        try {
            repository.updateProductStock(updateProductRequest);
            view.printMessage("[*] Stock updated.");
        }
        catch (NotFoundException | IllegalArgumentException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void updateProductWarranty() {

        String productId = view.getProductId();
        int productWarranty = view.getProductWarranty();
        UpdateProductRequest updateProductRequest = UpdateProductRequest.withWarranty(productId, productWarranty);

        try {
            repository.updateProductWarranty(updateProductRequest);
            view.printMessage("[*] Warranty updated.");
        }
        catch (NotFoundException | IllegalArgumentException e) {
            view.printMessage(e.getMessage());
        }
    }
}
