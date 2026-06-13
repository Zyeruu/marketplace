package main.java.com.example.marketplace.user.view;

import main.java.com.example.marketplace.user.dto.CartRequest;
import main.java.com.example.marketplace.user.dto.CartResponse;
import main.java.com.example.marketplace.user.model.CartProduct;
import main.java.com.example.marketplace.shared.enums.ProductType;

import java.util.Scanner;

public final class CartView {

    Scanner scanner = new Scanner(System.in);

    public void printBuyerCart(CartResponse cart) {

        System.out.println("-----------| PRODUCTS |-----------");

        for (CartProduct cartProduct : cart.cartProducts()) {
            if (cartProduct.getType() == ProductType.FOOD) {
                System.out.println("\nName: " + cartProduct.getName());
                System.out.println("ID: " + cartProduct.getId());
                System.out.println("Seller: " + cartProduct.getStoreName());
                System.out.println("Selected: " + (cartProduct.isSelected() ? "Yes" : "No"));
                System.out.println("Quantity: " + cartProduct.getQuantity());
                System.out.printf("Unit Price: R$%.2f\n", cartProduct.getUnitPrice());
                System.out.printf("Total: R$%.2f\n", cartProduct.getUnitPrice() * cartProduct.getQuantity());
            }
        }

        for (CartProduct cartProduct : cart.cartProducts()) {
            if (cartProduct.getType() == ProductType.MISCELLANEOUS) {
                System.out.println("\nName: " + cartProduct.getName());
                System.out.println("ID: " + cartProduct.getId());
                System.out.println("Seller: " + cartProduct.getStoreName());
                System.out.println("Selected: " + (cartProduct.isSelected() ? "Yes" : "No"));
                System.out.println("Quantity: " + cartProduct.getQuantity());
                System.out.printf("Unit Price: R$%.2f\n", cartProduct.getUnitPrice());
                System.out.printf("Total: R$%.2f\n", cartProduct.getUnitPrice() * cartProduct.getQuantity());
            }
        }

        if (cart.totalCost() > 0)
            if (cart.shipping() > 0)
                System.out.printf("\nShipping: R$%.2f", cart.shipping());
            else
                System.out.print("\nShipping: Free");

        System.out.printf("\nTotal cost: R$%.2f\n", cart.totalCost());

        if (cart.totalFood() > 0)
            System.out.println("Total Food: " + cart.totalFood());

        if (cart.totalMisc() > 0)
            System.out.println("Total Miscellaneous: " + cart.totalMisc());

        if (cart.totalProducts() > 0)
            System.out.println("Total Products: " + cart.totalProducts());

        System.out.println();
    }

    public void printCartProduct(CartProduct cartProduct) {

        System.out.println("------------| PRODUCT |------------");
        System.out.println("Name: " + cartProduct.getName());
        System.out.println("ID: " + cartProduct.getId());
        System.out.println("Seller: " + cartProduct.getStoreName());
        System.out.println("Selected: " + (cartProduct.isSelected() ? "Yes" : "No"));
        if (cartProduct.getBrand() != null)
            System.out.println("Brand: " + cartProduct.getBrand());
        System.out.println("Type: " + cartProduct.getType().getName());
        System.out.println("Quantity: " + cartProduct.getQuantity());
        System.out.printf("Unit Price: R$%.2f\n", cartProduct.getUnitPrice());
        System.out.printf("Weight: %.1fKg\n", cartProduct.getWeight());
        if (cartProduct.getWarranty() > 0)
            System.out.print("Warranty: " + cartProduct.getWarranty() + " months");
        System.out.println("\n");
    }

    public CartRequest getProductData() {

        System.out.print("Enter the product ID: ");
        System.out.flush();
        String productId = scanner.nextLine();

        System.out.print("Enter the stock: ");
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
                default -> System.out.println("[!] Invalid option. Try again.");
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
                    printMessage("[!] Needs to be greater than 0. Try again.");
                    System.out.print(">> ");
                    System.out.flush();
                    continue;
                }

                return value;
            }
            catch (NumberFormatException e) {
                printMessage("[!] Invalid input. Please enter a number.");
                System.out.print(">> ");
                System.out.flush();
            }
        }
    }

    public void printMessage(String message) {
        System.out.println("\n" + message + "\n");
    }
}
