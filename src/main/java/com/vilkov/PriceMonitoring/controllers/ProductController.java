package com.vilkov.PriceMonitoring.controllers;



import com.vilkov.PriceMonitoring.controllers.entity.EntityHelper;
import com.vilkov.PriceMonitoring.controllers.entity.ProductVO;
import com.vilkov.PriceMonitoring.model.dataStorage.DataStorage;
import com.vilkov.PriceMonitoring.model.entity.BaseEntity;
import com.vilkov.PriceMonitoring.model.entity.Client;
import com.vilkov.PriceMonitoring.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RestController
@RequestMapping("api/product")
public class ProductController {
    @Autowired
    DataStorage dataStorage;

    @GetMapping("/getAllProduct/{clientID}")
    public List<ProductVO> getAllClientProducts(@PathVariable String clientID) {
        List<BaseEntity> baseEntityList = dataStorage.readEntities(new Client(clientID), Product.class);
        List<ProductVO> result = new ArrayList<>();
        for (BaseEntity baseEntity : baseEntityList) {
            if (baseEntity instanceof Product) {
                result.add(EntityHelper.convertProductToProductVO((Product) baseEntity));
            }
        }
        return result;
    }

    @PostMapping("/getAllProductFromDate/{clientID}")
    public List<ProductVO> getClientProductsFromPeriod(@PathVariable String clientID, @RequestParam("startDay") String startDay, @RequestParam("endDay") String endDay) {
        Date startDate = ControllerHelper.getDateFromString(startDay);
        Date endDate = ControllerHelper.getDateFromString(endDay);
        endDate.setHours(23);
        endDate.setMinutes(59);
        endDate.setSeconds(59);
        List<BaseEntity> baseEntityList = dataStorage.readEntities(new Client(clientID), Product.class);
        List<ProductVO> products = new ArrayList<>();
        for (BaseEntity baseEntity : baseEntityList) {
            if (baseEntity instanceof Product) {
                Date productDate = ((Product) baseEntity).getDate();
                if (!productDate.before(startDate) && !productDate.after(endDate))
                    products.add(EntityHelper.convertProductToProductVO((Product) baseEntity));
            }
        }

        return products;
    }


    @PostMapping("/getAllProductFromShop/{clientID}")
    public List<ProductVO> getClientProductsFromShops(@RequestParam("shop") String shop, @PathVariable String clientID) {
        List<BaseEntity> baseEntityList = dataStorage.readEntities(new Client(clientID), Product.class);
        List<ProductVO> products = new ArrayList<>();
        for (BaseEntity baseEntity : baseEntityList) {
            if (baseEntity instanceof Product) {
                String productShop = ((Product) baseEntity).getShop();
                if (productShop.equalsIgnoreCase(shop)) {
                    products.add(EntityHelper.convertProductToProductVO((Product) baseEntity));
                }
            }
        }
        return products;
    }
}
