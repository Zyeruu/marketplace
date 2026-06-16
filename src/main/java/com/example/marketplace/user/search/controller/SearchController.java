package main.java.com.example.marketplace.user.search.controller;

import main.java.com.example.marketplace.exceptions.EmptyCatalogException;
import main.java.com.example.marketplace.user.search.dto.SearchRequest;
import main.java.com.example.marketplace.user.search.dto.SearchResponse;
import main.java.com.example.marketplace.user.search.repository.SearchRepository;
import main.java.com.example.marketplace.user.search.view.SearchView;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.user.store.model.Product;
import main.java.com.example.marketplace.shared.enums.ProductType;
import main.java.com.example.marketplace.user.store.model.Reputation;

import java.util.List;

public final class SearchController {

    private final SearchView view;
    private final SearchRepository repository;

    public SearchController(SearchView view, SearchRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public boolean searchAll() {

        try {
            List<Product> productList = repository.findProducts();
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
            List<Product> productList = repository.findByName(productName);
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
            List<Product> productList = repository.findByType(productType);
            view.printProductListCompacted(productList);
            return true;
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean searchByNameAndType() {

        SearchRequest searchRequest = view.getProductNameAndType();

        try {
            List<Product> productList = repository.findByNameAndType(searchRequest);
            view.printProductListCompacted(productList);
            return true;
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean searchForSellers() {

        try {
            List<String> sellers = repository.searchForSellers();
            view.printSellers(sellers);
            return true;
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean searchForSellersWithCatalog() {

        try {
            List<String> sellers = repository.searchForSellersWithCatalog();
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
            SearchResponse sellerCatalog = repository.findCatalogByStoreName(storeName);
            view.printCatalog(sellerCatalog);
        }
        catch (NotFoundException | EmptyCatalogException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void searchForProductReview() {

        String productId = view.getProductId();

        try {
            Product product = repository.findProductReviewByProductId(productId);
            view.printProductCompacted(product);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void searchForLastProductViewedReview() {

        try {
            Reputation productReputation = repository.findProductReviewByLastProductViewed();
            view.printReviews(productReputation);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void searchForProductReviewByProductIdAndStoreName() {

        String productId = view.getProductId();

        try {
            Reputation productReputation = repository.findProductReviewByProductIdAndStoreName(productId);
            view.printReviews(productReputation);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public boolean printAllProductDetails() {

        String productId = view.getProductId();

        try {
            Product product = repository.findById(productId);
            view.printProduct(product);

            return !product.getReviewList().isEmpty();
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }
}
