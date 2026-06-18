package main.java.com.example.marketplace.user.view;

import main.java.com.example.marketplace.checkout.model.OrderedProduct;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.shared.utils.InputReader;
import main.java.com.example.marketplace.shared.utils.LocalDateTimeFormatter;
import main.java.com.example.marketplace.user.dto.ProductReviewRequest;
import main.java.com.example.marketplace.user.dto.UpdateReviewRequest;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public final class OrdersMenuView {

    Scanner scanner = new Scanner(System.in);

    public void printOrders(List<TaxReceipt> taxReceiptList) {

        for (TaxReceipt taxReceipt : taxReceiptList)
            printOrder(taxReceipt);

        System.out.println("-----------------------------------");
        System.out.println("Total Orders: " + taxReceiptList.size());
        System.out.println("-----------------------------------\n");
    }

    public void printOrder(TaxReceipt taxReceipt) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = taxReceipt.getDateTime().format(formatter);

        System.out.println("----------| TAX RECEIPT |----------");
        System.out.println("Order ID: " + taxReceipt.getOrderId());
        System.out.println("Seller's store name: " + taxReceipt.getSellerName());
        System.out.println("Buyer name: " + taxReceipt.getBuyerName());
        System.out.println("Date: " + formattedDate);
        if (taxReceipt.getShipping() > 0)
            System.out.printf("Shipping: R$%.2f\n", taxReceipt.getShipping());
        else
            System.out.println("Shipping: Free");
        System.out.printf("Total: R$%.2f\n\n", taxReceipt.getTotalCost());

        System.out.println("-----------| PRODUCTS |-----------");

        for (OrderedProduct orderedProduct : taxReceipt.getOrderedProductList()) {

            System.out.println("Name: " + orderedProduct.getName());
            System.out.println("ID: " + orderedProduct.getId());
            System.out.println("Type: " + orderedProduct.getType().getName());
            System.out.println("Quantity: " + orderedProduct.getQuantity());
            System.out.printf("Unit price: R$%.2f\n", orderedProduct.getUnitPrice());
            System.out.printf("Total: R$%.2f\n\n", orderedProduct.getTotalCost());
        }
    }

    public void printReviewList(List<OrderedProduct> productListWithReview) {

        System.out.println("------------| REVIEWS |------------\n");
        System.out.println(productListWithReview.size() + " review(s)\n");

        for (OrderedProduct product : productListWithReview) {

            System.out.println("Product name: " + product.getName());
            System.out.println("Product ID: " + product.getId());
            System.out.print("Rating: ");
            for (int j = 0; j <  product.getReviewRating(); j++)
                System.out.print("*");

            System.out.println("\t" + LocalDateTimeFormatter.formatDateTime(product.getReviewDate()));

            if (product.getReviewMessage() != null)
                System.out.print("Review: " + product.getReviewMessage());

            System.out.println("\n");
        }
    }

    public void printUnratedProductList(List<OrderedProduct> unratedProducts) {

        for (OrderedProduct product : unratedProducts) {
            System.out.println("Product name: " + product.getName());
            System.out.println("Product ID: " + product.getId() + "\n");
        }
    }

    public String getOrderId() {

        System.out.print("Enter the order ID: ");
        System.out.flush();
        return scanner.nextLine();
    }

    public ProductReviewRequest getReviewData() {

        System.out.print("Enter the product ID: ");
        System.out.flush();
        String productId = scanner.nextLine();

        System.out.print("Enter your rating for this product (1–5): ");
        System.out.flush();
        int rating = readRating();

        System.out.println("Would you like to write a message?");
        System.out.println("[1] Yes\n[2] No");
        System.out.flush();

        if (readBoolean()) {
            System.out.print("Write a review: ");
            String review = scanner.nextLine();

            return new ProductReviewRequest(productId, review, rating);
        }
        else
            return new ProductReviewRequest(productId, null, rating);
    }

    public String getReviewId() {

        System.out.print("Enter the review ID: ");
        System.out.flush();
        return scanner.nextLine();
    }

    public UpdateReviewRequest getUpdateReviewRequest() {

        String reviewId = getReviewId();
        String message = null;
        Boolean deleteMessage = null;
        Integer rating = null;
        int choice;

        System.out.println("[1] Update message\n[2] Update rating\n[3] Both");

        do {
            choice = InputReader.readInt();

            switch (choice) {
                case 1:
                    System.out.println("[1] New message\n[2] Delete message");

                    do {
                        choice = InputReader.readInt();

                        switch (choice) {
                            case 1:
                                System.out.print("Write the new review: ");
                                message = scanner.nextLine();
                                break;
                            case 2:
                                deleteMessage = true;
                                break;
                            default:
                                System.out.println("[!] Invalid option. Try again.");
                                break;
                        }
                    } while (choice != 1 && choice != 2);
                    break;

                case 2:
                    System.out.print("Enter the new rating (1-5): ");
                    rating = readRating();
                    break;

                case 3:
                    System.out.println("[1] New message\n[2] Delete message");

                    do {
                        choice = InputReader.readInt();

                        switch (choice) {
                            case 1:
                                System.out.print("Write the new review: ");
                                message = scanner.nextLine();
                                break;
                            case 2:
                                deleteMessage = true;
                                break;
                            default:
                                System.out.println("[!] Invalid option. Try again.");
                                break;
                        }
                    } while (choice != 1 && choice != 2);

                    System.out.print("Enter the new rating (1-5): ");
                    rating = readRating();
                    break;

                default:
                    System.out.println("[!] Invalid option. Try again.");
                    break;
            }
        } while (choice < 1 || choice > 3);

        return new UpdateReviewRequest(reviewId, message, deleteMessage, rating);
    }

    public boolean getFinalDecision() {

        System.out.println("Are you sure you want to delete this review?");
        System.out.println("[1] Yes, I'm sure");
        System.out.println("[2] No, cancel");
        return readBoolean();
    }

    public int readRating() {

        while (true) {
            try {
                int rating = Integer.parseInt(scanner.nextLine());

                if (rating < 1 || rating > 5) {
                    System.out.println("[!] Invalid input. Please enter a rating between 1 and 5.");
                    System.out.print(">> ");
                    continue;
                }

                return rating;
            }
            catch (NumberFormatException e) {
                printMessage("[!] Invalid input. Please enter a integer number.");
                System.out.print(">> ");
            }
        }
    }

    public boolean readBoolean() {

        while (true) {
            try {
                System.out.print(">> ");
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        return true;
                    case 2:
                        return false;
                    default:
                        System.out.println("[!] Invalid input. Please try again.");
                        break;
                }
            }
            catch (NumberFormatException e) {
                printMessage("[!] Invalid input. Please enter a number.");
            }
        }
    }

    public void printMessage(String message) {
        System.out.println("\n" + message + "\n");
    }
}
