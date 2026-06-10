package main.java.com.example.marketplace.buyer.search.repository;

import main.java.com.example.marketplace.buyer.search.dto.BuyerSearchRequest;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.seller.model.Product;
import main.java.com.example.marketplace.shared.enums.ProductType;

import java.util.ArrayList;
import java.util.List;

public final class BuyerSearchRepository {

    private final DataBase dataBase;

    public BuyerSearchRepository(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public List<Product> findProducts() {

        if (dataBase.isProductListEmpty())
            throw new NotFoundException("[!] No results were found.");

        List<Product> productList = new ArrayList<>();

        for (Product product : dataBase.getProductList())
            productList.add(new Product(product));

        return productList;
    }
    public List<Product> findByName(String productName) {

        if (!dataBase.existsProductByName(productName))
            throw new NotFoundException("[!] No results were found.");

        List<Product> productList = new ArrayList<>();

        for (Product product : dataBase.getProductList())
            if (product.getName().equalsIgnoreCase(productName))
                productList.add(new Product(product));

        return productList;
    }

    public List<Product> findByType(ProductType productType) {

        if (!dataBase.existsProductByType(productType))
            throw new NotFoundException("[!] No results were found.");

        List<Product> productList = new ArrayList<>();

        for (Product product : dataBase.getProductList())
            if (product.getType() == productType)
                productList.add(new Product(product));

        return productList;
    }

    public List<Product> findByNameAndType(BuyerSearchRequest searchRequest) {

        String name = searchRequest.getProductName();
        ProductType type = searchRequest.getProductType();

        List<Product> productList = new ArrayList<>();

        for (Product product : dataBase.getProductList())
            if (product.getName().equalsIgnoreCase(name) && product.getType() == type)
                productList.add(new Product(product));

        if (productList.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        return productList;
    }

    public Product findById(String productId) {

        Product product = null;

        for (Product p : dataBase.getProductList())
            if (p.getId().equals(productId))
                product = new Product(p);

        if (product == null)
            throw new NotFoundException("Product with ID \"" + productId + "\" not found.");

        return product;
    }
}
