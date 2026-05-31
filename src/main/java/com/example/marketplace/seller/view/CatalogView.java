package main.java.com.example.marketplace.seller.view;

import main.java.com.example.marketplace.seller.controller.CatalogController;
import main.java.com.example.marketplace.seller.dto.CatalogResponse;
import main.java.com.example.marketplace.shared.ItemType;
import main.java.com.example.marketplace.seller.model.Product;

public final class CatalogView {

    CatalogController controller;

    public void printCatalog(String cnpj) {

        CatalogResponse catalog = controller.findByCnpj(cnpj);

        if (catalog != null) {
            if (!catalog.getProductList().isEmpty()) {

                System.out.println("-------- ITEMS --------\n\n");

                for (Product p : catalog.getProductList())
                    if (p.getType() == ItemType.FOOD) {
                        System.out.println("Name: " + p.getName());
                        System.out.println("ID: " + p.getId());
                        System.out.println("Type: " + p.getType().name());
                        System.out.printf("Unit Price: R$%.2f\n", p.getUnitPrice());
                        System.out.printf("Weight: %.1fKg\n", p.getWeight());
                        System.out.println("Stock: " + p.getStock());
                    }

                for (Product p : catalog.getProductList())
                    if (p.getType() == ItemType.MISCELLANEOUS) {
                        System.out.println("Name: " + p.getName());
                        System.out.println("ID: " + p.getId());
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
            else
                System.out.println("Catalog is empty!");
        }
    }
}
