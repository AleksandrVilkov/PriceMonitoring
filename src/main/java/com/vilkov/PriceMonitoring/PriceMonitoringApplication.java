package com.vilkov.PriceMonitoring;

import com.vilkov.PriceMonitoring.model.Product;
import com.vilkov.PriceMonitoring.model.ProductHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
public class PriceMonitoringApplication {

    public static void main(String[] args) {
        SpringApplication.run(PriceMonitoringApplication.class, args);
        //Индексируем БД
        List<Product> products = ProductHelper.readProductInDataBase();

    }
}
