package main.java.com.example.marketplace.seller.view;

import main.java.com.example.marketplace.payment.model.OrderedItem;
import main.java.com.example.marketplace.payment.model.TaxReceipt;
import main.java.com.example.marketplace.seller.controller.SalesMenuController;

import java.time.format.DateTimeFormatter;
import java.util.List;

public final class SalesMenuView {

    private SalesMenuController controller;

    public void printSalesMenu(String email) {

        List<TaxReceipt> taxReceiptList = controller.searchUser(email);

        if (!taxReceiptList.isEmpty()) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            for (TaxReceipt taxReceipt : taxReceiptList) {

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
            System.out.println("Total items sold: " + taxReceiptList.size());
        }
    }
}
