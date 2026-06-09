package main.java.com.example.marketplace.app;

import main.java.com.example.marketplace.MarketplaceApplication;
import main.java.com.example.marketplace.buyer.auth.controller.BuyerAuthController;
import main.java.com.example.marketplace.seller.auth.controller.SellerAuthController;
import main.java.com.example.marketplace.shared.session.BuyerSession;
import main.java.com.example.marketplace.shared.session.SellerSession;

import java.util.Scanner;

public final class LoginAndRegisterPage {

    private static final BuyerAuthController buyerAuth = new BuyerAuthController();
    private static final SellerAuthController sellerAuth = new SellerAuthController();

    public static void page() {
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
                            case 1 -> buyerAuth.login();
                            case 2 -> sellerAuth.login();
                            case 3 -> System.out.println();
                            default -> System.out.println("[!] Invalid option. Please try again.");
                        }
                        if (BuyerSession.isLogged() || SellerSession.isLogged())
                            return;
                    } while (choice != 3);
                    choice = 0;
                    break;
                case 2:
                    do {
                        System.out.println("\n------------| SIGN UP |------------");
                        System.out.println("[1] Sign up as a customer\n[2] Sign up as a seller\n[3] <- Back");
                        System.out.println("-----------------------------------");
                        choice = readInt();

                        switch (choice) {
                            case 1 -> buyerAuth.register();
                            case 2 -> sellerAuth.register();
                            case 3 -> System.out.println();
                            default -> System.out.println("[!] Invalid option. Please try again.");
                        }
                        if (BuyerSession.isLogged() || SellerSession.isLogged())
                            return;
                    } while (choice != 3);
                    choice = 0;
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
