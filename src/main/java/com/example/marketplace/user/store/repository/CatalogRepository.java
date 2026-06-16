package main.java.com.example.marketplace.user.store.repository;

import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.EmptyCatalogException;
import main.java.com.example.marketplace.exceptions.InsufficientStockException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.enums.ProductType;
import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.user.store.dto.UpdateProductRequest;
import main.java.com.example.marketplace.user.store.dto.CatalogResponse;
import main.java.com.example.marketplace.user.store.model.Product;
import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.user.store.model.Reputation;

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

        List<Product> productList = user.getCatalogProductList().stream()
                .map(Product::new)
                .collect(Collectors.toList());

        user.updateCatalog();

        int totalProducts = user.getCatalogTotalProducts();
        int totalFood = user.getCatalogTotalFood();
        int totalMisc = user.getCatalogTotalMisc();

        return new CatalogResponse(productList, totalProducts, totalFood, totalMisc);
    }

    public CatalogResponse findAvailableCatalog() {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getCatalogProductList().isEmpty())
            throw new EmptyCatalogException("[!] Your catalog is empty.");

        List<Product> productList = user.getCatalogProductList().stream()
                .filter(Product::isAvailable)
                .map(Product::new)
                .collect(Collectors.toList());

        if (productList.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        user.updateCatalog();;

        int totalProducts = user.getCatalogAvailableTotalProducts();
        int totalFood = user.getCatalogAvailableTotalFood();
        int totalMisc = user.getCatalogAvailableTotalMisc();

        return new CatalogResponse(productList, totalProducts, totalFood, totalMisc);
    }

    public CatalogResponse findUnavailableCatalog() {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getCatalogProductList().isEmpty())
            throw new EmptyCatalogException("[!] Your catalog is empty.");

        List<Product> productList = user.getCatalogProductList().stream()
                .filter(product -> !product.isAvailable())
                .map(Product::new)
                .collect(Collectors.toList());

        if (productList.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        user.updateCatalog();

        int totalProducts = user.getCatalogUnavailableTotalProducts();
        int totalFood = user.getCatalogUnavailableTotalFood();
        int totalMisc = user.getCatalogUnavailableTotalMisc();

        return new CatalogResponse(productList, totalProducts, totalFood, totalMisc);
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

        user.updateCatalog();

        if (productType == ProductType.FOOD) {
            int totalFood = user.getCatalogTotalFood();
            return new CatalogResponse(productListCopy, null, totalFood, null);
        }
        else {
            int totalMisc = user.getCatalogTotalMisc();
            return new CatalogResponse(productListCopy, null, null, totalMisc);
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

        int totalFood = 0;
        int totalMisc = 0;

        for (Product product : user.getCatalogProductList()) {
            if (product.getName().toLowerCase().contains(productName.toLowerCase())) {
                productListCopy.add(new Product(product));

                if (product.getType() == ProductType.FOOD)
                    totalFood++;
                else
                    totalMisc++;
            }
        }

        if (productListCopy.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        int totalProducts = totalFood + totalMisc;

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

        if (user.getCatalogProductList().stream()
                .anyMatch(p -> p.getName().equalsIgnoreCase(product.getName())))
            throw new AlreadyExistsException("[!] The product you want to add already exists in your catalog.");

        product.setStoreName(session.getStoreName());
        user.getCatalogProductList().add(product);
        dataBase.addToProductList(product);
        user.updateCatalog();
    }

    public void removeProduct(Product product) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        user.getCatalogProductList().remove(product);
        dataBase.deleteFromProductList(product);
        user.updateCatalog();
    }

    public void updateProductName(UpdateProductRequest updateName) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] user not found.");

        if (user.getCatalogProductList().isEmpty())
            throw new EmptyCatalogException("[!] Your catalog is empty.");

        Product product = user.getCatalogProductList().stream()
                .filter(p -> p.getId().equals(updateName.getId()))
                .findFirst()
                .orElse(null);

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + updateName.getId() + "\" not found.");

        if (product.getName().equals(updateName.getName()))
            throw new IllegalArgumentException("[!] The new product name must be different from current name.");

        product.setName(updateName.getName());
        user.updateCatalog();
    }

    public void updateProductType(UpdateProductRequest updateType) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] user not found.");

        Product product = user.getCatalogProductList().stream()
                .filter(p -> p.getId().equals(updateType.getId()))
                .findFirst()
                .orElse(null);

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + updateType.getId() + "\" not found.");

        if (product.getType() == updateType.getType())
            throw new IllegalArgumentException("[!] The new product type must be different from the current type.");

        product.setType(updateType.getType());
        user.updateCatalog();
    }

    public void updateProductBrand(UpdateProductRequest updateBrand) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] user not found.");

        Product product = user.getCatalogProductList().stream()
                .filter(p -> p.getId().equals(updateBrand.getId()))
                .findFirst()
                .orElse(null);

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + updateBrand.getId() + "\" not found.");

        if (product.getType() == ProductType.FOOD)
            throw new IllegalArgumentException("[!] The product with ID \"" + updateBrand.getId() +
                    "\" is a Food item, so it does not have a brand.");

        if (product.getBrand().equals(updateBrand.getBrand()))
            throw new IllegalArgumentException("[!] The new product brand must be different from the current brand.");

        product.setBrand(updateBrand.getBrand());
        user.updateCatalog();
    }

    public void updateProductPrice(UpdateProductRequest updatePrice) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        Product product = user.getCatalogProductList().stream()
                .filter(p -> p.getId().equals(updatePrice.getId()))
                .findFirst()
                .orElse(null);

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + updatePrice.getId() + "\" not found.");

        if (product.getUnitPrice() == updatePrice.getUnitPrice())
            throw new IllegalArgumentException("[!] The new stock mus be different from the current stock.");

        product.setUnitPrice(updatePrice.getUnitPrice());
        user.updateCatalog();
    }

    public void updateProductWeight(UpdateProductRequest updateWeight) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        Product product = user.getCatalogProductList().stream()
                .filter(p -> p.getId().equals(updateWeight.getId()))
                .findFirst()
                .orElse(null);

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + updateWeight.getId() + "\" not found.");

        if (product.getWeight() == updateWeight.getWeight())
            throw new IllegalArgumentException("[!] The new weight must be different from the current weight.");

        product.setWeight(updateWeight.getWeight());
        user.updateCatalog();
    }

    public void updateProductStock(UpdateProductRequest updateStock) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        Product product = user.getCatalogProductList().stream()
                .filter(p -> p.getId().equals(updateStock.getId()))
                .findFirst()
                .orElse(null);

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + updateStock.getId() + "\" not found.");

        if (product.getStock() == updateStock.getStock())
            throw new IllegalArgumentException("[!] The new stock must be different from the current stock.");

        product.setStock(updateStock.getStock());
        user.updateCatalog();
    }

    public void updateProductWarranty(UpdateProductRequest updateWarranty) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        Product product = user.getCatalogProductList().stream()
                .filter(p -> p.getId().equals(updateWarranty.getId()))
                .findFirst()
                .orElse(null);

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + updateWarranty.getId() + "\" not found.");

        if (product.getType() == ProductType.FOOD)
            throw new IllegalArgumentException("[!] The product with ID \"" + updateWarranty.getId() +
                    "\" is a Food item, so it does not have warranty coverage.");

        if (product.getWarranty().equals(updateWarranty.getWarranty()))
            throw new IllegalArgumentException("[!] The product warranty must be different from the current warranty.");

        product.setWarranty(updateWarranty.getWarranty());
        user.updateCatalog();
    }

    public void updateAvailability(UpdateProductRequest updateAvailability) {

        String email = session.getEmail();;
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        Product product = user.getCatalogProductList().stream()
                .filter(p -> p.getId().equals(updateAvailability.getId()))
                .findFirst()
                .orElse(null);

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + updateAvailability.getId() + "\" not found.");

        if (product.isAvailable() == updateAvailability.getAvailable()) {
            if (product.isAvailable()) {
                throw new IllegalArgumentException("[!] The product is already available.");
            }
            else
                throw new IllegalArgumentException("[!] The product is already unavailable.");
        }
        else {
            if (updateAvailability.getAvailable() && product.getStock() == 0)
                throw new InsufficientStockException("[!] The product you want to make available is out of stock. Update the stock first.");
        }
        
        product.setAvailable(updateAvailability.getAvailable());
        user.updateCatalog();
    }

    public Reputation findProductReviewByProductId(String productId) {

        String email = session.getEmail();;
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getCatalogProductList().isEmpty())
            throw new EmptyCatalogException("[!] Your catalog is empty");

        Product product = user.getCatalogProductList().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + productId + "\" not found.");

        if (product.getReviewList().isEmpty())
            throw new NotFoundException("[!] \"" + product.getName() + "\" has no reviews yet.");

        return new Reputation(product.getReputation());
    }

    public Product findProductById(String productId) {

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

        return product;
    }
}
