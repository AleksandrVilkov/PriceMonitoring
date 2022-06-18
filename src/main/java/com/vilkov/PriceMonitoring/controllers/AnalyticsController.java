package com.vilkov.PriceMonitoring.controllers;


import com.vilkov.PriceMonitoring.controllers.entity.EntityHelper;
import com.vilkov.PriceMonitoring.controllers.entity.FixedPriceVO;
import com.vilkov.PriceMonitoring.model.ProductHelper;
import com.vilkov.PriceMonitoring.model.entity.FixedPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/getDynamicPrice/{clientID}")
    public Map<String, TreeSet<FixedPriceVO>> getDynamicPrice(@PathVariable String clientID) {
        Map<String, TreeSet<FixedPrice>> products = productHelper.getSortedPrices(clientID);
        Map<String, TreeSet<FixedPriceVO>> result = new HashMap<>();
        for (Map.Entry<String, TreeSet<FixedPrice>> entry : products.entrySet()) {
            TreeSet<FixedPriceVO> fixedPriceVOTreeSet = new TreeSet<>();

            for (FixedPrice fixedPrice : entry.getValue()) {
                FixedPriceVO fixedPriceVO = EntityHelper.convertFixedPriceToFixedPriceVO(fixedPrice);
                fixedPriceVOTreeSet.add(fixedPriceVO);
            }
            result.put(entry.getKey(), fixedPriceVOTreeSet);
        }

        return result;
    }

}
