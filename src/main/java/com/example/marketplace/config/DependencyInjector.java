package main.java.com.example.marketplace.config;

import main.java.com.example.marketplace.buyer.account.controller.BuyerAccountController;
import main.java.com.example.marketplace.buyer.account.repository.BuyerAccountRepository;
import main.java.com.example.marketplace.buyer.account.view.BuyerAccountView;
import main.java.com.example.marketplace.buyer.auth.controller.BuyerAuthController;
import main.java.com.example.marketplace.buyer.auth.repository.BuyerAuthRepository;
import main.java.com.example.marketplace.buyer.auth.view.BuyerAuthView;
import main.java.com.example.marketplace.buyer.controller.CartController;
import main.java.com.example.marketplace.buyer.controller.OrdersMenuController;
import main.java.com.example.marketplace.buyer.repository.CartRepository;
import main.java.com.example.marketplace.buyer.repository.OrdersMenuRepository;
import main.java.com.example.marketplace.buyer.search.controller.BuyerSearchController;
import main.java.com.example.marketplace.buyer.search.repository.BuyerSearchRepository;
import main.java.com.example.marketplace.buyer.search.view.BuyerSearchView;
import main.java.com.example.marketplace.buyer.view.CartView;
import main.java.com.example.marketplace.buyer.view.OrdersMenuView;
import main.java.com.example.marketplace.checkout.controller.CheckoutController;
import main.java.com.example.marketplace.checkout.repository.CheckoutRepository;
import main.java.com.example.marketplace.checkout.view.CheckoutView;
import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.seller.account.controller.SellerAccountController;
import main.java.com.example.marketplace.seller.account.repository.SellerAccountRepository;
import main.java.com.example.marketplace.seller.account.view.SellerAccountView;
import main.java.com.example.marketplace.seller.auth.controller.SellerAuthController;
import main.java.com.example.marketplace.seller.auth.repository.SellerAuthRepository;
import main.java.com.example.marketplace.seller.auth.view.SellerAuthView;
import main.java.com.example.marketplace.seller.controller.CatalogController;
import main.java.com.example.marketplace.seller.controller.SalesMenuController;
import main.java.com.example.marketplace.seller.repository.CatalogRepository;
import main.java.com.example.marketplace.seller.repository.SalesMenuRepository;
import main.java.com.example.marketplace.seller.view.CatalogView;
import main.java.com.example.marketplace.seller.view.SalesMenuView;
import main.java.com.example.marketplace.shared.session.Session;

public class DependencyInjector {

    private final DataBase dataBase;

    private final Session session;

    private final BuyerAuthRepository buyerAuthRepository;
    private final BuyerAccountRepository buyerAccountRepository;
    private final CartRepository cartRepository;
    private final OrdersMenuRepository ordersMenuRepository;
    private final BuyerSearchRepository buyerSearchRepository;
    private final CheckoutRepository checkoutRepository;

    private final SellerAuthRepository sellerAuthRepository;
    private final SellerAccountRepository sellerAccountRepository;
    private final CatalogRepository catalogRepository;
    private final SalesMenuRepository salesMenuRepository;

    private final BuyerAuthController buyerAuthController;
    private final BuyerAccountController buyerAccountController;
    private final CartController cartController;
    private final OrdersMenuController ordersMenuController;
    private final BuyerSearchController searchController;
    private final CheckoutController checkoutController;

    private final SellerAuthController sellerAuthController;
    private final SellerAccountController sellerAccountController;
    private final CatalogController catalogController;
    private final SalesMenuController salesMenuController;

    public DependencyInjector() {

        this.dataBase = new DataBase();

        this.session = new Session();

        this.buyerAuthRepository = new BuyerAuthRepository(dataBase);
        this.buyerAccountRepository = new BuyerAccountRepository(dataBase, session);
        this.cartRepository = new CartRepository(dataBase, session);
        this.ordersMenuRepository = new OrdersMenuRepository(dataBase, session);
        this.buyerSearchRepository = new BuyerSearchRepository(dataBase);
        this.checkoutRepository = new CheckoutRepository(dataBase, session);

        this.sellerAuthRepository = new SellerAuthRepository(dataBase);
        this.sellerAccountRepository = new SellerAccountRepository(dataBase, session);
        this.catalogRepository = new CatalogRepository(dataBase, session);
        this.salesMenuRepository = new SalesMenuRepository(dataBase, session);

        this.buyerAuthController = new BuyerAuthController(new BuyerAuthView(), buyerAuthRepository, session);
        this.buyerAccountController = new BuyerAccountController(new BuyerAccountView(), buyerAccountRepository, session);
        this.cartController = new CartController(new CartView(), cartRepository);
        this.ordersMenuController = new OrdersMenuController(new OrdersMenuView(), ordersMenuRepository);
        this.searchController = new BuyerSearchController(new BuyerSearchView(), buyerSearchRepository);
        this.checkoutController = new CheckoutController(new CheckoutView(), checkoutRepository);

        this.sellerAuthController = new SellerAuthController(new SellerAuthView(), sellerAuthRepository, session);
        this.sellerAccountController = new SellerAccountController(new SellerAccountView(), sellerAccountRepository, session);
        this.catalogController = new CatalogController(new CatalogView(), catalogRepository);
        this.salesMenuController = new SalesMenuController(new SalesMenuView(), salesMenuRepository);
    }

    // Getters
    public Session getSession() {
        return session;
    }

    public BuyerAuthController getBuyerAuthController() {
        return buyerAuthController;
    }

    public BuyerAccountController getBuyerAccountController() {
        return buyerAccountController;
    }

    public CartController getCartController() {
        return cartController;
    }

    public OrdersMenuController getOrdersMenuController() {
        return ordersMenuController;
    }

    public BuyerSearchController getSearchController() {
        return searchController;
    }

    public CheckoutController getCheckoutController() {
        return checkoutController;
    }

    public SellerAuthController getSellerAuthController() {
        return sellerAuthController;
    }

    public SellerAccountController getSellerAccountController() {
        return sellerAccountController;
    }

    public CatalogController getCatalogController() {
        return catalogController;
    }

    public SalesMenuController getSalesMenuController() {
        return salesMenuController;
    }
}
