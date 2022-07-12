package com.vilkov.PriceMonitoring.model.parsers;

import com.vilkov.PriceMonitoring.model.parsers.VideoShoper.VideoShoperParser;
import org.junit.Test;

public class VideoShoperTest {
    @Test
    public void getProducttest() {
        VideoShoperParser videoShoperParser = new VideoShoperParser();
        videoShoperParser.getProduct("https://video-shoper.ru/shipment/honor-50-8-256gb-midnight-black-polnochnyy-chernyy.html");
    }
}
