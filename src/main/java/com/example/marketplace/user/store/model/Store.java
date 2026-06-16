package main.java.com.example.marketplace.user.store.model;

public final class Store {

    private String name;
    private final String cnpj;
    private Reputation reputation;
    private final Catalog catalog;
    private final SalesMenu salesMenu;

    public Store(String name, String cnpj) {
        this.name = name;
        this.cnpj = cnpj;
        this.reputation = new Reputation();
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

    public Reputation getReputation() {
        return reputation;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public SalesMenu getSalesMenu() {
        return salesMenu;
    }

    // Setters
    public void setStoreName(String name) {
        this.name = name;
    }
}
