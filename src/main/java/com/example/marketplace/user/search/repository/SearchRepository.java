package main.java.com.example.marketplace.user.search.repository;

import main.java.com.example.marketplace.exceptions.EmptyCatalogException;
import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.user.search.dto.SearchRequest;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.user.search.dto.SearchResponse;
import main.java.com.example.marketplace.user.store.model.Product;
import main.java.com.example.marketplace.shared.enums.ProductType;
import main.java.com.example.marketplace.user.store.model.Reputation;

import java.util.List;
import java.util.stream.Collectors;

public final class SearchRepository {

    private final DataBase dataBase;
    private final Session session;

    public SearchRepository(DataBase dataBase, Session session) {
        this.dataBase = dataBase;
        this.session = session;
    }

    public List<Product> findProducts() {

        List<Product> productList = dataBase.getProductList().stream()
                .filter(Product::isAvailable)
                .map(Product::new)
                .collect(Collectors.toList());

        if (productList.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        return productList;
    }

    public List<Product> findByName(String productName) {

        List<Product> productList = dataBase.getProductList().stream()
                .filter(Product::isAvailable)
                .filter(product -> product.getName().toLowerCase().contains(productName.toLowerCase()))
                .map(Product::new)
                .collect(Collectors.toList());

        if (productList.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        return productList;
    }

    public List<Product> findByType(ProductType productType) {

        List<Product> productList = dataBase.getProductList().stream()
                .filter(Product::isAvailable)
                .filter(product -> product.getType() == productType)
                .map(Product::new)
                .collect(Collectors.toList());

        if (productList.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        return productList;
    }

    public List<Product> findByNameAndType(SearchRequest searchRequest) {

        String name = searchRequest.productName();
        ProductType type = searchRequest.productType();

        List<Product> productList = dataBase.getProductList().stream()
                .filter(Product::isAvailable)
                .filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()) && product.getType() == type)
                .map(Product::new)
                .collect(Collectors.toList());

        if (productList.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        return productList;
    }

    public Product findById(String productId) {

        Product product = dataBase.getProductList().stream()
                .filter(Product::isAvailable)
                .filter(p -> p.getId().equals(productId))
                .map(Product::new)
                .findFirst().orElse(null);

        if (product == null) {
            session.updateLastProductViewed(null);
            throw new NotFoundException("Product with ID \"" + productId + "\" not found.");
        }

        if (product.getReviewList().isEmpty())
            session.updateLastProductViewed(null);
        else
            session.updateLastProductViewed(productId);

        return product;
    }

    public List<String> searchForSellers() {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        List<String> sellers = dataBase.getUserList().stream()
                .filter(seller -> seller.getStore() != null)
                .map(User::getStoreName)
                .collect(Collectors.toList());

        if (sellers.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        return formatSellerList(user, sellers);
    }

    public List<String> searchForSellersWithCatalog() {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        List<String> sellersWithCatalog = dataBase.getUserList().stream()
                .filter(seller -> seller.getStore() != null)
                .filter(seller -> seller.getCatalogAvailableTotalProducts() > 0)
                .map(User::getStoreName)
                .collect(Collectors.toList());

        if (sellersWithCatalog.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        return formatSellerList(user, sellersWithCatalog);
    }

    public SearchResponse findCatalogByStoreName(String storeName) {

        User seller = dataBase.findSellerByStoreNameIgnoreCase(storeName);

        if (seller == null) {
            session.updateLastStoreViewed(null);
            throw new NotFoundException("[!] Seller named \"" + storeName + "\" not found.");
        }

        if (seller.getCatalogAvailableTotalProducts() == 0) {
            session.updateLastStoreViewed(null);
            throw new EmptyCatalogException(seller.getStoreName() + " has no products listed.");
        }

        List<Product> sellerCatalog = seller.getCatalogProductList().stream()
                .filter(Product::isAvailable)
                .map(Product::new)
                .collect(Collectors.toList());

        seller.updateCatalog();

        int totalProducts = seller.getCatalogAvailableTotalProducts();
        int totalFoods = seller.getCatalogAvailableTotalFood();
        int totalMisc = seller.getCatalogAvailableTotalMisc();

        session.updateLastStoreViewed(seller.getStoreName());

        return new SearchResponse(sellerCatalog, totalProducts, totalFoods, totalMisc);
    }

    public Product findProductReviewByProductId(String productId) {

        Product product = dataBase.findAvailableProductById(productId);

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + productId + "\" not found.");

        if (product.getReviewList().isEmpty())
            throw new NotFoundException("[!] \"" + product.getName() + "\" has no reviews yet.");

        return new Product(product);
    }

    public Reputation findProductReviewByLastProductViewed() {

        Product product = dataBase.findAvailableProductById(session.getLastStoreViewed());

        if (product == null)
            throw new NotFoundException("[!] Product not found.");

        if (product.getReviewList().isEmpty())
            throw new NotFoundException("[!] \"" + product.getName() + "\" has no reviews yet.");

        return new Reputation(product.getReputation());
    }

    public Reputation findProductReviewByProductIdAndStoreName(String productId) {

        User seller = dataBase.findSellerByStoreName(session.getLastStoreViewed());

        Product product = seller.getCatalogProductList().stream()
                .filter(Product::isAvailable)
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + productId + "\" not found in " + session.getLastStoreViewed() + "'s catalog.");

        if (product.getReviewList().isEmpty())
            throw new NotFoundException("[!] \"" + product.getName() + "\" has no reviews yet.");

        return new Reputation(product.getReputation());
    }

    public List<String> formatSellerList(User user, List<String> sellerList) {

        for (int i = 0; i < sellerList.size(); i++) {
            User seller = dataBase.findSellerByStoreName(sellerList.get(i));

            if (user.getStore() != null) {
                if (sellerList.get(i).equals(user.getStoreName())) {

                    if (user.getReputationRating() > 0) {
                        sellerList.set(i, sellerList.get(i) + " [" + user.getReputationRating() + "/5] (You)");
                    }
                    else
                        sellerList.set(i, sellerList.get(i) + " [No Reputation] (You)");

                    continue;
                }
            }

            if (seller.getReputationRating() > 0)
                sellerList.set(i, sellerList.get(i) + " [" + seller.getReputationRating() + "/5]");
            else
                sellerList.set(i, sellerList.get(i) + " [No Reputation]");
        }

        return sellerList;
    }
}
