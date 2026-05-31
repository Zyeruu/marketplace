package main.java.com.example.marketplace.seller.repository;

import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.payment.model.TaxReceipt;
import main.java.com.example.marketplace.seller.model.Seller;

import java.util.ArrayList;
import java.util.List;

public final class SalesMenuRepository {

    public boolean existsByEmail (String email) {

        List<Seller> sellerList = DataBase.getSellerList();

        if (!sellerList.isEmpty())
            for (Seller seller : sellerList)
                if (seller.getEmail().equals(email))
                    return true;
        return false;
    }

    public Seller findByEmail(String email) {

        List<Seller> sellerList = DataBase.getSellerList();

        for (Seller seller : sellerList)
            if (seller.getEmail().equals(email))
                return seller;
        return null;
    }

    public List<TaxReceipt> findByEmailAndCnpj(String email, String cnpj) {

        if (!existsByEmail(email))
            throw new IllegalArgumentException("The registered email address could not be found!");

        Seller seller = findByEmail(email);

        if (!seller.getStore().getCnpj().equals(cnpj))
            throw new IllegalArgumentException("CNPJ does not match the registered email address!");

        List<TaxReceipt> taxReceiptListPointer = seller.getStore().getSalesMenu().getTaxReceiptsList();
        List<TaxReceipt> taxReceiptListCopy = new ArrayList<>();

        // Copies the items from taxReceiptListPointer to taxReceiptListCopy
        for (TaxReceipt taxReceipt : taxReceiptListPointer)
            taxReceiptListCopy.add(new TaxReceipt(taxReceipt));

        return taxReceiptListCopy;
    }

    public TaxReceipt findByEmailAndCnpjAndOrderId(String email, String cnpj, int orderId) {

        if (!existsByEmail(email))
            throw new IllegalArgumentException("The registered email address could not be found!");

        Seller seller = findByEmail(email);

        if (!seller.getStore().getCnpj().equals(cnpj))
            throw new IllegalArgumentException("CNPJ does not match the registered email address!");

        List<TaxReceipt> taxReceiptListPointer = seller.getStore().getSalesMenu().getTaxReceiptsList();

        for (TaxReceipt taxReceipt : taxReceiptListPointer)
            if (taxReceipt.getOrderId() == orderId)
                return new TaxReceipt(taxReceipt);

        throw new IllegalArgumentException("Order ID " + orderId + " not found!");
    }
}
