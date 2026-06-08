package main.java.com.example.marketplace.seller.repository;

import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.shared.session.SellerSession;

import java.util.ArrayList;
import java.util.List;

public final class SalesMenuRepository {

    public List<TaxReceipt> findByEmailAndCnpj() {

        String email = SellerSession.getEmail();

        if (!DataBase.existsSellerByEmail(email))
            throw new NotFoundException("[!] Logged-in user does not exist.");

        List<TaxReceipt> taxReceiptListPointer = DataBase.findBuyerTaxReceiptListByEmail(email);
        List<TaxReceipt> taxReceiptListCopy = new ArrayList<>();

        // Copies the products from taxReceiptListPointer to taxReceiptListCopy
        for (TaxReceipt taxReceipt : taxReceiptListPointer)
            taxReceiptListCopy.add(new TaxReceipt(taxReceipt));

        return taxReceiptListCopy;
    }

    public TaxReceipt findByEmailAndCnpjAndOrderId(String orderId) {

        String email = SellerSession.getEmail();

        if (!DataBase.existsSellerByEmail(email))
            throw new NotFoundException("[!] Logged-in user does not exist.");

        TaxReceipt taxReceipt = DataBase.findSellerTaxReceiptByEmailAndOrderId(email, orderId);

        if (taxReceipt == null)
            throw new NotFoundException("[!] The order with ID \"" + orderId + "\" was not found.");

        return new TaxReceipt(taxReceipt);
    }
}
