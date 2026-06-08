package main.java.com.example.marketplace.seller.account.view;

import main.java.com.example.marketplace.seller.account.dto.SellerAccountResponse;

import java.util.Scanner;

public final class SellerAccountView {

    Scanner scanner = new Scanner(System.in);

    public void printSellerProfile(SellerAccountResponse sellerProfile) {

        System.out.println("----------| PROFILE |----------");
        System.out.println("Name: " + sellerProfile.getName());
        System.out.println("E-mail: " + sellerProfile.getEmail());
        System.out.print("Password: ");
        for (int i = 0; i < sellerProfile.getPasswordSize(); i++)
            System.out.print("*");

        System.out.println("----------| STORE |----------");
        System.out.println("Name: " + sellerProfile.getStoreName());
        System.out.println("CNPJ: " + sellerProfile.getCnpj());
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
