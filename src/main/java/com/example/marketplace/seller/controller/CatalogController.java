package main.java.com.example.marketplace.seller.controller;

import main.java.com.example.marketplace.seller.dto.CatalogResponse;
import main.java.com.example.marketplace.seller.repository.CatalogRepository;

public final class CatalogController {

    CatalogRepository repository;

    public CatalogResponse searchCnpj(String storeCnpj) {

        CatalogResponse catalogResponse = repository.cnpjExists(storeCnpj);

        if (catalogResponse == null) {
            System.out.println("Catalog not found!");
            return null;
        }
        return catalogResponse;
    }
}
