package com.vilkov.PriceMonitoring.parsers.globusParser;

import com.vilkov.PriceMonitoring.model.entity.Product;
import com.vilkov.PriceMonitoring.model.Status;
import com.vilkov.PriceMonitoring.model.parsers.globusParser.GlobusParser;
import com.vilkov.PriceMonitoring.model.parsers.perekrestokParser.PerekrestokParser;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class GlobusParserTest {
    @Test
    public void getProductTest() {
        final String correctURL = "https://www.globus.ru/products/93220127_ST/?sphrase_id=8854716";
        final String notCorrectURL = "https://www.perektok.ru/cat/432/p/minsneki-healthy-wheels-fruktovo-orehovyj-miks-144g-4012937";
        Product result = GlobusParser.getProduct(correctURL);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getName());
        Assert.assertNotNull(result.getPrice());
        Assert.assertNotNull(result.getUlr());
        Assert.assertNotNull(result.getShop());
        Assert.assertEquals(result.getMessage().getStatus(), Status.SUCCESS);


        result = PerekrestokParser.getProduct(notCorrectURL);
        Assert.assertNull(result.getName());
        Assert.assertNull(result.getPrice());
        Assert.assertNull(result.getUlr());
        Assert.assertEquals(result.getMessage().getStatus(), Status.ERROR);
    }

}
