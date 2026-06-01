package main.java.com.example.marketplace.seller.controller;

import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.seller.dto.CatalogResponse;
import main.java.com.example.marketplace.seller.repository.CatalogRepository;
import main.java.com.example.marketplace.seller.view.CatalogView;

public final class CatalogController {

    CatalogRepository repository;
    CatalogView view;

    public void findByCnpj(String cnpj) {

        try {
            CatalogResponse catalogResponse = repository.findByCnpj(cnpj);

            if (catalogResponse.getProductList().isEmpty())
                view.printMessage("Catalog is empty!");

            view.printCatalog(catalogResponse);
        }
        catch (NotFoundException e) {
            view.printException(e);
        }
    }
}
