package main.java.com.example.marketplace.buyer.view;

import main.java.com.example.marketplace.buyer.controller.CartController;
import main.java.com.example.marketplace.buyer.dto.CartResponse;
import main.java.com.example.marketplace.buyer.model.CartItem;
import main.java.com.example.marketplace.shared.ItemType;

public final class CartView {

    private CartController controller;

    public void printBuyerCart(String email) {

        CartResponse cart = controller.searchUser(email);

        if (cart != null)
            if (!cart.getCartItems().isEmpty()) {

                System.out.println("-------- ITEMS --------\n\n");

                for (CartItem cartItem : cart.getCartItems()) {
                    if (cartItem.getType() == ItemType.FOOD) {
                        System.out.println("Name: " + cartItem.getName());
                        System.out.println("Type: " + cartItem.getType());
                        System.out.println("Quantity: " + cartItem.getQuantity());
                        System.out.printf("Unit Price: R$%.2f\n", cartItem.getUnitPrice());
                        System.out.printf("Weight: %.1fKg", cartItem.getWeight());
                    }
                }

                for (CartItem cartItem : cart.getCartItems()) {
                    if (cartItem.getType() == ItemType.MISCELLANEOUS) {
                        System.out.println("Name: " + cartItem.getName());
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
            else
                System.out.println("Your cart is empty!");
    }
}
