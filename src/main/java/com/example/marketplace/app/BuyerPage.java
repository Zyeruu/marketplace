package main.java.com.example.marketplace.app;

import main.java.com.example.marketplace.buyer.account.controller.BuyerAccountController;
import main.java.com.example.marketplace.buyer.auth.controller.BuyerAuthController;
import main.java.com.example.marketplace.buyer.controller.CartController;
import main.java.com.example.marketplace.buyer.controller.OrdersMenuController;
import main.java.com.example.marketplace.buyer.search.controller.BuyerSearchController;
import main.java.com.example.marketplace.checkout.controller.CheckoutController;
import main.java.com.example.marketplace.shared.session.BuyerSession;

import java.util.Scanner;

public final class BuyerPage {

    private final BuyerAuthController auth;
    private final BuyerAccountController account;
    private final CartController cart;
    private final OrdersMenuController ordersMenu;
    private final BuyerSearchController search;
    private final CheckoutController checkout;

    public BuyerPage(BuyerAuthController auth, BuyerAccountController account, CartController cart, OrdersMenuController ordersMenu, BuyerSearchController search, CheckoutController checkout) {
        this.auth = auth;
        this.account = account;
        this.cart = cart;
        this.ordersMenu = ordersMenu;
        this.search = search;
        this.checkout = checkout;
    }

    public void page() {

        int choice;

        do {
            System.out.println("\n----------| CUSTOMER PAGE |----------");
            System.out.println("[1] My profile\n[2] My Purchases\n[3] My Cart\n[4] Search for products\n[5] Log out");
            choice = readInt();

            switch (choice) {
                case 1:
                    do {
                        System.out.println("\n------------| PROFILE |------------");
                        System.out.println("[1] Show my profile\n[2] Change my e-mail\n[3] Change my password\n[4] Delete my account\n[5] <- Back");
                        System.out.println("-----------------------------------");
                        choice = readInt();

                        switch (choice) {
                            case 1 -> account.printBuyer();
                            case 2 -> account.changeEmail();
                            case 3 -> account.changePassword();
                            case 4 -> account.deleteAccount();
                            case 5 -> System.out.print("");
                            default -> System.out.println("[!] Invalid option. Try again.");
                        }

                        if (!BuyerSession.isLogged())
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
                        System.out.println("\n-----------| PURCHASES |-----------");
                        System.out.println("[1] Show my purchases\n[2] Show specific purchase\n[3] <- Back");
                        System.out.println("-----------------------------------");
                        choice = readInt();

                        switch (choice) {
                            case 1 -> ordersMenu.printOrders();
                            case 2 -> ordersMenu.printOrder();
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

                case 3:
                    do {
                        System.out.println("\n-------------| CART |-------------");
                        System.out.println("[1] Show my cart\n[2] Show by name\n[3] Show by type\n[4] Show all details of a product\n[5] Remove product\n[6] Checkout\n[7] <- Back");
                        System.out.println("-----------------------------------");
                        choice = readInt();

                        switch (choice) {
                            case 1 -> cart.printCart();
                            case 2 -> cart.printCartByProductName();
                            case 3 -> cart.printCartByProductType();
                            case 4 -> cart.printAllProductDetails();
                            case 5 -> cart.removeProduct();
                            case 6 -> checkout.checkout();
                            case 7 -> System.out.print("");
                            default -> System.out.println("[!] Invalid option. Try again.");
                        }

                        if (choice != 7) {
                            System.out.println("-----------------------------------");
                            System.out.println("[1] <- Back");
                            System.out.println("-----------------------------------");
                            do {
                                choice = readInt();
                            } while (choice != 1);
                        }

                    } while (choice != 7);
                    choice = 0;
                    break;

                case 4:
                    do {
                        System.out.println("\n------------| SEARCH |------------");
                        System.out.println("[1] Search by name\n[2] Search by type\n[3] Search by name and type\n[4] Search all\n[5] <- Back");
                        System.out.println("-----------------------------------");
                        choice = readInt();

                        switch (choice) {
                            case 1 -> search.searchByName();
                            case 2 -> search.searchByType();
                            case 3 -> search.searchByNameAndType();
                            case 4 -> search.searchAll();
                            case 5 -> System.out.print("");
                            default -> System.out.println("[!] Invalid option. Try again.");
                        }

                        if (choice != 5) {
                            System.out.println("\n--------| CART | DETAILS |--------");
                            System.out.println("[1] Add a product to the cart\n[2] Show all details of a product\n[3] <- Back");
                            System.out.println("-----------------------------------");
                            choice = readInt();

                            switch (choice) {
                                case 1 -> cart.addProduct();
                                case 2 -> search.printAllProductDetails();
                                case 3 -> System.out.print("");
                                default -> System.out.println("[!] Invalid option. Try again.");
                            }
                        }
                    } while (choice != 5);
                    choice = 0;
                    break;

                case 5:
                    auth.logout();
                    break;

                default:
                    System.out.println("[!] Invalid option. Try again.");
                    break;
            }
        } while (choice != 5);
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
