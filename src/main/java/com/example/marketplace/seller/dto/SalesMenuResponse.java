package main.java.com.example.marketplace.seller.dto;

import main.java.com.example.marketplace.checkout.model.TaxReceipt;

import java.util.List;

public record SalesMenuResponse(List<TaxReceipt> taxReceiptList, float revenue) {

}
