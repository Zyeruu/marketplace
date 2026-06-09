package main.java.com.example.marketplace.buyer.dto;

public final class CartRequest {

    private final String id;
    private final int quantity;

    public CartRequest(String id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    // Getters
    public String getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }
}
