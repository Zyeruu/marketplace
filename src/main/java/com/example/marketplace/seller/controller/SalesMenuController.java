package main.java.com.example.marketplace.seller.controller;

import main.java.com.example.marketplace.payment.model.TaxReceipt;
import main.java.com.example.marketplace.seller.repository.SalesMenuRepository;

import java.util.List;

public final class SalesMenuController {

    private SalesMenuRepository repository;

    public List<TaxReceipt> findByEmailAndCnpj(String email, String cnpj) {

        try {
            return repository.findByEmailAndCnpj(email, cnpj);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public TaxReceipt findByEmailAndCnpjAndOrderId(String email, String cnpj, int orderId) {

        try {
            return repository.findByEmailAndCnpjAndOrderId(email, cnpj, orderId);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}