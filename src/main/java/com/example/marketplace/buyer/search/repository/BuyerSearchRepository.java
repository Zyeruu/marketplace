package main.java.com.example.marketplace.buyer.search.repository;

import main.java.com.example.marketplace.buyer.search.dto.BuyerSearchRequest;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.seller.model.Product;
import main.java.com.example.marketplace.shared.enums.ProductType;

import java.util.ArrayList;
import java.util.List;

public final class BuyerSearchRepository {

    public List<Product> findProducts() {

        if (DataBase.isProductListEmpty())
            throw new NotFoundException("[!] No results were found.");

        List<Product> productList = new ArrayList<>();

        for (Product product : DataBase.getProductList())
            productList.add(new Product(product));

        return productList;
    }
    public List<Product> findByName(String productName) {

        if (!DataBase.existsProductByName(productName))
            throw new NotFoundException("[!] No results were found.");

        List<Product> productList = new ArrayList<>();

        for (Product product : DataBase.getProductList())
            if (product.getName().equalsIgnoreCase(productName))
                productList.add(new Product(product));

        return productList;
    }

    public List<Product> findByType(ProductType productType) {

        if (!DataBase.existsProductByType(productType))
            throw new NotFoundException("[!] No results were found.");

        List<Product> productList = new ArrayList<>();

        for (Product product : DataBase.getProductList())
            if (product.getType() == productType)
                productList.add(new Product(product));

        return productList;
    }

    public List<Product> findByNameAndType(BuyerSearchRequest searchRequest) {

        String name = searchRequest.getProductName();
        ProductType type = searchRequest.getProductType();

        if (!DataBase.existsProductByName(name))
            throw new NotFoundException("[!] No results were found.");

        if (!DataBase.existsProductByType(type))
            throw new NotFoundException("[!] No results were found.");

        List<Product> productList = new ArrayList<>();

        for (Product product : DataBase.getProductList())
            if (product.getName().equalsIgnoreCase(name) && product.getType() == type)
                productList.add(new Product(product));

        return productList;
    }
}
