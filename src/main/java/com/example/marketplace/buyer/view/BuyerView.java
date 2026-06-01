package main.java.com.example.marketplace.buyer.view;

import main.java.com.example.marketplace.buyer.controller.BuyerController;
import main.java.com.example.marketplace.buyer.dto.BuyerResponse;
import main.java.com.example.marketplace.exceptions.NotFoundException;

public final class BuyerView {

    private BuyerController controller;

    public void printBuyerProfile(BuyerResponse buyerProfile) {

        System.out.println("Name: " + buyerProfile.getName());
        System.out.println("E-mail: " + buyerProfile.getEmail());
        System.out.print("Password: ");
        for (int i = 0; i < buyerProfile.getPasswordSize(); i++)
            System.out.print("*");
    }

    public void printException(NotFoundException e) {
        System.out.println(e.getMessage());
    }
}
