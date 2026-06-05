package main.java.com.example.marketplace.seller.controller;

import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.seller.dto.CatalogRequest;
import main.java.com.example.marketplace.seller.dto.CatalogResponse;
import main.java.com.example.marketplace.seller.model.Product;
import main.java.com.example.marketplace.seller.repository.CatalogRepository;
import main.java.com.example.marketplace.seller.view.CatalogView;

public final class CatalogController {

    CatalogRepository repository;
    CatalogView view;

    public void printCatalog() {

        try {
            CatalogResponse catalogResponse = repository.findByCnpj();

            if (!catalogResponse.getProductList().isEmpty())
                view.printCatalog(catalogResponse);
            else
                view.printMessage("Catalog is empty!");
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void addItemToCatalog() {

        Product product = view.getProductData();

        try {
            repository.addProduct(product);
            view.printMessage("Item added to catalog.");
        }
        catch (AlreadyExistsException e) {
            view.printMessage(e.getMessage());

            if (view.getResponse()) {
                repository.increaseStock(product);
                view.printMessage("Stock increased.");
            }
            else
                view.printMessage("Action canceled.");
        }
    }

    public void removeItemFromCatalog() {

        CatalogRequest catalogRequest = view.getItemIdAndQuantity();

        try {
            repository.removeProduct(catalogRequest);
            view.printMessage("Item removed.");
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }
}
