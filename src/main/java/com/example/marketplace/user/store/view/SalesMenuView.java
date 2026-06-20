package main.java.com.example.marketplace.user.store.view;

import main.java.com.example.marketplace.checkout.model.OrderedProduct;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.shared.utils.Formatter;
import main.java.com.example.marketplace.user.store.dto.SalesMenuResponse;

import java.util.Scanner;

public final class SalesMenuView {

    Scanner scanner = new Scanner(System.in);

    public String getOrderId() {

        System.out.print("Enter the order ID: ");
        System.out.flush();
        return scanner.nextLine();
    }

    public void printSales(SalesMenuResponse salesMenuResponse) {

        for (TaxReceipt taxReceipt : salesMenuResponse.taxReceiptList())
            printOrder(taxReceipt);

        System.out.println("-----------------------------------");
        System.out.println("Total Sales: " + salesMenuResponse.taxReceiptList().size());
        System.out.printf("Total Revenue: R$%.2f\n", salesMenuResponse.revenue());
        System.out.println("-----------------------------------\n");
    }

    public void printOrder(TaxReceipt taxReceipt) {

        String formattedDate = Formatter.formatDateTime(taxReceipt.getDateTime());

        System.out.println("----------| TAX RECEIPT |----------");
        System.out.println("Order ID: " + taxReceipt.getOrderId());
        System.out.println("Buyer name: " + taxReceipt.getBuyerName());
        System.out.println("Seller's store name: " + taxReceipt.getSellerName());
        System.out.println("Date: " + formattedDate);
        System.out.printf("Total: R$%.2f\n\n", taxReceipt.getTotalCost());

        System.out.println("-----------| PRODUCTS |-----------");

        for (OrderedProduct orderedProduct : taxReceipt.getOrderedProductList()) {

            System.out.println("Name: " + orderedProduct.getName());
            System.out.println("ID: " + orderedProduct.getId());
            System.out.println("Type: " + orderedProduct.getType().getName());
            System.out.println("Quantity: " + orderedProduct.getQuantity());
            System.out.printf("Unit price: R$%.2f\n", orderedProduct.getUnitPrice());
            System.out.printf("Total: R$%.2f\n\n", orderedProduct.getTotalCost());
        }
    }

    public void printMessage(String message) {
        System.out.println("\n" + message + "\n");
    }
}
