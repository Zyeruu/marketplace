package main.java.com.example.marketplace.user.search.controller;

import main.java.com.example.marketplace.user.search.dto.SearchRequest;
import main.java.com.example.marketplace.user.search.repository.SearchRepository;
import main.java.com.example.marketplace.user.search.view.SearchView;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.user.store.model.Product;
import main.java.com.example.marketplace.shared.enums.ProductType;

import java.util.List;

public final class SearchController {

    private final SearchView view;
    private final SearchRepository repository;

    public SearchController(SearchView view, SearchRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void searchAll() {

        try {
            List<Product> productList = repository.findProducts();
            view.printProductListCompacted(productList);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void searchByName() {

        String productName = view.getProductName();

        try {
            List<Product> productList = repository.findByName(productName);
            view.printProductListCompacted(productList);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void searchByType() {

        ProductType productType = view.getProductType();

        try {
            List<Product> productList = repository.findByType(productType);
            view.printProductListCompacted(productList);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void searchByNameAndType() {

        SearchRequest searchRequest = view.getProductNameAndType();

        try {
            List<Product> productList = repository.findByNameAndType(searchRequest);
            view.printProductListCompacted(productList);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void printAllProductDetails() {

        String productId = view.getProductId();

        try {
            Product product = repository.findById(productId);
            view.printProduct(product);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }
}
