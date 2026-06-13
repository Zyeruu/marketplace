package main.java.com.example.marketplace.app;

import main.java.com.example.marketplace.user.account.controller.AccountController;
import main.java.com.example.marketplace.user.auth.controller.AuthController;
import main.java.com.example.marketplace.user.controller.CartController;
import main.java.com.example.marketplace.user.controller.OrdersMenuController;
import main.java.com.example.marketplace.user.search.controller.SearchController;
import main.java.com.example.marketplace.checkout.controller.CheckoutController;
import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.shared.utils.InputReader;

public final class UserPage {

    private final AuthController auth;
    private final AccountController account;
    private final CartController cart;
    private final OrdersMenuController ordersMenu;
    private final SearchController search;
    private final CheckoutController checkout;
    private final Session session;

    public UserPage(AuthController auth, AccountController account, CartController cart, OrdersMenuController ordersMenu, SearchController search, CheckoutController checkout, Session session) {
        this.auth = auth;
        this.account = account;
        this.cart = cart;
        this.ordersMenu = ordersMenu;
        this.search = search;
        this.checkout = checkout;
        this.session = session;
    }

    public void page() {

        int choice;

        do {
            System.out.println("----------| MAIN PAGE |----------");
            System.out.println("[1] My Account\n[2] My Purchases\n[3] My Cart\n[4] Search for products\n[5] Log out");

            choice = InputReader.readInt();

            switch (choice) {
                case 1:
                    do {
                        System.out.println("------------| PROFILE |------------");
                        System.out.println("[1] Show profile\n[2] Open a store\n[3] Settings\n[4] <- Back");
                        System.out.println("-----------------------------------");

                        choice = InputReader.readInt();

                        switch (choice) {
                            case 1:
                                account.printProfile();

                                System.out.println("-----------------------------------");
                                System.out.println("[1] <- Back");
                                System.out.println("-----------------------------------");

                                do {
                                    choice = InputReader.readInt();

                                    if (choice != 1)
                                        System.out.println("[!] Invalid option. Try again,");
                                } while (choice != 1);

                                break;

                            case 2:
                                account.createStore();

                                if (session.hasStore())
                                    return;

                                System.out.println("-----------------------------------");
                                System.out.println("[1] <- Back");
                                System.out.println("-----------------------------------");

                                do {
                                    choice = InputReader.readInt();

                                    if (choice != 1)
                                        System.out.println("[!] Invalid option. Try again,");
                                } while (choice != 1);

                                break;

                            case 3:
                                do {
                                    System.out.println("------------| SETTINGS |------------");
                                    System.out.println("[1] Change e-mail\n[2] Change password\n[3] Delete account\n[4] <- Back");
                                    System.out.println("------------------------------------");

                                    choice = InputReader.readInt();

                                    switch (choice) {
                                        case 1 -> account.changeEmail();
                                        case 2 -> account.changePassword();
                                        case 3 -> account.deleteAccount();
                                        case 4 -> System.out.print("");
                                        default -> System.out.println("[!] Invalid option. Try again.");
                                    }

                                    if (!session.isLogged())
                                        return;

                                    if (choice >= 1 && choice <= 3) {
                                        System.out.println("-----------------------------------");
                                        System.out.println("[1] <- Back");
                                        System.out.println("-----------------------------------");

                                        do {
                                            choice = InputReader.readInt();

                                            if (choice != 1)
                                                System.out.println("[!] Invalid option. Try again,");
                                        } while (choice != 1);

                                        choice = 0;
                                    }

                                } while (choice < 1 || choice > 4);

                                choice = 0;
                                break;
                            case 4:
                                System.out.print("");
                                break;
                            default:
                                System.out.println("[!] Invalid option. Try again.");
                                break;
                        }
                    } while (choice != 4);
                    choice = 0;
                    break;

                case 2:
                    do {
                        System.out.println("-----------| PURCHASES |-----------");
                        System.out.println("[1] Show my purchases\n[2] Show specific purchase\n[3] <- Back");
                        System.out.println("-----------------------------------");

                        choice = InputReader.readInt();

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
                                choice = InputReader.readInt();

                                if (choice != 1)
                                    System.out.println("[!] Invalid option. Try again,");
                            } while (choice != 1);
                        }

                    } while (choice != 3);
                    choice = 0;
                    break;

                case 3:
                    do {
                        System.out.println("-------------| CART |-------------");
                        System.out.println("[1] Show cart\n[2] Show cart by name\n[3] Show cart by type\n[4] Show selected products\n[5] Show deselected products\n[6] Show all details of a product\n[7] Remove product\n[8] Checkout\n[9] <- Back");
                        System.out.println("-----------------------------------");

                        choice = InputReader.readInt();

                        switch (choice) {
                            case 1 -> cart.printCart();
                            case 2 -> cart.printCartByProductName();
                            case 3 -> cart.printCartByProductType();
                            case 4 -> cart.printSelected();
                            case 5 -> cart.printDeselected();
                            case 6 -> cart.printAllProductDetails();
                            case 7 -> cart.removeProduct();
                            case 8 -> checkout.checkout();
                            case 9 -> System.out.print("");
                            default -> System.out.println("[!] Invalid option. Try again.");
                        }

                        if (choice >= 1 && choice <= 3) {
                            do {
                                System.out.println("-----------------------------------");
                                System.out.println("[1] Select a product\n[2] Deselect a product\n[3] <- Back");
                                System.out.println("-----------------------------------");

                                choice = InputReader.readInt();

                                switch (choice) {
                                    case 1 -> cart.selectProduct();
                                    case 2 -> cart.deselectProduct();
                                    case 3 -> System.out.print("");
                                    default -> System.out.println("[!] Invalid option. Try again.");
                                }
                            } while (choice != 3);
                        }

                        if (choice == 4) {
                            do {
                                System.out.println("-----------------------------------");
                                System.out.println("[1] Deselect a product\n[2] <- Back");
                                System.out.println("-----------------------------------");

                                choice = InputReader.readInt();

                                switch (choice) {
                                    case 1 -> cart.deselectProduct();
                                    case 2 -> System.out.print("");
                                    default -> System.out.println("[!] Invalid option. Try again.");
                                }
                            } while (choice != 2);
                        }

                        if (choice == 5) {
                            do {
                                System.out.println("-----------------------------------");
                                System.out.println("[1] Select a product\n[2] <- Back");
                                System.out.println("-----------------------------------");

                                choice = InputReader.readInt();

                                switch (choice) {
                                    case 1 -> cart.selectProduct();
                                    case 2 -> System.out.print("");
                                    default -> System.out.println("[!] Invalid option. Try again.");
                                }
                            } while (choice != 2);
                        }

                        if (choice >= 6 && choice <= 8) {
                            System.out.println("-----------------------------------");
                            System.out.println("[1] <- Back");
                            System.out.println("-----------------------------------");

                            do {
                                choice = InputReader.readInt();

                                if (choice != 1)
                                    System.out.println("[!] Invalid option. Try again.");
                            } while (choice != 1);
                        }

                    } while (choice != 9);
                    choice = 0;
                    break;

                case 4:
                    do {
                        System.out.println("------------| SEARCH |------------");
                        System.out.println("[1] Search all\n[2] Search by name\n[3] Search by type\n[4] Search by name and type\n[5] <- Back");
                        System.out.println("-----------------------------------");

                        choice = InputReader.readInt();

                        switch (choice) {
                            case 1 -> search.searchAll();
                            case 2 -> search.searchByName();
                            case 3 -> search.searchByType();
                            case 4 -> search.searchByNameAndType();
                            case 5 -> System.out.print("");
                            default -> System.out.println("[!] Invalid option. Try again.");
                        }

                        if (choice != 5) {
                            do {
                                System.out.println("--------| CART | DETAILS |--------");
                                System.out.println("[1] Add a product to the cart\n[2] Show all details of a product\n[3] <- Back");
                                System.out.println("-----------------------------------");

                                choice = InputReader.readInt();

                                switch (choice) {
                                    case 1 -> cart.addProduct();
                                    case 2 -> search.printAllProductDetails();
                                    case 3 -> System.out.print("");
                                    default -> System.out.println("[!] Invalid option. Try again.");
                                }
                            } while (choice != 3);
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
}
