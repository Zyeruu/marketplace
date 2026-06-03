package main.java.com.example.marketplace.shared.session;

public final class BuyerSession {

    private static String email;
    private static boolean logged;

    public static void login(String email) {
        BuyerSession.email = email;
        BuyerSession.logged = true;
    }

    public static void logout() {
        BuyerSession.email = null;
        BuyerSession.logged = false;
    }

    // Getters
    public static String getEmail() {
        return email;
    }

    public static boolean isLogged() {
        return logged;
    }
}
