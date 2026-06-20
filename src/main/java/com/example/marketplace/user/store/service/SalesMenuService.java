package main.java.com.example.marketplace.user.store.service;

import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.user.store.dto.SalesMenuResponse;
import main.java.com.example.marketplace.user.store.repository.SalesMenuRepository;

import java.util.ArrayList;
import java.util.List;

public final class SalesMenuService {

    private final SalesMenuRepository repository;

    public SalesMenuService(SalesMenuRepository repository) {
        this.repository = repository;
    }

    public SalesMenuResponse findTaxReceiptListByEmail(String email) {

        List<TaxReceipt> taxReceiptList = repository.findByEmail(email);

        if (taxReceiptList.isEmpty())
            throw new NotFoundException("[!] No sales history.");

        float totalRevenue = 0;

        for (TaxReceipt taxReceipt : taxReceiptList)
            totalRevenue += taxReceipt.getTotalCost();

        return new SalesMenuResponse(taxReceiptList, totalRevenue);
    }

    public SalesMenuResponse findTaxReceiptByEmailAndOrderId(String email, String orderId) {

        TaxReceipt taxReceipt = repository.findByEmailAndOrderId(email, orderId);

        if (taxReceipt == null)
            throw new NotFoundException("[!] Order with ID \"" + orderId + "\" not found.");

        List<TaxReceipt> taxReceiptList = new ArrayList<>();
        taxReceiptList.add(taxReceipt);

        return new SalesMenuResponse(taxReceiptList, taxReceipt.getTotalCost());
    }
}
