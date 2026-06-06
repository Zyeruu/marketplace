package main.java.com.example.marketplace.buyer.auth.view;

import main.java.com.example.marketplace.buyer.auth.dto.BuyerAuthRequest;

import java.util.Scanner;

public final class BuyerAuthView {

    Scanner scanner = new Scanner(System.in);

    public BuyerAuthRequest collectRegistrationData() {
        System.out.println("--------| BUYER | REGISTER |--------\n");

        System.out.print("E-mail: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Your name: ");
        String name = scanner.nextLine();

        return new BuyerAuthRequest(email, password, name);
    }

    public BuyerAuthRequest collectEmailAndPassword() {
        System.out.println("-------- LOGIN --------\n");

        System.out.println("E-mail: ");
        String email = scanner.nextLine();

        System.out.println("Password: ");
        String password = scanner.nextLine();

        return new BuyerAuthRequest(email, password, "");
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
