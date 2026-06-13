package main.java.com.example.marketplace.user.search.view;

import main.java.com.example.marketplace.user.search.dto.SearchRequest;
import main.java.com.example.marketplace.user.store.model.Product;
import main.java.com.example.marketplace.shared.enums.ProductType;

import java.util.List;
import java.util.Scanner;

public final class SearchView {

    Scanner scanner = new Scanner(System.in);

    public void printProduct(Product product) {

        System.out.println("------------| PRODUCT |------------");

        System.out.println("Name: " + product.getName());
        System.out.println("ID: " + product.getId());
        System.out.println("Seller: " + product.getStoreName());
        System.out.println("Type: " + product.getType().getName());
        if (product.getBrand() != null)
            System.out.println("Brand: " + product.getBrand());
        System.out.printf("Unit Price: R$%.2f\n", product.getUnitPrice());
        System.out.printf("Weight: %.1fKg\n", product.getWeight());
        System.out.print("Stock: " + product.getStock());
        if (product.getWarranty() != null)
            System.out.print("\nWarranty: " + product.getWarranty() + " months");
        System.out.println("\n");
    }

    public void printProductListCompacted(List<Product> productList) {

        System.out.println("------------| PRODUCTS |------------");
        System.out.println("\nTotal products found: " + productList.size());
        System.out.println();

        for (Product product : productList) {

            System.out.println("Name: " + product.getName());
            System.out.println("ID: " + product.getId());
            System.out.println("Seller: " + product.getStoreName());
            System.out.printf("Unit Price: R$%.2f\n", product.getUnitPrice());
            System.out.println("Stock: " + product.getStock() + "\n");
        }
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
            choice = readInt();

            switch (choice){
                case 1 -> productType = ProductType.FOOD;
                case 2 -> productType = ProductType.MISCELLANEOUS;
                default -> System.out.println("[!] Invalid option. Try again.");
            }
        } while (choice != 1 && choice != 2);

        return productType;
    }

    public SearchRequest getProductNameAndType() {

        String productName = getProductName();
        ProductType productType = getProductType();
        return new SearchRequest(productName, productType);
    }

    public String getProductId() {

        System.out.print("Enter the product ID: ");
        System.out.flush();
        return scanner.nextLine();
    }

    public void printMessage(String message) {
        System.out.println("\n" + message + "\n");
    }

    public static int readInt() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            }
            catch (NumberFormatException e) {
                System.out.println("[!] Invalid input. Please enter a number.");
                System.out.print(">> ");
                System.out.flush();
            }
        }
    }
}
