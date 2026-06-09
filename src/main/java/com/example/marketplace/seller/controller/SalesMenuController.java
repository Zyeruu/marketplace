package main.java.com.example.marketplace.seller.controller;

import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.seller.repository.SalesMenuRepository;
import main.java.com.example.marketplace.seller.view.SalesMenuView;

import java.util.List;

public final class SalesMenuController {

    private final SalesMenuRepository repository = new SalesMenuRepository();
    private final SalesMenuView view = new SalesMenuView();

    public void printTaxReceiptList() {

        try {
            List<TaxReceipt> taxReceiptList = repository.findByEmailAndCnpj();
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