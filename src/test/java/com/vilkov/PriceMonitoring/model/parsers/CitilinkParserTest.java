package com.vilkov.PriceMonitoring.model.parsers;

import com.beust.ah.A;
import com.vilkov.PriceMonitoring.model.entity.Product;
import com.vilkov.PriceMonitoring.model.parsers.dnsParser.CitilinkParser;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class CitilinkParserTest {
    @Test
    public void getProductTest() {
        String url = "https://www.citilink.ru/product/modul-pamyati-patriot-signature-psd48g266681-ddr4-8gb-2666-dimm-ret-1083963/";
        Product product = CitilinkParser.getProduct(url);
        Assert.assertNotNull(product.getPrice());
        Assert.assertNotNull(product.getShop());
        Assert.assertEquals(product.getShop(), "citilink");
    }
}
