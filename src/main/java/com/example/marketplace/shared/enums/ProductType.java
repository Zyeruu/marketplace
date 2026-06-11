package main.java.com.example.marketplace.shared.enums;

public enum ProductType {
    FOOD("Food"),
    MISCELLANEOUS("Miscellaneous");

    private final String name;

    ProductType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}