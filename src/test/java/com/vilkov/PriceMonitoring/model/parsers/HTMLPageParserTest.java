package com.vilkov.PriceMonitoring.model.parsers;

import com.vilkov.PriceMonitoring.model.Status;
import com.vilkov.PriceMonitoring.model.entity.Currency;
import com.vilkov.PriceMonitoring.model.entity.Product;
import com.vilkov.PriceMonitoring.model.parsers.Mvideo.MvideoParser;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class HTMLPageParserTest {
    //TODO покрыть тестами все паблик методы, поправить структуру директории
    @Test
    public void getProductTest() {
        String URL = "https://video-shoper.ru";
        Product perecrectokProduct = ParserHelper.searchProduct(URL);
        Assert.assertNotNull(perecrectokProduct);
        Assert.assertNotNull(perecrectokProduct.getPrice());
        Assert.assertEquals(perecrectokProduct.getPrice().getCurrency(), Currency.RUB);
        Assert.assertEquals(perecrectokProduct.getMessage().getStatus(), Status.SUCCESS);

        URL = "https://www.globus.ru/products/93301767_KG/";
        Product globusProduct = ParserHelper.searchProduct(URL);
        Assert.assertNotNull(globusProduct);
        Assert.assertNotNull(globusProduct.getPrice());
        Assert.assertEquals(globusProduct.getPrice().getCurrency(), Currency.RUB);
        Assert.assertEquals(globusProduct.getMessage().getStatus(), Status.SUCCESS);

        URL = "https://www.citilink.ru/product/smartfon-huawei-y5p-32gb-chernyi-3g-4g-5-45-and10hms-wifi-gps-1380431/";
        Product citilinkProduct = ParserHelper.searchProduct(URL);
        Assert.assertNotNull(citilinkProduct);
        Assert.assertNotNull(citilinkProduct.getPrice());
        Assert.assertEquals(citilinkProduct.getPrice().getCurrency(), Currency.RUB);
        Assert.assertEquals(citilinkProduct.getMessage().getStatus(), Status.SUCCESS);

        Product mvideoProduct = ParserHelper.searchProduct("https://www.mvideo.ru/products/smartfon-xiaomi-redmi-9a-32gb-granite-gray-30051224");
        Assert.assertEquals(mvideoProduct.getMessage().getStatus(), Status.SUCCESS);
        Assert.assertEquals(mvideoProduct.getShop(), "www.mvideo.ru");
    }

    @Test
    public void getRenounceTest() {
        MvideoParser mvideoParser = new MvideoParser();
        Product product1 = mvideoParser.getProduct("https://www.mvideo.ru/products/smartfon-xiaomi-redmi-9a-32gb-granite-gray-30051224");
        Product product2 = mvideoParser.getProduct("https://www.mvideo.ru/products/vneshnii-akkumulyator-tfn-power-mate-20000-mach-chernyi-tfn-pb-237-bk-50153484");
        Assert.assertEquals(product1.getMessage().getStatus(), Status.SUCCESS);
        Assert.assertEquals(product2.getMessage().getStatus(), Status.SUCCESS);
    }

}
