package main.java.com.example.marketplace.seller.controller;

import main.java.com.example.marketplace.payment.model.TaxReceipt;
import main.java.com.example.marketplace.seller.repository.SalesMenuRepository;

import java.util.List;

public final class SalesMenuController {

    private SalesMenuRepository repository;

    public List<TaxReceipt> searchUser(String email) {

        List<TaxReceipt> taxReceiptList = repository.emailExists(email);

        if (taxReceiptList == null)
            System.out.println("Menu not found!");

        return taxReceiptList;

    }
}
