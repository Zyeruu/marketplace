package main.java.com.example.marketplace.seller.view;

import main.java.com.example.marketplace.seller.controller.CatalogController;
import main.java.com.example.marketplace.seller.dto.CatalogRequest;
import main.java.com.example.marketplace.seller.dto.CatalogResponse;
import main.java.com.example.marketplace.shared.enums.ItemType;
import main.java.com.example.marketplace.seller.model.Product;
import main.java.com.example.marketplace.shared.session.SellerSession;
import main.java.com.example.marketplace.shared.utils.IdGenerator;

import java.util.Scanner;

public final class CatalogView {

    CatalogController controller;

    Scanner scanner = new Scanner(System.in);

    public Product getProductData() {

        int choice;
        String storeName = SellerSession.getStoreName();

        System.out.println("---------- CATALOG ----------\n");

        do {
            System.out.println("Select the product type:");
            System.out.print("[1] Food\n[2] Miscellaneous\n>> ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                case 2:
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (choice != 1 && choice != 2);

        System.out.print("Product name: ");
        String name = scanner.nextLine();

        String id = IdGenerator.generateProductId();

        System.out.print("Product unit price: ");
        float unitPrice = scanner.nextFloat();

        System.out.print("Product weight (Kg): ");
        float weight = scanner.nextFloat();

        System.out.print("Product stock: ");
        int stock = scanner.nextInt();

        if (choice == 1)
            return new Product(name, id, storeName, unitPrice, weight, stock);
        else {
            System.out.print("Product brand: ");
            String brand = scanner.nextLine();

            System.out.print("Product warranty: ");
            int warranty = scanner.nextInt();

            return new Product(name, id, storeName, brand, unitPrice, weight, stock, warranty);
            }
    }

    public String getProducttId() {

        System.out.print("Enter the product ID: ");
        return scanner.nextLine();
    }

    public void printCatalog(CatalogResponse catalog) {

        System.out.println("-------- ITEMS --------\n\n");

        for (Product p : catalog.getProductList())
            if (p.getType() == ItemType.FOOD) {
                System.out.println("Name: " + p.getName());
                System.out.println("ID: " + p.getId());
                System.out.println("Seller: " + p.getStoreName());
                System.out.println("Type: " + p.getType().name());
                System.out.printf("Unit Price: R$%.2f\n", p.getUnitPrice());
                System.out.printf("Weight: %.1fKg\n", p.getWeight());
                System.out.println("Stock: " + p.getStock());
            }

        for (Product p : catalog.getProductList())
            if (p.getType() == ItemType.MISCELLANEOUS) {
                System.out.println("Name: " + p.getName());
                System.out.println("ID: " + p.getId());
                System.out.println("Seller: " + p.getStoreName());
                System.out.println("Type: " + p.getType().name());
                System.out.println("Brand: " + p.getBrand());
                System.out.printf("Unit Price: R$%.2f\n", p.getUnitPrice());
                System.out.printf("Weight: %.1fKg\n", p.getWeight());
                System.out.println("Stock: " + p.getStock());
                System.out.println("Warranty: " + p.getWarranty());
            }

        if (catalog.getTotalFood() > 0)
            System.out.println("Total Food: " + catalog.getTotalFood());
        if (catalog.getTotalMisc() > 0)
            System.out.println("Total Miscellaneous: " + catalog.getTotalMisc());
        System.out.println("Total Items: " + catalog.getTotalItems());
    }

    public CatalogRequest getProductIdAndStock() {

        System.out.println("---------- CATALOG ----------\n");

        System.out.print("Enter the product ID: ");
        String id = scanner.nextLine();
        int stock;
        do {
            System.out.print("Enter the new stock: ");
            stock = scanner.nextInt();
            if (stock <= 0)
                System.out.println("Needs to be bigger than 0. Try again.");
        } while (stock <= 0);

        return new CatalogRequest(id, stock);
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
