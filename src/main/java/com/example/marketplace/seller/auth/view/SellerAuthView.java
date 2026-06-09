package main.java.com.example.marketplace.seller.auth.view;

import main.java.com.example.marketplace.seller.auth.dto.SellerAuthRequest;

import java.util.Scanner;

public final class SellerAuthView {

    Scanner scanner = new Scanner(System.in);

    public SellerAuthRequest collectRegistrationData() {

        System.out.println("\n-------| SELLER | REGISTER |-------");

        System.out.print("E-mail: ");
        System.out.flush();
        String email = scanner.nextLine().toLowerCase();

        System.out.print("Password: ");
        System.out.flush();
        String password = scanner.nextLine();

        System.out.print("Your name: ");
        System.out.flush();
        String name = scanner.nextLine();

        System.out.print("Store name: ");
        System.out.flush();
        String storeName = scanner.nextLine();

        return new SellerAuthRequest(email, password, name, storeName);
    }

    public SellerAuthRequest collectEmailAndPassword() {

        System.out.println("\n-------------| LOGIN |-------------");

        System.out.print("E-mail: ");
        System.out.flush();
        String email = scanner.nextLine();

        System.out.print("Password: ");
        System.out.flush();
        String password = scanner.nextLine();

        return new SellerAuthRequest(email, password, "", "");
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
