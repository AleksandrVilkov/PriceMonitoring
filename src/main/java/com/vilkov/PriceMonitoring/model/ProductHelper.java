package com.vilkov.PriceMonitoring.model;

import com.vilkov.PriceMonitoring.model.dataStorage.DataStorageInterface;
import com.vilkov.PriceMonitoring.model.entity.BaseEntity;
import com.vilkov.PriceMonitoring.model.entity.Client;
import com.vilkov.PriceMonitoring.model.entity.FixedPrice;
import com.vilkov.PriceMonitoring.model.entity.Product;
import com.vilkov.PriceMonitoring.model.parsers.ParserHelper;

import java.util.*;
import java.util.logging.Logger;

public class ProductHelper {
    private static Logger logger = Logger.getLogger("ProductHelper");

    private static DataStorageInterface dataStorage;

    public static void createProductToDataBase(Product product, Client client) {
        dataStorage.createEntity(product, client);
    }

    public static List<BaseEntity> readProductInDataBase(Client client) {
        return dataStorage.readEntity(client, Product.class);
    }

    public static boolean deleteProductInDataBase(Client client, Class cls) {
        return dataStorage.deleteEntity(client, cls);
    }

    public static void updateProductInDataBase(Product product, Client client) {
        dataStorage.updateEntity(product, client);
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


    public static void getAndSaveCurrentPricesOfProducts(Client client) {
        Set<String> urls = ProductHelper.getAllProductUrlsFromDataBase(client);
        List<Product> result = new ArrayList<>();

        for (String url : urls) {
            result.add(ParserHelper.getProduct(url));
        }

        for (Product product : result) {
            if (product != null && product.getMessage().getStatus().equals(Status.SUCCESS))
                ProductHelper.createProductToDataBase(product, client);
        }
    }

    private static Set<String> getAllProductUrlsFromDataBase(Client client) {
        List<BaseEntity> products = readProductInDataBase(client);
        Set<String> result = new HashSet<>();
        for (BaseEntity product : products) {
            if (product instanceof Product) {
                result.add(((Product) product).getUlr());
            }
        }
        return result;
    }


}
