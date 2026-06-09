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

public class DependencyInjector {

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

        this.buyerAuthRepository = new BuyerAuthRepository();
        this.buyerAccountRepository = new BuyerAccountRepository();
        this.cartRepository = new CartRepository();
        this.ordersMenuRepository = new OrdersMenuRepository();
        this.buyerSearchRepository = new BuyerSearchRepository();
        this.checkoutRepository = new CheckoutRepository();

        this.sellerAuthRepository = new SellerAuthRepository();
        this.sellerAccountRepository = new SellerAccountRepository();
        this.catalogRepository = new CatalogRepository();
        this.salesMenuRepository = new SalesMenuRepository();

        this.buyerAuthController = new BuyerAuthController(new BuyerAuthView(), buyerAuthRepository);
        this.buyerAccountController = new BuyerAccountController(new BuyerAccountView(), buyerAccountRepository);
        this.cartController = new CartController(new CartView(), cartRepository);
        this.ordersMenuController = new OrdersMenuController(new OrdersMenuView(), ordersMenuRepository);
        this.searchController = new BuyerSearchController(new BuyerSearchView(), buyerSearchRepository);
        this.checkoutController = new CheckoutController(new CheckoutView(), checkoutRepository);

        this.sellerAuthController = new SellerAuthController(new SellerAuthView(), sellerAuthRepository);
        this.sellerAccountController = new SellerAccountController(new SellerAccountView(), sellerAccountRepository);
        this.catalogController = new CatalogController(new CatalogView(), catalogRepository);
        this.salesMenuController = new SalesMenuController(new SalesMenuView(), salesMenuRepository);
    }

    // Getters
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
