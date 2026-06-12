package main.java.com.example.marketplace.user.store.view;

import main.java.com.example.marketplace.shared.enums.ProductType;
import main.java.com.example.marketplace.shared.utils.IdGenerator;
import main.java.com.example.marketplace.user.store.dto.CatalogRequest;
import main.java.com.example.marketplace.user.store.dto.CatalogResponse;
import main.java.com.example.marketplace.user.store.model.Product;

import java.util.Scanner;

public final class CatalogView {

    Scanner scanner = new Scanner(System.in);

    public void printCatalog(CatalogResponse catalog) {

        System.out.println("-----------| PRODUCTS |-----------");

        for (Product p : catalog.productList()) {
            System.out.println("Name: " + p.getName());
            System.out.println("ID: " + p.getId());
            System.out.printf("Unit Price: R$%.2f\n", p.getUnitPrice());
            System.out.println("Stock: " + p.getStock() + "\n");
        }

        System.out.println("----------------------------------");
        if (catalog.totalFood() > 0)
            System.out.println("Total Food: " + catalog.totalFood());

        if (catalog.totalMisc() > 0)
            System.out.println("Total Miscellaneous: " + catalog.totalMisc());

        if (catalog.totalProduct() > 0)
            System.out.println("Total Products: " + catalog.totalProduct());
        System.out.println("----------------------------------\n");
    }

    public void printCatalogProduct(Product product) {

        System.out.println("------------| PRODUCT |------------");

        System.out.println("Name: " + product.getName());
        System.out.println("ID: " + product.getId());
        System.out.println("Type: " + product.getType().getName());
        if (product.getBrand() != null)
            System.out.println("Brand: " + product.getBrand());
        System.out.printf("Unit Price: R$%.2f\n", product.getUnitPrice());
        System.out.printf("Weight: %.1fKg\n", product.getWeight());
        System.out.print("Stock: " + product.getStock());
        if (product.getWarranty() > 0)
            System.out.print("\nWarranty: " + product.getWarranty() + " months");
        System.out.println("\n");
    }

    public Product getProductData() {

        int choice;

        System.out.println("------------| CATALOG |------------");

        System.out.println("Select the product type:");
        System.out.println("[1] Food\n[2] Miscellaneous");
        System.out.flush();
        choice = readChoice();

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
        int stock = readInt();

        if (choice == 1)
            return new Product(name, id, "", unitPrice, weight, stock);
        else {
            System.out.print("Product brand: ");
            System.out.flush();
            String brand = scanner.nextLine();

            System.out.print("Product warranty: ");
            System.out.flush();
            int warranty = readInt();

            return new Product(name, id, "", brand, unitPrice, weight, stock, warranty);
            }
    }

    public ProductType getProductType() {

        ProductType productType = null;
        int choice;

        System.out.println("Select the product type:");

        do {
            System.out.print("[1] Food\n[2] Miscellaneous\n>> ");
            System.out.flush();
            choice = readInt();

            switch (choice){
                case 1 -> productType = ProductType.FOOD;
                case 2 -> productType = ProductType.MISCELLANEOUS;
                default -> System.out.println("[!] Invalid option. Try again.");
            }
        } while (choice != 1 && choice != 2);

        return productType;
    }

    public String getProductName() {

        System.out.print("Enter the product name: ");
        System.out.flush();
        return scanner.nextLine();
    }

    public String getProductId() {

        System.out.print("Enter the product ID: ");
        System.out.flush();
        return scanner.nextLine();
    }

    public CatalogRequest getProductIdAndStock() {

        System.out.print("Enter the product ID: ");
        System.out.flush();
        String id = scanner.nextLine();

        System.out.print("Enter the new stock: ");
        System.out.flush();
        int stock = readInt();

        return new CatalogRequest(id, stock);
    }

    public CatalogRequest getProductIdAndPrice() {

        System.out.print("Enter the product ID: ");
        System.out.flush();
        String id = scanner.nextLine();

        System.out.print("Enter the new price: ");
        System.out.flush();
        float price = readFloat();

        return new CatalogRequest(id, price);
    }

    public int readInt() {

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

    public int readChoice() {

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

    public void printMessage(String message) {
        System.out.println("\n" + message + "\n");
    }
}
