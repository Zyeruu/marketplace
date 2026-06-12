package main.java.com.example.marketplace.user.store.view;

import main.java.com.example.marketplace.user.store.dto.StoreResponse;

import java.util.Scanner;

public class StoreView {

    Scanner scanner = new Scanner(System.in);

    public void printStore(StoreResponse storeResponse) {

        System.out.println("-----------| STORE |-----------");
        System.out.println("Name: " + storeResponse.getStoreName());
        System.out.println("CNPJ: " + storeResponse.getCnpj());
        System.out.println("---------------------------------");
        System.out.println("Total Sales: " + storeResponse.getTotalSales());
        System.out.printf("Total Revenue: R$%.2f", storeResponse.getTotalRevenue());
        System.out.println("\n---------------------------------\n");
    }

    public String getPassword() {

        System.out.print("Confirm with your password: ");
        System.out.flush();
        return scanner.nextLine();
    }

    public String getStoreName() {

        System.out.print("New store name: ");
        System.out.flush();
        return scanner.nextLine();
    }

    public void printMessage(String message) {
        System.out.println("\n" + message + "\n");
    }
}
