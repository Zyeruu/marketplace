package main.java.com.example.marketplace.user.repository;

import main.java.com.example.marketplace.checkout.model.OrderedProduct;
import main.java.com.example.marketplace.shared.model.Review;
import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.user.store.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public final class OrdersMenuRepository {

    private final DataBase dataBase;

    public OrdersMenuRepository(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public List<OrderedProduct> findOrderedProductListByEmail(String email) {

        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        return user.getOrdersMenuOrderedProductList();
    }

    public List<TaxReceipt> findTaxReceiptListByEmail(String email) {

        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        return user.getOrdersMenuTaxReceiptList().stream()
                .map(TaxReceipt::new)
                .collect(Collectors.toList());
    }

    public TaxReceipt findTaxReceiptByEmailAndOrderId(String email, String orderId) {

        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getOrdersMenuTaxReceiptList().isEmpty())
            throw new NotFoundException("[!] No purchase history.");

        return user.getOrdersMenuTaxReceiptList().stream()
                .filter(product -> product.getOrderId().equals(orderId)).findFirst().orElse(null);
    }

    public List<OrderedProduct> findProductsReviewByEmail(String email) {

        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        return user.getOrdersMenuOrderedProductList().stream()
                .filter(product -> product.getReview() != null)
                .map(OrderedProduct::new)
                .collect(Collectors.toList());
    }

    public List<OrderedProduct> findUnratedProductsByEmail(String email) {

        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getOrdersMenuOrderedProductList().isEmpty())
            throw new NotFoundException("[!] You haven't made any purchases yet.");

        return user.getOrdersMenuOrderedProductList().stream()
                .filter(product -> product.getReview() == null)
                .map(OrderedProduct::new)
                .collect(Collectors.toList());
    }

    public void saveReview(String email, Review review, String productId) {

        User user = dataBase.findUserByEmail(email);
        OrderedProduct orderedProduct = user.getOrdersMenuOrderedProductList().stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElse(null);
        User seller = dataBase.findSellerByCnpj(orderedProduct.getSellerCnpj());

        user.getOrdersMenuReviewList().add(review);
        orderedProduct.setReview(review);

        seller.getStoreReviewList().add(review);
        seller.updateReputation();

        Product catalogProduct = dataBase.findProductById(productId);

        if (catalogProduct != null) {

            catalogProduct.getReviewList().add(review);
            catalogProduct.updateReputation();
        }
    }

    public void deleteReview(String email, OrderedProduct orderedProduct) {

        User user = dataBase.findUserByEmail(email);

        Product catalogProduct = dataBase.findProductById(orderedProduct.getId());
        User seller = dataBase.findSellerByCnpj(orderedProduct.getSellerCnpj());

        if (catalogProduct != null) {
            catalogProduct.getReviewList().remove(orderedProduct.getReview());
            catalogProduct.updateReputation();
        }

        if (seller != null) {
            seller.getStoreReviewList().remove(orderedProduct.getReview());
            seller.updateReputation();
        }

        user.getOrdersMenuReviewList().remove(orderedProduct.getReview());
        orderedProduct.setReview(null);
    }

    public void updateReviewMessage(OrderedProduct orderedProduct, String message) {
        orderedProduct.setReviewMessage(message);
    }

    public void updateReviewRating(OrderedProduct orderedProduct, int rating) {
        orderedProduct.setReviewRating(rating);
    }

    public void updateSellerAndProductReputation(String cnpj, String productId) {

        User seller = dataBase.findSellerByCnpj(cnpj);
        Product product = dataBase.findProductById(productId);

        if (seller != null)
            seller.updateReputation();

        if (product != null)
            product.updateReputation();
    }

    public OrderedProduct findReviewByEmailAndId(String email, String reviewId) {

        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getOrdersMenuReviewList().isEmpty())
            throw new NotFoundException("[!] You don't have any published reviews yet.");

        OrderedProduct orderedProduct = user.getOrdersMenuOrderedProductList().stream()
                .filter(product -> product.getReview() != null)
                .filter(product -> product.getReview().getId().equals(reviewId))
                .findFirst()
                .orElse(null);

        if (orderedProduct == null)
            throw new NotFoundException("[!] Review with ID \"" + reviewId + "\" not found.");

        return orderedProduct;
    }
}
