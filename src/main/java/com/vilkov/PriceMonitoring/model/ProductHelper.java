package com.vilkov.PriceMonitoring.model;

import com.vilkov.PriceMonitoring.model.dataStorage.DataStorage;
import com.vilkov.PriceMonitoring.model.entity.*;
import com.vilkov.PriceMonitoring.model.parsers.ParserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.logging.Logger;

@Component
public class ProductHelper {
    private static final Logger logger = Logger.getLogger("ProductHelper");
@Autowired
DataStorage dataStorage;

    public void getAndSaveCurrentPricesOfProducts(Client client) {
        List<BaseEntity> baseEntities = dataStorage.readEntities(client, Product.class);
        List<Product> result = new ArrayList<>();
        for (BaseEntity baseEntity : baseEntities) {
            if (baseEntity instanceof MonitoringList) {
                List<String> urls = ((MonitoringList) baseEntity).getUrls();
                for (String url : urls) {
                    result.add(ParserHelper.getProduct(url));
                }
            }
        }

        for (Product product : result) {
            if (product != null && product.getMessage().getStatus().equals(Status.SUCCESS))
                dataStorage.createEntity(product, client);
        }
    }
}
