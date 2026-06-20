package main.java.com.example.marketplace.user.store.view;

import main.java.com.example.marketplace.shared.enums.ProductType;
import main.java.com.example.marketplace.shared.model.Review;
import main.java.com.example.marketplace.shared.utils.IdGenerator;
import main.java.com.example.marketplace.shared.utils.Formatter;
import main.java.com.example.marketplace.user.store.dto.CatalogResponse;
import main.java.com.example.marketplace.user.store.model.Product;
import main.java.com.example.marketplace.user.store.model.Reputation;

import java.util.Scanner;

public final class CatalogView {

    Scanner scanner = new Scanner(System.in);

    public void printCatalog(CatalogResponse catalog) {

        System.out.println("-----------| PRODUCTS |-----------");

        for (Product p : catalog.productList()) {
            System.out.println("Name: " + p.getName());
            System.out.println("ID: " + p.getId());
            System.out.println(p.isAvailable() ? "Status: Available" : "Status: Unavailable");
            System.out.printf("Unit Price: R$%.2f\n", p.getUnitPrice());
            System.out.println("Stock: " + p.getStock() + "\n");
        }

        System.out.println("----------------------------------");
        if (catalog.totalFood() > 0 && catalog.totalMisc() > 0)
            System.out.println("Total Food: " + catalog.totalFood());

        if (catalog.totalMisc() > 0 && catalog.totalFood() > 0)
            System.out.println("Total Miscellaneous: " + catalog.totalMisc());

        if (catalog.totalProduct() > 0)
            System.out.println("Total Products: " + catalog.totalProduct());
        System.out.println("----------------------------------\n");
    }

    public void printCatalogProduct(Product product) {

        System.out.println("------------| PRODUCT |------------");

        System.out.println("Name: " + product.getName());
        System.out.println("ID: " + product.getId());
        System.out.println(product.isAvailable() ? "Status: Available" : "Status: Unavailable");
        System.out.println("Type: " + product.getType().getName());
        if (product.getBrand() != null)
            System.out.println("Brand: " + product.getBrand());
        System.out.printf("Unit Price: R$%.2f\n", product.getUnitPrice());
        System.out.printf("Weight: %.1fKg\n", product.getWeight());
        System.out.print("Stock: " + product.getStock());
        if (product.getWarranty() != null)
            System.out.print("\nWarranty: " + product.getWarranty() + " month(s)");
        System.out.println("\n");
    }

    public void printReviews(Reputation reputation) {

        System.out.println("------------| REVIEWS |------------");
        System.out.println(reputation.getTotalReviews() + " review(s)\n");

        System.out.printf("***** (%d)\n", reputation.getTotal5Stars());
        System.out.printf("****  (%d)\n", reputation.getTotal4Stars());
        System.out.printf("***   (%d)\n", reputation.getTotal3Stars());
        System.out.printf("**    (%d)\n", reputation.getTotal2Stars());
        System.out.printf("*     (%d)\n\n", reputation.getTotal1Star());

        int i = 0;

        for (Review review : reputation.getReviewList()) {

            System.out.print(i + "\nRating: ");
            for (int j = 0; j < review.getRating(); j++)
                System.out.print("*");

            System.out.println("\t" + Formatter.formatDateTime(review.getDate()));

            if (review.getMessage() != null)
                System.out.print("Review: " + review.getMessage());

            System.out.println("\n");
        }
    }

    public Product getProductData() {

        int choice;

        System.out.println("------------| CATALOG |------------");

        System.out.println("Select the product type:");
        System.out.println("[1] Food\n[2] Miscellaneous");
        System.out.flush();
        choice = readProductTypeChoice();

        System.out.print("Product name: ");
        System.out.flush();
        String name = scanner.nextLine();

        String id = IdGenerator.generateProductId();

        System.out.print("Product unit price: ");
        System.out.flush();
        float unitPrice = readFloat();

        System.out.print("Product weight (Kg): ");
        System.out.flush();
        float weight = readFloat();

        System.out.print("Product stock: ");
        System.out.flush();
        int stock = readProductDataInt();

        if (choice == 1)
            return new Product(name, id, null, unitPrice, weight, stock);
        else {
            System.out.print("Product brand: ");
            System.out.flush();
            String brand = scanner.nextLine();

            System.out.print("Product warranty: ");
            System.out.flush();
            int warranty = readProductDataInt();

            return new Product(name, id, null, brand, unitPrice, weight, stock, warranty);
            }
    }

