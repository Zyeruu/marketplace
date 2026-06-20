package main.java.com.example.marketplace.user.store.repository;

import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

public final class SalesMenuRepository {

    private final DataBase dataBase;

    public SalesMenuRepository(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public List<TaxReceipt> findByEmail(String email) {

        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        return user.getSalesMenuTaxReceiptList().stream()
                .map(TaxReceipt::new)
                .collect(Collectors.toList());
    }

    public TaxReceipt findByEmailAndOrderId(String email, String orderId) {

        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        if (user.getSalesMenuTaxReceiptList().isEmpty())
            throw new NotFoundException("[!] You have no sales history.");

        return user.getSalesMenuTaxReceiptList().stream()
                .filter(taxReceipt -> taxReceipt.getOrderId().equals(orderId))
                .findFirst()
                .orElse(null);
    }
}
