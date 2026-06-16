package main.java.com.example.marketplace.user.store.controller;

import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.EmptyCatalogException;
import main.java.com.example.marketplace.exceptions.InsufficientStockException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.enums.ProductType;
import main.java.com.example.marketplace.shared.utils.Normalizer;
import main.java.com.example.marketplace.shared.utils.Validator;
import main.java.com.example.marketplace.user.store.dto.UpdateProductRequest;
import main.java.com.example.marketplace.user.store.dto.CatalogResponse;
import main.java.com.example.marketplace.user.store.model.Product;
import main.java.com.example.marketplace.user.store.model.Reputation;
import main.java.com.example.marketplace.user.store.repository.CatalogRepository;
import main.java.com.example.marketplace.user.store.view.CatalogView;

public final class CatalogController {

    private final CatalogView view;
    private final CatalogRepository repository;

    public CatalogController(CatalogView view, CatalogRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public boolean printCatalog() {

        try {
            CatalogResponse catalogResponse = repository.findByEmail();
            view.printCatalog(catalogResponse);
            return true;
        }
        catch (NotFoundException | EmptyCatalogException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean printAvailableCatalog() {

        try {
            CatalogResponse catalogResponse = repository.findAvailableCatalog();
            view.printCatalog(catalogResponse);
            return true;
        }
        catch (NotFoundException | EmptyCatalogException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean printUnavailableCatalog() {

        try {
            CatalogResponse catalogResponse = repository.findUnavailableCatalog();
            view.printCatalog(catalogResponse);
            return true;
        }
        catch (NotFoundException | EmptyCatalogException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean printCatalogByProductType() {

        ProductType productType = view.getProductType();

        try {
            CatalogResponse catalogResponse = repository.findByEmailAndProductType(productType);
            view.printCatalog(catalogResponse);
            return true;
        }
        catch (NotFoundException | EmptyCatalogException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean printCatalogByProductName() {

        String productName = view.getProductName();

        try {
            CatalogResponse catalogResponse = repository.findByEmailAndProductName(productName);
            view.printCatalog(catalogResponse);
            return true;
        }
        catch (NotFoundException | EmptyCatalogException e) {
            view.printMessage(e.getMessage());
            return false;
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

    public void deleteCatalogProduct() {

        String productId = view.getProductId();

        try {
            Product product = repository.findProductById(productId);

            if (view.getFinalDecision()) {
                repository.removeProduct(product);
                view.printMessage("[-] Product deleted.");
            }
            else
                view.printMessage("[X] Action canceled.");
        }
        catch (NotFoundException | EmptyCatalogException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void updateCatalogProduct() {

        view.printMessageWithOutLn("-------| UPDATE A PRODUCT |--------");
        view.printMessageWithOutLn("[1] Update name");
        view.printMessageWithOutLn("[2] Update type");
        view.printMessageWithOutLn("[3] Update brand");
        view.printMessageWithOutLn("[4] Update price");
        view.printMessageWithOutLn("[5] Update weight");
        view.printMessageWithOutLn("[6] Update stock");
        view.printMessageWithOutLn("[7] Update warranty");
        view.printMessageWithOutLn("[8] Update availability");
        view.printMessageWithOutLn("-----------------------------------");
        int choice = view.readUpdateProductChoice();

        switch (choice) {
            case 1 -> updateProductName();
            case 2 -> updateProductType();
            case 3 -> updateProductBrand();
            case 4 -> updateProductPrice();
            case 5 -> updateProductWeight();
            case 6 -> updateProductStock();
            case 7 -> updateProductWarranty();
            case 8 -> updateProductAvailability();
        }
    }

    public void updateProductName() {

        String productId = view.getProductId();
        String productName = view.getProductName();

        try {
            productName = Normalizer.normalizeName(productName);
            Validator.isValidProductName(productName);

            UpdateProductRequest updateName = UpdateProductRequest.withName(productId,productName);

            repository.updateProductName(updateName);
            view.printMessage("[*] Name updated.");
        }
        catch (NotFoundException | IllegalArgumentException | EmptyCatalogException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void updateProductType() {

        String productId = view.getProductId();
        ProductType productType = view.getProductType();
        UpdateProductRequest updateType = UpdateProductRequest.withType(productId, productType);

        try {
            repository.updateProductType(updateType);
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

            UpdateProductRequest updateBrand = UpdateProductRequest.withBrand(productId, productBrandName);

            repository.updateProductBrand(updateBrand);
            view.printMessage("[*] Brand updated.");
        }
        catch (NotFoundException | IllegalArgumentException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void updateProductPrice() {

        String productId = view.getProductId();
        float productPrice = view.getProductPrice();
        UpdateProductRequest updatePrice = UpdateProductRequest.withPrice(productId, productPrice);

        try {
            repository.updateProductPrice(updatePrice);
            view.printMessage("[*] Price updated.");
        }
        catch (NotFoundException | IllegalArgumentException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void updateProductWeight() {

        String productId = view.getProductId();
        float productWeight = view.getProductWeight();
        UpdateProductRequest updateWeight = UpdateProductRequest.withWeight(productId, productWeight);

        try {
            repository.updateProductWeight(updateWeight);
            view.printMessage("[*] Weight updated.");
        }
        catch (NotFoundException | IllegalArgumentException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void updateProductStock() {


        String productId = view.getProductId();
        int productStock = view.getProductStock();
        UpdateProductRequest updateStock = UpdateProductRequest.withStock(productId, productStock);

        try {
            repository.updateProductStock(updateStock);
            view.printMessage("[*] Stock updated.");
        }
        catch (NotFoundException | IllegalArgumentException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void updateProductWarranty() {

        String productId = view.getProductId();
        int productWarranty = view.getProductWarranty();
        UpdateProductRequest updateWarranty = UpdateProductRequest.withWarranty(productId, productWarranty);

        try {
            repository.updateProductWarranty(updateWarranty);
            view.printMessage("[*] Warranty updated.");
        }
        catch (NotFoundException | IllegalArgumentException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void updateProductAvailability() {

        String productId = view.getProductId();
        boolean availability = view.getAvailability();
        UpdateProductRequest updateProductRequest = UpdateProductRequest.withAvailable(productId, availability);

        try {
            repository.updateAvailability(updateProductRequest);

            if (availability)
                view.printMessage("[*] Product marked as available.");
            else
                view.printMessage("[*] Product marked as unavailable.");
        }
        catch (NotFoundException | IllegalArgumentException e) {
            view.printMessage(e.getMessage());
        }
        catch (InsufficientStockException e) {

            view.printMessage(e.getMessage());

            int productStock = view.getProductStock();
            UpdateProductRequest updateStock = UpdateProductRequest.withStock(productId, productStock);

            try {
                repository.updateProductStock(updateStock);
            }
            catch (NotFoundException | IllegalArgumentException ex) {
                view.printMessage(ex.getMessage());
            }
        }
    }

    public void searchForProductReview() {

        String productId = view.getProductId();

        try {
            Reputation productReputation = repository.findProductReviewByProductId(productId);
            view.printReviews(productReputation);
        }
        catch (NotFoundException | EmptyCatalogException e) {
            view.printMessage(e.getMessage());
        }
    }
}
