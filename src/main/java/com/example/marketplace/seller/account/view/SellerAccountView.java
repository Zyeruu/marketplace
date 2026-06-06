package main.java.com.example.marketplace.seller.account.view;

import java.util.Scanner;

public final class SellerAccountView {

    Scanner scanner = new Scanner(System.in);

    public String getPassword() {

        System.out.println("Confirm with your password: ");
        return scanner.nextLine();
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
