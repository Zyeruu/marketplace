package main.java.com.example.marketplace.user.search.controller;

import main.java.com.example.marketplace.exceptions.EmptyCatalogException;
import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.user.search.service.SearchService;
import main.java.com.example.marketplace.user.search.view.SearchView;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.user.store.model.Catalog;
import main.java.com.example.marketplace.user.store.model.Product;
import main.java.com.example.marketplace.shared.enums.ProductType;
import main.java.com.example.marketplace.user.store.model.Reputation;

import java.util.List;

public final class SearchController {

    private final SearchView view;
    private final SearchService service;
    private final Session session;

    public SearchController(SearchView view, SearchService service, Session session) {
        this.view = view;
        this.service = service;
        this.session = session;
    }

    public boolean searchAll() {

        try {
            List<Product> productList = service.findAvailableProductList();
            view.printProductListCompacted(productList);
            return true;
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean searchByName() {

        String productName = view.getProductName();

        try {
            List<Product> productList = service.findAvailableProductListByName(productName);
            view.printProductListCompacted(productList);
            return true;
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean searchByType() {

        ProductType productType = view.getProductType();

        try {
            List<Product> productList = service.findAvailableProductListByType(productType);
            view.printProductListCompacted(productList);
            return true;
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean searchByNameAndType() {

        String name = view.getProductName();
        ProductType type = view.getProductType();

        try {
            List<Product> productList = service.findAvailableProductListByNameAndType(name, type);
            view.printProductListCompacted(productList);
            return true;
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean searchForSellers() {

        String email = session.getLoggedUserEmail();

        try {
            List<String> sellers = service.searchForSellers(email);
            view.printSellers(sellers);
            return true;
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean searchForSellersWithCatalog() {

        String email = session.getLoggedUserEmail();

        try {
            List<String> sellers = service.searchForSellersWithCatalog(email);
            view.printSellers(sellers);
            return true;
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public void searchCatalogByStoreName() {

        String storeName = view.getStoreName();

        try {
            Catalog sellerCatalog = service.findAndVerifyStoreCatalogByStoreName(storeName);
            view.printCatalog(sellerCatalog);
            session.updateLastStoreViewed(storeName);
        }
        catch (NotFoundException | EmptyCatalogException e) {
            session.updateLastStoreViewed(null);
            view.printMessage(e.getMessage());
        }
    }

    public void searchForProductReview() {

        String productId = view.getProductId();

        try {
            Reputation reputation = service.findProductReviewByProductId(productId);
            view.printReviews(reputation);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void searchForLastProductViewedReview() {

        String productId = session.getLastProductViewed();

        try {
            Reputation reputation = service.findProductReviewByProductId(productId);
            view.printReviews(reputation);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void searchForProductReviewByProductIdAndStoreName() {

        String productId = view.getProductId();
        String storeName = session.getLastStoreViewed();

        try {
            Reputation productReputation = service.findProductReviewByProductIdAndStoreName(productId, storeName);
            view.printReviews(productReputation);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public boolean printAllProductDetails() {

        String productId = view.getProductId();

        try {
            Product product = service.findProductById(productId);
            view.printProduct(product);

            if (product.getReviewList().isEmpty())
                session.updateLastProductViewed(null);
            else
                session.updateLastProductViewed(productId);

            return true;
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }
}
