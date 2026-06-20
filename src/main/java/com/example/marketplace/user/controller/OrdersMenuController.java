package main.java.com.example.marketplace.user.controller;

import main.java.com.example.marketplace.checkout.model.OrderedProduct;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.shared.model.Review;
import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.shared.utils.IdGenerator;
import main.java.com.example.marketplace.user.dto.ProductReviewRequest;
import main.java.com.example.marketplace.user.dto.UpdateReviewRequest;
import main.java.com.example.marketplace.user.service.OrdersMenuService;
import main.java.com.example.marketplace.user.view.OrdersMenuView;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.exceptions.NotFoundException;

import java.util.List;

public final class OrdersMenuController {

    private final OrdersMenuView view;
    private final OrdersMenuService service;
    private final Session session;

    public OrdersMenuController(OrdersMenuView view, OrdersMenuService service, Session session) {
        this.view = view;
        this.service = service;
        this.session = session;
    }

    public void printOrders() {

        String email = session.getLoggedUserEmail();

        try {
            List<TaxReceipt> taxReceiptList = service.findAndVerifyTaxReceiptList(email);
            view.printOrders(taxReceiptList);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void printOrder() {

        String email = session.getLoggedUserEmail();
        String orderId = view.getOrderId();

        try {
            TaxReceipt taxReceipt = service.findTaxReceiptByEmailAndOrderId(email, orderId);
            view.printOrder(taxReceipt);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public boolean printReviewList() {

        String email = session.getLoggedUserEmail();

        try {
            List<OrderedProduct> productsWithReviews = service.findProductsWithReviewByEmail(email);
            view.printReviewList(productsWithReviews);
            return true;
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public boolean printUnratedProducts() {

        String email = session.getLoggedUserEmail();

        try {
            List<OrderedProduct> unratedProductList = service.findUnratedProductsByEmail(email);
            view.printUnratedProductList(unratedProductList);
            return true;
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
            return false;
        }
    }

    public void reviewProduct() {

        String email = session.getLoggedUserEmail();
        ProductReviewRequest reviewRequest = view.getReviewData();

        try {
            service.validateReviewMessage(reviewRequest.message());
            service.verifyReviewRequest(email, reviewRequest);

            Review review = new Review(IdGenerator.generateReviewId(), reviewRequest.message(), reviewRequest.rating());
            service.saveReview(email, review, reviewRequest.productId());

            view.printMessage("[+] Review successfully saved!");
        }
        catch (NotFoundException | IllegalArgumentException | AlreadyExistsException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void deleteReview() {

        String email = session.getLoggedUserEmail();
        String reviewId = view.getReviewId();

        try {
            OrderedProduct orderedProduct = service.findReviewByEmailAndId(email, reviewId);

            if (view.getFinalDecision()) {
                service.deleteReview(email, orderedProduct);
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

        String email = session.getLoggedUserEmail();
        UpdateReviewRequest updateReviewRequest = view.getUpdateReviewRequest();

        try {
            service.validateReviewMessage(updateReviewRequest.message());
            service.verifyReviewRequestAndUpdate(email, updateReviewRequest);

            view.printMessage("[*] Review updated.");
        }
        catch (NotFoundException | IllegalArgumentException e) {
            view.printMessage(e.getMessage());
        }
    }
}
