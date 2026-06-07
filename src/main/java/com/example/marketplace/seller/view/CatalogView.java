package main.java.com.example.marketplace.seller.view;

import main.java.com.example.marketplace.seller.controller.CatalogController;
import main.java.com.example.marketplace.seller.dto.CatalogRequest;
import main.java.com.example.marketplace.seller.dto.CatalogResponse;
import main.java.com.example.marketplace.shared.enums.ProductType;
import main.java.com.example.marketplace.seller.model.Product;
import main.java.com.example.marketplace.shared.session.SellerSession;
import main.java.com.example.marketplace.shared.utils.IdGenerator;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class CatalogView {

    CatalogController controller;

    Scanner scanner = new Scanner(System.in);

    public void printCatalog(CatalogResponse catalog) {

        System.out.println("-------- PRODUCTS --------\n\n");

        for (Product p : catalog.getProductList())
            if (p.getType() == ProductType.FOOD) {
                System.out.println("Name: " + p.getName());
                System.out.println("ID: " + p.getId());
                System.out.println("Seller: " + p.getStoreName());
                System.out.println("Type: " + p.getType().name());
                System.out.printf("Unit Price: R$%.2f\n", p.getUnitPrice());
                System.out.printf("Weight: %.1fKg\n", p.getWeight());
                System.out.println("Stock: " + p.getStock());
            }

        for (Product p : catalog.getProductList())
            if (p.getType() == ProductType.MISCELLANEOUS) {
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
        if (catalog.getTotalProduct() > 0)
            System.out.println("Total Products: " + catalog.getTotalProduct());
    }

    public Product getProductData() {

        int choice;
        String storeName = SellerSession.getStoreName();

        System.out.println("---------- CATALOG ----------\n");

        System.out.println("Select the product type:");
        System.out.print("[1] Food\n[2] Miscellaneous\n>> ");
        choice = readChoice();

        System.out.print("Product name: ");
        String name = scanner.nextLine();

        String id = IdGenerator.generateProductId();

        System.out.print("Product unit price: ");
        float unitPrice = readFloat();

        System.out.print("Product weight (Kg): ");
        float weight = readFloat();

        System.out.print("Product stock: ");
        int stock = readInt();

        if (choice == 1)
            return new Product(name, id, storeName, unitPrice, weight, stock);
        else {
            System.out.print("Product brand: ");
            String brand = scanner.nextLine();

            System.out.print("Product warranty: ");
            int warranty = readInt();

            return new Product(name, id, storeName, brand, unitPrice, weight, stock, warranty);
            }
    }

    public ProductType getProductType() {

        ProductType productType = null;
        int choice = 0;

        System.out.println("Select the product type:");

        do {
            System.out.print("[1] Food\n[2] Miscellaneous\n>> ");
            choice = readInt();

            switch (choice){
                case 1 -> productType = ProductType.FOOD;
                case 2 -> productType = ProductType.MISCELLANEOUS;
                default -> System.out.println("Invalid option. Try again.");
            }
        } while (choice != 1 && choice != 2);

        return productType;
    }

    public String getProductName() {

        System.out.print("Enter the product name: ");
        return scanner.nextLine();
    }

    public String getProductId() {

        System.out.print("Enter the product ID: ");
        return scanner.nextLine();
    }

    public CatalogRequest getProductIdAndStock() {

        System.out.print("Enter the product ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter the new stock: ");
        int stock = readInt();
        return new CatalogRequest(id, stock);
    }

    public CatalogRequest getProductIdAndPrice() {

        System.out.print("Enter the product ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter the new price: ");
        float price = readFloat();
        return new CatalogRequest(id, price);
    }

    public int readInt() {

        while (true) {
            try {
                int value = scanner.nextInt();

                if (value <= 0) {
                    printMessage("Needs to be greater than 0. Try again.");
                    continue;
                }

                return value;
            }
            catch (InputMismatchException e) {
                scanner.nextLine();
                printMessage("Invalid input. Please enter a number.");
            }
        }
    }

    public float readFloat() {

        while (true) {
            try {
                float value = scanner.nextInt();

                if (value <= 0) {
                    printMessage("Needs to be greater than 0. Try again.");
                    continue;
                }

                return value;
            }
            catch (InputMismatchException e) {
                scanner.nextLine();
                printMessage("Invalid input. Please enter a number.");
            }
        }
    }

    public int readChoice() {

        while (true) {
            try {
                int choice = scanner.nextInt();

                if (choice != 1 && choice != 2) {
                    printMessage("Invalid option. Try again.");
                    continue;
                }

                return choice;
            }
            catch (InputMismatchException e) {
                scanner.nextLine();
                printMessage("Invalid input. Please enter a number.");
            }
        }
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
