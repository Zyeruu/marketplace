package main.java.com.example.marketplace.shared.utils;

public final class IdGenerator {

    private static int productCounter = 0;
    private static int orderCounter = 0;

    public static String generateProductId() {

        productCounter++;
        return String.format("%05d", productCounter);
    }

    public static String generateOrderId() {

        orderCounter++;
        return String.format("%05d", orderCounter);
    }
}
