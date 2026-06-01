package main.java.com.example.marketplace.seller.repository;

import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.seller.dto.CatalogResponse;
import main.java.com.example.marketplace.seller.model.Product;
import main.java.com.example.marketplace.seller.model.Seller;

import java.util.ArrayList;
import java.util.List;

public final class CatalogRepository {

    public CatalogResponse findByCnpj(String cnpj) {

        if (!DataBase.existsByCnpj(cnpj))
            throw new NotFoundException("Catalog not found.");

        Seller seller = DataBase.findByCnpj(cnpj);

        List<Product> productListPointer = seller.getStore().getCatalog().getProductList();
        List<Product> productListCopy = new ArrayList<>();

        // Copies the items from productListPointer to productListCopy
        for (Product product : productListPointer)
            productListCopy.add(new Product(product));

        int totalItems = seller.getStore().getCatalog().getTotalItems();
        int totalFood = seller.getStore().getCatalog().getTotalFood();
        int totalMisc = seller.getStore().getCatalog().getTotalMisc();

        return new CatalogResponse(productListCopy, totalItems, totalFood, totalMisc);
    }
}
