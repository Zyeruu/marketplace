package main.java.com.example.marketplace.checkout.view;

import main.java.com.example.marketplace.checkout.model.PaymentMethod;

import java.util.Scanner;

public class CheckoutView {

    Scanner scanner = new Scanner(System.in);

    public PaymentMethod selectPaymentMethod() {

        int choice;
        PaymentMethod paymentMethod = null;

        do {
            System.out.println("Select payment method:");
            System.out.print("[1] Credit Card\n[2] Debit Card\n[3] Pix\n>> ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    paymentMethod = PaymentMethod.CREDIT_CARD;
                    break;
                case 2:
                    paymentMethod = PaymentMethod.DEBIT_CARD;
                    break;
                case 3:
                    paymentMethod = PaymentMethod.PIX;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (choice != 1 && choice != 2 && choice != 3);

        return paymentMethod;
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
