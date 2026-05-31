package main.java.com.example.marketplace.seller.repository;

import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.payment.model.TaxReceipt;
import main.java.com.example.marketplace.seller.model.Seller;

import java.util.ArrayList;
import java.util.List;

public final class SalesMenuRepository {

    public List<TaxReceipt> emailExists(String email) {

        List<Seller> sellerList = DataBase.getSellerList();

        if (!sellerList.isEmpty())
            for (Seller seller : sellerList)
                if (seller.getEmail().equals(email)) {

                    List<TaxReceipt> taxReceiptListPointer = seller.getStore().getSalesMenu().getTaxReceiptsList();
                    List<TaxReceipt> taxReceiptListCopy = new ArrayList<>();

                    // Copies the items from taxReceiptListPointer to taxReceiptListCopy
                    for (TaxReceipt taxReceipt : taxReceiptListPointer)
                        taxReceiptListCopy.add(new TaxReceipt(taxReceipt));

                    return taxReceiptListCopy;
                }
        return null;
    }
}
