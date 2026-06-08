package main.java.com.example.marketplace.buyer.account.view;

import main.java.com.example.marketplace.buyer.account.dto.BuyerAccountResponse;

import java.util.Scanner;

public final class BuyerAccountView {

    Scanner scanner = new Scanner(System.in);

    public void printBuyerProfile(BuyerAccountResponse buyerProfile) {

        System.out.println("Name: " + buyerProfile.name());
        System.out.println("E-mail: " + buyerProfile.email());
        System.out.print("Password: ");
        for (int i = 0; i < buyerProfile.passwordSize(); i++)
            System.out.print("*");
        System.out.println();
    }

    public String getPassword() {

        System.out.print("Confirm with your password: ");
        System.out.flush();
        return scanner.nextLine();
    }

    public String getNewPassword() {

        System.out.print("Enter your new password: ");
        System.out.flush();
        return scanner.nextLine();
    }

    public String getEmail() {

        System.out.print("Enter the new e-mail: ");
        System.out.flush();
        return scanner.nextLine();
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
