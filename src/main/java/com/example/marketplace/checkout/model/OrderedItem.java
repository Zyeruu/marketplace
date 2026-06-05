package main.java.com.example.marketplace.checkout.model;

import main.java.com.example.marketplace.shared.enums.ItemType;

public final class OrderedItem {

    private String name;
    private String id;
    private ItemType type;
    private int quantity;
    private float unitPrice;
    private float totalCost;

    public OrderedItem(String name, String id, ItemType type, int quantity, float unitPrice) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalCost = quantity * unitPrice;
    }

    // Creates a copy of orderedItemPointer
    public OrderedItem(OrderedItem orderedItemPointer) {

        this.name = orderedItemPointer.name;
        this.id = orderedItemPointer.id;
        this.type = orderedItemPointer.type;
        this.quantity = orderedItemPointer.quantity;
        this.unitPrice = orderedItemPointer.unitPrice;
        this.totalCost = orderedItemPointer.totalCost;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public ItemType getType() {
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
