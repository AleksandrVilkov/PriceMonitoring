package com.vilkov.PriceMonitoring.parsers;

import com.vilkov.PriceMonitoring.model.Status;
import com.vilkov.PriceMonitoring.model.entity.Product;
import com.vilkov.PriceMonitoring.model.parsers.globusParser.GlobusParser;
import com.vilkov.PriceMonitoring.model.parsers.mVideoParser.MVideoParser;
import com.vilkov.PriceMonitoring.model.parsers.perekrestokParser.PerekrestokParser;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class MVideoParserTest {

    @Test
    public void getProductTest() {
//        Product product = MVideoParser.getProduct("https://www.mvideo.ru/products/noutbuk-hp-15s-fq2151ur-5r9r8ea-30061890");
//        boolean result = product.getName() != null;
//        Assert.assertEquals(result, true);


        final String correctURL = "https://www.mvideo.ru/products/noutbuk-hp-15s-fq2111ur-5d5e5ea-30061889";
        final String notCorrectURL = "https://www.perektok.ru/cat/432/p/minsneki-healthy-wheels-fruktovo-orehovyj-miks-144g-4012937";
        Product result = MVideoParser.getProduct(correctURL);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getName());
        Assert.assertNotNull(result.getPrice());
        Assert.assertNotNull(result.getUlr());
        Assert.assertNotNull(result.getShop());
        Assert.assertEquals(result.getMessage().getStatus(), Status.SUCCESS);


        result = MVideoParser.getProduct(notCorrectURL);
        Assert.assertNull(result.getName());
        Assert.assertNull(result.getPrice());
        Assert.assertNull(result.getUlr());
        Assert.assertEquals(result.getMessage().getStatus(), Status.ERROR);
    }
}


