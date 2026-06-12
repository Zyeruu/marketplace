package main.java.com.example.marketplace.config;

import main.java.com.example.marketplace.user.account.controller.AccountController;
import main.java.com.example.marketplace.user.account.repository.AccountRepository;
import main.java.com.example.marketplace.user.account.view.AccountView;
import main.java.com.example.marketplace.user.auth.controller.AuthController;
import main.java.com.example.marketplace.user.auth.repository.AuthRepository;
import main.java.com.example.marketplace.user.auth.view.AuthView;
import main.java.com.example.marketplace.user.controller.CartController;
import main.java.com.example.marketplace.user.controller.OrdersMenuController;
import main.java.com.example.marketplace.user.repository.CartRepository;
import main.java.com.example.marketplace.user.repository.OrdersMenuRepository;
import main.java.com.example.marketplace.user.search.controller.SearchController;
import main.java.com.example.marketplace.user.search.repository.SearchRepository;
import main.java.com.example.marketplace.user.search.view.SearchView;
import main.java.com.example.marketplace.user.store.controller.CatalogController;
import main.java.com.example.marketplace.user.store.controller.SalesMenuController;
import main.java.com.example.marketplace.user.store.controller.StoreController;
import main.java.com.example.marketplace.user.store.repository.CatalogRepository;
import main.java.com.example.marketplace.user.store.repository.SalesMenuRepository;
import main.java.com.example.marketplace.user.store.repository.StoreRepository;
import main.java.com.example.marketplace.user.store.view.CatalogView;
import main.java.com.example.marketplace.user.store.view.SalesMenuView;
import main.java.com.example.marketplace.user.store.view.StoreView;
import main.java.com.example.marketplace.user.view.CartView;
import main.java.com.example.marketplace.user.view.OrdersMenuView;
import main.java.com.example.marketplace.checkout.controller.CheckoutController;
import main.java.com.example.marketplace.checkout.repository.CheckoutRepository;
import main.java.com.example.marketplace.checkout.view.CheckoutView;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.shared.session.Session;

public class DependencyInjector {

    private final DataBase dataBase;

    private final Session session;

    private final AuthRepository authRepository;
    private final AccountRepository accountRepository;
    private final CheckoutRepository checkoutRepository;
    private final CartRepository cartRepository;
    private final OrdersMenuRepository ordersMenuRepository;
    private final StoreRepository storeRepository;
    private final SalesMenuRepository salesMenuRepository;
    private final CatalogRepository catalogRepository;
    private final SearchRepository searchRepository;

    private final AuthController authController;
    private final AccountController accountController;
    private final CheckoutController checkoutController;
    private final CartController cartController;
    private final OrdersMenuController ordersMenuController;
    private final StoreController storeController;
    private final SalesMenuController salesMenuController;
    private final CatalogController catalogController;
    private final SearchController searchController;

    public DependencyInjector() {

        this.dataBase = new DataBase();

        this.session = new Session();

        this.authRepository = new AuthRepository(dataBase, session);
        this.accountRepository = new AccountRepository(dataBase, session);
        this.checkoutRepository = new CheckoutRepository(dataBase, session);
        this.cartRepository = new CartRepository(dataBase, session);
        this.ordersMenuRepository = new OrdersMenuRepository(dataBase, session);
        this.storeRepository = new StoreRepository(dataBase, session);
        this.salesMenuRepository = new SalesMenuRepository(dataBase, session);
        this.catalogRepository = new CatalogRepository(dataBase, session);
        this.searchRepository = new SearchRepository(dataBase);

        this.authController = new AuthController(new AuthView(), authRepository, session);
        this.accountController = new AccountController(new AccountView(), accountRepository, session);
        this.checkoutController = new CheckoutController(new CheckoutView(), checkoutRepository);
        this.cartController = new CartController(new CartView(), cartRepository);
        this.ordersMenuController = new OrdersMenuController(new OrdersMenuView(), ordersMenuRepository);
        this.storeController = new StoreController(new StoreView(), storeRepository);
        this.salesMenuController = new SalesMenuController(new SalesMenuView(), salesMenuRepository);
        this.catalogController = new CatalogController(new CatalogView(), catalogRepository);
        this.searchController = new SearchController(new SearchView(), searchRepository);
    }

    // Getters
    public Session getSession() {
        return session;
    }

    public AuthController getAuthController() {
        return authController;
    }

    public AccountController getAccountController() {
        return accountController;
    }

    public CheckoutController getCheckoutController() {
        return checkoutController;
    }

    public CartController getCartController() {
        return cartController;
    }

    public OrdersMenuController getOrdersMenuController() {
        return ordersMenuController;
    }

    public StoreController getStoreController() {
        return storeController;
    }

    public SalesMenuController getSalesMenuController() {
        return salesMenuController;
    }

    public CatalogController getCatalogController() {
        return catalogController;
    }

    public SearchController getSearchController() {
        return searchController;
    }
}
