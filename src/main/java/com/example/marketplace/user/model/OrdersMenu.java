package main.java.com.example.marketplace.user.model;

import main.java.com.example.marketplace.checkout.model.OrderedProduct;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.shared.model.Review;

import java.util.ArrayList;
import java.util.List;

public final class OrdersMenu {

    private final List<TaxReceipt> taxReceiptList = new ArrayList<>();
    private final List<OrderedProduct> orderedProductList = new ArrayList<>();
    private final List<Review> reviewList = new ArrayList<>();

    // Getters
    public List<TaxReceipt> getTaxReceiptList() {
        return taxReceiptList;
    }

    public List<OrderedProduct> getOrderedProductList() {
        return orderedProductList;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    // Setters
    public void setTaxReceiptList(TaxReceipt taxReceipt) {
        taxReceiptList.add(taxReceipt);
    }

    public void setOrderedProductList(OrderedProduct orderedProduct) {
        orderedProductList.add(orderedProduct);
    }
}
