package main.java.com.example.marketplace.app;

import main.java.com.example.marketplace.MarketplaceApplication;
import main.java.com.example.marketplace.buyer.auth.controller.BuyerAuthController;
import main.java.com.example.marketplace.seller.auth.controller.SellerAuthController;
import main.java.com.example.marketplace.shared.interfaces.Authenticable;
import main.java.com.example.marketplace.shared.session.BuyerSession;
import main.java.com.example.marketplace.shared.session.SellerSession;

import java.util.Scanner;

public final class LoginAndRegisterPage {

    private final BuyerAuthController buyerAuthController;
    private final SellerAuthController sellerAuthController;

    public LoginAndRegisterPage(BuyerAuthController buyerAuthController, SellerAuthController sellerAuthController) {
        this.buyerAuthController = buyerAuthController;
        this.sellerAuthController = sellerAuthController;
    }

    public void page() {

        Authenticable controller = null;
        int choice;

        do {
            System.out.println("\n--------| LOGIN | SIGN UP |--------");
            System.out.println("[1] Log in\n[2] Sign up\n[3] Close program");
            System.out.println("-----------------------------------");
            choice = readInt();

            switch (choice) {
                case 1:
                    do {
                        System.out.println("\n-------------| LOG IN |-------------");
                        System.out.println("[1] Log in as a customer\n[2] Log in as a seller\n[3] <- Back");
                        System.out.println("-----------------------------------");
                        choice = readInt();

                        switch (choice) {
                            case 1 -> controller = buyerAuthController;
                            case 2 -> controller = sellerAuthController;
                            case 3 -> System.out.print("");
                            default -> System.out.println("[!] Invalid option. Please try again.");
                        }

                        if (controller != null)
                            controller.login();

                        if (BuyerSession.isLogged() || SellerSession.isLogged())
                            return;

                    } while (choice != 3);
                    break;
                case 2:
                    do {
                        System.out.println("\n------------| SIGN UP |------------");
                        System.out.println("[1] Sign up as a customer\n[2] Sign up as a seller\n[3] <- Back");
                        System.out.println("-----------------------------------");
                        choice = readInt();

                        switch (choice) {
                            case 1 -> controller = buyerAuthController;
                            case 2 -> controller = sellerAuthController;
                            case 3 -> System.out.print("");
                            default -> System.out.println("[!] Invalid option. Please try again.");
                        }

                        if (controller != null)
                            controller.register();

                        if (BuyerSession.isLogged() || SellerSession.isLogged())
                            return;

                    } while (choice != 3);
                    break;
                case 3:
                    MarketplaceApplication.setRunning(false);
                    break;
                default:
                    System.out.println("[!] Invalid option. Please try again.");
                    break;
            }
        } while (choice != 3 && (!BuyerSession.isLogged() || !SellerSession.isLogged()));
    }

    public static int readInt() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.print(">> ");
                System.out.flush();
                return Integer.parseInt(scanner.nextLine());
            }
            catch (NumberFormatException e) {
                System.out.println("[!] Invalid input. Please enter a number.");
            }
        }
    }
}
