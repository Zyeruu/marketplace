package main.java.com.example.marketplace.seller.account.view;

import main.java.com.example.marketplace.exceptions.NotFoundException;

import java.util.Scanner;

public final class AccountView {

    Scanner scanner = new Scanner(System.in);

    public String getPassword() {

        System.out.print("Confirm with your password:\n>> ");
        return scanner.nextLine();
    }

    public void printException(NotFoundException e) {
        System.out.println(e.getMessage());
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
