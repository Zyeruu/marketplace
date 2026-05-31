package main.java.com.example.marketplace.seller.model;

public final class Store {

    private String name;
    private String cnpj;
    private Address address;
    private Catalog catalog;
    private SalesMenu salesMenu;

    // Getters
    public String getCnpj() {
        return cnpj;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public SalesMenu getSalesMenu() {
        return salesMenu;
    }
}
