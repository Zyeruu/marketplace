package main.java.com.example.marketplace.app;

import main.java.com.example.marketplace.user.account.controller.AccountController;
import main.java.com.example.marketplace.user.auth.controller.AuthController;
import main.java.com.example.marketplace.user.controller.CartController;
import main.java.com.example.marketplace.user.controller.OrdersMenuController;
import main.java.com.example.marketplace.user.search.controller.SearchController;
import main.java.com.example.marketplace.checkout.controller.CheckoutController;
import main.java.com.example.marketplace.shared.session.Session;
import main.java.com.example.marketplace.shared.utils.InputReader;
import main.java.com.example.marketplace.user.store.controller.CatalogController;
import main.java.com.example.marketplace.user.store.controller.SalesMenuController;
import main.java.com.example.marketplace.user.store.controller.StoreController;

public final class StoreUserPage {

    private final AuthController auth;
    private final AccountController account;
    private final CartController cart;
    private final CatalogController catalog;
    private final OrdersMenuController ordersMenu;
    private final StoreController store;
    private final SalesMenuController salesMenu;
    private final SearchController search;
    private final CheckoutController checkout;
    private final Session session;

    public StoreUserPage(AuthController auth, AccountController account, CartController cart, CatalogController catalog, OrdersMenuController ordersMenu, StoreController store, SalesMenuController salesMenu, SearchController search, CheckoutController checkout, Session session) {
        this.auth = auth;
        this.account = account;
        this.cart = cart;
        this.catalog = catalog;
        this.ordersMenu = ordersMenu;
        this.store = store;
        this.salesMenu = salesMenu;
        this.search = search;
        this.checkout = checkout;
        this.session = session;
    }

    public void page() {

        int choice;

        do {
            System.out.println("----------| MAIN PAGE |----------");
            System.out.println("[1] My Account\n[2] My Store\n[3] My Purchases\n[4] My Cart\n[5] Search for products\n[6] Log out");

            choice = InputReader.readInt();

            switch (choice) {
                case 1:
                    do {
                        System.out.println("------------| PROFILE |------------");
                        System.out.println("[1] Show profile\n[2] Settings\n[3] <- Back");
                        System.out.println("-----------------------------------");

                        choice = InputReader.readInt();

                        switch (choice) {
                            case 1:
                                account.printProfile();
                                break;
                            case 2:
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
                                } while (choice < 1 || choice > 4);

                                if (!session.isLogged())
                                    return;

                                if (choice != 4) {
                                    System.out.println("-----------------------------------");
                                    System.out.println("[1] <- Back");
                                    System.out.println("-----------------------------------");

                                    do {
                                        choice = InputReader.readInt();

                                        if (choice != 1)
                                            System.out.println("[!] Invalid option. Try again,");
                                    } while (choice != 1);
                                }

                                choice = 0;
                                break;
                            case 3:
                                System.out.print("");
                                break;
                            default:
                                System.out.println("[!] Invalid option. Try again.");
                                break;
                        }
                    } while (choice != 3);
                    choice = 0;
                    break;

                case 2:
                    do {
                        System.out.println("-------------| STORE |--------------");
                        System.out.println("[1] Show store\n[2] My catalog\n[3] My sales\n[4] Settings\n[5] <- Back");
                        System.out.println("------------------------------------");

                        choice = InputReader.readInt();

                        switch (choice) {
                            case 1:
                                store.printStore();

                                System.out.println("-----------------------------------");
                                System.out.println("[1] <- Back");
                                System.out.println("-----------------------------------");

                                do {
                                    choice = InputReader.readInt();

                                    if (choice != 1)
                                        System.out.println("[!] Invalid option. Try again,");
                                } while (choice != 1);

                                choice = 0;
                                break;

                            case 2:
                                do {
                                    System.out.println("-----------------------------------");
                                    System.out.println("[1] Show catalog\n[2] Show catalog by name\n[3] Show catalog by type\n[4] Show all details of a product\n[5] List a product\n[6] Delete a product\n[7] Update a product\n[8] <- Back");
                                    System.out.println("-----------------------------------");

                                    choice = InputReader.readInt();

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
                                            choice = InputReader.readInt();

                                            if (choice != 1)
                                                System.out.println("[!] Invalid option. Try again,");
                                        } while (choice != 1);
                                    }

                                } while (choice != 8);
                                choice = 0;
                                break;

                            case 3:
                                do {
                                    System.out.println("-------------| SALES |-------------");
                                    System.out.println("[1] Show my sales\n[2] Show specific sale\n[3] <- Back");
                                    System.out.println("-----------------------------------");

                                    choice = InputReader.readInt();

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
                                            choice = InputReader.readInt();

                                            if (choice != 1)
                                                System.out.println("[!] Invalid option. Try again,");
                                        } while (choice != 1);
                                    }

                                } while (choice != 3);
                                choice = 0;
                                break;

                            case 4:
                                do {
                                    System.out.println("------------| SETTINGS |------------");
                                    System.out.println("[1] Change store name\n[2] Delete store\n[3] <- Back");
                                    System.out.println("------------------------------------");

                                    choice = InputReader.readInt();

                                    switch (choice) {
                                        case 1 -> store.changeStoreName();
                                        case 2 -> account.deleteStore();
                                        case 3 -> System.out.print("");
                                        default -> System.out.println("[!] Invalid option. Try again.");
                                    }

                                    if (!session.hasStore())
                                        return;

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

                            case 5:
                                System.out.print("");
                                break;

                            default:
                                System.out.println("[!] Invalid option. Try again.");
                                break;
                        }
                    } while (choice != 5);
                    break;

                case 3:
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

                case 4:
                    do {
                        System.out.println("-------------| CART |-------------");
                        System.out.println("[1] Show my cart\n[2] Show by name\n[3] Show by type\n[4] Show selected products\n[5] Show deselected products\n[6] Show all details of a product\n[7] Remove product\n[8] Checkout\n[9] <- Back");
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

                case 5:
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

                case 6:
                    auth.logout();
                    break;

                default:
                    System.out.println("[!] Invalid option. Try again.");
                    break;
            }
        } while (choice != 6);
    }
}
