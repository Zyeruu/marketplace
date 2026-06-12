package main.java.com.example.marketplace.user.store.repository;

import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.EmptyCatalogException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.enums.ProductType;
import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.user.store.dto.CatalogRequest;
import main.java.com.example.marketplace.user.store.dto.CatalogResponse;
import main.java.com.example.marketplace.user.store.model.Product;
import main.java.com.example.marketplace.user.model.User;

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
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getCatalogProductList().isEmpty())
            throw new EmptyCatalogException("[!] Your catalog is empty.");

        List<Product> productListCopy = user.getCatalogProductList().stream()
                .map(Product::new)
                .collect(Collectors.toList());

        int totalProducts = user.getCatalogTotalProducts();
        int totalFood = user.getCatalogTotalFood();
        int totalMisc = user.getCatalogTotalMisc();

        return new CatalogResponse(productListCopy, totalProducts, totalFood, totalMisc);
    }

    public CatalogResponse findByEmailAndProductType(ProductType productType) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getCatalogProductList().isEmpty())
            throw new EmptyCatalogException("[!] Your catalog is empty.");

        List<Product> productListCopy = user.getCatalogProductList().stream()
                .filter(product -> product.getType() == productType)
                .map(Product::new)
                .collect(Collectors.toList());

        if (productListCopy.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        if (productType == ProductType.FOOD) {
            int totalFood = user.getCatalogTotalFood();
            return new CatalogResponse(productListCopy, 0, totalFood, 0);
        }
        else {
            int totalMisc = user.getCatalogTotalMisc();
            return new CatalogResponse(productListCopy, 0, 0, totalMisc);
        }
    }

    public CatalogResponse findByEmailAndProductName(String productName) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getCatalogProductList().isEmpty())
            throw new EmptyCatalogException("[!] Your catalog is empty.");

        List<Product> productListCopy = new ArrayList<>();

        int totalProducts;
        int totalFood = 0;
        int totalMisc = 0;

        for (Product product : user.getCatalogProductList()) {
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
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getCatalogProductList().isEmpty())
            throw new EmptyCatalogException("[!] Your catalog is empty.");

        Product catalogProduct = user.getCatalogProductList().stream()
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
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getCatalogProductList().stream().anyMatch(p -> p.getName().equalsIgnoreCase(product.getName())))
            throw new AlreadyExistsException("[!] The product you want to add already exists in your catalog.");

        product.setStoreName(session.getStoreName());
        user.getCatalogProductList().add(product);
        dataBase.addToProductList(product);
        user.updateCatalog();
    }

    public void removeProduct(String productId) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getCatalogProductList().isEmpty())
            throw new EmptyCatalogException("[!] Your catalog is empty.");

        Product product = user.getCatalogProductList().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + productId + "\" not found.");

        user.getCatalogProductList().remove(product);
        dataBase.deleteFromProductList(product);
        user.updateCatalog();
    }

    public void updateProductStock(CatalogRequest catalogRequest) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        Product product = user.getCatalogProductList().stream()
                .filter(p -> p.getId().equals(catalogRequest.getId()))
                .findFirst()
                .orElse(null);

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + catalogRequest.getId() + "\" not found.");

        product.setStock(catalogRequest.getQuantity());
    }

    public void updateProductPrice(CatalogRequest catalogRequest) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        Product product = user.getCatalogProductList().stream()
                .filter(p -> p.getId().equals(catalogRequest.getId()))
                .findFirst()
                .orElse(null);

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + catalogRequest.getId() + "\" not found.");

        product.setPrice(catalogRequest.getPrice());
    }
}
