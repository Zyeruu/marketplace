package main.java.com.example.marketplace.payment.model;

import main.java.com.example.marketplace.shared.ItemType;

public final class OrderedItem {

    private String name;
    private String id;
    private ItemType type;
    private int quantity;
    private float unitPrice;
    private float totalCost;

    // Creates a copy of itemOrderedPointer
    public OrderedItem(OrderedItem itemOrderedPointer) {

        this.name = itemOrderedPointer.name;
        this.id = itemOrderedPointer.id;
        this.type = itemOrderedPointer.type;
        this.quantity = itemOrderedPointer.quantity;
        this.unitPrice = itemOrderedPointer.unitPrice;
        this.totalCost = itemOrderedPointer.totalCost;
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
