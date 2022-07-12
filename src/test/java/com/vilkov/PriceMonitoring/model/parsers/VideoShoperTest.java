package com.vilkov.PriceMonitoring.model.parsers;

import com.vilkov.PriceMonitoring.model.entity.Product;
import com.vilkov.PriceMonitoring.model.parsers.VideoShoper.VideoShoperParser;
import org.junit.Test;

public class VideoShoperTest {
    @Test
    public void getProductTest() {
        VideoShoperParser videoShoperParser = new VideoShoperParser();
        Product product = videoShoperParser.getProduct("https://video-shoper.ru/shipment/pylesos-dyson-v15-detect-absolute.html");
        System.out.println(product);
    }
}
