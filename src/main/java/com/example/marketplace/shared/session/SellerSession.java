package main.java.com.example.marketplace.shared.session;

public final class SellerSession extends Session {

    private static String cnpj;

    public static void login(String email, String cnpj) {
        Session.login(email);
        SellerSession.cnpj = cnpj;
    }

    public static void logout() {
        Session.logout();
        SellerSession.cnpj = null;
    }
}
