package com.vilkov.PriceMonitoring.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
@RequestMapping("/product")
public class ProductController {

    //TODO добавить продкукт в отслеживаемые
    @PostMapping("/add")
    public boolean addNewProduct(String url) {
        return true;
    }


    //TODO удалить продукут из отслеживаемых
    //TODO задать настройки крон

}
