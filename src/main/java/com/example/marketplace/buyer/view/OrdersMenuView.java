package main.java.com.example.marketplace.buyer.view;

import main.java.com.example.marketplace.checkout.model.OrderedProduct;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public final class OrdersMenuView {

    Scanner scanner = new Scanner(System.in);

    public void printOrders(List<TaxReceipt> taxReceiptList) {

        for (TaxReceipt taxReceipt : taxReceiptList)
            printOrder(taxReceipt);

        System.out.println("-----------------------------------");
        System.out.println("Total Orders: " + taxReceiptList.size());
        System.out.println("-----------------------------------");
    }

    public void printOrder(TaxReceipt taxReceipt) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = taxReceipt.getDateTime().format(formatter);

        System.out.println("\n----------| TAX RECEIPT |----------");
        System.out.println("Order ID: " + taxReceipt.getOrderId());
        System.out.println("Seller name: " + taxReceipt.getSellerName());
        System.out.println("Buyer name: " + taxReceipt.getBuyerName());
        System.out.println("Date: " + formattedDate);
        if (taxReceipt.getShipping() > 0)
            System.out.printf("Shipping: R$%.2f\n", taxReceipt.getShipping());
        else
            System.out.println("Shipping: Free");
        System.out.printf("Total: R$%.2f\n", taxReceipt.getTotalCost());

        System.out.println("\n-----------| PRODUCTS |-----------");

        for (OrderedProduct orderedProduct : taxReceipt.getOrderedProductList()) {

            System.out.println("Name: " + orderedProduct.getName());
            System.out.println("ID: " + orderedProduct.getId());
            System.out.println("Type: " + orderedProduct.getType().getName());
            System.out.println("Quantity: " + orderedProduct.getQuantity());
            System.out.printf("Unit price: R$%.2f\n", orderedProduct.getUnitPrice());
            System.out.printf("Total: R$%.2f\n\n", orderedProduct.getTotalCost());
        }
    }

    public String getOrderId() {

        System.out.print("Enter the order ID: ");
        System.out.flush();
        return scanner.nextLine();
    }

    public void printMessage(String message) {
        System.out.println();
        System.out.println(message);
    }
}
