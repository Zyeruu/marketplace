package main.java.com.example.marketplace.buyer.controller;

import main.java.com.example.marketplace.buyer.repository.OrdersMenuRepository;
import main.java.com.example.marketplace.buyer.view.OrdersMenuView;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.exceptions.NotFoundException;

import java.util.List;

public final class OrdersMenuController {

    private final OrdersMenuRepository repository = new OrdersMenuRepository();
    private final OrdersMenuView view = new OrdersMenuView();

    public void printOrders() {

        try {
            List<TaxReceipt> taxReceiptList = repository.findByEmail();

            if (taxReceiptList.isEmpty())
                view.printMessage("No purchase history.");
            else
                view.printOrders(taxReceiptList);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void printOrder() {

        String orderId = view.getOrderId();

        try {
            TaxReceipt taxReceipt = repository.findByEmailAndOrderId(orderId);
            view.printOrder(taxReceipt);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }
}
