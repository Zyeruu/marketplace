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

    public void addProductToCatalog() {

        Product product = view.getProductData();

        try {
            repository.saveProduct(product);
            view.printMessage("Item added to catalog.");
        }
        catch (AlreadyExistsException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void removeCatalogItem() {

        String itemId = view.getProducttId();

        try {
            repository.removeProduct(itemId);
            view.printMessage("Item removed.");
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void updateCatalogItem() {

        CatalogRequest catalogRequest = view.getProductIdAndStock();

        try {
            repository.updateProductStock(catalogRequest);
            view.printMessage("Stock updated.");
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }
}
