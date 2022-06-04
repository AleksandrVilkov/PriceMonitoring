package com.vilkov.PriceMonitoring.controllers;

import com.vilkov.PriceMonitoring.model.MonitoringListHelper;
import com.vilkov.PriceMonitoring.model.entity.BaseEntity;
import com.vilkov.PriceMonitoring.model.entity.Client;
import com.vilkov.PriceMonitoring.model.entity.MonitoringList;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/monitoring")
public class MonitoringUrlsController {

    @PostMapping("/add/{clientID}")
    public Message saveNewMonitoringUrl(@PathVariable String clientID, @RequestParam("url") String url) {
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
            message.setMessageText("Url добавлен");
        } else {
            message.setStatus("ERROR");
            message.setMessageText("Ошибка выполнения");
            logger.warning("runtime error");
        }
        return message;
    }

    @GetMapping("/delete/{clientID}/{url}")
    public Message deleteUrlFromMonitoring(@PathVariable String clientID, @PathVariable String url) {
        Logger logger = Logger.getLogger("saveNewMonitoringUrl");
        logger.info("delete url " + url + " at client " + clientID);
        Message message = new Message();
        Client client = new Client(clientID);
        List<BaseEntity> monitoringList = MonitoringListHelper.readMonitoringList(client);
        boolean result = false;
        for (BaseEntity baseEntity : monitoringList) {
            if (baseEntity instanceof MonitoringList) {
                result = ((MonitoringList) baseEntity).getUrls().remove(url);
                if (result)
                    MonitoringListHelper.updateMonitoringList((MonitoringList) baseEntity, client);
            }
        }
        if (result) {

            logger.warning("successful deleting");
            return new Message("SUCCESS", "URL удален");
        } else {
            logger.warning("deletion error");
            return new Message("ERROR", "Ошибка удаления");
        }
    }

    @GetMapping("getAllURLs/{clientID}")
    public MonitoringList getAllUrls(@PathVariable String clientID) {
        Client client = new Client(clientID);
        List<BaseEntity> baseEntityList = MonitoringListHelper.readMonitoringList(client);
        MonitoringList result = new MonitoringList();
        if (baseEntityList.isEmpty()) {
            return null;
        } else {
            for (BaseEntity baseEntity : baseEntityList) {
                if (baseEntity instanceof MonitoringList) {
                    result.getUrls().addAll(((MonitoringList) baseEntity).getUrls());
                }
            }
        }

        return result;
    }
}

