package main.java.com.example.marketplace.buyer.search.view;

import main.java.com.example.marketplace.buyer.search.dto.BuyerSearchRequest;
import main.java.com.example.marketplace.seller.model.Product;
import main.java.com.example.marketplace.shared.enums.ProductType;

import java.util.List;
import java.util.Scanner;

public final class BuyerSearchView {

    Scanner scanner = new Scanner(System.in);

    public void printProduct(Product product) {

        System.out.println("\n------------| PRODUCT |------------");

        System.out.println("Name: " + product.getName());
        System.out.println("ID: " + product.getId());
        System.out.println("Seller: " + product.getStoreName());
        System.out.println("Type: " + product.getType().name());
        if (product.getBrand() != null)
            System.out.println("Brand: " + product.getBrand());
        System.out.printf("Unit Price: R$%.2f\n", product.getUnitPrice());
        System.out.printf("Weight: %.1fKg\n", product.getWeight());
        System.out.println("Stock: " + product.getStock());
        if (product.getWarranty() != 0)
            System.out.println("Warranty: " + product.getWarranty());
    }

    public void printProductListCompacted(List<Product> productList) {

        System.out.println("\n------------| PRODUCTS |------------");
        System.out.println("\nTotal products found: " + productList.size());
        System.out.println();

        for (Product product : productList) {

            System.out.println("Name: " + product.getName());
            System.out.println("ID: " + product.getId());
            System.out.println("Seller: " + product.getStoreName());
            System.out.printf("Unit Price: R$%.2f\n", product.getUnitPrice());
            System.out.println("Stock: " + product.getStock());
            System.out.println();
        }
    }

    public String getProductName() {

        System.out.print("Enter the product name: ");
        System.out.flush();
        return scanner.nextLine();
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
                default -> System.out.println("[!] Invalid option. Try again.");
            }
        } while (choice != 1 && choice != 2);

        return productType;
    }

    public BuyerSearchRequest getProductNameAndType() {

        String productName = getProductName();
        ProductType productType = getProductType();
        return new BuyerSearchRequest(productName, productType);
    }

    public String getProductId() {

        System.out.print("Enter the product ID: ");
        System.out.flush();
        return scanner.nextLine();
    }

    public void printMessage(String message) {
        System.out.println(message);
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
