package com.vilkov.PriceMonitoring.parsers.globusParser.globusParser;

import com.vilkov.PriceMonitoring.model.Product;
import com.vilkov.PriceMonitoring.parsers.globusParser.GlobusParser;
import org.junit.jupiter.api.Test;

public class GlobusParserTest {
    @Test
    public void getProductTest() {
        Product result = GlobusParser.getProduct("https://www.globus.ru/products/93220127_ST/?sphrase_id=8854716");
        System.out.println(result);
    }

}
