package main.java.com.example.marketplace.seller.repository;

import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.seller.model.Seller;
import main.java.com.example.marketplace.shared.session.Session;

import java.util.ArrayList;
import java.util.List;

public final class SalesMenuRepository {

    private final DataBase dataBase;
    private final Session session;

    public SalesMenuRepository(DataBase dataBase, Session session) {
        this.dataBase = dataBase;
        this.session = session;
    }

    public List<TaxReceipt> findByEmailAndCnpj() {

        String email = session.getEmail();
        Seller seller = dataBase.findSellerByEmail(email);

        if (seller == null)
            throw new NotFoundException("[!] User not found.");

        List<TaxReceipt> taxReceiptListPointer = seller.getTaxReceiptList();

        if (taxReceiptListPointer.isEmpty())
            throw new NotFoundException("[!] No sales history.");

        List<TaxReceipt> taxReceiptListCopy = new ArrayList<>();

        for (TaxReceipt taxReceipt : taxReceiptListPointer)
            taxReceiptListCopy.add(new TaxReceipt(taxReceipt));

        return taxReceiptListCopy;
    }

    public TaxReceipt findByEmailAndCnpjAndOrderId(String orderId) {

        String email = session.getEmail();
        Seller seller = dataBase.findSellerByEmail(email);

        if (seller == null)
            throw new NotFoundException("[!] User not found.");

        TaxReceipt taxReceiptCopy = null;

        for (TaxReceipt taxReceipt : seller.getTaxReceiptList())
            if (taxReceipt.getOrderId().equals(orderId)) {
                taxReceiptCopy = new TaxReceipt(taxReceipt);
                break;
            }

        if (taxReceiptCopy == null)
            throw new NotFoundException("[!] Order with ID \"" + orderId + "\" not found.");

        return taxReceiptCopy;
    }
}
