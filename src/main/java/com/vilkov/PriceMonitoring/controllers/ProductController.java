package com.vilkov.PriceMonitoring.controllers;


import com.vilkov.PriceMonitoring.controllers.entity.EntityHelper;
import com.vilkov.PriceMonitoring.controllers.entity.ProductVO;
import com.vilkov.PriceMonitoring.model.logger.Logger;
import com.vilkov.PriceMonitoring.model.ProductHelper;
import com.vilkov.PriceMonitoring.model.entity.Client;
import com.vilkov.PriceMonitoring.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Component
@RestController
@RequestMapping("api/product")
public class ProductController {
    @Autowired
    ProductHelper productHelper;

    Logger logger = new Logger("Product_Controller_log", "log.txt");

    @PostMapping("/getAllProduct")
    public List<ProductVO> getAllClientProducts(@RequestParam("clientID") String clientID,
                                                @RequestParam("password") String password) {
        List<Product> products = productHelper.getAllClientProducts(new Client(clientID, password.toCharArray()));
        List<ProductVO> result = new ArrayList<>();
        for (Product product : products) {
            result.add(EntityHelper.convertProductToProductVO(product));
        }
        return result;
    }

    @PostMapping("/getAllProductFromDate")
    public List<ProductVO> getClientProductsFromPeriod(@RequestParam("clientID") String clientID,
                                                       @RequestParam("password") String password,
                                                       @RequestParam("startDay") String startDay,
                                                       @RequestParam("endDay") String endDay) {
        List<ProductVO> productsVO = new ArrayList<>();
        List<Product> products = productHelper.getClientProductsFromPeriod(new Client(clientID, password.toCharArray()), startDay, endDay);
        for (Product product : products) {
            productsVO.add(EntityHelper.convertProductToProductVO(product));
        }
        return productsVO;
    }


    @PostMapping("/getAllProductFromShop")
    public List<ProductVO> getClientProductsFromShops(@RequestParam("clientID") String clientID,
                                                      @RequestParam("password") String password,
                                                      @RequestParam("shop") String shop) {
        List<Product> products = productHelper.getClientProductsFromShops(shop, new Client(clientID, password.toCharArray()));
        List<ProductVO> productsVO = new ArrayList<>();
        for (Product product : products) {
            productsVO.add(EntityHelper.convertProductToProductVO(product));
        }
        return productsVO;
    }
}
