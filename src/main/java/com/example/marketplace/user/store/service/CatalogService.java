package main.java.com.example.marketplace.user.store.service;

import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.EmptyCatalogException;
import main.java.com.example.marketplace.exceptions.InsufficientStockException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.enums.ProductType;
import main.java.com.example.marketplace.shared.utils.Normalizer;
import main.java.com.example.marketplace.shared.utils.Validator;
import main.java.com.example.marketplace.user.store.dto.CatalogResponse;
import main.java.com.example.marketplace.user.store.dto.UpdateProductRequest;
import main.java.com.example.marketplace.user.store.model.Catalog;
import main.java.com.example.marketplace.user.store.model.Product;
import main.java.com.example.marketplace.user.store.model.Reputation;
import main.java.com.example.marketplace.user.store.repository.CatalogRepository;

import java.util.List;
import java.util.stream.Collectors;

public final class CatalogService {

    private final CatalogRepository repository;

    public CatalogService(CatalogRepository repository) {
        this.repository = repository;
    }

    public CatalogResponse findByEmail(String email) {

        Catalog catalog = repository.findByEmail(email);

        if (catalog.getProductList().isEmpty())
            throw new EmptyCatalogException("[!] Your catalog is empty.");

        List<Product> productList = catalog.getProductList().stream()
                .map(Product::new).collect(Collectors.toList());

        return new CatalogResponse(productList, catalog.getTotalProducts(), catalog.getTotalFood(),
                catalog.getTotalMisc());
    }

    public CatalogResponse findAvailableCatalogByEmail(String email) {

        Catalog catalog = repository.findByEmail(email);

        if (catalog.getProductList().isEmpty())
            throw new EmptyCatalogException("[!] Your catalog is empty.");

        List<Product> availableProductList = catalog.getProductList().stream()
                .filter(Product::isAvailable).map(Product::new).collect(Collectors.toList());

        Catalog newCatalog = new Catalog(availableProductList);

        return new CatalogResponse(availableProductList, newCatalog.getAvailableTotalProducts(),
                newCatalog.getAvailableTotalFood(), newCatalog.getAvailableTotalMisc());
    }

    public CatalogResponse findUnavailableCatalogByEmail(String email) {

        Catalog catalog = repository.findByEmail(email);

        if (catalog.getProductList().isEmpty())
            throw new EmptyCatalogException("[!] Your catalog is empty.");

        List<Product> unavailableProductList = catalog.getProductList().stream()
                .filter(product -> !product.isAvailable()).map(Product::new).collect(Collectors.toList());

        Catalog newCatalog = new Catalog(unavailableProductList);

        return new CatalogResponse(unavailableProductList, newCatalog.getUnavailableTotalProducts(),
                newCatalog.getUnavailableTotalFood(), newCatalog.getUnavailableTotalMisc());
    }

    public CatalogResponse findByEmailAndProductType(String email, ProductType type) {

        List<Product> productList = repository.findByEmailAndProductType(email, type);

        if (productList.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        if (type == ProductType.FOOD)
            return new CatalogResponse(productList, null, productList.size(), null);
        else
            return new CatalogResponse(productList, null, null, productList.size());
    }

    public CatalogResponse findByEmailAndProductName(String email, String name) {

        List<Product> productList = repository.findByEmailAndProductName(email, name);

        if (productList.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        int totalFood = 0;
        int totalMisc = 0;

        for (Product product : productList) {
            if (product.getType() == ProductType.FOOD)
                totalFood++;
            else
                totalMisc++;
        }

        return new CatalogResponse(productList, totalFood + totalMisc, totalFood, totalMisc);
    }

    public Product findByEmailAndId(String email, String productId) {

        Product product = repository.findByEmailAndId(email, productId);

        if (product == null)
            throw new NotFoundException("Product with ID \"" + productId + "\" not found.");

        return product;
    }

    public Product findAndCopyByEmailAndId(String email, String productId) {

        Product product = repository.findByEmailAndId(email, productId);

        if (product == null)
            throw new NotFoundException("Product with ID \"" + productId + "\" not found.");

        return new Product(product);
    }

    public void verifyCatalogAndSaveProduct(String email, Product product) {

        Catalog catalog = repository.findByEmail(email);

        if (catalog.getProductList().stream()
                .anyMatch(p -> p.getName().equalsIgnoreCase(product.getName())))
            throw new AlreadyExistsException("[!] The product you want to add already exists in your catalog.");

        repository.saveProduct(catalog, product);
    }

    public void deleteProduct(String email, Product product) {

        Catalog catalog = repository.findByEmail(email);
        repository.deleteProduct(catalog, product);
    }

    public void verifyAndUpdateProductName(String email, UpdateProductRequest request) {

        Product product = repository.findByEmailAndId(email, request.id());

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + request.id() + "\" not found.");

        if (product.getName().equals(request.name()))
            throw new IllegalArgumentException("[!] The new product name must be different from current name.");

        Catalog catalog = repository.findByEmail(email);
        repository.updateProductName(catalog, product, request.name());
    }

    public void verifyAndUpdateProductType(String email, UpdateProductRequest request) {

        Product product = repository.findByEmailAndId(email, request.id());

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + request.id() + "\" not found.");

        if (product.getType() == request.type())
            throw new IllegalArgumentException("[!] The new product type must be different from current type.");

        Catalog catalog = repository.findByEmail(email);
        repository.updateProductType(catalog, product, request.type());
    }

