package main.java.com.example.marketplace.buyer.view;

import main.java.com.example.marketplace.buyer.controller.CartController;
import main.java.com.example.marketplace.buyer.dto.CartRequest;
import main.java.com.example.marketplace.buyer.dto.CartResponse;
import main.java.com.example.marketplace.buyer.model.CartItem;
import main.java.com.example.marketplace.shared.enums.ItemType;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class CartView {

    private CartController controller;

    Scanner scanner = new Scanner(System.in);

    public void printBuyerCart(CartResponse cart) {

        System.out.println("-------- ITEMS --------\n\n");

        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getType() == ItemType.FOOD) {
                System.out.println("Name: " + cartItem.getName());
                System.out.println("ID: " + cartItem.getId());
                System.out.println("Seller: " + cartItem.getStoreName());
                System.out.println("Type: " + cartItem.getType());
                System.out.println("Quantity: " + cartItem.getQuantity());
                System.out.printf("Unit Price: R$%.2f\n", cartItem.getUnitPrice());
                System.out.printf("Weight: %.1fKg", cartItem.getWeight());
            }
        }

        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getType() == ItemType.MISCELLANEOUS) {
                System.out.println("Name: " + cartItem.getName());
                System.out.println("ID: " + cartItem.getId());
                System.out.println("Seller: " + cartItem.getStoreName());
                System.out.println("Type: " + cartItem.getType());
                System.out.println("Quantity: " + cartItem.getQuantity());
                System.out.printf("Unit Price: R$%.2f\n", cartItem.getUnitPrice());
                System.out.printf("Weight: %.1fKg", cartItem.getWeight());
            }
        }

        if (cart.getTotalFood() > 0)
            System.out.println("Total Food: " + cart.getTotalFood());
        if (cart.getTotalMisc() > 0)
            System.out.println("Total Miscellaneous: " + cart.getTotalMisc());
        System.out.println("\nTotal Items: " + cart.getTotalItems());
    }

    public CartRequest getItemData() {

        System.out.print("Enter the item ID: ");
        String itemId = scanner.nextLine();

        System.out.print("Enter the quantity: ");
        int quantity = readInt();

        return new CartRequest(itemId, quantity);
    }

    public int readInt() {

        while (true) {
            try {
                int value = scanner.nextInt();

                if (value <= 0) {
                    printMessage("Needs to be greater than 0. Try again.");
                    continue;
                }

                return value;
            }
            catch (InputMismatchException e) {
                scanner.nextLine();
                printMessage("Invalid input. Please enter a number.");
            }
        }
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
