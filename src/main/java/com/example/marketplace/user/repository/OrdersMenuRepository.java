package main.java.com.example.marketplace.user.repository;

import main.java.com.example.marketplace.user.model.User;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.session.Session;

import java.util.List;
import java.util.stream.Collectors;

public final class OrdersMenuRepository {

    private final DataBase dataBase;
    private final Session session;

    public OrdersMenuRepository(DataBase dataBase, Session session) {
        this.dataBase = dataBase;
        this.session = session;
    }

    public List<TaxReceipt> findByEmail() {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        List<TaxReceipt> taxReceiptListCopy = user.getOrdersMenuTaxReceiptList().stream().map(TaxReceipt::new).collect(Collectors.toList());

        if (taxReceiptListCopy.isEmpty())
            throw new NotFoundException("[!] No purchase history.");

        return taxReceiptListCopy;
    }

    public TaxReceipt findByEmailAndOrderId(String orderId) {

        String email = session.getEmail();
        User user = dataBase.findUserByEmail(email);

        if (user == null)
            throw new NotFoundException("[!] User not found.");

        TaxReceipt taxReceipt = user.getOrdersMenuTaxReceiptList().stream()
                .filter(product -> product.getOrderId().equals(orderId))
                .findFirst()
                .orElse(null);

        if (taxReceipt == null)
            throw new NotFoundException("[!] Order with ID \"" + orderId + "\" not found.");

        return new TaxReceipt(taxReceipt);
    }
}
