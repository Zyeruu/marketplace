package main.java.com.example.marketplace.shared.session;

public final class SellerSession {

    private static String email;
    private static String cnpj;
    private static String storeName;
    private static boolean logged;

    public static void login(String email) {
        SellerSession.email = email;
        SellerSession.logged = true;
    }

    public static void logout() {
        SellerSession.email = null;
        SellerSession.cnpj = null;
        SellerSession.logged = false;
    }

    // Getters
    public static String getCnpj() {
        return SellerSession.cnpj;
    }

    public static String getEmail() {
        return email;
    }

    public static String getStoreName() {
        return storeName;
    }

    public static boolean isLogged() {
        return logged;
    }

    public static void setCurrent(String cnpj) {
        SellerSession.cnpj = cnpj;
    }
}
