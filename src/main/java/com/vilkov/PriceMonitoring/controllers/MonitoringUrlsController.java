package com.vilkov.PriceMonitoring.controllers;

import com.vilkov.PriceMonitoring.controllers.entity.EntityHelper;
import com.vilkov.PriceMonitoring.controllers.entity.MessageVO;
import com.vilkov.PriceMonitoring.model.logger.Logger;
import com.vilkov.PriceMonitoring.model.MonitoringListHelper;
import com.vilkov.PriceMonitoring.model.entity.Client;
import com.vilkov.PriceMonitoring.model.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@RequestMapping("api/monitoring")
public class MonitoringUrlsController {
    @Autowired
    MonitoringListHelper monitoringListHelper;
    Logger logger = new Logger("MonitoringUrlsController", "ProductControllerLog.txt");
    @PostMapping("/saveUrl/")
    public MessageVO saveNewMonitoringUrl(@RequestParam("clientID") String clientID, @RequestParam("password") String password, @RequestParam("url") String url) {
        Message message = monitoringListHelper.saveNewMonitoringUrl(new Client(clientID, password.toCharArray()), url);
        return EntityHelper.convertMessageToMessageVO(message);
    }

    @PostMapping("/delete")
    public MessageVO deleteUrlFromMonitoring(@RequestParam("clientID") String clientID, @RequestParam("password") String password, @RequestParam("url") String url) {
        Message message = monitoringListHelper.deleteUrlFromMonitoring(new Client(clientID, password.toCharArray()), url);
        return EntityHelper.convertMessageToMessageVO(message);
    }

    @PostMapping("/getAllUrls")
    public List<String> getAllUrls(@RequestParam("clientID") String clientID, @RequestParam("password") String password) {
        return monitoringListHelper.getAllUrls(new Client(clientID, password.toCharArray()));
    }
}

