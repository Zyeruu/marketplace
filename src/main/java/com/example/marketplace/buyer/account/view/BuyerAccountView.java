package main.java.com.example.marketplace.buyer.account.view;

import main.java.com.example.marketplace.buyer.account.dto.BuyerAccountResponse;

import java.util.Scanner;

public final class BuyerAccountView {

    Scanner scanner = new Scanner(System.in);

    public String getPassword() {

        System.out.println("Confirm with your password: ");
        return scanner.nextLine();
    }

    public void printBuyerProfile(BuyerAccountResponse buyerProfile) {

        System.out.println("Name: " + buyerProfile.getName());
        System.out.println("E-mail: " + buyerProfile.getEmail());
        System.out.print("Password: ");
        for (int i = 0; i < buyerProfile.getPasswordSize(); i++)
            System.out.print("*");
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
