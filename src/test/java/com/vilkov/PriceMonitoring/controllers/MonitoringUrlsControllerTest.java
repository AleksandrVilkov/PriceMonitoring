package com.vilkov.PriceMonitoring.controllers;

import com.vilkov.PriceMonitoring.controllers.entity.MessageVO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class MonitoringUrlsControllerTest {
    @Test
    public void saveNewMonitoringUrlTest() {
        MonitoringUrlsController monitoringUrlsController = new MonitoringUrlsController();
        MessageVO message = monitoringUrlsController.saveNewMonitoringUrl("test", "www.w.com");
        Assert.assertEquals(message.getStatus(), "ERROR");
        MessageVO message1 = monitoringUrlsController.saveNewMonitoringUrl("client", "fdsghgdfh");
        Assert.assertEquals(message1.getStatus(), "ERROR");
        MessageVO message2 = monitoringUrlsController.saveNewMonitoringUrl("user", "www.tr.ru/fdsfsagg");
        Assert.assertEquals(message2.getStatus(), "SUCCESS");
    }
}
