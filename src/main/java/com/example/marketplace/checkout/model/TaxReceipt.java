package main.java.com.example.marketplace.checkout.model;

import main.java.com.example.marketplace.checkout.dto.CheckoutRequest;
import main.java.com.example.marketplace.shared.utils.IdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class TaxReceipt {
    private final String orderId;
    private final String sellerName;
    private final String buyerName;
    private final PaymentMethod paymentMethod;
    private final LocalDateTime dateTime;
    private final float totalCost;
    private final float shipping;
    private final List<OrderedProduct> orderedProductList;

    public TaxReceipt(String sellerName, String buyerName, CheckoutRequest checkoutRequest, List<OrderedProduct> orderedProductList) {
        this.orderId = IdGenerator.generateOrderId();
        this.sellerName = sellerName;
        this.buyerName = buyerName;
        this.paymentMethod = checkoutRequest.getPaymentMethod();
        this.dateTime = LocalDateTime.now();
        this.totalCost = checkoutRequest.getTotalCost();
        this.shipping = checkoutRequest.getShipping();
        this.orderedProductList = orderedProductList;
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

        List<OrderedProduct> orderedProductListCopy = new ArrayList<>();

        // Copies the products of taxReceiptPointer's orderedProductList to orderedProductListCopy
        for (OrderedProduct orderedProduct : taxReceiptPointer.getOrderedProductList())
            orderedProductListCopy.add(new OrderedProduct(orderedProduct));
        this.orderedProductList = orderedProductListCopy;
    }

    // Getters
    public List<OrderedProduct> getOrderedProductList() {
        return orderedProductList;
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
