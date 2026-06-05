package main.java.com.example.marketplace.shared.session;

public final class StoreContext {

    private static String cnpj;
    private static String storeName;

    // Getters
    public static String getCnpj() {
        return cnpj;
    }

    public static String getStoreName() {
        return storeName;
    }

    // Setters
    public static void setCnpj(String cnpj) {
        StoreContext.cnpj = cnpj;
    }

    public static void setStoreName(String storeName) {
        StoreContext.storeName = storeName;
    }
}
