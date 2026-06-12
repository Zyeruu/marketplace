package main.java.com.example.marketplace.user.store.repository;

import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.user.store.dto.SalesMenuResponse;
import main.java.com.example.marketplace.user.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class SalesMenuRepository {

    private final DataBase dataBase;
    private final Session session;

    public SalesMenuRepository(DataBase dataBase, Session session) {
        this.dataBase = dataBase;
        this.session = session;
    }

    public SalesMenuResponse findByEmailAndCnpj() {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getSalesMenuTaxReceiptList().isEmpty())
            throw new NotFoundException("[!] No sales history.");

        List<TaxReceipt> taxReceiptListCopy = user.getSalesMenuTaxReceiptList().stream()
                .map(TaxReceipt::new)
                .collect(Collectors.toList());

        return new SalesMenuResponse(taxReceiptListCopy, user.getTotalRevenue());
    }

    public SalesMenuResponse findByOrderId(String orderId) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        TaxReceipt taxReceiptCopy = user.getSalesMenuTaxReceiptList().stream()
                .filter(taxReceipt -> taxReceipt.getOrderId().equals(orderId))
                .findFirst()
                .orElse(null);

        if (taxReceiptCopy == null)
            throw new NotFoundException("[!] Order with ID \"" + orderId + "\" not found.");

        List<TaxReceipt> taxReceiptListCopy = new ArrayList<>();
        taxReceiptListCopy.add(taxReceiptCopy);

        return new SalesMenuResponse(taxReceiptListCopy, taxReceiptCopy.getTotalCost());
    }
}
