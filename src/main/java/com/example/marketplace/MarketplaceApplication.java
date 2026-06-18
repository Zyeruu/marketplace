package main.java.com.example.marketplace;

import main.java.com.example.marketplace.app.*;
import main.java.com.example.marketplace.config.DependencyInjector;
import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.shared.utils.InputReader;

public final class MarketplaceApplication {

    private static boolean running = true;

    public static void main(String[] args) {

        final DependencyInjector injector = new DependencyInjector();
        final LoginAndRegisterPage loginAndRegister = new LoginAndRegisterPage(injector.getAuthController(), injector.getSession());
        final UserPage storeUser = new UserPage(injector.getAuthController(), injector.getAccountController(), injector.getCartController(), injector.getCatalogController(), injector.getOrdersMenuController(), injector.getStoreController(), injector.getSalesMenuController(), injector.getSearchController(), injector.getCheckoutController(), injector.getSession());
        final Session session = injector.getSession();
        
        int choice;

        do {
            if (!session.isLogged())
                loginAndRegister.page();

            if (!session.hasStore() && session.isLogged())
                storeUser.userPage();

            if (session.hasStore() && session.isLogged())
                storeUser.storeOwnerPage();

            if (!running) {

                System.out.print("Are you sure you want to close the program? ");
                System.out.println("If you close the program, all data will be permanently deleted.");
                System.out.println("[1] Yes, exit");
                System.out.println("[2] No, don't close the program");

                do {
                    choice = InputReader.readInt();

                    switch (choice) {
                        case 1 -> System.out.println("Closing...");
                        case 2 -> running = true;
                        default -> System.out.println("[!] Invalid option. Try again.");
                    }
                } while (choice != 1 && choice != 2);
            }

        } while (running);
    }

    public static void setRunning(boolean running) {
        MarketplaceApplication.running = running;
    }
}