package main.java.com.example.marketplace.seller.repository;

import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.seller.dto.CatalogRequest;
import main.java.com.example.marketplace.seller.dto.CatalogResponse;
import main.java.com.example.marketplace.seller.model.Product;
import main.java.com.example.marketplace.seller.model.Seller;
import main.java.com.example.marketplace.shared.session.SellerSession;

import java.util.ArrayList;
import java.util.List;

public final class CatalogRepository {

    public CatalogResponse findByCnpj() {

        String cnpj = SellerSession.getCnpj();

        if (!DataBase.existsByCnpj(cnpj))
            throw new NotFoundException("Catalog not found.");

        Seller seller = DataBase.findSellerByCnpj(cnpj);

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

    public void saveProduct(Product product) {

        String cnpj = SellerSession.getCnpj();
        String productName = product.getName();

        if (DataBase.existsCatalogItemByNameAndCnpj(productName, cnpj))
            throw new AlreadyExistsException("The product you want to add already exists in your catalog.");

        DataBase.addToCatalog(product, cnpj);
    }

    public void removeProduct(String itemId) {

        String cnpj = SellerSession.getCnpj();

        if (!DataBase.existsCatalogItemByIdAndCnpj(itemId, cnpj))
            throw new NotFoundException("Item with ID \"" + itemId + "\" was not found.");

        DataBase.removeCatalogItem(itemId, cnpj);
    }

    public void updateProductStock(CatalogRequest catalogRequest) {

        String cnpj = SellerSession.getCnpj();

        if (!DataBase.existsCatalogItemByIdAndCnpj(catalogRequest.getId(), cnpj))
            throw new NotFoundException("Item with ID \"" + catalogRequest.getId() + "\" was not found.");

        DataBase.updateStock(catalogRequest, cnpj);
    }
}
