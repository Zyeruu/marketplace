package main.java.com.example.marketplace.buyer.repository;

import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.shared.session.BuyerSession;

import java.util.ArrayList;
import java.util.List;

public class OrdersMenuRepository {

    public List<TaxReceipt> findByEmail() {

        String email = BuyerSession.getEmail();

        if (!DataBase.existsBuyerByEmail(email))
            throw new NotFoundException("Logged-in user does not exist.");

        List<TaxReceipt> taxReceiptListPointer = DataBase.findBuyerTaxReceiptListByEmail(email);
        List<TaxReceipt> taxReceiptListCopy = new ArrayList<>();

        // Copies the items from taxReceiptListPointer to taxReceiptListCopy
        for (TaxReceipt taxReceipt : taxReceiptListPointer)
            taxReceiptListCopy.add(new TaxReceipt(taxReceipt));

        return taxReceiptListCopy;
    }

    public TaxReceipt findByEmailAndOrderId(String orderId) {

        String email = BuyerSession.getEmail();

        if (!DataBase.existsBuyerByEmail(email))
            throw new NotFoundException("Logged-in user does not exist.");

        TaxReceipt taxReceipt = DataBase.findBuyerTaxReceiptByEmailAndOrderId(email, orderId);

        if (taxReceipt == null)
            throw new NotFoundException("The order with ID \"" + orderId + "\" was not found.");

        return new TaxReceipt(taxReceipt);
    }
}
