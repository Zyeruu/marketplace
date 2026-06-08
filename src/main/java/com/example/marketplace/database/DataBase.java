package main.java.com.example.marketplace.database;

import main.java.com.example.marketplace.buyer.model.Buyer;
import main.java.com.example.marketplace.buyer.model.CartProduct;
import main.java.com.example.marketplace.checkout.model.TaxReceipt;
import main.java.com.example.marketplace.seller.dto.CatalogRequest;
import main.java.com.example.marketplace.seller.model.Product;
import main.java.com.example.marketplace.seller.model.Seller;
import main.java.com.example.marketplace.shared.enums.ProductType;

import java.util.ArrayList;
import java.util.List;

public final class DataBase {

    // List of registered users and their respective database
    private static List<Buyer> buyerList = new ArrayList<>();
    private static List<Seller> sellerList = new ArrayList<>();
    private static List<Product> productList = new ArrayList<>();


    // =============================================| BUYER METHODS |=============================================

    // ---------------------------------------------| AUTH | ACCOUNT |--------------------------------------------

    public static void saveBuyer(Buyer buyer) {
        buyerList.add(buyer);
    }

    public static void deleteBuyer(Buyer buyer) {
        buyerList.remove(buyer);
    }


    // ---------------------------------------------| CART METHODS |---------------------------------------------

    public static void addProductToCart(String email, String productId, int quantity) {

        Buyer buyer = findBuyerByEmail(email);
        Product product = findProductById(productId);

        CartProduct item = new CartProduct(product.getName(), productId, product.getStoreName(), product.getType(), product.getUnitPrice(), product.getWeight(), quantity);
        buyer.getCart().getCartProductList().add(item);

        buyer.getCart().updateCart();
    }

    public static void updateProductQuantity(String email, String productId, int quantity) {

        Buyer buyer = findBuyerByEmail(email);

        for (CartProduct product : buyer.getCart().getCartProductList())
            if (product.getId().equals(productId)) {
                product.setQuantity(product.getQuantity() + quantity);
                break;
            }
    }

    public static void removeProductFromCart(String buyerEmail, String productId, int quantity) {

        Buyer buyer = findBuyerByEmail(buyerEmail);

        for (CartProduct product : buyer.getCart().getCartProductList())
            if (product.getId().equals(productId)) {
                if (quantity >= product.getQuantity()) {
                    buyer.getCart().getCartProductList().remove(product);
                    buyer.getCart().updateCart();
                } else
                    product.setQuantity(product.getQuantity() - quantity);
                break;
            }
    }

    public static boolean existsCartProductByEmailAndId(String buyerEmail, String id) {

        Buyer buyer = findBuyerByEmail(buyerEmail);

        for (CartProduct product : buyer.getCart().getCartProductList())
            if (product.getId().equals(id))
                return true;
        return false;
    }

    public static int findCartProductQuantityByEmailAndId(String buyerEmail, String id) {

        Buyer buyer = findBuyerByEmail(buyerEmail);

        for (CartProduct product : buyer.getCart().getCartProductList())
            if (product.getId().equals(id))
                return product.getQuantity();
        return 0;
    }

    // ------------------------------------------| ORDERS-MENU METHODS |-------------------------------------------

    public static List<TaxReceipt> findBuyerTaxReceiptListByEmail(String email) {

        Buyer buyer = findBuyerByEmail(email);
        return buyer.getOrdersMenu().getTaxReceiptList();
    }

    public static TaxReceipt findBuyerTaxReceiptByEmailAndOrderId(String email, String orderId) {

        List<TaxReceipt> taxReceiptList = findBuyerTaxReceiptListByEmail(email);

        for (TaxReceipt taxReceipt : taxReceiptList)
            if (taxReceipt.getOrderId().equals(orderId))
                return taxReceipt;
        return null;
    }

    // ---------------------------------------------| OTHERS METHODS |---------------------------------------------

    public static boolean existsBuyerByEmail(String email) {

        for (Buyer buyer : buyerList)
            if (buyer.getEmail().equals(email))
                return true;
        return false;
    }

    public static Buyer findBuyerByEmail(String email) {

        for (Buyer buyer : buyerList)
            if (buyer.getEmail().equals(email))
                return buyer;
        return null;
    }

    public static boolean existsBuyerByEmailAndPassword(String email, String password) {

        for (Buyer buyer : buyerList)
            if (buyer.getEmail().equals(email))
                return buyer.getPassword().equals(password);
        return false;
    }


    // ==============================================| SELLER METHODS |==============================================

    // ----------------------------------------------| AUTH | ACCOUNT |----------------------------------------------

    public static void saveSeller(Seller seller) {
        sellerList.add(seller);
    }

    public static void deleteSeller(Seller seller) {
        sellerList.remove(seller);
    }

    // -----------------------------------------------| CATALOG METHODS |--------------------------------------------

    public static boolean existsCatalogProductByIdAndCnpj(String productId, String cnpj) {

        Seller seller = findSellerByCnpj(cnpj);

        for (Product product : seller.getStore().getCatalog().getProductList())
            if (product.getId().equals(productId))
                return true;
        return false;
    }

    public static boolean existsCatalogProductByNameAndCnpj(String productName, String cnpj) {

        Seller seller = findSellerByCnpj(cnpj);

        for (Product product : seller.getStore().getCatalog().getProductList())
            if (product.getName().equalsIgnoreCase(productName))
                return true;
        return false;
    }

