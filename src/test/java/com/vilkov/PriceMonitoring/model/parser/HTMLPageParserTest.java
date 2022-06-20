package com.vilkov.PriceMonitoring.model.parser;

import com.vilkov.PriceMonitoring.model.Status;
import com.vilkov.PriceMonitoring.model.entity.Currency;
import com.vilkov.PriceMonitoring.model.entity.Product;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class HTMLPageParserTest {

    @Test
    public void getProductTest() {
        String URL = "https://www.perekrestok.ru/cat/366/p/smes-salatnaa-belaa-daca-tango-mix-65g-3451379";
        Product perecrectokProduct = HTMLPageParser.searchProduct(URL);
        Assert.assertNotNull(perecrectokProduct);
        Assert.assertNotNull(perecrectokProduct.getPrice());
        Assert.assertEquals(perecrectokProduct.getPrice().getCurrency(), Currency.RUB);
        Assert.assertEquals(perecrectokProduct.getMessage().getStatus(), Status.SUCCESS);

        URL = "https://www.globus.ru/products/93301767_KG/";
        Product globusProduct = HTMLPageParser.searchProduct(URL);
        Assert.assertNotNull(globusProduct);
        Assert.assertNotNull(globusProduct.getPrice());
        Assert.assertEquals(globusProduct.getPrice().getCurrency(), Currency.RUB);
        Assert.assertEquals(globusProduct.getMessage().getStatus(), Status.SUCCESS);

        URL = "https://www.citilink.ru/product/smartfon-huawei-y5p-32gb-chernyi-3g-4g-5-45-and10hms-wifi-gps-1380431/";
        Product citilinkProduct = HTMLPageParser.searchProduct(URL);
        Assert.assertNotNull(citilinkProduct);
        Assert.assertNotNull(citilinkProduct.getPrice());
        Assert.assertEquals(citilinkProduct.getPrice().getCurrency(), Currency.RUB);
        Assert.assertEquals(citilinkProduct.getMessage().getStatus(), Status.SUCCESS);


    }

    @Test
    public void getRenounceTest() {
        MvideoResponseParser.getResponse("https://www.mvideo.ru/bff/products/prices?productIds=30051224");
    }

}
