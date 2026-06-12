package main.java.com.example.marketplace.app;

import main.java.com.example.marketplace.MarketplaceApplication;
import main.java.com.example.marketplace.user.auth.controller.AuthController;
import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.shared.utils.InputReader;

public final class LoginAndRegisterPage {

    private final AuthController controller;
    private final Session session;

    public LoginAndRegisterPage(AuthController controller, Session session) {
        this.controller = controller;
        this.session = session;
    }

    public void page() {

        int choice;

        do {
            System.out.println("--------| LOGIN | SIGN UP |--------");
            System.out.println("[1] Log in\n[2] Sign up\n[3] Exit");
            System.out.println("-----------------------------------");

            choice = InputReader.readInt();

            switch (choice) {
                case 1 -> controller.login();
                case 2 -> controller.register();
                case 3 -> MarketplaceApplication.setRunning(false);
                default -> System.out.println("[!] Invalid option. Try again.");
            }
        } while (choice != 3 && !session.isLogged());
    }
}
