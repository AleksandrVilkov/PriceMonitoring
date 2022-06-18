package com.vilkov.PriceMonitoring.controllers;

import com.vilkov.PriceMonitoring.controllers.entity.EntityHelper;
import com.vilkov.PriceMonitoring.controllers.entity.MessageVO;
import com.vilkov.PriceMonitoring.model.MonitoringListHelper;
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

    @PostMapping("/add/{clientID}")
    public MessageVO saveNewMonitoringUrl(@PathVariable String clientID, @RequestParam("url") String url) {
        Message message = monitoringListHelper.saveNewMonitoringUrl(clientID, url);
        return EntityHelper.convertMessageToMessageVO(message);
    }

    @GetMapping("/delete/{clientID}/{url}")
    public MessageVO deleteUrlFromMonitoring(@PathVariable String clientID, @PathVariable String url) {
        Message message = monitoringListHelper.deleteUrlFromMonitoring(clientID, url);
        return EntityHelper.convertMessageToMessageVO(message);
    }

    @GetMapping("getAllURLs/{clientID}")
    public List<String> getAllUrls(@PathVariable String clientID) {
        return monitoringListHelper.getAllUrls(clientID);
    }
}

