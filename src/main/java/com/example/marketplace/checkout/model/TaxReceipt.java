package main.java.com.example.marketplace.checkout.model;

import main.java.com.example.marketplace.checkout.dto.CheckoutRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class TaxReceipt {
    private String orderId;
    private String buyerName;
    private String sellerName;
    private PaymentMethod paymentMethod;
    private LocalDateTime dateTime;
    private float totalCost;
    private float shipping;
    private List<OrderedItem> orderedItemsList;

    public TaxReceipt(String orderId, String buyerName, String sellerName, CheckoutRequest checkoutRequest, List<OrderedItem> orderedItemsList) {
        this.orderId = orderId;
        this.buyerName = buyerName;
        this.sellerName = sellerName;
        this.paymentMethod = checkoutRequest.getPaymentMethod();
        this.dateTime = LocalDateTime.now();
        this.totalCost = checkoutRequest.getTotalCost();
        this.shipping = checkoutRequest.getShipping();
        this.orderedItemsList = orderedItemsList;
    }

    // Creates a copy of taxReceiptPointer
    public TaxReceipt(TaxReceipt taxReceiptPointer) {

        this.orderId = taxReceiptPointer.orderId;
        this.buyerName = taxReceiptPointer.buyerName;
        this.sellerName = taxReceiptPointer.sellerName;
        this.paymentMethod = taxReceiptPointer.paymentMethod;
        this.dateTime = taxReceiptPointer.dateTime;
        this.totalCost = taxReceiptPointer.totalCost;
        this.shipping = taxReceiptPointer.shipping;

        List<OrderedItem> orderedItemsListCopy = new ArrayList<>();

        // Copies the items of taxReceiptPointer's orderedItemsList to orderedItemsListCopy
        for (OrderedItem itemOrdered : taxReceiptPointer.getOrderedItemsList())
            orderedItemsListCopy.add(new OrderedItem(itemOrdered));
    }

    // Getters
    public List<OrderedItem> getOrderedItemsList() {
        return orderedItemsList;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getSellerName() {
        return sellerName;
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
