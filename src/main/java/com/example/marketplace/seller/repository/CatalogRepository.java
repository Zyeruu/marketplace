package main.java.com.example.marketplace.seller.repository;

import main.java.com.example.marketplace.database.DataBase;
import main.java.com.example.marketplace.exceptions.AlreadyExistsException;
import main.java.com.example.marketplace.exceptions.EmptyCatalogException;
import main.java.com.example.marketplace.exceptions.NotFoundException;
import main.java.com.example.marketplace.seller.dto.CatalogRequest;
import main.java.com.example.marketplace.seller.dto.CatalogResponse;
import main.java.com.example.marketplace.seller.model.Product;
import main.java.com.example.marketplace.seller.model.Seller;
import main.java.com.example.marketplace.shared.enums.ProductType;
import main.java.com.example.marketplace.shared.session.SellerSession;

import java.util.ArrayList;
import java.util.List;

public final class CatalogRepository {

    public CatalogResponse findByEmail() {

        String email = SellerSession.getEmail();
        Seller seller = DataBase.findSellerByEmail(email);

        if (!DataBase.existsSellerByEmail(email))
            throw new NotFoundException("[!] Logged-in user does not exist.");

        List<Product> productListPointer = seller.getStore().getCatalog().getProductList();

        if (productListPointer.isEmpty())
            throw new EmptyCatalogException("[!] Your catalog is empty.");

        List<Product> productListCopy = new ArrayList<>();

        for (Product product : productListPointer)
            productListCopy.add(new Product(product));

        int totalProducts = seller.getStore().getCatalog().getTotalProducts();
        int totalFood = seller.getStore().getCatalog().getTotalFood();
        int totalMisc = seller.getStore().getCatalog().getTotalMisc();

        return new CatalogResponse(productListCopy, totalProducts, totalFood, totalMisc);
    }

    public CatalogResponse findByEmailAndProductType(ProductType productType) {

        String email = SellerSession.getEmail();
        Seller seller = DataBase.findSellerByEmail(email);

        if (seller == null)
            throw new NotFoundException("[!] Logged-in user does not exist.");

        List<Product> productListPointer = seller.getStore().getCatalog().getProductList();

        if (productListPointer.isEmpty())
            throw new EmptyCatalogException("[!] Your catalog is empty.");

        List<Product> productListCopy = new ArrayList<>();

        for (Product product : productListPointer)
            if (product.getType() == productType)
                productListCopy.add(new Product(product));

        if (productListCopy.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        if (productType == ProductType.FOOD) {
            int totalFood = seller.getStore().getCatalog().getTotalFood();
            return new CatalogResponse(productListCopy, 0, totalFood, 0);
        }
        else {
            int totalMisc = seller.getStore().getCatalog().getTotalMisc();
            return new CatalogResponse(productListCopy, 0, 0, totalMisc);
        }
    }

    public CatalogResponse findByEmailAndProductName(String productName) {

        String email = SellerSession.getEmail();
        Seller seller = DataBase.findSellerByEmail(email);

        if (seller == null)
            throw new NotFoundException("[!] Logged-in user does not exist.");

        List<Product> productListPointer = seller.getStore().getCatalog().getProductList();

        if (productListPointer.isEmpty())
            throw new EmptyCatalogException("[!] Your catalog is empty.");

        List<Product> productListCopy = new ArrayList<>();

        int totalProducts;
        int totalFood = 0;
        int totalMisc = 0;

        for (Product product : productListPointer) {
            if (product.getName().equalsIgnoreCase(productName)) {
                productListCopy.add(new Product(product));

                if (product.getType() == ProductType.FOOD)
                    totalFood++;
                else
                    totalMisc++;
            }
        }

        totalProducts = totalFood + totalMisc;

        if (productListCopy.isEmpty())
            throw new NotFoundException("[!] No results were found.");

        return new CatalogResponse(productListCopy, totalProducts, totalFood, totalMisc);
    }

    public Product findByEmailAndProductId(String productId) {

        String email = SellerSession.getEmail();

        if (!DataBase.existsSellerByEmail(email))
            throw new NotFoundException("[!] Logged-in user does not exist.");

        List<Product> productListPointer = DataBase.findCatalogProductListByEmail(email);

        if (productListPointer.isEmpty())
            throw new EmptyCatalogException("[!] Your catalog is empty.");

        Product catalogProduct = null;

        for (Product product : productListPointer)
            if (product.getId().equals(productId)) {
                catalogProduct = new Product(product);
                break;
            }

        if (catalogProduct == null)
            throw new NotFoundException("The product with ID \"" + productId + "\" was not found.");

        return catalogProduct;
    }

    public void saveProduct(Product product) {

        String cnpj = SellerSession.getCnpj();
        String productName = product.getName();

        if (DataBase.existsCatalogProductByNameAndCnpj(productName, cnpj))
            throw new AlreadyExistsException("[!] The product you want to add already exists in your catalog.");

        DataBase.addProductToCatalog(product, cnpj);
    }

    public void removeProduct(String productId) {

        String cnpj = SellerSession.getCnpj();

        if (!DataBase.existsCatalogProductByIdAndCnpj(productId, cnpj))
            throw new NotFoundException("[!] The product with ID \"" + productId + "\" was not found.");

        DataBase.removeCatalogProduct(productId, cnpj);
    }

    public void updateProductStock(CatalogRequest catalogRequest) {

        String cnpj = SellerSession.getCnpj();

        if (!DataBase.existsCatalogProductByIdAndCnpj(catalogRequest.getId(), cnpj))
            throw new NotFoundException("[!] The product with ID \"" + catalogRequest.getId() + "\" was not found.");

        DataBase.updateProductStock(catalogRequest);
    }

    public void updateProductPrice(CatalogRequest catalogRequest) {

        String cnpj = SellerSession.getCnpj();

        if (!DataBase.existsCatalogProductByIdAndCnpj(catalogRequest.getId(), cnpj))
            throw new NotFoundException("[!] The product with ID \"" + catalogRequest.getId() + "\" was not found.");

        DataBase.updateProductPrice(catalogRequest);
    }
}
