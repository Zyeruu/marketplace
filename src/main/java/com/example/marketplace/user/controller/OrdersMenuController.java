package main.java.com.example.marketplace.user.controller;

import main.java.com.example.marketplace.checkout.model.OrderedProduct;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.shared.utils.Validator;
import main.java.com.example.marketplace.user.dto.ProductReviewRequest;
import main.java.com.example.marketplace.user.dto.UpdateReviewRequest;
import main.java.com.example.marketplace.user.repository.OrdersMenuRepository;
import main.java.com.example.marketplace.user.view.OrdersMenuView;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.exceptions.NotFoundException;

import java.util.List;

public final class OrdersMenuController {

    private final OrdersMenuView view;
    private final OrdersMenuRepository repository;

    public OrdersMenuController(OrdersMenuView view, OrdersMenuRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void printOrders() {

        try {
            List<TaxReceipt> taxReceiptList = repository.findTaxReceiptListByEmail();
            view.printOrders(taxReceiptList);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void printOrder() {

        String orderId = view.getOrderId();

        try {
            TaxReceipt taxReceipt = repository.findTaxReceiptByEmailAndOrderId(orderId);
            view.printOrder(taxReceipt);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public boolean printReviewList() {

        try {
            List<OrderedProduct> productListWithReviews = repository.findReviewListByEmail();
            view.printReviewList(productListWithReviews);
            return true;
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean printUnratedProducts() {

        try {
            List<OrderedProduct> unratedProductList = repository.findUnratedProductsByEmail();
            view.printUnratedProductList(unratedProductList);
            return true;
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public void reviewProduct() {

        ProductReviewRequest reviewRequest = view.getReviewData();

        try {
            if (reviewRequest.message() != null)
                Validator.isValidMessage(reviewRequest.message());

            repository.saveReview(reviewRequest);
            view.printMessage("[+] Review successfully saved!");
        }
        catch (NotFoundException | IllegalArgumentException | AlreadyExistsException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void deleteReview() {

        String reviewId = view.getReviewId();

        try {
            OrderedProduct orderedProduct = repository.findReviewById(reviewId);

            if (view.getFinalDecision()) {
                repository.deleteReview(orderedProduct);
                view.printMessage("[-] Review deleted.");
            }
            else
                view.printMessage("[X] Action canceled.");
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void updateReview() {

        UpdateReviewRequest updateReviewRequest = view.getUpdateReviewRequest();

        try {
            if (updateReviewRequest.message() != null)
                Validator.isValidMessage(updateReviewRequest.message());

            repository.updateReview(updateReviewRequest);
            view.printMessage("[*] Review updated.");
        }
        catch (NotFoundException | IllegalArgumentException e) {
            view.printMessage(e.getMessage());
        }
    }
}