    public static void addProductToCatalog(Product product, String cnpj) {

        Seller seller = findSellerByCnpj(cnpj);

        productList.add(product);
        seller.getStore().getCatalog().getProductList().add(product);
        seller.getStore().getCatalog().updateCatalog();
    }

    public static void removeCatalogProduct(String productId, String cnpj) {

        Seller seller = findSellerByCnpj(cnpj);

        for (Product product : seller.getStore().getCatalog().getProductList())
            if (product.getId().equals(productId)) {
                productList.remove(product);
                seller.getStore().getCatalog().getProductList().remove(product);
                break;
            }
    }

    public static void updateProductStock(CatalogRequest catalogRequest) {

        for (Product product : productList)
            if (product.getId().equals(catalogRequest.getId())) {
                product.setStock(catalogRequest.getQuantity());
                break;
            }
    }

    public static void updateProductPrice(CatalogRequest catalogRequest) {

        for (Product product : productList)
            if (product.getId().equals(catalogRequest.getId())) {
                product.setPrice(catalogRequest.getPrice());
                break;
            }
    }

    // --------------------------------------------| SALES-MENU METHODS |--------------------------------------------

    public static List<TaxReceipt> findSellerTaxReceiptListByEmail(String email) {

        Seller seller = findSellerByEmail(email);
        return seller.getStore().getSalesMenu().getTaxReceiptsList();
    }

    public static TaxReceipt findSellerTaxReceiptByEmailAndOrderId(String email, String orderId) {

        List<TaxReceipt> taxReceiptList = findSellerTaxReceiptListByEmail(email);

        for (TaxReceipt taxReceipt : taxReceiptList)
            if (taxReceipt.getOrderId().equals(orderId))
                return taxReceipt;
        return null;
    }

    // ----------------------------------------------| OTHERS METHODS |----------------------------------------------

    public static boolean existsSellerByEmail(String email) {

        for (Seller seller : sellerList)
            if (seller.getEmail().equals(email))
                return true;
        return false;
    }

    public static Seller findSellerByEmail(String email) {

        for (Seller seller : sellerList)
            if (seller.getEmail().equals(email))
                return seller;
        return null;
    }

    public static boolean existsSellerByCnpj(String cnpj) {

        for (Seller seller : sellerList)
            if (seller.getStore().getCnpj().equals(cnpj))
                return true;
        return false;
    }

    public static Seller findSellerByCnpj(String cnpj) {

        for (Seller seller : sellerList)
            if (seller.getStore().getCnpj().equals(cnpj))
                return seller;
        return null;
    }

    public static boolean existsSellerByStoreName(String storeName) {

        for (Seller seller : sellerList)
            if (seller.getStore().getName().equals(storeName))
                return true;
        return false;
    }

    public static boolean existsSellerByEmailAndPassword(String email, String password) {

        for (Seller seller : sellerList)
            if (seller.getEmail().equals(email))
                return seller.getPassword().equals(password);
        return false;
    }

    public static Seller findSellerByEmailAndPassword(String email, String password) {

        for (Seller seller : sellerList)
            if (seller.getEmail().equals(email))
                if (seller.getPassword().equals(password))
                    return seller;
        return null;
    }

    public static int getNumberOfSellers() {
        return sellerList.size();
    }


    // --------------------------------------------| CHECKOUT METHODS |--------------------------------------------

    public static boolean isCartEmptyByEmail(String email) {

        Buyer buyer = findBuyerByEmail(email);
        return buyer.getCart().getCartProductList().isEmpty();
    }

    public static List<CartProduct> findCartProductListByEmail(String email) {

        Buyer buyer = findBuyerByEmail(email);
        return buyer.getCart().getCartProductList();
    }

    public static Seller findSellerByStoreName(String storeName) {

        for (Seller seller : sellerList)
            if (seller.getStore().getName().equals(storeName))
                return seller;
        return null;
    }

    public static List<Product> findCatalogProductListByStoreName(String storeName) {

        Seller seller = findSellerByStoreName(storeName);
        return seller.getStore().getCatalog().getProductList();
    }

    public static void removeProductFromCatalog(String storeName, Product product, int quantity) {

        Seller seller = findSellerByStoreName(storeName);

        if (quantity == product.getStock()) {
            removeFromProductList(product);
            seller.getStore().getCatalog().getProductList().remove(product);
        }
        else
            product.updateStock(product.getStock() - quantity);

        seller.getStore().getCatalog().updateCatalog();
    }


    // ---------------------------------------------| PRODUCT LIST |--------------------------------------------

    public static boolean isProductListEmpty() {
        return productList.isEmpty();
    }

    public static void addToProductList(Product product) {
        productList.add(product);
    }

    public static void removeFromProductList(Product product) {
        productList.remove(product);
    }

    public static boolean existsProductByName(String productName) {

        for (Product product : productList)
            if (product.getName().equalsIgnoreCase(productName))
                return true;
        return false;
    }

    public static boolean existsProductByType(ProductType productType) {

        for (Product product : productList)
            if (product.getType() == productType)
                return true;
        return false;
    }

    public static boolean existsProductById(String id) {

        for (Product product : productList)
            if (product.getId().equals(id))
                return true;
        return false;
    }

    public static Product findProductById(String id) {

        for (Product product : productList)
            if (product.getId().equals(id))
                return product;
        return null;
    }

    public static int getProductStockById(String id) {

        Product product = findProductById(id);
        return product.getStock();
    }

    public static List<Product> getProductList() {
        return productList;
    }
}
