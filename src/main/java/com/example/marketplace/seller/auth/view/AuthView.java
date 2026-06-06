package main.java.com.example.marketplace.seller.auth.view;

import main.java.com.example.marketplace.seller.auth.dto.AuthRequest;

import java.util.Scanner;

public final class AuthView {

    Scanner scanner = new Scanner(System.in);

    public AuthRequest collectRegistrationData() {

        System.out.println("--------| SELLER | REGISTER |--------\n");

        System.out.print("E-mail: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Your name: ");
        String name = scanner.nextLine();

        System.out.print("Store name: ");
        String storeName = scanner.nextLine();

        return new AuthRequest(email, password, name, storeName);
    }

    public AuthRequest collectEmailAndPassword() {

        System.out.println("-------- LOGIN --------\n");

        System.out.print("E-mail: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        return new AuthRequest(email, password, "", "");
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
