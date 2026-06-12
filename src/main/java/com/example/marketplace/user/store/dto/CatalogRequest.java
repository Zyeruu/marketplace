package main.java.com.example.marketplace.user.store.dto;

public final class CatalogRequest {

    private final String id;
    private final int quantity;
    private final float price;

    public CatalogRequest(String id, int quantity) {
        this.id = id;
        this.quantity = quantity;
        this.price = 0;
    }

    public CatalogRequest(String id, float price) {
        this.id = id;
        this.price = price;
        this.quantity = 0;
    }

    // Getters
    public String getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }
}