    public String getProductId() {

        System.out.print("Enter the product ID: ");
        System.out.flush();
        return scanner.nextLine();
    }

    public String getProductName() {

        System.out.print("Enter the product name: ");
        System.out.flush();
        return scanner.nextLine();
    }

    public ProductType getProductType() {

        ProductType productType = null;
        int choice;

        System.out.println("Select the product type:");

        do {
            System.out.print("[1] Food\n[2] Miscellaneous\n>> ");
            System.out.flush();
            choice = readProductDataInt();

            switch (choice){
                case 1 -> productType = ProductType.FOOD;
                case 2 -> productType = ProductType.MISCELLANEOUS;
                default -> System.out.println("[!] Invalid option. Try again.");
            }
        } while (choice != 1 && choice != 2);

        return productType;
    }

    public String getProductBrand() {

        System.out.print("Enter the new product brand: ");
        System.out.flush();
        return scanner.nextLine();
    }

    public float getProductPrice() {

        System.out.print("Enter the new product price: ");
        System.out.flush();
        return readFloat();
    }

    public float getProductWeight() {

        System.out.print("Enter the new product weight: ");
        System.out.flush();
        return readFloat();
    }

    public int getProductStock() {

        System.out.print("Enter the new product stock: ");
        System.out.flush();
        return readProductDataInt();
    }

    public int getProductWarranty() {

        System.out.print("Enter the new product warranty: ");
        System.out.flush();
        return readProductDataInt();
    }

    public boolean getAvailability() {

        System.out.println("[1] Mark product as available\n[2] Mark product as unavailable");
        System.out.flush();
        return readBoolean();
    }

    public boolean getFinalDecision() {

        System.out.println("Are you sure you want to remove this product? All data related to this product will be lost.");
        System.out.println("[1] Yes, I'm sure");
        System.out.println("[2] No, cancel");
        return readBoolean();
    }

    public boolean readBoolean() {

        while (true) {
            try {
                System.out.print(">> ");
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        return true;
                    case 2:
                        return false;
                    default:
                        System.out.println("[!] Invalid option. Try again.");
                        break;
                }
            }
            catch (NumberFormatException e) {
                printMessage("[!] Invalid input. Please enter a number.");
                System.out.print(">> ");
                System.out.flush();
            }
        }
    }

    public int readProductDataInt() {

        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine());

                if (value <= 0) {
                    printMessage("[!] Needs to be greater than 0. Try again.");
                    System.out.print(">> ");
                    System.out.flush();
                    continue;
                }

                return value;
            }
            catch (NumberFormatException e) {
                printMessage("[!] Invalid input. Please enter a number.");
                System.out.print(">> ");
                System.out.flush();
            }
        }
    }

    public float readFloat() {

        while (true) {
            try {
                float value = Float.parseFloat(scanner.nextLine());

                if (value <= 0) {
                    printMessage("[!] Needs to be greater than 0. Try again.");
                    System.out.print(">> ");
                    System.out.flush();
                    continue;
                }

                return value;
            }
            catch (NumberFormatException e) {
                printMessage("[!] Invalid input. Please enter a number.");
                System.out.print(">> ");
                System.out.flush();
            }
        }
    }

    public int readProductTypeChoice() {

        while (true) {
            try {
                System.out.print(">> ");
                System.out.flush();
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice != 1 && choice != 2) {
                    printMessage("[!] Invalid option. Try again.");
                    continue;
                }

                return choice;
            }
            catch (NumberFormatException e) {
                printMessage("[!] Invalid input. Please enter a number.");
            }
        }
    }

    public int readUpdateProductChoice() {

        while (true) {
            try {
                System.out.print(">> ");
                System.out.flush();
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice < 1 || choice > 8) {
                    printMessage("[!] Invalid option. Try again.");
                    continue;
                }

                return choice;
            }
            catch (NumberFormatException e) {
                printMessage("[!] Invalid input. Please enter a number.");
            }
        }
    }

    public void printMessage(String message) {
        System.out.println("\n" + message + "\n");
    }

    public void printMessageWithOutLn(String message) {
        System.out.println(message);
    }
}
