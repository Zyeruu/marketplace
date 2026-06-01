package main.java.com.example.marketplace.seller.repository;

import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.payment.model.TaxReceipt;
import main.java.com.example.marketplace.seller.model.Seller;

import java.util.ArrayList;
import java.util.List;

public final class SalesMenuRepository {

    public List<TaxReceipt> findByEmailAndCnpj(String email, String cnpj) {

        if (!DataBase.existsSellerByEmail(email))
            throw new NotFoundException("Logged-in user does not exist!");

        Seller seller = DataBase.findSellerByEmail(email);

        if (!seller.getStore().getCnpj().equals(cnpj))
            throw new NotFoundException("CNPJ does not match the registered email address!");

        List<TaxReceipt> taxReceiptListPointer = seller.getStore().getSalesMenu().getTaxReceiptsList();
        List<TaxReceipt> taxReceiptListCopy = new ArrayList<>();

        // Copies the items from taxReceiptListPointer to taxReceiptListCopy
        for (TaxReceipt taxReceipt : taxReceiptListPointer)
            taxReceiptListCopy.add(new TaxReceipt(taxReceipt));

        return taxReceiptListCopy;
    }

    public TaxReceipt findByEmailAndCnpjAndOrderId(String email, String cnpj, int orderId) {

        if (!DataBase.existsSellerByEmail(email))
            throw new NotFoundException("Logged-in user does not exist!");

        Seller seller = DataBase.findSellerByEmail(email);

        if (!seller.getStore().getCnpj().equals(cnpj))
            throw new NotFoundException("CNPJ does not match the registered email address!");

        List<TaxReceipt> taxReceiptListPointer = seller.getStore().getSalesMenu().getTaxReceiptsList();

        for (TaxReceipt taxReceipt : taxReceiptListPointer)
            if (taxReceipt.getOrderId() == orderId)
                return new TaxReceipt(taxReceipt);

        throw new NotFoundException("Order \"" + orderId + "\" not found!");
    }
}
