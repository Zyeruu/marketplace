package main.java.com.example.marketplace.seller.controller;

import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.seller.repository.SalesMenuRepository;
import main.java.com.example.marketplace.seller.view.SalesMenuView;

import java.util.List;

public final class SalesMenuController {

    private SalesMenuRepository repository;
    private SalesMenuView view;

    public void printTaxReceiptList() {

        try {
            List<TaxReceipt> taxReceiptList = repository.findByEmailAndCnpj();

            if (taxReceiptList.isEmpty())
                view.printMessage("No sales history.");
            else
                view.printSales(taxReceiptList);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void printTaxReceipt() {

        String orderId = view.getOrderId();

        try {
            TaxReceipt taxReceipt = repository.findByEmailAndCnpjAndOrderId(orderId);
            view.printOrder(taxReceipt);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }
}