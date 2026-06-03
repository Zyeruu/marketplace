package main.java.com.example.marketplace.buyer.account.view;

import main.java.com.example.marketplace.buyer.account.dto.AccountResponse;
import main.java.com.example.marketplace.exceptions.NotFoundException;

import java.util.Scanner;

public final class AccountView {

    Scanner scanner = new Scanner(System.in);

    public String getPassword() {

        System.out.print("Confirm with your password:\n>> ");
        return scanner.nextLine();
    }

    public void printBuyerProfile(AccountResponse buyerProfile) {

        System.out.println("Name: " + buyerProfile.getName());
        System.out.println("E-mail: " + buyerProfile.getEmail());
        System.out.print("Password: ");
        for (int i = 0; i < buyerProfile.getPasswordSize(); i++)
            System.out.print("*");
    }

    public void printException(NotFoundException e) {
        System.out.println(e.getMessage());
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
