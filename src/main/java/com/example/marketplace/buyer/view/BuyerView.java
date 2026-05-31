package main.java.com.example.marketplace.buyer.view;

import main.java.com.example.marketplace.buyer.controller.BuyerController;
import main.java.com.example.marketplace.buyer.dto.BuyerResponse;

public final class BuyerView {

    private BuyerController controller;

    public void printBuyerProfile(String email) {

        BuyerResponse buyerProfile = controller.findByEmail(email);

        if (buyerProfile != null) {
            System.out.println("Name: " + buyerProfile.getName());
            System.out.println("E-mail: " + buyerProfile.getEmail());
            System.out.print("Password: ");
            for (int i = 1; i <= buyerProfile.getPasswordSize(); i++)
                System.out.print("*");
        }
    }
}
