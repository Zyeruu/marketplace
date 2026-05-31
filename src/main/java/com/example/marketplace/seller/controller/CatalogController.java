package main.java.com.example.marketplace.seller.controller;

import main.java.com.example.marketplace.seller.dto.CatalogResponse;
import main.java.com.example.marketplace.seller.repository.CatalogRepository;

public final class CatalogController {

    CatalogRepository repository;

    public CatalogResponse findByCnpj(String cnpj) {

        try {
            return repository.findByCnpj(cnpj);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
