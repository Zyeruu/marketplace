package main.java.com.example.marketplace.seller.dto;

public class CatalogRequest {

    private String id;
    private int quantity;

    public CatalogRequest(String id, int quantity) {
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
