package main.java.com.example.marketplace.seller.view;

import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.payment.model.OrderedItem;
import main.java.com.example.marketplace.payment.model.TaxReceipt;
import main.java.com.example.marketplace.seller.controller.SalesMenuController;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public final class SalesMenuView {

    private SalesMenuController controller;

    Scanner scanner = new Scanner(System.in);

    public String getOrderId() {

        System.out.println("Enter the order ID: ");
        return scanner.nextLine();
    }

    public void printSalesMenu(List<TaxReceipt> taxReceiptList) {

        System.out.println("\n-------- SALES MENU --------\n");

        for (TaxReceipt taxReceipt : taxReceiptList)
            printOrder(taxReceipt);

        System.out.println("Total Sales: " + taxReceiptList.size());
    }

    public void printOrder(TaxReceipt taxReceipt) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        System.out.println("\n-------- TAX RECEIPT --------\n");
        System.out.println("Order ID: " + taxReceipt.getOrderId());
        System.out.println("Buyer name: " + taxReceipt.getBuyerName());
        System.out.println("Seller name: " + taxReceipt.getSellerName());
        String formattedDate = taxReceipt.getDateTime().format(formatter);
        System.out.println("Date: " + formattedDate);
        System.out.printf("Total cost: R$%.2f\n", taxReceipt.getTotalCost());
        if (taxReceipt.getShipping() == 0)
            System.out.println("Shipping: Free");
        else
            System.out.printf("Shipping: R$%.2f\n", taxReceipt.getShipping());

        for (OrderedItem orderedItem : taxReceipt.getOrderedItemsList()) {

            System.out.println("\n-------- ITEMS --------\n");
            System.out.println("Name: " + orderedItem.getName());
            System.out.println("ID: " + orderedItem.getId());
            System.out.println("Type: " + orderedItem.getType().name());
            System.out.println("Quantity: " + orderedItem.getQuantity());
            System.out.printf("Unit price: R$%.2f\n", orderedItem.getUnitPrice());
            System.out.printf("Total cost: R$%.2f\n", orderedItem.getTotalCost());
        }
    }

    public void printException(NotFoundException e) {
        System.out.println(e.getMessage());
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
