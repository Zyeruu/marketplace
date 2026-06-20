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

public final class UserPage {

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

    public UserPage(AuthController auth, AccountController account, CartController cart, CatalogController catalog, OrdersMenuController ordersMenu, StoreController store, SalesMenuController salesMenu, SearchController search, CheckoutController checkout, Session session) {
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


    // ===================================================| MAIN |===================================================

    public void userPage() {

        int choice;

        do {
            System.out.println("----------| MAIN PAGE |----------");
            System.out.println("[1] My Account");
            System.out.println("[2] My Purchases");
            System.out.println("[3] My Cart");
            System.out.println("[4] Search for products");
            System.out.println("[5] Search for sellers");
            System.out.println("[6] Log out");
            System.out.println("---------------------------------");

            choice = InputReader.readInt();

            switch (choice) {
                case 1 -> myUserAccount();
                case 2 -> myPurchases();
                case 3 -> myCart();
                case 4 -> searchForProducts();
                case 5 -> searchForSellers();
                case 6 -> auth.logout();
                default -> System.out.println("[!] Invalid option. Try again.");
            }

        } while (session.isLogged() && !session.hasStore());
    }

    public void storeOwnerPage() {

        int choice;

        do {
            System.out.println("----------| MAIN PAGE |----------");
            System.out.println("[1] My Account");
            System.out.println("[2] My Store");
            System.out.println("[3] My Purchases");
            System.out.println("[4] My Cart");
            System.out.println("[5] Search for products");
            System.out.println("[6] Search for sellers");
            System.out.println("[7] Log out");
            System.out.println("-----------------------------------");

            choice = InputReader.readInt();

            switch (choice) {
                case 1 -> myStoreOwnerAccount();
                case 2 -> myStore();
                case 3 -> myPurchases();
                case 4 -> myCart();
                case 5 -> searchForProducts();
                case 6 -> searchForSellers();
                case 7 -> auth.logout();
                default -> System.out.println("[!] Invalid option. Try again.");
            }

        } while (session.isLogged() && session.hasStore());
    }

    public void myUserAccount() {

        int choice;

        do {
            System.out.println("------------| PROFILE |------------");
            System.out.println("[1] Show profile");
            System.out.println("[2] Open a store");
            System.out.println("[3] Settings");
            System.out.println("[4] <- Back");
            System.out.println("-----------------------------------");

            choice = InputReader.readInt();

            switch (choice) {
                case 1 -> account.printProfile();
                case 2 -> account.createStore();
                case 3 -> myAccountSetting();
                case 4 -> System.out.print("");
                default -> System.out.println("[!] Invalid option. Try again.");
            }

            if (!session.isLogged() || session.hasStore())
                return;

            if (choice == 1 || choice == 2)
                back();

        } while (choice != 4);
    }

    public void myStoreOwnerAccount() {

        int choice;

        do {
            System.out.println("------------| PROFILE |------------");
            System.out.println("[1] Show profile");
            System.out.println("[2] Settings");
            System.out.println("[3] <- Back");
            System.out.println("-----------------------------------");

            choice = InputReader.readInt();

            switch (choice) {
                case 1 -> account.printProfile();
                case 2 -> myAccountSetting();
                case 3 -> System.out.print("");
                default -> System.out.println("[!] Invalid option. Try again.");
            }

            if (!session.isLogged() || !session.hasStore())
                return;

            if (choice == 1)
                back();

        } while (choice != 3);
    }

    public void myStore() {

        int choice;

        do {
            System.out.println("-------------| STORE |--------------");
            System.out.println("[1] Show my store");
            System.out.println("[2] My catalog");
            System.out.println("[3] My sales");
            System.out.println("[4] Settings");
            System.out.println("[5] <- Back");
            System.out.println("------------------------------------");

            choice = InputReader.readInt();

            switch (choice) {
                case 1 -> store.printStore();
                case 2 -> myStoreCatalog();
                case 3 -> myStoreSales();
                case 4 -> myStoreSettings();
                case 5 -> System.out.print("");
                default -> System.out.println("[!] Invalid option. Try again.");
            }

            if (!session.hasStore())
                return;

            if (choice == 1)
                back();

        } while (choice != 5);
    }

    public void myPurchases() {

        int choice;

        do {
            System.out.println("-----------| PURCHASES |-----------");
            System.out.println("[1] Show my purchases");
            System.out.println("[2] Show specific purchase");
            System.out.println("[3] My reviews");
            System.out.println("[4] <- Back");
            System.out.println("-----------------------------------");

            choice = InputReader.readInt();

            switch (choice) {
                case 1 -> ordersMenu.printOrders();
                case 2 -> ordersMenu.printOrder();
                case 3 -> myPurchasesReviews();
                case 4 -> System.out.print("");
                default -> System.out.println("[!] Invalid option. Try again.");
            }

            if (choice == 1 || choice == 2)
                back();

        } while (choice != 4);
    }

    public void myCart() {

        int choice1, choice2;
        boolean found = false;

        do {
            System.out.println("-------------| CART |-------------");
            System.out.println("[1] Show my cart");
            System.out.println("[2] Show selected products");
            System.out.println("[3] Show deselected products");
            System.out.println("[4] Show all details of a product");
            System.out.println("[5] Remove product");
            System.out.println("[6] Checkout");
            System.out.println("[7] <- Back");
            System.out.println("----------------------------------");

            choice1 = InputReader.readInt();

            switch (choice1) {
                case 1 -> showMyCart();
                case 2 -> found = cart.printSelected();
                case 3 -> found = cart.printDeselected();
                case 4 -> found = cart.printAllProductDetails();
                case 5 -> cart.removeProduct();
                case 6 -> checkout.checkout();
                case 7 -> System.out.print("");
                default -> System.out.println("[!] Invalid option. Try again.");
            }

            if (choice1 == 2 && found) {
                do {
                    System.out.println("------------| DESELECT |-----------");
                    System.out.println("[1] Deselect a product");
                    System.out.println("[2] <- Back");
                    System.out.println("-----------------------------------");

                    choice2 = InputReader.readInt();

                    switch (choice2) {
                        case 1 -> cart.deselectProduct();
                        case 2 -> System.out.print("");
                        default -> System.out.println("[!] Invalid option. Try again.");
                    }
                } while (choice2 != 2);
            }

            if (choice1 == 3 && found) {
                do {
                    System.out.println("-------------| SELECT |------------");
                    System.out.println("[1] Select a product");
                    System.out.println("[2] <- Back");
                    System.out.println("-----------------------------------");

                    choice2 = InputReader.readInt();

                    switch (choice2) {
                        case 1 -> cart.selectProduct();
                        case 2 -> System.out.print("");
                        default -> System.out.println("[!] Invalid option. Try again.");
                    }
                } while (choice2 != 2);
            }

            if (choice1 == 4 && found) {
                System.out.println("------------| REVIEWS |------------");
                System.out.println("[1] Show reviews");
                System.out.println("[2] <- Back");
                System.out.println("-----------------------------------");

                do {
                    choice2 = InputReader.readInt();

                    switch (choice2) {
                        case 1 -> search.searchForLastProductViewedReview();
                        case 2 -> System.out.print("");
                        default -> System.out.println("[!] Invalid option. Try again.");
                    }
                } while (choice2 != 1 && choice2 != 2);
            }

            if (((choice1 == 5 || choice1 == 6) || (choice1 != 1 && !found)) && choice1 != 7)
                back();

        } while (choice1 != 7);
    }

    public void searchForProducts() {

        int choice1, choice2;
        boolean found = false;

        do {
            System.out.println("------------| SEARCH |------------");
            System.out.println("[1] Search for all");
            System.out.println("[2] Search by name");
            System.out.println("[3] Search by type");
            System.out.println("[4] Search by name and type");
            System.out.println("[5] <- Back");
            System.out.println("----------------------------------");

            choice1 = InputReader.readInt();

            switch (choice1) {
                case 1 -> found = search.searchAll();
                case 2 -> found = search.searchByName();
                case 3 -> found = search.searchByType();
                case 4 -> found = search.searchByNameAndType();
                case 5 -> System.out.print("");
                default -> System.out.println("[!] Invalid option. Try again.");
            }

            if (choice1 >= 1 && choice1 <= 4 && found) {
                do {
                    System.out.println("--------| CART | DETAILS |--------");
                    System.out.println("[1] Add a product to the cart");
                    System.out.println("[2] Show all details of a product");
                    System.out.println("[3] Show a product review");
                    System.out.println("[4] <- Back");
                    System.out.println("----------------------------------");

                    choice2 = InputReader.readInt();

                    switch (choice2) {
                        case 1 -> cart.addProduct();
                        case 2 -> showAllDetails();
                        case 3 -> search.searchForProductReview();
                        case 4 -> System.out.print("");
                        default -> System.out.println("[!] Invalid option. Try again.");
                    }

                } while (choice2 != 4);

            }

            if (choice1 >= 1 && choice1 <= 4 && !found)
                back();

        } while (choice1 != 5);
    }

    public void searchForSellers() {

        int choice;
        boolean found = false;

        do {
            System.out.println("------| SEARCH FOR SELLERS |------");
            System.out.println("[1] All sellers");
            System.out.println("[2] Sellers with catalog");
            System.out.println("[3] <- Back");
            System.out.println("----------------------------------");

            choice = InputReader.readInt();

            switch (choice) {
                case 1 -> found = search.searchForSellers();
                case 2 -> found = search.searchForSellersWithCatalog();
                case 3 -> System.out.print("");
                default -> System.out.println("[!] Invalid option. Try again.");
            }

            if ((choice == 1 || choice == 2) && found)
                showSellerCatalog();

            if ((choice == 1 || choice == 2) && !found)
                back();

        } while (choice != 3);
    }

    public void back() {

        int choice;

        System.out.println("-----------------------------------");
        System.out.println("[1] <- Back");
        System.out.println("-----------------------------------");

        do {
            choice = InputReader.readInt();

            if (choice != 1)
                System.out.println("[!] Invalid option. Try again.");
        } while (choice != 1);
    }

    // =================================================| MY ACCOUNT |=================================================

    public void myAccountSetting() {

        int choice;

        do {
            System.out.println("------------| SETTINGS |------------");
            System.out.println("[1] Change e-mail");
            System.out.println("[2] Change password");
            System.out.println("[3] Delete account");
            System.out.println("[4] <- Back");
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
                back();
            }

        } while (choice != 4);
    }

