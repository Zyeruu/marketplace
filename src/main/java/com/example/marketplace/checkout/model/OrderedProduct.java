package main.java.com.example.marketplace.checkout.model;

import main.java.com.example.marketplace.shared.enums.ProductType;
import main.java.com.example.marketplace.shared.model.Review;

import java.time.LocalDateTime;

public final class OrderedProduct {

    private final String name;
    private final String id;
    private final String sellerCnpj;
    private final ProductType type;
    private final int quantity;
    private final float unitPrice;
    private final float totalCost;
    private Review review;

    // Buyer
    public OrderedProduct(String name, String id, String sellerCnpj, ProductType type, int quantity, float unitPrice) {
        this.name = name;
        this.id = id;
        this.sellerCnpj = sellerCnpj;
        this.type = type;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalCost = quantity * unitPrice;
        this.review = null;
    }

    // Seller
    public OrderedProduct(String name, String id, ProductType type, int quantity, float unitPrice) {
        this.name = name;
        this.id = id;
        this.sellerCnpj = null;
        this.type = type;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalCost = quantity * unitPrice;
        this.review = null;
    }

    public OrderedProduct(OrderedProduct orderedProductPointer) {
        this.name = orderedProductPointer.name;
        this.id = orderedProductPointer.id;
        this.sellerCnpj = orderedProductPointer.sellerCnpj;
        this.type = orderedProductPointer.type;
        this.quantity = orderedProductPointer.quantity;
        this.unitPrice = orderedProductPointer.unitPrice;
        this.totalCost = orderedProductPointer.totalCost;
        if (orderedProductPointer.getReview() != null)
            this.review = new Review(orderedProductPointer.getReview());
        else
            this.review = null;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getSellerCnpj() {
        return sellerCnpj;
    }

    public ProductType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public Review getReview() {
        return review;
    }

    public String getReviewMessage() {
        return review.getMessage();
    }

    public int getReviewRating() {
        return review.getRating();
    }

    public LocalDateTime getReviewDate() {
        return review.getDate();
    }

    // Setters
    public void setReview(Review review) {
        this.review = review;
    }

    public void setReviewMessage(String message) {
        review.setMessage(message);
    }

    public void setReviewRating(int rating) {
        review.setRating(rating);
    }
}
