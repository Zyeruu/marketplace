package main.java.com.example.marketplace.user.repository;

import main.java.com.example.marketplace.checkout.model.OrderedProduct;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.shared.model.Review;
import main.java.com.example.marketplace.shared.utils.IdGenerator;
import main.java.com.example.marketplace.user.dto.ProductReviewRequest;
import main.java.com.example.marketplace.user.dto.UpdateReviewRequest;
import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.user.store.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public final class OrdersMenuRepository {

    private final DataBase dataBase;
    private final Session session;

    public OrdersMenuRepository(DataBase dataBase, Session session) {
        this.dataBase = dataBase;
        this.session = session;
    }

    public List<TaxReceipt> findTaxReceiptListByEmail() {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        List<TaxReceipt> taxReceiptListCopy = user.getOrdersMenuTaxReceiptList().stream()
                .map(TaxReceipt::new)
                .collect(Collectors.toList());

        if (taxReceiptListCopy.isEmpty())
            throw new NotFoundException("[!] No purchase history.");

        return taxReceiptListCopy;
    }

    public TaxReceipt findTaxReceiptByEmailAndOrderId(String orderId) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        TaxReceipt taxReceipt = user.getOrdersMenuTaxReceiptList().stream()
                .filter(product -> product.getOrderId().equals(orderId))
                .findFirst()
                .orElse(null);

        if (taxReceipt == null)
            throw new NotFoundException("[!] Order with ID \"" + orderId + "\" not found.");

        return new TaxReceipt(taxReceipt);
    }

    public List<OrderedProduct> findReviewListByEmail() {

        String email = session.getEmail();;
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getOrdersMenuReviewList().isEmpty())
            throw new NotFoundException("[!] No results were found.");

        return user.getOrdersMenuOrderedProductList().stream()
                .filter(product -> product.getReview() != null)
                .map(OrderedProduct::new)
                .collect(Collectors.toList());
    }

    public List<OrderedProduct> findUnratedProductsByEmail() {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getOrdersMenuOrderedProductList().isEmpty())
            throw new NotFoundException("[!] You haven't made any purchases yet.");

        List<OrderedProduct> unratedProductList = user.getOrdersMenuOrderedProductList().stream()
                .filter(product -> product.getReview() == null)
                .map(OrderedProduct::new)
                .collect(Collectors.toList());

        if (unratedProductList.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        return unratedProductList;
    }

    public void saveReview(ProductReviewRequest reviewRequest) {

        String email = session.getEmail();;
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        OrderedProduct orderedProduct = user.getOrdersMenuOrderedProductList().stream()
                .filter(product -> product.getId().equals(reviewRequest.productId()))
                .findFirst()
                .orElse(null);

        if (orderedProduct == null)
            throw new NotFoundException("[!] Product with ID \"" + reviewRequest.productId() + "\" was not found in your purchase list.");

        User seller = dataBase.findSellerByCnpj(orderedProduct.getSellerCnpj());

        if (seller == null)
            throw new NotFoundException("[!] The seller of this product is no longer active.");

        if (orderedProduct.getReview() != null)
            throw new AlreadyExistsException("[!] The product with ID \"" + reviewRequest.productId() + "\" already has a review.");

        Review review = new Review(IdGenerator.generateReviewId(), reviewRequest.message(), reviewRequest.rating());

        user.getOrdersMenuReviewList().add(review);
        orderedProduct.setReview(review);

        seller.getStoreReviewList().add(review);
        seller.updateReputation();

        Product catalogProduct = dataBase.findProductById(reviewRequest.productId());

        if (catalogProduct != null) {

            catalogProduct.getReviewList().add(review);
            catalogProduct.updateReputation();
        }
    }

    public void deleteReview(OrderedProduct orderedProduct) {

        String email = session.getEmail();
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

    public void updateReview(UpdateReviewRequest updateReview) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getOrdersMenuReviewList().isEmpty())
            throw new NotFoundException("[!] You don't have any published reviews yet.");

        OrderedProduct orderedProduct = user.getOrdersMenuOrderedProductList().stream()
                .filter(product -> product.getReview() != null)
                .filter(product -> product.getReview().getId().equals(updateReview.reviewId()))
                .findFirst()
                .orElse(null);

        if (orderedProduct == null)
            throw new NotFoundException("[!] Review with ID \"" + updateReview.reviewId() + "\" not found.");

        if (updateReview.deleteMessage() != null) {
            if (orderedProduct.getReviewMessage() == null) {
                throw new IllegalArgumentException("[!] Your review already has no message.");
            }
            else
                orderedProduct.setReviewMessage(null);
        }
        else {
            if (updateReview.message() != null) {
                if (orderedProduct.getReviewMessage() != null) {
                    if (updateReview.message().equals(orderedProduct.getReviewMessage()))
                        throw new IllegalArgumentException("[!] The new review message must be different from the current message.");
                }
                orderedProduct.setReviewMessage(updateReview.message());
            }
        }

        if (updateReview.rating() != null) {
            if (orderedProduct.getReviewRating() == updateReview.rating()) {
                throw new IllegalArgumentException("[!] The new review rating must be different from the current rating.");
            }
            else
                orderedProduct.setReviewRating(updateReview.rating());
        }

        Product catalogProduct = dataBase.findProductById(orderedProduct.getId());
        User seller = dataBase.findSellerByCnpj(orderedProduct.getSellerCnpj());

        if (catalogProduct != null)
            catalogProduct.updateReputation();

        if (seller != null)
            seller.updateReputation();
    }

    public OrderedProduct findReviewById(String reviewId) {

        String email = session.getEmail();
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