    // ==================================================| MY STORE |=================================================

    public void myStoreCatalog() {

        int choice;

        do {
            System.out.println("-----------| MY CATALOG |----------");
            System.out.println("[1] Show my catalog");
            System.out.println("[2] List a product");
            System.out.println("[3] Delete a product");
            System.out.println("[4] Update a product");
            System.out.println("[5] Show all details of a product");
            System.out.println("[6] Show a product's reviews");
            System.out.println("[7] <- Back");
            System.out.println("-----------------------------------");

            choice = InputReader.readInt();

            switch (choice) {
                case 1 -> showMyCatalog();
                case 2 -> catalog.addProductToCatalog();
                case 3 -> catalog.deleteCatalogProduct();
                case 4 -> catalog.updateCatalogProduct();
                case 5 -> catalog.printAllProductDetails();
                case 6 -> catalog.searchForProductReview();
                case 7 -> System.out.print("");
                default -> System.out.println("[!] Invalid option. Try again.");
            }

            if (choice != 1 && choice != 7)
                back();

        } while (choice != 7);
    }

    public void showMyCatalog() {

        int choice1, choice2;
        boolean found = false;

        do {
            System.out.println("----------| SHOW CATALOG |---------");
            System.out.println("[1] Show my entire catalog");
            System.out.println("[2] Show available catalog");
            System.out.println("[3] Show unavailable catalog");
            System.out.println("[4] Show catalog by name");
            System.out.println("[5] Show catalog by type");
            System.out.println("[6] <- Back");
            System.out.println("-----------------------------------");

            choice1 = InputReader.readInt();

            switch (choice1) {
                case 1 -> found = catalog.printCatalog();
                case 2 -> found = catalog.printAvailableCatalog();
                case 3 -> found = catalog.printUnavailableCatalog();
                case 4 -> found = catalog.printCatalogByProductName();
                case 5 -> found = catalog.printCatalogByProductType();
                case 6 -> System.out.print("");
                default -> System.out.println("[!] Invalid option. Try again.");
            }


            if (choice1 >= 1 && choice1 <= 5 && found) {
                do {
                    System.out.println("---------| MANAGE | VIEW |---------");
                    System.out.println("[1] Show all details of a product");
                    System.out.println("[2] Delete a product");
                    System.out.println("[3] Update a product");
                    System.out.println("[4] Show a product's reviews");
                    System.out.println("[5] <- Back");
                    System.out.println("-----------------------------------");

                    choice2 = InputReader.readInt();

                    switch (choice2) {
                        case 1 -> catalog.printAllProductDetails();
                        case 2 -> catalog.deleteCatalogProduct();
                        case 3 -> catalog.updateCatalogProduct();
                        case 4 -> catalog.searchForProductReview();
                        case 5 -> System.out.print("");
                        default -> System.out.println("[!] Invalid option. Try again.");
                    }

                    if (choice2 >= 1 && choice2 <= 4)
                        back();

                } while (choice2 != 5);
            }

            if (choice1 >= 1 && choice1 <= 5 && !found)
                back();

        } while (choice1 != 6);
    }

