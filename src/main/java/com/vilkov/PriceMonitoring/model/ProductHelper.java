package com.vilkov.PriceMonitoring.model;

import com.vilkov.PriceMonitoring.dataBaseAdapter.Config;
import com.vilkov.PriceMonitoring.model.dataStorage.ProductDataStorage;
import com.vilkov.PriceMonitoring.model.parsers.ParserHelper;
import com.vilkov.PriceMonitoring.model.parsers.perekrestokParser.PerekrestokParser;

import java.util.*;
import java.util.logging.Logger;

public class ProductHelper {
    private static Logger logger = Logger.getLogger("ProductHelper");

    private static ProductDataStorage dataStorage = Config.getProductDataStorage();

    public static void createProductToDataBase(Product product) {
        dataStorage.createProduct(product);
    }

    public static List<Product> readProductInDataBase() {
        return dataStorage.readProducts();
    }

    public static void deleteProductInDataBase(String id) {
        dataStorage.deleteProduct(id);
    }

    public static void updateProductInDataBase(Product product) {
        dataStorage.updateProduct(product);
    }

    public static Map<String, TreeSet<FixedPrice>> getSortedPrices(List<Product> products) {
        Map<String, TreeSet<FixedPrice>> result = new HashMap<>();
        if (!products.isEmpty()) {
            for (Product product : products) {
                String name = product.getName().toLowerCase(Locale.ROOT);
                FixedPrice fixedPrice = new FixedPrice()
                        .setPrice(product.getPrice())
                        .setDate(product.getDate())
                        .setShop(product.getShop());
                if (result.containsKey(name)) {
                    result.get(name).add(fixedPrice);
                } else {
                    TreeSet<FixedPrice> fixedPrices = new TreeSet<>();
                    fixedPrices.add(fixedPrice);
                    result.put(name, fixedPrices);
                }
            }
        }

        return result;
    }

    public static FixedPrice getFixedPriceFromProduct(Product product) {
        return new FixedPrice().setPrice(product.getPrice()).setDate(product.getDate());
    }


    public static void getAndSaveCurrentPricesOfProducts() {
        Set<String> urls = ProductHelper.getAllProductUrlsFromDataBase();
        List<Product> result = new ArrayList<>();

        for (String url : urls) {
            result.add(ParserHelper.getProductsByAllShops(url));
        }

        for (Product product : result) {
            if (product != null && product.getMessage().getStatus().equals(Status.SUCCESS))
                ProductHelper.createProductToDataBase(product);
        }
    }

    private static Set<String> getAllProductUrlsFromDataBase() {
        List<Product> products = readProductInDataBase();
        Set<String> result = new HashSet<>();
        for (Product product : products) {
            result.add(product.getUlr());
        }
        return result;
    }


}
