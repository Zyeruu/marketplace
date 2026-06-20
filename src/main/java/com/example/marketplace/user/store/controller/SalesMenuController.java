package main.java.com.example.marketplace.user.store.controller;

import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.user.store.dto.SalesMenuResponse;
import main.java.com.example.marketplace.user.store.service.SalesMenuService;
import main.java.com.example.marketplace.user.store.view.SalesMenuView;

public final class SalesMenuController {

    private final SalesMenuView view;
    private final SalesMenuService service;
    private final Session session;

    public SalesMenuController(SalesMenuView view, SalesMenuService service, Session session) {
        this.view = view;
        this.service = service;
        this.session = session;
    }

    public void printTaxReceiptList() {

        String email = session.getLoggedUserEmail();

        try {
            SalesMenuResponse salesMenuResponse = service.findTaxReceiptListByEmail(email);
            view.printSales(salesMenuResponse);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }

    public void printTaxReceipt() {

        String email = session.getLoggedUserEmail();
        String orderId = view.getOrderId();

        try {
            SalesMenuResponse salesMenuResponse = service.findTaxReceiptByEmailAndOrderId(email, orderId);
            view.printSales(salesMenuResponse);
        }
        catch (NotFoundException e) {
            view.printMessage(e.getMessage());
        }
    }
}