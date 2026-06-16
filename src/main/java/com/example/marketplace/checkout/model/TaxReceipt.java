package main.java.com.example.marketplace.checkout.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public final class TaxReceipt {
    private final String orderId;
    private final String sellerName;
    private final String storeCnpj;
    private final String buyerName;
    private final PaymentMethod paymentMethod;
    private final LocalDateTime dateTime;
    private final float totalCost;
    private final Float shipping;
    private final List<OrderedProduct> orderedProductList;

    public TaxReceipt(String orderId, String sellerName, String storeCnpj, String buyerName, PaymentMethod paymentMethod, float totalCost, Float shipping, List<OrderedProduct> orderedProductList) {
        this.orderId = orderId;
        this.sellerName = sellerName;
        this.storeCnpj = storeCnpj;
        this.buyerName = buyerName;
        this.paymentMethod = paymentMethod;
        this.dateTime = LocalDateTime.now();
        this.totalCost = totalCost;
        this.shipping = shipping;
        this.orderedProductList = orderedProductList;
    }

    public TaxReceipt(TaxReceipt taxReceiptPointer) {

        this.orderId = taxReceiptPointer.orderId;
        this.sellerName = taxReceiptPointer.sellerName;
        this.storeCnpj = taxReceiptPointer.storeCnpj;
        this.buyerName = taxReceiptPointer.buyerName;
        this.paymentMethod = taxReceiptPointer.paymentMethod;
        this.dateTime = taxReceiptPointer.dateTime;
        this.totalCost = taxReceiptPointer.totalCost;
        this.shipping = taxReceiptPointer.shipping;
        this.orderedProductList = taxReceiptPointer.getOrderedProductList().stream()
                .map(OrderedProduct::new)
                .collect(Collectors.toList());
    }

    // Getters
    public List<OrderedProduct> getOrderedProductList() {
        return orderedProductList;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getStoreCnpj() {
        return storeCnpj;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public float getShipping() {
        return shipping;
    }
}
