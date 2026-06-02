package main.java.com.example.marketplace.seller.model;

public final class Store {

    private String name;
    private String cnpj;
    private Catalog catalog;
    private SalesMenu salesMenu;

    public Store(String name, String cnpj) {
        this.name = name;
        this.cnpj = cnpj;
        this.catalog = new Catalog();
        this.salesMenu = new SalesMenu();
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
