package main.java.com.example.marketplace.shared.session;

public abstract class Session {

    private static String email;
    private static boolean loggedIn;

    public static void login(String email) {
        Session.email = email;
        Session.loggedIn = true;
    }

    public static void logout() {
        Session.email = null;
        Session.loggedIn = false;
    }
}
