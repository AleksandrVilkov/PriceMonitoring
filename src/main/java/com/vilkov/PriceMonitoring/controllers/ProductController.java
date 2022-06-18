package com.vilkov.PriceMonitoring.controllers;


import com.vilkov.PriceMonitoring.controllers.entity.EntityHelper;
import com.vilkov.PriceMonitoring.controllers.entity.ProductVO;
import com.vilkov.PriceMonitoring.model.ProductHelper;
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

    @GetMapping("/getAllProduct/{clientID}")
    public List<ProductVO> getAllClientProducts(@PathVariable String clientID) {
        List<Product> products = productHelper.getAllClientProducts(clientID);
        List<ProductVO> result = new ArrayList<>();
        for (Product product : products) {
            result.add(EntityHelper.convertProductToProductVO(product));
        }
        return result;
    }

    @PostMapping("/getAllProductFromDate/{clientID}")
    public List<ProductVO> getClientProductsFromPeriod(@PathVariable String clientID, @RequestParam("startDay") String startDay, @RequestParam("endDay") String endDay) {
        List<ProductVO> productsVO = new ArrayList<>();
        List<Product> products = productHelper.getClientProductsFromPeriod(clientID, startDay, endDay);
        for (Product product : products) {
            productsVO.add(EntityHelper.convertProductToProductVO(product));
        }
        return productsVO;
    }


    @PostMapping("/getAllProductFromShop/{clientID}")
    public List<ProductVO> getClientProductsFromShops(@RequestParam("shop") String shop, @PathVariable String clientID) {
        List<Product> products = productHelper.getClientProductsFromShops(shop, clientID);
        List<ProductVO> productsVO = new ArrayList<>();
        for (Product product : products) {
            productsVO.add(EntityHelper.convertProductToProductVO(product));
        }
        return productsVO;
    }
}
