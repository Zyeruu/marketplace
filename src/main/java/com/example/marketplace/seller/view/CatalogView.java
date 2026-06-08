package main.java.com.example.marketplace.seller.view;

import main.java.com.example.marketplace.seller.dto.CatalogRequest;
import main.java.com.example.marketplace.seller.dto.CatalogResponse;
import main.java.com.example.marketplace.shared.enums.ProductType;
import main.java.com.example.marketplace.seller.model.Product;
import main.java.com.example.marketplace.shared.session.SellerSession;
import main.java.com.example.marketplace.shared.utils.IdGenerator;

import java.util.Scanner;

public final class CatalogView {

    Scanner scanner = new Scanner(System.in);

    public void printCatalog(CatalogResponse catalog) {

        System.out.println("-------- PRODUCTS --------\n");

        for (Product p : catalog.getProductList()) {
            System.out.println("\nName: " + p.getName());
            System.out.println("ID: " + p.getId());
            System.out.printf("Unit Price: R$%.2f\n", p.getUnitPrice());
            System.out.println("Stock: " + p.getStock());
        }

        if (catalog.getTotalFood() > 0)
            System.out.println("Total Food: " + catalog.getTotalFood());
        if (catalog.getTotalMisc() > 0)
            System.out.println("Total Miscellaneous: " + catalog.getTotalMisc());
        if (catalog.getTotalProduct() > 0)
            System.out.println("Total Products: " + catalog.getTotalProduct());
    }

    public void printCatalogProduct(Product product) {

        System.out.println("Name: " + product.getName());
        System.out.println("ID: " + product.getId());
        System.out.println("Type: " + product.getType().name());
        if (product.getBrand() != null)
            System.out.println("Brand: " + product.getBrand());
        System.out.printf("Unit Price: R$%.2f\n", product.getUnitPrice());
        System.out.printf("Weight: %.1fKg\n", product.getWeight());
        System.out.println("Stock: " + product.getStock());
        if (product.getWarranty() > 0)
            System.out.println("Warranty: " + product.getWarranty());
    }

    public Product getProductData() {

        int choice;
        String storeName = SellerSession.getStoreName();

        System.out.println("---------- CATALOG ----------\n");

        System.out.println("Select the product type:");
        System.out.print("[1] Food\n[2] Miscellaneous\n>> ");
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
            return new Product(name, id, storeName, unitPrice, weight, stock);
        else {
            System.out.print("Product brand: ");
            System.out.flush();
            String brand = scanner.nextLine();

            System.out.print("Product warranty: ");
            System.out.flush();
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
            System.out.flush();
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
                    printMessage("Needs to be greater than 0. Try again.");
                    System.out.print(">> ");
                    System.out.flush();
                    continue;
                }

                return value;
            }
            catch (NumberFormatException e) {
                printMessage("Invalid input. Please enter a number.");
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
                    printMessage("Needs to be greater than 0. Try again.");
                    System.out.print(">> ");
                    System.out.flush();
                    continue;
                }

                return value;
            }
            catch (NumberFormatException e) {
                printMessage("Invalid input. Please enter a number.");
                System.out.print(">> ");
                System.out.flush();
            }
        }
    }

    public int readChoice() {

        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice != 1 && choice != 2) {
                    printMessage("Invalid option. Try again.");
                    System.out.print(">> ");
                    System.out.flush();
                    continue;
                }

                return choice;
            }
            catch (NumberFormatException e) {
                printMessage("Invalid input. Please enter a number.");
                System.out.print(">> ");
                System.out.flush();
            }
        }
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
