package com.vilkov.PriceMonitoring.dataBaseAdapter;

import com.vilkov.PriceMonitoring.model.Status;
import com.vilkov.PriceMonitoring.model.entity.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

public class DataBaseHelperTest {
/**
 * Запускать только тесты целиком!
 * */
    @Test
    public void createEntityTest() {
        Money money = new Money().setAmount(1000.0).setCurrency(Currency.RUB);
        Product product = new Product("Трава зеленая", money, "Рога и копыта", "www.lolkek.ru",
                new Date(), new Message(Status.SUCCESS, "ОК"));
        Product product2 = new Product("Трава желтая", money, "Рога и копыта", "www.lolkek.ru",
                new Date(), new Message(Status.SUCCESS, "ОК"));

        DataBaseAdapter dataBaseAdapter = new DataBaseAdapter();
        Client client = new Client("testClient1");
        Client client2 = new Client("testClient2");
        Assert.assertEquals(dataBaseAdapter.createEntity(product, client), true);
        Assert.assertEquals(dataBaseAdapter.createEntity(product2, client), true);
        Assert.assertEquals(dataBaseAdapter.createEntity(product, client2), true);
        Assert.assertEquals(dataBaseAdapter.createEntity(product2, client2), true);

        MonitoringList monitoringList = new MonitoringList();
        monitoringList.addUrl("www.testurl2.ru");
        monitoringList.addUrl("www.testurl1.ru");
        Assert.assertEquals(dataBaseAdapter.createEntity(monitoringList, client), true);
        Assert.assertEquals(dataBaseAdapter.createEntity(monitoringList, client2), true);

        Assert.assertEquals(dataBaseAdapter.createEntity(product, client), false);
        Assert.assertEquals(dataBaseAdapter.createEntity(product2, client2), false);
        Assert.assertEquals(dataBaseAdapter.createEntity(monitoringList, client), true);
        Assert.assertEquals(dataBaseAdapter.createEntity(monitoringList, client2), true);
    }

    @Test
    public void readEntityTest() {
        DataBaseAdapter dataBaseAdapter = new DataBaseAdapter();
        Client client = new Client("testClient1");
        List<BaseEntity> baseEntities = dataBaseAdapter.readEntity(client, Product.class);
        List<BaseEntity> baseEntities1 = dataBaseAdapter.readEntity(client, MonitoringList.class);
        Assert.assertEquals(baseEntities1.isEmpty(), false);
        Assert.assertEquals(baseEntities.isEmpty(), false);
    }

    @Test
    public void deleteTest() {
        DataBaseAdapter dataBaseAdapter = new DataBaseAdapter();
        Client client = new Client("testClient1");
        Client client2 = new Client("testClient2");
        Assert.assertEquals(dataBaseAdapter.deleteEntity(client, Product.class), true);
        Assert.assertEquals(dataBaseAdapter.deleteEntity(client2, Product.class), true);
        Assert.assertEquals(dataBaseAdapter.deleteEntity(client, MonitoringList.class), true);
        Assert.assertEquals(dataBaseAdapter.deleteEntity(client2, MonitoringList.class), true);
    }
}
