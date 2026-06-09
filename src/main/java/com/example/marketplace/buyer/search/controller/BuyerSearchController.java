package main.java.com.example.marketplace.buyer.search.controller;

import main.java.com.example.marketplace.buyer.search.dto.BuyerSearchRequest;
import main.java.com.example.marketplace.buyer.search.repository.BuyerSearchRepository;
import main.java.com.example.marketplace.buyer.search.view.BuyerSearchView;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.seller.model.Product;
import main.java.com.example.marketplace.shared.enums.ProductType;

import java.util.List;

public final class BuyerSearchController {

    private final BuyerSearchView view;
    private final BuyerSearchRepository repository;

    public BuyerSearchController(BuyerSearchView view, BuyerSearchRepository repository) {
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

        BuyerSearchRequest searchRequest = view.getProductNameAndType();

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
