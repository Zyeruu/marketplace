package main.java.com.example.marketplace.seller.model;

public final class Store {

    private String name;
    private String cnpj;
    private Address address;
    private Catalog catalog;
    private SalesMenu salesMenu;

    public Store(String name, String cnpj, Address address, Catalog catalog, SalesMenu salesMenu) {
        this.name = name;
        this.cnpj = cnpj;
        this.address = address;
        this.catalog = catalog;
        this.salesMenu = salesMenu;
    }

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
