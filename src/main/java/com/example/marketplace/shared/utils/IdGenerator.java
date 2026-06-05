package main.java.com.example.marketplace.shared.utils;

public final class IdGenerator {

    private static int sellerCounter = 0;
    private static int productCounter = 0;
    private static int orderCounter = 0;

    public static String generateCnpj() {

        sellerCounter++;
        return String.format("%014d", sellerCounter)
                .replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
    }

    public static String generateProductId() {

        productCounter++;
        return String.format("%05d", productCounter);
    }

    public static String generateOrderId() {

        orderCounter++;
        return String.format("%05d", orderCounter);
    }
}
