package com.vilkov.PriceMonitoring.parsers;

import com.vilkov.PriceMonitoring.model.entity.Product;
import com.vilkov.PriceMonitoring.model.parsers.mVideoParser.MVideoParser;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class MVideoParserTest {

    @Test
    public void getProductTest() {
        Product product = MVideoParser.getProduct("https://www.mvideo.ru/products/noutbuk-hp-15s-fq2111ur-5d5e5ea-30061889");
        boolean result = product.getName() != null;
        Assert.assertEquals(result, true);
    }
}

