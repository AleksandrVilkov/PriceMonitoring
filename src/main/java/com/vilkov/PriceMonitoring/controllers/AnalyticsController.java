package com.vilkov.PriceMonitoring.controllers;


import com.vilkov.PriceMonitoring.model.ProductHelper;
import com.vilkov.PriceMonitoring.model.entity.BaseEntity;
import com.vilkov.PriceMonitoring.model.entity.Client;
import com.vilkov.PriceMonitoring.model.entity.FixedPrice;
import com.vilkov.PriceMonitoring.model.entity.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

@RestController
@RequestMapping("api/analytics")
public class AnalyticsController {

    @GetMapping("/getDynamicPrice/{clientID}")
    public Map<String, TreeSet<FixedPrice>> getDynamicPrice(@PathVariable String clientID) {
        List<BaseEntity> baseEntityList = ProductHelper.readProductInDataBase(new Client(clientID));
        List<Product> products = new ArrayList<>();
        for (BaseEntity baseEntity : baseEntityList) {
            if (baseEntity instanceof Product) {
                products.add((Product) baseEntity);
            }
        }
        return ProductHelper.getSortedPrices(products);
    }

}