    public void myStoreSales() {

        int choice;

        do {
            System.out.println("-------------| SALES |-------------");
            System.out.println("[1] Show my sales");
            System.out.println("[2] Show specific sale");
            System.out.println("[3] <- Back");
            System.out.println("-----------------------------------");

            choice = InputReader.readInt();

            switch (choice) {
                case 1 -> salesMenu.printTaxReceiptList();
                case 2 -> salesMenu.printTaxReceipt();
                case 3 -> System.out.print("");
                default -> System.out.println("[!] Invalid option. Try again.");
            }

            if (choice != 3)
                back();

        } while (choice != 3);
    }

    public void myStoreSettings() {

        int choice;

        do {
            System.out.println("------------| SETTINGS |------------");
            System.out.println("[1] Change store name");
            System.out.println("[2] Delete store");
            System.out.println("[3] <- Back");
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

            if (choice != 3)
                back();

        } while (choice != 3);
    }

    // ================================================| MY PURCHASES |================================================

    public void myPurchasesReviews() {

        int choice1, choice2;
        boolean found = false;

        do {
            System.out.println("-----------| MY REVIEWS |----------");
            System.out.println("[1] Show my reviews");
            System.out.println("[2] Show my unrated purchases");
            System.out.println("[3] Review a product");
            System.out.println("[4] Delete a review");
            System.out.println("[5] Update a review");
            System.out.println("[6] <- Back");
            System.out.println("-----------------------------------");

            choice1 = InputReader.readInt();

            switch (choice1) {
                case 1 -> found = ordersMenu.printReviewList();
                case 2 -> found = ordersMenu.printUnratedProducts();
                case 3 -> ordersMenu.reviewProduct();
                case 4 -> ordersMenu.deleteReview();
                case 5 -> ordersMenu.updateReview();
                case 6 -> System.out.print("");
                default -> System.out.println("[!] Invalid option. Try again.");
            }


            if (choice1 == 1 && found) {
                do {
                    System.out.println("--------| DELETE | UPDATE |--------");
                    System.out.println("[1] Delete a review");
                    System.out.println("[2] Update a review");
                    System.out.println("[3] <- Back");
                    System.out.println("-----------------------------------");

                    choice2 = InputReader.readInt();

                    switch (choice2) {
                        case 1 -> ordersMenu.deleteReview();
                        case 2 -> ordersMenu.updateReview();
                        case 3 -> System.out.print("");
                        default -> System.out.println("[!] Invalid option. Try again.");
                    }

                } while (choice2 != 3);
            }

            if (choice1 == 2 && found) {
                do {
                    System.out.println("-------------| REVIEW |------------");
                    System.out.println("[1] Review a product");
                    System.out.println("[2] <- Back");
                    System.out.println("-----------------------------------");

                    choice2 = InputReader.readInt();

                    switch (choice2) {
                        case 1 -> ordersMenu.reviewProduct();
                        case 2 -> System.out.print("");
                        default -> System.out.println("[!] Invalid option. Try again.");
                    }
                } while (choice2 != 2);
            }

            if ((choice1 >= 1 && choice1 <= 5 && !found))
                back();

        } while (choice1 != 6);
    }

