package main.java.com.example.marketplace.buyer.repository;

import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.session.Session;

import java.util.ArrayList;
import java.util.List;

public final class OrdersMenuRepository {

    private final DataBase dataBase;
    private final Session session;

    public OrdersMenuRepository(DataBase dataBase, Session session) {
        this.dataBase = dataBase;
        this.session = session;
    }

    public List<TaxReceipt> findByEmail() {

        String email = session.getEmail();
        Buyer buyer = dataBase.findBuyerByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] User not found.");

        List<TaxReceipt> taxReceiptListPointer = buyer.getTaxReceiptList();
        List<TaxReceipt> taxReceiptListCopy = new ArrayList<>();

        // Copies the products from taxReceiptListPointer to taxReceiptListCopy
        for (TaxReceipt taxReceipt : taxReceiptListPointer)
            taxReceiptListCopy.add(new TaxReceipt(taxReceipt));

        if (taxReceiptListCopy.isEmpty())
            throw new NotFoundException("[!] No purchase history.");

        return taxReceiptListCopy;
    }

    public TaxReceipt findByEmailAndOrderId(String orderId) {

        String email = session.getEmail();
        Buyer buyer = dataBase.findBuyerByEmail(email);

        if (buyer == null)
            throw new NotFoundException("[!] User not found.");

        TaxReceipt taxReceipt = findTaxReceiptByOrderId(buyer, orderId);

        if (taxReceipt == null)
            throw new NotFoundException("[!] The order with ID \"" + orderId + "\" was not found.");

        return new TaxReceipt(taxReceipt);
    }

    public TaxReceipt findTaxReceiptByOrderId(Buyer buyer, String orderId) {

        for (TaxReceipt taxReceipt : buyer.getTaxReceiptList())
            if (taxReceipt.getOrderId().equals(orderId))
                return taxReceipt;
        return null;
    }
}
