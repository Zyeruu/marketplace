package main.java.com.example.marketplace.shared.session;

public final class BuyerSession extends Session {

    public static void login(String email) {
        Session.login(email);
    }

    public static void logout() {
        Session.logout();
    }
}
