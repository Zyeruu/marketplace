package main.java.com.example.marketplace.seller.repository;

import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.EmptyCatalogException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.seller.dto.CatalogRequest;
import main.java.com.example.marketplace.seller.dto.CatalogResponse;
import main.java.com.example.marketplace.seller.model.Product;
import main.java.com.example.marketplace.seller.model.Seller;
import main.java.com.example.marketplace.shared.enums.ProductType;
import main.java.com.example.marketplace.shared.session.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class CatalogRepository {

    private final DataBase dataBase;
    private final Session session;

    public CatalogRepository(DataBase dataBase, Session session) {
        this.dataBase = dataBase;
        this.session = session;
    }

    public CatalogResponse findByEmail() {

        String email = session.getEmail();
        Seller seller = dataBase.findSellerByEmail(email);

        if (seller == null)
            throw new NotFoundException("[!] User not found.");

        if (seller.getProductList().isEmpty())
            throw new EmptyCatalogException("[!] Your catalog is empty.");

        List<Product> productListCopy = seller.getProductList().stream().map(Product::new).collect(Collectors.toList());

        int totalProducts = seller.getTotalProducts();
        int totalFood = seller.getTotalFood();
        int totalMisc = seller.getTotalMisc();

        return new CatalogResponse(productListCopy, totalProducts, totalFood, totalMisc);
    }

    public CatalogResponse findByEmailAndProductType(ProductType productType) {

        String email = session.getEmail();
        Seller seller = dataBase.findSellerByEmail(email);

        if (seller == null)
            throw new NotFoundException("[!] User not found.");

        if (seller.getProductList().isEmpty())
            throw new EmptyCatalogException("[!] Your catalog is empty.");

        List<Product> productListCopy = seller.getProductList().stream()
                .filter(product -> product.getType() == productType)
                .map(Product::new)
                .collect(Collectors.toList());

        if (productListCopy.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        if (productType == ProductType.FOOD) {
            int totalFood = seller.getTotalFood();
            return new CatalogResponse(productListCopy, 0, totalFood, 0);
        }
        else {
            int totalMisc = seller.getTotalMisc();
            return new CatalogResponse(productListCopy, 0, 0, totalMisc);
        }
    }

    public CatalogResponse findByEmailAndProductName(String productName) {

        String email = session.getEmail();
        Seller seller = dataBase.findSellerByEmail(email);

        if (seller == null)
            throw new NotFoundException("[!] User not found.");

        if (seller.getProductList().isEmpty())
            throw new EmptyCatalogException("[!] Your catalog is empty.");

        List<Product> productListCopy = new ArrayList<>();

        int totalProducts;
        int totalFood = 0;
        int totalMisc = 0;

        for (Product product : seller.getProductList()) {
            if (product.getName().equalsIgnoreCase(productName)) {
                productListCopy.add(new Product(product));

                if (product.getType() == ProductType.FOOD)
                    totalFood++;
                else
                    totalMisc++;
            }
        }

        totalProducts = totalFood + totalMisc;

        if (productListCopy.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        return new CatalogResponse(productListCopy, totalProducts, totalFood, totalMisc);
    }

    public Product findByEmailAndProductId(String productId) {

        String email = session.getEmail();
        Seller seller = dataBase.findSellerByEmail(email);

        if (seller == null)
            throw new NotFoundException("[!] User not found.");

        if (seller.getProductList().isEmpty())
            throw new EmptyCatalogException("[!] Your catalog is empty.");

        Product catalogProduct = seller.getProductList().stream()
                .filter(product -> product.getId().equals(productId))
                .map(Product::new)
                .findFirst()
                .orElse(null);

        if (catalogProduct == null)
            throw new NotFoundException("Product with ID \"" + productId + "\" not found.");

        return catalogProduct;
    }

    public void saveProduct(Product product) {

        String email = session.getEmail();
        Seller seller = dataBase.findSellerByEmail(email);

        if (seller == null)
            throw new NotFoundException("[!] User not found.");

        if (seller.getProductList().stream().anyMatch(p -> p.getName().equalsIgnoreCase(product.getName())))
            throw new AlreadyExistsException("[!] The product you want to add already exists in your catalog.");

        product.setStoreName(session.getStoreName());
        seller.getProductList().add(product);
        dataBase.addToProductList(product);
    }

    public void removeProduct(String productId) {

        String email = session.getEmail();
        Seller seller = dataBase.findSellerByEmail(email);

        if (seller == null)
            throw new NotFoundException("[!] User not found.");

        Product product = dataBase.findProductById(productId);

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + productId + "\" not found.");

        seller.getProductList().remove(product);
        dataBase.deleteFromProductList(product);
    }

    public void updateProductStock(CatalogRequest catalogRequest) {

        String email = session.getEmail();
        Seller seller = dataBase.findSellerByEmail(email);

        if (seller == null)
            throw new NotFoundException("[!] User not found.");

        Product product = dataBase.findProductById(catalogRequest.getId());

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + catalogRequest.getId() + "\" not found.");

        product.setStock(catalogRequest.getQuantity());
    }

    public void updateProductPrice(CatalogRequest catalogRequest) {

        String email = session.getEmail();
        Seller seller = dataBase.findSellerByEmail(email);

        if (seller == null)
            throw new NotFoundException("[!] User not found.");

        Product product = dataBase.findProductById(catalogRequest.getId());

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + catalogRequest.getId() + "\" not found.");

        product.setPrice(catalogRequest.getPrice());
    }
}
