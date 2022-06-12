package com.vilkov.PriceMonitoring.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

@RestController
@RequestMapping("api/analytics")
public class AnalyticsController {
//    DataStorage dataStorage = new DataStorage();
//    @GetMapping("/getDynamicPrice/{clientID}")
//    public Map<String, TreeSet<FixedPrice>> getDynamicPrice(@PathVariable String clientID) {
//        List<BaseEntity> baseEntityList = dataStorage.readEntitiesInDataBase(new Client(clientID));
//        List<Product> products = new ArrayList<>();
//        for (BaseEntity baseEntity : baseEntityList) {
//            if (baseEntity instanceof Product) {
//                products.add((Product) baseEntity);
//            }
//        }
//        return ProductHelper.getSortedPrices(products);
//    }

}
