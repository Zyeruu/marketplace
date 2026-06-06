package main.java.com.example.marketplace.seller.account.view;

import main.java.com.example.marketplace.seller.account.dto.SellerAccountResponse;

import java.util.Scanner;

public final class SellerAccountView {

    Scanner scanner = new Scanner(System.in);

    public void printSellerProfile(SellerAccountResponse sellerProfile) {

        System.out.println("Name: " + sellerProfile.getName());
        System.out.println("E-mail: " + sellerProfile.getEmail());
        System.out.print("Password: ");
        for (int i = 0; i < sellerProfile.getPasswordSize(); i++)
            System.out.print("*");
    }
    public String getPassword() {

        System.out.print("Confirm with your password: ");
        return scanner.nextLine();
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
