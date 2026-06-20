package main.java.com.example.marketplace.user.search.repository;

import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.user.store.model.Catalog;
import main.java.com.example.marketplace.user.store.model.Product;
import main.java.com.example.marketplace.shared.enums.ProductType;

import java.util.List;
import java.util.stream.Collectors;

public final class SearchRepository {

    private final DataBase dataBase;

    public SearchRepository(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public List<Product> findProductList() {
        return dataBase.getProductList();
    }

    public List<Product> findByName(String productName) {

        return dataBase.getProductList().stream()
                .filter(Product::isAvailable)
                .filter(product -> product.getName().toLowerCase().contains(productName.toLowerCase()))
                .map(Product::new)
                .collect(Collectors.toList());
    }

    public List<Product> findByType(ProductType productType) {

        return dataBase.getProductList().stream()
                .filter(Product::isAvailable)
                .filter(product -> product.getType() == productType)
                .map(Product::new)
                .collect(Collectors.toList());
    }

    public List<Product> findByNameAndType(String name, ProductType type) {

        return dataBase.getProductList().stream()
                .filter(Product::isAvailable)
                .filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()) && product.getType() == type)
                .map(Product::new)
                .collect(Collectors.toList());
    }

    public Product findProductById(String productId) {

        return dataBase.getProductList().stream()
                .filter(Product::isAvailable)
                .filter(p -> p.getId().equals(productId))
                .map(Product::new)
                .findFirst().orElse(null);
    }

    public List<String> searchForSellers() {

        return dataBase.getUserList().stream()
                .filter(seller -> seller.getStore() != null)
                .map(User::getStoreName)
                .collect(Collectors.toList());
    }

    public List<String> searchForSellersWithCatalog() {

        return dataBase.getUserList().stream()
                .filter(seller -> seller.getStore() != null)
                .filter(seller -> seller.getCatalogAvailableTotalProducts() > 0)
                .map(User::getStoreName)
                .collect(Collectors.toList());
    }

    public Catalog findCatalogByStoreName2(String storeName) {

        User seller = dataBase.findSellerByStoreNameIgnoreCase(storeName);

        if (seller == null)
            throw new NotFoundException("[!] Seller named \"" + storeName + "\" not found.");

        return seller.getCatalog();
    }

    public Product findProductReviewByProductId(String productId) {
        return dataBase.findAvailableProductById(productId);
    }

    public Product findProductReviewByProductIdAndStoreName(String productId, String storeName) {

        User seller = dataBase.findSellerByStoreName(storeName);

        return seller.getCatalogProductList().stream()
                .filter(Product::isAvailable)
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElse(null);
    }
}
