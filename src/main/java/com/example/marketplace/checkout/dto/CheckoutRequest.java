package main.java.com.example.marketplace.checkout.dto;

import main.java.com.example.marketplace.checkout.model.PaymentMethod;

public class CheckoutRequest {

    private PaymentMethod paymentMethod;
    private float totalCost;
    private float shipping;

    public CheckoutRequest(float totalCost, float shipping) {
        this.totalCost = totalCost;
        this.shipping = shipping;
    }

    // Getters
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public float getShipping() {
        return shipping;
    }

    // Setters
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
