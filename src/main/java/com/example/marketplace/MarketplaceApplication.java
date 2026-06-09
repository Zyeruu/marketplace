package main.java.com.example.marketplace;

import main.java.com.example.marketplace.app.BuyerPage;
import main.java.com.example.marketplace.app.LoginAndRegisterPage;
import main.java.com.example.marketplace.app.SellerPage;
import main.java.com.example.marketplace.shared.session.BuyerSession;
import main.java.com.example.marketplace.shared.session.SellerSession;

import java.util.Scanner;

public class MarketplaceApplication {

    private static boolean running = true;

    public static void main(String[] args) {

        int choice;

        do {
            if (!(BuyerSession.isLogged() || SellerSession.isLogged()))
                LoginAndRegisterPage.page();

            if (BuyerSession.isLogged())
                BuyerPage.page();

            if (SellerSession.isLogged())
                SellerPage.page();

            if (!running) {

                System.out.print("Are you sure you want to close the program? ");
                System.out.println("If you close the program, all data will be permanently deleted.");
                System.out.println("[1] Yes, I'm sure\n[2] No, don't close the program");

                do {
                    choice = readInt();

                    switch (choice) {
                        case 1 -> System.out.println("Closing...");
                        case 2 -> running = true;
                        default -> System.out.println("[!] Invalid option. Try again.");
                    }
                } while (choice != 1 && choice != 2);
            }

        } while (running);
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

    public static void setRunning(boolean running) {
        MarketplaceApplication.running = running;
    }
}