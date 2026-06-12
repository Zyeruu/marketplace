package main.java.com.example.marketplace.user.account.view;

import main.java.com.example.marketplace.shared.utils.InputReader;
import main.java.com.example.marketplace.user.account.dto.AccountResponse;

import java.util.Scanner;

public final class AccountView {

    Scanner scanner = new Scanner(System.in);

    public void printBuyerProfile(AccountResponse buyerProfile) {

        System.out.println("------------| PROFILE |------------");
        System.out.println("Name: " + buyerProfile.name());
        System.out.println("E-mail: " + buyerProfile.email());
        System.out.print("Password: ");
        for (int i = 0; i < buyerProfile.passwordSize(); i++)
            System.out.print("*");
        System.out.println("\n-----------------------------------\n");
    }

    public String getStoreName() {

        System.out.print("Store name: ");
        System.out.flush();
        return scanner.nextLine();
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
        return scanner.nextLine().toLowerCase();
    }

    public boolean getFinalResponse() {

        System.out.println("[!] Do you confirm this action? Once it's done, there's no going back.");
        System.out.println("[1] Yes, I confirm\n[2] No, I changed my mind");

        int choice;
        boolean response = false;

        do {
            choice = InputReader.readInt();

            switch (choice) {
                case 1 -> response = true;
                case 2 -> response = false;
                default -> System.out.println("[!] Invalid option. Try again.");
            }
        } while (choice != 1 && choice != 2);

        return response;
    }

    public void printMessage(String message) {
        System.out.println("\n" + message + "\n");
    }
}
