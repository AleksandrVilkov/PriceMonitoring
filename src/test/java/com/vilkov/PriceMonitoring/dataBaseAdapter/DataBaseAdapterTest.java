package com.vilkov.PriceMonitoring.dataBaseAdapter;

import com.vilkov.PriceMonitoring.model.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

public class DataBaseAdapterTest {

    //Работает, только если изначально тестовой записи нет в коллекции МонгоДБ
    @Test
    public void createAndDeleteProductTest() {
        Money money = new Money().setAmount(1000.0).setCurrency(Currency.RUB);
        Product product = new Product("Трава зеленая", money, "Рога и копыта", "www.lolkek.ru",
                new Date(), new Message(Status.SUCCESS, "ОК"));
        DataBaseProductAdapter dataBaseProductAdapter = new DataBaseProductAdapter();
        Assert.assertEquals(dataBaseProductAdapter.createProduct(product), true);
        Assert.assertEquals(dataBaseProductAdapter.createProduct(product), false);
        dataBaseProductAdapter.deleteProduct(product.getUlr());
    }

    @Test
    public void readProductsTest() {
        List<Product> productList =  new DataBaseProductAdapter().readProducts();
        Assert.assertEquals(productList.isEmpty(), false);
    }
}
