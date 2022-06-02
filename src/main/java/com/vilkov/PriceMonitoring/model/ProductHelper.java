package com.vilkov.PriceMonitoring.model;

import com.vilkov.PriceMonitoring.model.dataStorage.ProductDataStorage;

import java.util.*;
import java.util.logging.Logger;

public class ProductHelper {
    private static Logger logger = Logger.getLogger("ProductHelper");
    private static ProductDataStorage dataStorage;

    public static void createProductToDataBase(Product product) {
        dataStorage.createProduct(product);
    }

    public static void readProductInDataBase(String id) {
        dataStorage.readProduct(id);
    }

    public static void deleteProductInDataBase(String id) {
        dataStorage.deleteProduct(id);
    }

    public static void updateProductInDataBase(String id) {
        dataStorage.updateProduct(id);
    }


    public static Map<String, TreeSet<FixedPrice>> getSortedPrices(List<Product> products) {
        Map<String, TreeSet<FixedPrice>> result = new HashMap<>();
        if (!products.isEmpty()) {
            for (Product product : products) {
                String name = product.getName();
                FixedPrice fixedPrice = new FixedPrice().setPrice(product.getPrice()).setDate(product.getDate());
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
