package main.java.com.example.marketplace.buyer.view;

import main.java.com.example.marketplace.buyer.controller.CartController;
import main.java.com.example.marketplace.buyer.dto.CartRequest;
import main.java.com.example.marketplace.buyer.dto.CartResponse;
import main.java.com.example.marketplace.buyer.model.CartProduct;
import main.java.com.example.marketplace.shared.enums.ProductType;

import java.util.Scanner;

public final class CartView {

    private CartController controller;

    Scanner scanner = new Scanner(System.in);

    public void printBuyerCart(CartResponse cart) {

        System.out.println("-------- PRODUCTS --------\n\n");

        for (CartProduct cartProduct : cart.getCartProducts()) {
            if (cartProduct.getType() == ProductType.FOOD) {
                System.out.println("Name: " + cartProduct.getName());
                System.out.println("ID: " + cartProduct.getId());
                System.out.println("Seller: " + cartProduct.getStoreName());
                System.out.println("Quantity: " + cartProduct.getQuantity());
                System.out.printf("Unit Price: R$%.2f\n", cartProduct.getUnitPrice());
            }
        }

        for (CartProduct cartProduct : cart.getCartProducts()) {
            if (cartProduct.getType() == ProductType.MISCELLANEOUS) {
                System.out.println("Name: " + cartProduct.getName());
                System.out.println("ID: " + cartProduct.getId());
                System.out.println("Seller: " + cartProduct.getStoreName());
                System.out.println("Quantity: " + cartProduct.getQuantity());
                System.out.printf("Unit Price: R$%.2f\n", cartProduct.getUnitPrice());
            }
        }

        if (cart.getTotalFood() > 0)
            System.out.println("Total Food: " + cart.getTotalFood());
        if (cart.getTotalMisc() > 0)
            System.out.println("Total Miscellaneous: " + cart.getTotalMisc());
        if (cart.getTotalProducts() > 0)
            System.out.println("\nTotal Products: " + cart.getTotalProducts());
    }

    public void printCartProduct(CartProduct cartProduct) {

        System.out.println("Name: " + cartProduct.getName());
        System.out.println("ID: " + cartProduct.getId());
        System.out.println("Seller: " + cartProduct.getStoreName());
        if (cartProduct.getType() != null)
            System.out.println("Brand: " + cartProduct.getBrand());
        System.out.println("Type: " + cartProduct.getType());
        System.out.println("Quantity: " + cartProduct.getQuantity());
        System.out.printf("Unit Price: R$%.2f\n", cartProduct.getUnitPrice());
        System.out.printf("Weight: %.1fKg", cartProduct.getWeight());
        if (cartProduct.getWarranty() > 0)
            System.out.println("Warranty: " + cartProduct.getWarranty() + " months");
    }

    public CartRequest getProductData() {

        System.out.print("Enter the product ID: ");
        System.out.flush();
        String productId = scanner.nextLine();

        System.out.print("Enter the quantity: ");
        System.out.flush();
        int quantity = readInt();

        return new CartRequest(productId, quantity);
    }

    public ProductType getProductType() {

        ProductType productType = null;
        int choice;

        System.out.println("Select the product type:");

        do {
            System.out.print("[1] Food\n[2] Miscellaneous\n>> ");
            System.out.flush();
            choice = readInt();

            switch (choice){
                case 1 -> productType = ProductType.FOOD;
                case 2 -> productType = ProductType.MISCELLANEOUS;
                default -> System.out.println("Invalid option. Try again.");
            }
        } while (choice != 1 && choice != 2);

        return productType;
    }

    public String getProductName() {

        System.out.print("Enter the product name: ");
        System.out.flush();
        return scanner.nextLine();
    }

    public String getProductId() {

        System.out.print("Enter the product ID: ");
        System.out.flush();
        return scanner.nextLine();
    }

    public int readInt() {

        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine());

                if (value <= 0) {
                    printMessage("Needs to be greater than 0. Try again.");
                    System.out.print(">> ");
                    System.out.flush();
                    continue;
                }

                return value;
            }
            catch (NumberFormatException e) {
                printMessage("Invalid input. Please enter a number.");
                System.out.print(">> ");
                System.out.flush();
            }
        }
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
