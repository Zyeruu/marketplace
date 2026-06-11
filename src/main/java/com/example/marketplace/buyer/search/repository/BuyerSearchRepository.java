package main.java.com.example.marketplace.buyer.search.repository;

import main.java.com.example.marketplace.buyer.search.dto.BuyerSearchRequest;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.seller.model.Product;
import main.java.com.example.marketplace.shared.enums.ProductType;

import java.util.List;
import java.util.stream.Collectors;

public final class BuyerSearchRepository {

    private final DataBase dataBase;

    public BuyerSearchRepository(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public List<Product> findProducts() {

        List<Product> productList = dataBase.getProductList().stream()
                .map(Product::new)
                .collect(Collectors.toList());

        if (productList.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        return productList;
    }

    public List<Product> findByName(String productName) {

        List<Product> productList = dataBase.getProductList().stream()
                .filter(product -> product.getName().equalsIgnoreCase(productName))
                .map(Product::new)
                .collect(Collectors.toList());

        if (productList.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        return productList;
    }

    public List<Product> findByType(ProductType productType) {

        List<Product> productList = dataBase.getProductList().stream()
                .filter(product -> product.getType() == productType)
                .map(Product::new)
                .collect(Collectors.toList());

        if (productList.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        return productList;
    }

    public List<Product> findByNameAndType(BuyerSearchRequest searchRequest) {

        String name = searchRequest.getProductName();
        ProductType type = searchRequest.getProductType();

        List<Product> productList = dataBase.getProductList().stream()
                .filter(product -> product.getName().equalsIgnoreCase(name) && product.getType() == type)
                .map(Product::new)
                .collect(Collectors.toList());

        if (productList.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        return productList;
    }

    public Product findById(String productId) {

        Product product = dataBase.getProductList().stream()
                .filter(p -> p.getId().equals(productId))
                .map(Product::new)
                .findFirst().orElse(null);

        if (product == null)
            throw new NotFoundException("Product with ID \"" + productId + "\" not found.");

        return product;
    }
}
