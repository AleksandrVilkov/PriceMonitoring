package com.vilkov.PriceMonitoring.model;

import com.vilkov.PriceMonitoring.model.dataStorage.DataStorage;
import com.vilkov.PriceMonitoring.model.entity.*;
import com.vilkov.PriceMonitoring.model.parser.HTMLPageParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ProductHelper {
    @Autowired
    DataStorage dataStorage;

    //TODO добавить методов по аналитике: отдавать динамику, искать максимальную цену, реализовать сканирование и создание уведомления о снижении цены
    public List<Product> getClientProductsFromShops(String shop, Client client) {
        List<Product> productList = getAllClientProducts(client);
        List<Product> result = new ArrayList<>();
        for (Product product : productList) {
            if (product.getShop().equalsIgnoreCase(shop)) {
                result.add(product);
            }
        }
        return result;
    }

    public List<Product> getClientProductsFromPeriod(Client client, String startDay, String endDay) {
        Date startDate = getDateFromString(startDay);
        Date endDate = getDateFromString(endDay);
        endDate.setHours(23);
        endDate.setMinutes(59);
        endDate.setSeconds(59);
        List<Product> allProducts = getAllClientProducts(client);
        List<Product> result = new ArrayList<>();
        for (Product product : allProducts) {
            Date productDate = product.getDate();
            if (!productDate.before(startDate) && !productDate.after(endDate)) {
                result.add(product);
            }
        }
        return result;
    }

    public List<Product> getAllClientProducts(Client client) {
        List<BaseEntity> baseEntityList = dataStorage.readEntities(client, Product.class);
        List<Product> result = new ArrayList<>();
        for (BaseEntity baseEntity : baseEntityList) {
            if (baseEntity instanceof Product) {
                result.add((Product) baseEntity);
            }
        }
        return result;
    }


    public Map<String, TreeSet<FixedPrice>> getSortedPrices(Client client) {

        List<BaseEntity> baseEntityList = dataStorage.readEntities(client, Product.class);
        List<Product> products = new ArrayList<>();
        for (BaseEntity baseEntity : baseEntityList) {
            if (baseEntity instanceof Product) {
                products.add((Product) baseEntity);
            }
        }
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

    public void getAndSaveCurrentPricesOfProducts(Client client) {
        List<BaseEntity> baseEntities = dataStorage.readEntities(client, MonitoringList.class);
        List<Product> result = new ArrayList<>();
        for (BaseEntity baseEntity : baseEntities) {
            if (baseEntity instanceof MonitoringList) {
                List<String> urls = ((MonitoringList) baseEntity).getUrls();
                for (String url : urls) {
                    result.add(HTMLPageParser.searchProduct(url));
                }
            }
        }

        for (Product product : result) {
            if (product != null && product.getMessage().getStatus().equals(Status.SUCCESS))
                dataStorage.createEntity(product, client);
        }
    }

    public static Date getDateFromString(String stringDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            return dateFormat.parse(stringDate);
        } catch (ParseException e) {
            return null;
        }
    }
}
