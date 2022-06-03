package com.vilkov.PriceMonitoring.controllers;

import com.vilkov.PriceMonitoring.model.MonitoringListHelper;
import com.vilkov.PriceMonitoring.model.entity.BaseEntity;
import com.vilkov.PriceMonitoring.model.entity.Client;
import com.vilkov.PriceMonitoring.model.entity.MonitoringList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/monitoring")
public class MonitoringUrlsController {


    @GetMapping("/add/{clientID}/{url}")
    public Message saveNewMonitoringUrl(@PathVariable String clientID, @PathVariable String url) {
        Logger logger = Logger.getLogger("saveNewMonitoringUrl");
        logger.info("get new url " + url + " for client " + clientID);
        Message message = new Message();
        Client client = new Client(clientID);
        List<BaseEntity> monitoringList = MonitoringListHelper.readMonitoringList(client);

        MonitoringList result = new MonitoringList();

        for (BaseEntity entity : monitoringList) {
            if (entity instanceof MonitoringList && !monitoringList.isEmpty()) {
                result.setUrl(((MonitoringList) entity).getUrls());
            }
        }
        if (result.getUrls().contains(url)) {
            message.setMessageText(message.getMessageText() + "Url не добавлен, потому что уже существует");
            logger.warning("url already exists");
        } else {
            result.getUrls().add(url);
            logger.info("url added to client " + clientID);
        }

        if (MonitoringListHelper.updateMonitoringList(result, client)) {
            message.setStatus("SUCCESS");
        } else {
            message.setStatus("ERROR");
            message.setMessageText("Ошибка выполнения");
            logger.warning("runtime error");
        }
        return message;
    }
}