    // ==================================================| MY CART |==================================================

    public void showMyCart() {

        int choice1, choice2;
        boolean found = false;

        do {
            System.out.println("---------| SHOW MY CART |---------");
            System.out.println("[1] Show all my cart items");
            System.out.println("[2] Show by name");
            System.out.println("[3] Show by type");
            System.out.println("[4] <- Back");
            System.out.println("----------------------------------");

            choice1 = InputReader.readInt();

            switch (choice1) {
                case 1 -> found = cart.printCart();
                case 2 -> found = cart.printCartByProductName();
                case 3 -> found = cart.printCartByProductType();
                case 4 -> System.out.print("");
                default -> System.out.println("[!] Invalid option. Try again.");
            }

            if (choice1 >= 1 && choice1 <= 3 && found) {
                do {
                    System.out.println("-------| SELECT | DESELECT |-------");
                    System.out.println("[1] Select a product");
                    System.out.println("[2] Deselect a product");
                    System.out.println("[3] Select all");
                    System.out.println("[4] Deselect all");
                    System.out.println("[5] <- Back");
                    System.out.println("-----------------------------------");

                    choice2 = InputReader.readInt();

                    switch (choice2) {
                        case 1 -> cart.selectProduct();
                        case 2 -> cart.deselectProduct();
                        case 3 -> cart.selectAll();
                        case 4 -> cart.deselectAll();
                        case 5 -> System.out.print("");
                        default -> System.out.println("[!] Invalid option. Try again.");
                    }
                } while (choice2 != 5);
            }

            if (choice1 != 4 && !found)
                back();

        } while (choice1 != 4);
    }

