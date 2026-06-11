package main.java.com.example.marketplace;

import main.java.com.example.marketplace.app.BuyerPage;
import main.java.com.example.marketplace.app.LoginAndRegisterPage;
import main.java.com.example.marketplace.app.SellerPage;
import main.java.com.example.marketplace.config.DependencyInjector;
import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.shared.utils.InputReader;

public class MarketplaceApplication {

    private static boolean running = true;

    public static void main(String[] args) {

        final DependencyInjector injector = new DependencyInjector();
        final LoginAndRegisterPage loginAndRegister = new LoginAndRegisterPage(injector.getBuyerAuthController(), injector.getSellerAuthController(), injector.getSession());
        final BuyerPage buyer = new BuyerPage(injector.getBuyerAuthController(), injector.getBuyerAccountController(), injector.getCartController(), injector.getOrdersMenuController(), injector.getSearchController(), injector.getCheckoutController(), injector.getSession());
        final SellerPage seller = new SellerPage(injector.getSellerAuthController(), injector.getSellerAccountController(), injector.getCatalogController(), injector.getSalesMenuController(), injector.getSession());
        final Session session = injector.getSession();
        
        int choice;

        do {
            if (!(session.isBuyerLogged() || session.isSellerLogged()))
                loginAndRegister.page();

            if (session.isBuyerLogged())
                buyer.page();

            if (session.isSellerLogged())
                seller.page();

            if (!running) {

                System.out.print("Are you sure you want to close the program? ");
                System.out.println("If you close the program, all data will be permanently deleted.");
                System.out.println("[1] Yes, I'm sure\n[2] No, don't close the program");

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