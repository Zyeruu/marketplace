package main.java.com.example.marketplace.user.search.service;

import main.java.com.example.marketplace.exceptions.EmptyCatalogException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.enums.ProductType;
import main.java.com.example.marketplace.user.account.service.AccountService;
import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.user.search.repository.SearchRepository;
import main.java.com.example.marketplace.user.store.model.Catalog;
import main.java.com.example.marketplace.user.store.model.Product;
import main.java.com.example.marketplace.user.store.model.Reputation;

import java.util.List;
import java.util.stream.Collectors;

public final class SearchService {

    private final SearchRepository repository;
    private final AccountService accountService;

    public SearchService(SearchRepository repository, AccountService accountService) {
        this.repository = repository;
        this.accountService = accountService;
    }

    public List<Product> findAvailableProductList() {

        List<Product> productList = repository.findProductList();

        List<Product> availableProductList = productList.stream()
                .filter(Product::isAvailable)
                .map(Product::new)
                .collect(Collectors.toList());

        if (availableProductList.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        return availableProductList;
    }

    public List<Product> findAvailableProductListByName(String name) {

        List<Product> productList = repository.findByName(name);

        if (productList.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        return productList;
    }

    public List<Product> findAvailableProductListByType(ProductType productType) {

        List<Product> productList = repository.findByType(productType);

        if (productList.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        return productList;
    }

    public List<Product> findAvailableProductListByNameAndType(String name, ProductType type) {

        List<Product> productList = repository.findByNameAndType(name, type);

        if (productList.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        return productList;
    }

    public List<String> searchForSellers(String email) {

        List<String> sellers = repository.searchForSellers();

        if (sellers.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        User user = accountService.findUserByEmail(email);

        return formatSellerList(user, sellers);
    }

    public List<String> searchForSellersWithCatalog(String email) {

        List<String> sellersWithCatalog = repository.searchForSellersWithCatalog();

        if (sellersWithCatalog.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        User user = accountService.findUserByEmail(email);

        return formatSellerList(user, sellersWithCatalog);
    }

    public Catalog findAndVerifyStoreCatalogByStoreName(String storeName) {

        Catalog catalog = repository.findCatalogByStoreName2(storeName);

        if (catalog.getAvailableTotalProducts() == 0)
            throw new EmptyCatalogException(storeName + " has no products listed.");

        List<Product> availableProductList = catalog.getProductList().stream()
                .filter(Product::isAvailable)
                .map(Product::new).collect(Collectors.toList());

        return new Catalog(availableProductList);
    }

    public Reputation findProductReviewByProductId(String productId) {

        Product product = repository.findProductReviewByProductId(productId);

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + productId + "\" not found.");

        if (product.getReviewList().isEmpty())
            throw new NotFoundException("[!] \"" + product.getName() + "\" has no reviews yet.");

        return new Reputation(product.getReputation());
    }

    public Reputation findProductReviewByProductIdAndStoreName(String productId, String storeName) {

        Product product = repository.findProductReviewByProductIdAndStoreName(productId, storeName);

        if (product == null)
            throw new NotFoundException("[!] Product with ID \"" + productId + "\" not found in " + storeName + "'s catalog.");

        if (product.getReviewList().isEmpty())
            throw new NotFoundException("[!] \"" + product.getName() + "\" has no reviews yet.");

        return new Reputation(product.getReputation());
    }

    public Product findProductById(String productId) {

        Product product = repository.findProductById(productId);

        if (product == null)
            throw new NotFoundException("Product with ID \"" + productId + "\" not found.");

        return product;
    }

    public List<String> formatSellerList(User user, List<String> sellerList) {

        for (int i = 0; i < sellerList.size(); i++) {
            User seller = accountService.findSellerByStoreName(sellerList.get(i));

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