    public void verifyAndUpdateProductBrand(String email, UpdateProductRequest request) {

        Product product = repository.findByEmailAndId(email, request.id());

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + request.id() + "\" not found.");

        if (product.getBrand().equals(request.brand()))
            throw new IllegalArgumentException("[!] The new product brand name must be different from current brand.");

        Catalog catalog = repository.findByEmail(email);
        repository.updateProductBrand(catalog, product, request.brand());
    }

    public void verifyAndUpdateProductPrice(String email, UpdateProductRequest request) {

        Product product = repository.findByEmailAndId(email, request.id());

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + request.id() + "\" not found.");

        if (product.getUnitPrice() == request.unitPrice())
            throw new IllegalArgumentException("[!] The new product price must be different from current price.");

        Catalog catalog = repository.findByEmail(email);
        repository.updateProductPrice(catalog, product, request.unitPrice());
    }

    public void verifyAndUpdateProductWeight(String email, UpdateProductRequest request) {

        Product product = repository.findByEmailAndId(email, request.id());

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + request.id() + "\" not found.");

        if (product.getWeight() == request.weight())
            throw new IllegalArgumentException("[!] The new product weight must be different from current weight.");

        Catalog catalog = repository.findByEmail(email);
        repository.updateProductWeight(catalog, product, request.weight());
    }

    public void verifyAndUpdateProductStock(String email, UpdateProductRequest request) {

        Product product = repository.findByEmailAndId(email, request.id());

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + request.id() + "\" not found.");

        if (product.getStock() == request.stock())
            throw new IllegalArgumentException("[!] The new product stock must be different from current stock.");

        Catalog catalog = repository.findByEmail(email);
        repository.updateProductStock(catalog, product, request.stock());
    }

    public void verifyAndUpdateProductWarranty(String email, UpdateProductRequest request) {

        Product product = repository.findByEmailAndId(email, request.id());

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + request.id() + "\" not found.");

        if (product.getWarranty().equals(request.warranty()))
            throw new IllegalArgumentException("[!] The new product warranty must be different from current warranty.");

        Catalog catalog = repository.findByEmail(email);
        repository.updateProductWarranty(catalog, product, request.warranty());
    }

    public void verifyAndUpdateProductAvailability(String email, UpdateProductRequest request) {

        Product product = repository.findByEmailAndId(email, request.id());

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + request.id() + "\" not found.");

        if (product.isAvailable() == request.available()) {
            if (product.isAvailable()) {
                throw new IllegalArgumentException("[!] The product is already available.");
            }
            else
                throw new IllegalArgumentException("[!] The product is already unavailable.");
        }
        else {
            if (request.available() && product.getStock() == 0)
                throw new InsufficientStockException("[!] The product you want to make available is out of stock. Update the stock first.");
        }

        Catalog catalog = repository.findByEmail(email);
        repository.updateProductAvailability(catalog, product, request.available());
    }

    public Reputation findProductReviewByEmailAndProductId(String email, String productId) {

        Product product = repository.findByEmailAndId(email, productId);

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + productId + "\" not found.");

        if (product.getReviewList().isEmpty())
            throw new NotFoundException("[!] \"" + product.getName() + "\" has no reviews yet.");

        return new Reputation(product.getReputation());
    }

    public void normalizeAndValidateProduct(Product product) {

        product.setName(normalizeAndValidateProductName(product.getName()));

        if (product.getType() == ProductType.MISCELLANEOUS)
            product.setBrand(normalizeAndValidateProductBrand(product.getBrand()));
    }

    public String normalizeAndValidateProductName(String name) {

        name = Normalizer.normalizeName(name);
        Validator.isValidProductName(name);
        return name;
    }

    public String normalizeAndValidateProductBrand(String brand) {

        brand = Normalizer.normalizeBrandName(brand);
        Validator.isValidBrandName(brand);
        return brand;
    }
}
