package main.java.com.example.marketplace.user.store.repository;

import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.EmptyCatalogException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.enums.ProductType;
import main.java.com.example.marketplace.user.store.model.Catalog;
import main.java.com.example.marketplace.user.store.model.Product;
import main.java.com.example.marketplace.user.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class CatalogRepository {

    private final DataBase dataBase;

    public CatalogRepository(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public Catalog findByEmail(String email) {

        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        return user.getCatalog();
    }

    public List<Product> findByEmailAndProductType(String email, ProductType productType) {

        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getCatalogProductList().isEmpty())
            throw new EmptyCatalogException("[!] Your catalog is empty.");

        return user.getCatalogProductList().stream()
                .filter(product -> product.getType() == productType)
                .map(Product::new)
                .collect(Collectors.toList());
    }

    public List<Product> findByEmailAndProductName(String email, String productName) {

        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getCatalogProductList().isEmpty())
            throw new EmptyCatalogException("[!] Your catalog is empty.");

        List<Product> productListCopy = new ArrayList<>();

        for (Product product : user.getCatalogProductList())
            if (product.getName().toLowerCase().contains(productName.toLowerCase()))
                productListCopy.add(new Product(product));

        return productListCopy;
    }

    public Product findByEmailAndId(String email, String productId) {

        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getCatalogProductList().isEmpty())
            throw new EmptyCatalogException("[!] Your catalog is empty.");

        return user.getCatalogProductList().stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public void saveProduct(Catalog catalog, Product product) {

        catalog.getProductList().add(product);
        dataBase.addToProductList(product);
        catalog.updateCatalog();
    }

    public void deleteProduct(Catalog catalog, Product product) {

        catalog.getProductList().remove(product);
        dataBase.deleteFromProductList(product);
        catalog.updateCatalog();
    }

    public void updateProductName(Catalog catalog, Product product, String name) {

        product.setName(name);
        catalog.updateCatalog();
    }

    public void updateProductType(Catalog catalog, Product product, ProductType type) {

        product.setType(type);
        catalog.updateCatalog();
    }

    public void updateProductBrand(Catalog catalog, Product product, String brand) {

        product.setBrand(brand);
        catalog.updateCatalog();
    }

    public void updateProductPrice(Catalog catalog, Product product, float price) {

        product.setUnitPrice(price);
        catalog.updateCatalog();
    }

    public void updateProductWeight(Catalog catalog, Product product, float weight) {

        product.setWeight(weight);
        catalog.updateCatalog();
    }

    public void updateProductStock(Catalog catalog, Product product, int stock) {

        product.setStock(stock);
        catalog.updateCatalog();
    }

    public void updateProductWarranty(Catalog catalog, Product product, int warranty) {

        product.setWarranty(warranty);
        catalog.updateCatalog();
    }

    public void updateProductAvailability(Catalog catalog, Product product, boolean availability) {
        
        product.setAvailable(availability);
        catalog.updateCatalog();
    }
}
