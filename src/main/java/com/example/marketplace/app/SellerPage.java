package main.java.com.example.marketplace.app;

import main.java.com.example.marketplace.seller.account.controller.SellerAccountController;
import main.java.com.example.marketplace.seller.auth.controller.SellerAuthController;
import main.java.com.example.marketplace.seller.controller.CatalogController;
import main.java.com.example.marketplace.seller.controller.SalesMenuController;
import main.java.com.example.marketplace.shared.session.Session;

import java.util.Scanner;

public final class SellerPage {

    private final SellerAuthController auth;
    private final SellerAccountController account;
    private final CatalogController catalog;
    private final SalesMenuController salesMenu;
    private final Session session;

    public SellerPage(SellerAuthController auth, SellerAccountController account, CatalogController catalog, SalesMenuController salesMenu, Session session) {
        this.auth = auth;
        this.account = account;
        this.catalog = catalog;
        this.salesMenu = salesMenu;
        this.session = session;
    }

    public void page() {

        int choice;

        do {
            System.out.println("\n----------| SELLER PAGE |----------");
            System.out.println("[1] My profile\n[2] My Catalog\n[3] My Sales\n[4] Log out");
            System.out.println("-----------------------------------");
            choice = readInt();

            switch (choice) {
                case 1:
                    do {
                        System.out.println("\n------------| PROFILE |------------");
                        System.out.println("[1] Show my profile\n[2] Change my e-mail\n[3] Change my password\n[4] Delete my account\n[5] <- Back");
                        System.out.println("-----------------------------------");
                        choice = readInt();

                        switch (choice) {
                            case 1 -> account.printSeller();
                            case 2 -> account.changeEmail();
                            case 3 -> account.changePassword();
                            case 4 -> account.deleteAccount();
                            case 5 -> System.out.print("");
                            default -> System.out.println("[!] Invalid option. Try again.");
                        }

                        if (!session.isSellerLogged())
                            return;

                        if (choice != 5) {
                            System.out.println("-----------------------------------");
                            System.out.println("[1] <- Back");
                            System.out.println("-----------------------------------");
                            do {
                                choice = readInt();
                            } while (choice != 1);
                        }

                    } while (choice != 5);
                    choice = 0;
                    break;

                case 2:
                    do {
                        System.out.println("\n------------| CATALOG |------------");
                        System.out.println("[1] Show my catalog\n[2] Show by name\n[3] Show by type\n[4] Show all details of a product\n[5] List a product\n[6] Delete a product\n[7] Update a product\n[8] <- Back");
                        System.out.println("-----------------------------------");
                        choice = readInt();

                        switch (choice) {
                            case 1 -> catalog.printCatalog();
                            case 2 -> catalog.printCatalogByProductName();
                            case 3 -> catalog.printCatalogByProductType();
                            case 4 -> catalog.printAllProductDetails();
                            case 5 -> catalog.addProductToCatalog();
                            case 6 -> catalog.removeCatalogProduct();
                            case 7 -> catalog.updateCatalogProduct();
                            case 8 -> System.out.print("");
                            default -> System.out.println("[!] Invalid option. Try again.");
                        }

                        if (choice != 8) {
                            System.out.println("-----------------------------------");
                            System.out.println("[1] <- Back");
                            System.out.println("-----------------------------------");
                            do {
                                choice = readInt();
                            } while (choice != 1);
                        }

                    } while (choice != 8);
                    choice = 0;
                    break;

                case 3:
                    do {
                        System.out.println("\n-------------| SALES |-------------");
                        System.out.println("[1] Show my sales\n[2] Show specific sale\n[3] <- Back");
                        System.out.println("-----------------------------------");
                        choice = readInt();

                        switch (choice) {
                            case 1 -> salesMenu.printTaxReceiptList();
                            case 2 -> salesMenu.printTaxReceipt();
                            case 3 -> System.out.print("");
                            default -> System.out.println("[!] Invalid option. Try again.");
                        }

                        if (choice != 3) {
                            System.out.println("-----------------------------------");
                            System.out.println("[1] <- Back");
                            System.out.println("-----------------------------------");
                            do {
                                choice = readInt();
                            } while (choice != 1);
                        }

                    } while (choice != 3);
                    choice = 0;
                    break;

                case 4:
                    auth.logout();
                    break;

                default:
                    System.out.println("[!] Invalid option. Try again.");
                    break;
            }
        } while (choice != 4);
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
