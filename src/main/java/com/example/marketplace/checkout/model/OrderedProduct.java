package main.java.com.example.marketplace.checkout.model;

import main.java.com.example.marketplace.shared.enums.ProductType;

public final class OrderedProduct {

    private final String name;
    private final String id;
    private final ProductType type;
    private final int quantity;
    private final float unitPrice;
    private final float totalCost;

    public OrderedProduct(String name, String id, ProductType type, int quantity, float unitPrice) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalCost = quantity * unitPrice;
    }

    // Creates a copy of orderedProductPointer
    public OrderedProduct(OrderedProduct orderedProductPointer) {

        this.name = orderedProductPointer.name;
        this.id = orderedProductPointer.id;
        this.type = orderedProductPointer.type;
        this.quantity = orderedProductPointer.quantity;
        this.unitPrice = orderedProductPointer.unitPrice;
        this.totalCost = orderedProductPointer.totalCost;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public ProductType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public float getTotalCost() {
        return totalCost;
    }
}
