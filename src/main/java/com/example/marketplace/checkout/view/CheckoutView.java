package main.java.com.example.marketplace.checkout.view;

import main.java.com.example.marketplace.checkout.model.PaymentMethod;

import java.util.Scanner;

public final class CheckoutView {

    Scanner scanner = new Scanner(System.in);

    public PaymentMethod selectPaymentMethod() {

        PaymentMethod paymentMethod = null;

        System.out.println("Select payment method:");
        System.out.print("[1] Credit Card\n[2] Debit Card\n[3] Pix\n>> ");
        System.out.flush();
        int choice = readChoice();

        paymentMethod = switch (choice) {
            case 1 -> PaymentMethod.CREDIT_CARD;
            case 2 -> PaymentMethod.DEBIT_CARD;
            case 3 -> PaymentMethod.PIX;
            default -> paymentMethod;
        };

        return paymentMethod;
    }

    public int readChoice() {

        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice != 1 && choice != 2 && choice != 3) {
                    System.out.println("[!] Invalid option. Try again.");
                    System.out.print(">> ");
                    System.out.flush();
                    continue;
                }

                return choice;
            }
            catch (NumberFormatException e) {
                printMessage("[!] Invalid input. Please enter a number.");
                System.out.print(">> ");
                System.out.flush();
            }
        }
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
