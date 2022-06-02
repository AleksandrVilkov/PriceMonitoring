package com.vilkov.PriceMonitoring;

import com.vilkov.PriceMonitoring.model.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Iterator;

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
}
