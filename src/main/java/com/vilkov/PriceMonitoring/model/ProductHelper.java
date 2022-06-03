package com.vilkov.PriceMonitoring.model;

import com.vilkov.PriceMonitoring.dataBaseAdapter.Config;
import com.vilkov.PriceMonitoring.model.dataStorage.ProductDataStorage;

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

}
