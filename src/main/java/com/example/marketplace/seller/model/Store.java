package main.java.com.example.marketplace.seller.model;

public final class Store {

    private final String name;
    private final String cnpj;
    private final Catalog catalog;
    private final SalesMenu salesMenu;

    public Store(String name, String cnpj) {
        this.name = name;
        this.cnpj = cnpj;
        this.catalog = new Catalog();
        this.salesMenu = new SalesMenu();
    }

    // Getters
    public String getName() {
        return name;
    }

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
