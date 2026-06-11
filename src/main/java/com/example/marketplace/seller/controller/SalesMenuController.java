package main.java.com.example.marketplace.seller.controller;

import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.seller.dto.SalesMenuResponse;
import main.java.com.example.marketplace.seller.repository.SalesMenuRepository;
import main.java.com.example.marketplace.seller.view.SalesMenuView;

public final class SalesMenuController {

    private final SalesMenuView view;
    private final SalesMenuRepository repository;

    public SalesMenuController(SalesMenuView view, SalesMenuRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void printTaxReceiptList() {

        try {
            SalesMenuResponse salesMenuResponse = repository.findByEmailAndCnpj();
            view.printSales(salesMenuResponse);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void printTaxReceipt() {

        String orderId = view.getOrderId();

        try {
            SalesMenuResponse salesMenuResponse = repository.findByOrderId(orderId);
            view.printSales(salesMenuResponse);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }
}