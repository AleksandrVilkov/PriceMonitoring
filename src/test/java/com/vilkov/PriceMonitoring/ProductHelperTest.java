package com.vilkov.PriceMonitoring;

import com.vilkov.PriceMonitoring.model.*;
import com.vilkov.PriceMonitoring.model.Currency;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ProductHelperTest {
    @Test
    public void getFixedPriceFromProductTest() {
        Product product = new Product("Шоколад",
                new Money().setAmount(150.00).setCurrency(Currency.RUB),
                "Рога и Копыта",
                "www.roga.ru",
                new Date(),
                new Message(Status.SUCCESS,"Success"));
       FixedPrice fixedPrice = ProductHelper.getFixedPriceFromProduct(product);
       int price = (int) fixedPrice.getPrice().getAmount();
        Assert.assertEquals(price, 150);
        Assert.assertEquals(fixedPrice.getDate().getDate(), new Date().getDate());
    }
    @Test
    public void getSortedPricesTest() {
        Product product1 = new Product("Шоколад",
                new Money().setAmount(150.00).setCurrency(Currency.RUB),
                "Рога и Копыта",
                "www.roga.ru",
                new Date(),
                new Message(Status.SUCCESS,"Success"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Product product2 = new Product("Шоколад",
                new Money().setAmount(155.00).setCurrency(Currency.RUB),
                "Рога и Копыта",
                "www.roga.ru",
                new Date(),
                new Message(Status.SUCCESS,"Success"));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Product product3 = new Product("Манго",
                new Money().setAmount(110.00).setCurrency(Currency.RUB),
                "Рога и Копыта",
                "www.roga.ru",
                new Date(),
                new Message(Status.SUCCESS,"Success"));

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product3);
        products.add(product2);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Product product4 = new Product("шоколад",
                new Money().setAmount(110.00).setCurrency(Currency.RUB),
                "Рога и Копыта",
                "www.roga.ru",
                new Date(),
                new Message(Status.SUCCESS,"Success"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Product product5 = new Product("Манго",
                new Money().setAmount(110.00).setCurrency(Currency.RUB),
                "Рога и Копыта",
                "www.roga.ru",
                new Date(),
                new Message(Status.SUCCESS,"Success"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Product product6 = new Product("Персик",
                new Money().setAmount(60.00).setCurrency(Currency.RUB),
                "Рога и Копыта",
                "www.roga.ru",
                new Date(),
                new Message(Status.SUCCESS,"Success"));

        products.add(product4);
        products.add(product5);
        products.add(product6);

        Map<String, TreeSet<FixedPrice>> result = ProductHelper.getSortedPrices(products);
        System.out.println(result);


    }
}
