package com.vilkov.PriceMonitoring.controllers;


import com.vilkov.PriceMonitoring.controllers.entity.EntityHelper;
import com.vilkov.PriceMonitoring.controllers.entity.FixedPriceVO;
import com.vilkov.PriceMonitoring.model.ProductHelper;
import com.vilkov.PriceMonitoring.model.entity.Client;
import com.vilkov.PriceMonitoring.model.entity.FixedPrice;
import com.vilkov.PriceMonitoring.model.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

@Component
@RestController
@RequestMapping("api/analytics")
public class AnalyticsController {
    @Autowired
    ProductHelper productHelper;
    private final Logger logger = new Logger(Logger.getLoggerProperties().getProperty("controllersLogFolder"),
            Logger.getLoggerProperties().getProperty("controllersLogFileName"));

    @PostMapping("/getDynamicPrice")
    public Map<String, TreeSet<FixedPriceVO>> getDynamicPrice(@RequestParam("clientID") String clientID, @RequestParam("password") String password) {
        Client client = new Client(clientID, password.toCharArray());
        Map<String, TreeSet<FixedPrice>> products = productHelper.getSortedPrices(client);
        Map<String, TreeSet<FixedPriceVO>> result = new HashMap<>();
        for (Map.Entry<String, TreeSet<FixedPrice>> entry : products.entrySet()) {
            TreeSet<FixedPriceVO> fixedPriceVOTreeSet = new TreeSet<>();
            for (FixedPrice fixedPrice : entry.getValue()) {
                FixedPriceVO fixedPriceVO = EntityHelper.convertFixedPriceToFixedPriceVO(fixedPrice);
                fixedPriceVOTreeSet.add(fixedPriceVO);
            }
            result.put(entry.getKey(), fixedPriceVOTreeSet);
        }
        logger.save("successful get dynamic price for client: " + clientID);
        return result;
    }

}