    // ============================================| SEARCH FOR PRODUCTS |============================================

    public void showAllDetails() {

        int choice;
        boolean found = search.printAllProductDetails();

        if (session.getLastProductViewed() != null && found) {
            System.out.println("------------| REVIEWS |------------");
            System.out.println("[1] Show reviews");
            System.out.println("[2] <- Back");
            System.out.println("-----------------------------------");

            do {
                choice = InputReader.readInt();

                switch (choice) {
                    case 1 -> search.searchForLastProductViewedReview();
                    case 2 -> System.out.print("");
                    default -> System.out.println("[!] Invalid option. Try again.");
                }
            } while (choice != 1 && choice != 2);
        }
    }

    // ============================================| SEARCH FOR SELLERS |=============================================

    public void showSellerCatalog() {

        int choice;

        do {
            System.out.println("---------| SELLER CATALOG |--------");
            System.out.println("[1] Show a seller's catalog");
            System.out.println("[2] <- Back");
            System.out.println("-----------------------------------");

            choice = InputReader.readInt();

            switch (choice) {
                case 1 -> search.searchCatalogByStoreName();
                case 2 -> System.out.print("");
                default -> System.out.println("[!] Invalid option. Try again.");
            }

            if (choice == 1) {
                if (session.getLastStoreViewed() != null && !session.getLastStoreViewed().equalsIgnoreCase(session.getLoggedUserStoreName())) {
                    do {
                        System.out.println("------| " + session.getLastStoreViewed().toUpperCase() + " CATALOG |------");
                        System.out.println("[1] Add a " + session.getLastStoreViewed() + " product to cart");
                        System.out.println("[2] Show a " + session.getLastStoreViewed() + " product review");
                        System.out.println("[3] <- Back");
                        System.out.println("-----------------------------------");

                        choice = InputReader.readInt();

                        switch (choice) {
                            case 1 -> cart.addProductByStoreNameAndProductId();
                            case 2 -> search.searchForProductReviewByProductIdAndStoreName();
                            case 3 -> System.out.print("");
                            default -> System.out.println("[!] Invalid option. Try again.");
                        }
                    } while (choice != 3);
                }

                if (choice != 3)
                    back();
            }
        } while (choice != 2);
    }
}
