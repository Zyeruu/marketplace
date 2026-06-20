package main.java.com.example.marketplace.user.store.controller;

import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.EmptyCatalogException;
import main.java.com.example.marketplace.exceptions.InsufficientStockException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.enums.ProductType;
import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.user.store.dto.UpdateProductRequest;
import main.java.com.example.marketplace.user.store.dto.CatalogResponse;
import main.java.com.example.marketplace.user.store.model.Product;
import main.java.com.example.marketplace.user.store.model.Reputation;
import main.java.com.example.marketplace.user.store.service.CatalogService;
import main.java.com.example.marketplace.user.store.view.CatalogView;

public final class CatalogController {

    private final CatalogView view;
    private final CatalogService service;
    private final Session session;

    public CatalogController(CatalogView view, CatalogService service, Session session) {
        this.view = view;
        this.service = service;
        this.session = session;
    }

    public boolean printCatalog() {

        String email = session.getLoggedUserEmail();

        try {
            CatalogResponse catalog = service.findByEmail(email);
            view.printCatalog(catalog);
            return true;
        }
        catch (NotFoundException | EmptyCatalogException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean printAvailableCatalog() {

        String email = session.getLoggedUserEmail();

        try {
            CatalogResponse catalog = service.findAvailableCatalogByEmail(email);
            view.printCatalog(catalog);
            return true;
        }
        catch (NotFoundException | EmptyCatalogException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean printUnavailableCatalog() {

        String email = session.getLoggedUserEmail();

        try {
            CatalogResponse catalog = service.findUnavailableCatalogByEmail(email);
            view.printCatalog(catalog);
            return true;
        }
        catch (NotFoundException | EmptyCatalogException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean printCatalogByProductType() {

        String email = session.getLoggedUserEmail();
        ProductType productType = view.getProductType();

        try {
            CatalogResponse catalog = service.findByEmailAndProductType(email, productType);
            view.printCatalog(catalog);
            return true;
        }
        catch (NotFoundException | EmptyCatalogException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean printCatalogByProductName() {

        String email = session.getLoggedUserEmail();
        String productName = view.getProductName();

        try {
            CatalogResponse catalogResponse = service.findByEmailAndProductName(email, productName);
            view.printCatalog(catalogResponse);
            return true;
        }
        catch (NotFoundException | EmptyCatalogException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public void printAllProductDetails() {

        String email = session.getLoggedUserEmail();
        String productId = view.getProductId();

        try {
            Product product = service.findAndCopyByEmailAndId(email, productId);
            view.printCatalogProduct(product);
        }
        catch (NotFoundException | EmptyCatalogException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void addProductToCatalog() {

        String email = session.getLoggedUserEmail();
        Product product = view.getProductData();
        product.setStoreName(session.getLoggedUserStoreName());

        try {
            service.normalizeAndValidateProduct(product);

            service.verifyCatalogAndSaveProduct(email, product);
            view.printMessage("[+] Product added to catalog.");
        }
        catch (NotFoundException | AlreadyExistsException | IllegalArgumentException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void deleteCatalogProduct() {

        String email = session.getLoggedUserEmail();
        String productId = view.getProductId();

        try {
            Product product = service.findByEmailAndId(email, productId);

            if (view.getFinalDecision()) {
                service.deleteProduct(email, product);
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

        String email = session.getLoggedUserEmail();
        String productId = view.getProductId();
        String productName = view.getProductName();

        try {
            productName = service.normalizeAndValidateProductName(productName);

            UpdateProductRequest request = UpdateProductRequest.withName(productId, productName);

            service.verifyAndUpdateProductName(email, request);
            view.printMessage("[*] Name updated.");
        }
        catch (NotFoundException | IllegalArgumentException | EmptyCatalogException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void updateProductType() {

        String email = session.getLoggedUserEmail();
        String productId = view.getProductId();
        ProductType productType = view.getProductType();
        UpdateProductRequest request = UpdateProductRequest.withType(productId, productType);

        try {
            service.verifyAndUpdateProductType(email, request);
            view.printMessage("[*] Type updated.");
        }
        catch (NotFoundException | IllegalArgumentException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void updateProductBrand() {

        String email = session.getLoggedUserEmail();
        String productId = view.getProductId();
        String productBrandName = view.getProductBrand();

        try {
            productBrandName = service.normalizeAndValidateProductBrand(productBrandName);

            UpdateProductRequest updateBrand = UpdateProductRequest.withBrand(productId, productBrandName);

            service.verifyAndUpdateProductBrand(email, updateBrand);
            view.printMessage("[*] Brand updated.");
        }
        catch (NotFoundException | IllegalArgumentException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void updateProductPrice() {

        String email = session.getLoggedUserEmail();
        String productId = view.getProductId();
        float productPrice = view.getProductPrice();
        UpdateProductRequest updatePrice = UpdateProductRequest.withPrice(productId, productPrice);

        try {
            service.verifyAndUpdateProductPrice(email, updatePrice);
            view.printMessage("[*] Price updated.");
        }
        catch (NotFoundException | IllegalArgumentException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void updateProductWeight() {

        String email = session.getLoggedUserEmail();
        String productId = view.getProductId();
        float productWeight = view.getProductWeight();
        UpdateProductRequest updateWeight = UpdateProductRequest.withWeight(productId, productWeight);

        try {
            service.verifyAndUpdateProductWeight(email, updateWeight);
            view.printMessage("[*] Weight updated.");
        }
        catch (NotFoundException | IllegalArgumentException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void updateProductStock() {

        String email = session.getLoggedUserEmail();
        String productId = view.getProductId();
        int productStock = view.getProductStock();
        UpdateProductRequest updateStock = UpdateProductRequest.withStock(productId, productStock);

        try {
            service.verifyAndUpdateProductStock(email, updateStock);
            view.printMessage("[*] Stock updated.");
        }
        catch (NotFoundException | IllegalArgumentException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void updateProductWarranty() {

        String email = session.getLoggedUserEmail();
        String productId = view.getProductId();
        int productWarranty = view.getProductWarranty();
        UpdateProductRequest updateWarranty = UpdateProductRequest.withWarranty(productId, productWarranty);

        try {
            service.verifyAndUpdateProductWarranty(email, updateWarranty);
            view.printMessage("[*] Warranty updated.");
        }
        catch (NotFoundException | IllegalArgumentException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void updateProductAvailability() {

        String email = session.getLoggedUserEmail();
        String productId = view.getProductId();
        boolean availability = view.getAvailability();
        UpdateProductRequest updateProductRequest = UpdateProductRequest.withAvailable(productId, availability);

        try {
            service.verifyAndUpdateProductAvailability(email, updateProductRequest);

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
            UpdateProductRequest request = UpdateProductRequest.withStock(productId, productStock);

            try {
                service.verifyAndUpdateProductStock(email, request);
            }
            catch (NotFoundException | IllegalArgumentException ex) {
                view.printMessage(ex.getMessage());
            }
        }
    }

    public void searchForProductReview() {

        String email = session.getLoggedUserEmail();
        String productId = view.getProductId();

        try {
            Reputation productReputation = service.findProductReviewByEmailAndProductId(email, productId);
            view.printReviews(productReputation);
        }
        catch (NotFoundException | EmptyCatalogException e) {
            view.printMessage(e.getMessage());
        }
    }
}
