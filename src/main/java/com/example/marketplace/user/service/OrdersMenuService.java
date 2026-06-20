package main.java.com.example.marketplace.user.service;

import main.java.com.example.marketplace.checkout.model.OrderedProduct;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.model.Review;
import main.java.com.example.marketplace.shared.utils.Validator;
import main.java.com.example.marketplace.user.account.service.AccountService;
import main.java.com.example.marketplace.user.dto.ProductReviewRequest;
import main.java.com.example.marketplace.user.dto.UpdateReviewRequest;
import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.user.repository.OrdersMenuRepository;

import java.util.List;

public final class OrdersMenuService {

    private final OrdersMenuRepository repository;
    private final AccountService accountService;

    public OrdersMenuService(OrdersMenuRepository repository, AccountService accountService) {
        this.repository = repository;
        this.accountService = accountService;
    }

    public List<OrderedProduct> findOrderedProductListByEmail(String email) {

        List<OrderedProduct> orderedProductList = repository.findOrderedProductListByEmail(email);

        if (orderedProductList.isEmpty())
            throw new NotFoundException("[!] You haven't made any purchases yet.");

        return orderedProductList;
    }

    public List<TaxReceipt> findAndVerifyTaxReceiptList(String email) {

        List<TaxReceipt> taxReceiptList = repository.findTaxReceiptListByEmail(email);

        if (taxReceiptList.isEmpty())
            throw new NotFoundException("[!] No purchase history.");

        return taxReceiptList;
    }

    public TaxReceipt findTaxReceiptByEmailAndOrderId(String email, String orderId) {
        return repository.findTaxReceiptByEmailAndOrderId(email, orderId);
    }

    public List<OrderedProduct> findProductsWithReviewByEmail(String email) {

        List<OrderedProduct> productList = repository.findProductsReviewByEmail(email);

        if (productList.isEmpty())
            throw new NotFoundException("[!] You do not have any published reviews yet.");

        return productList;
    }

    public List<OrderedProduct> findUnratedProductsByEmail(String email) {

        List<OrderedProduct> unratedProductList = repository.findUnratedProductsByEmail(email);

        if (unratedProductList.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        return unratedProductList;
    }

    public void verifyReviewRequest(String email, ProductReviewRequest request) {

        List<OrderedProduct> orderedProductList = findOrderedProductListByEmail(email);

        OrderedProduct orderedProduct = orderedProductList.stream()
                .filter(product -> product.getId().equals(request.productId()))
                .findFirst()
                .orElse(null);

        if (orderedProduct == null)
            throw new NotFoundException("[!] Product with ID \"" + request.productId() + "\" was not found in your purchase list.");

        User seller = accountService.findSellerByCnpj(orderedProduct.getSellerCnpj());

        if (seller == null)
            throw new NotFoundException("[!] The seller of this product is no longer active.");

        if (orderedProduct.getReview() != null)
            throw new AlreadyExistsException("[!] The product with ID \"" + request.productId() + "\" already has a review.");
    }

    public void saveReview(String email, Review review, String productId) {
        repository.saveReview(email, review, productId);
    }

    public OrderedProduct findReviewByEmailAndId(String email, String reviewId) {
        return repository.findReviewByEmailAndId(email, reviewId);
    }

    public void deleteReview(String email, OrderedProduct orderedProduct) {
        repository.deleteReview(email, orderedProduct);
    }

    public void validateReviewMessage(String message) {

        if (message != null)
            Validator.isValidMessage(message);
    }

    public void verifyReviewRequestAndUpdate(String email, UpdateReviewRequest request) {

        User user = accountService.findUserByEmail(email);

        if (user.getOrdersMenuReviewList().isEmpty())
            throw new NotFoundException("[!] You don't have any published reviews yet.");

        OrderedProduct orderedProduct = user.getOrdersMenuOrderedProductList().stream()
                .filter(product -> product.getReview() != null)
                .filter(product -> product.getReview().getId().equals(request.reviewId()))
                .findFirst()
                .orElse(null);

        if (orderedProduct == null)
            throw new NotFoundException("[!] Review with ID \"" + request.reviewId() + "\" not found.");

        if (request.deleteMessage() != null) {
            if (orderedProduct.getReviewMessage() == null) {
                throw new IllegalArgumentException("[!] Your review already has no message.");
            }
            else
                repository.updateReviewMessage(orderedProduct, null);
        }
        else {
            if (request.message() != null) {
                if (orderedProduct.getReviewMessage() != null) {
                    if (request.message().equals(orderedProduct.getReviewMessage()))
                        throw new IllegalArgumentException("[!] The new review message must be different from the current message.");
                }
                repository.updateReviewMessage(orderedProduct, request.message());
            }
        }

        if (request.rating() != null) {
            if (orderedProduct.getReviewRating() == request.rating()) {
                throw new IllegalArgumentException("[!] The new review rating must be different from the current rating.");
            }
            else
                repository.updateReviewRating(orderedProduct, request.rating());
        }

        repository.updateSellerAndProductReputation(orderedProduct.getSellerCnpj(), orderedProduct.getId());
    }
}
