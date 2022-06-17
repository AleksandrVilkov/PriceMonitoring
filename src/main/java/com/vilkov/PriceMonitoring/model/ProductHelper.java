package com.vilkov.PriceMonitoring.model;

import com.vilkov.PriceMonitoring.model.dataStorage.DataStorage;
import com.vilkov.PriceMonitoring.model.entity.*;
import com.vilkov.PriceMonitoring.model.parsers.HTMLPageParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductHelper {
@Autowired
DataStorage dataStorage;

    public Map<String, TreeSet<FixedPrice>> getSortedPrices(String clientID) {

        List<BaseEntity> baseEntityList = dataStorage.readEntities(new Client(clientID), Product.class);
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
}
