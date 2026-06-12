package main.java.com.example.marketplace.user.store.dto;

public class StoreResponse {

    private final String storeName;
    private final String cnpj;
    private final int totalSales;
    private final float totalRevenue;

    public StoreResponse(String storeName, String cnpj, int totalSales, float totalRevenue) {
        this.storeName = storeName;
        this.cnpj = cnpj;
        this.totalSales = totalSales;
        this.totalRevenue = totalRevenue;
    }

    // Getters
    public String getStoreName() {
        return storeName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public float getTotalRevenue() {
        return totalRevenue;
    }
}
