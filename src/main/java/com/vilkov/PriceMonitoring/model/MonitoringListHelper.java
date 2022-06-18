package com.vilkov.PriceMonitoring.model;

import com.vilkov.PriceMonitoring.model.dataStorage.DataStorage;
import com.vilkov.PriceMonitoring.model.entity.BaseEntity;
import com.vilkov.PriceMonitoring.model.entity.Client;
import com.vilkov.PriceMonitoring.model.entity.Message;
import com.vilkov.PriceMonitoring.model.entity.MonitoringList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

@Component
public class MonitoringListHelper {
    @Autowired
    DataStorage dataStorage;
    Logger logger = Logger.getLogger("saveNewMonitoringUrl");

    public Message saveNewMonitoringUrl(String clientID, String url) {
        Client client = new Client(clientID, null);
        Message message = new Message();
        if (!isCorrectUrl(url)) {
            logger.warning("Получен не верный url от клиента " + clientID);
            return new Message(Status.ERROR, "Прислан не корректный url");
        }
        logger.info("get new url " + url + " for client " + clientID);
        List<BaseEntity> monitoringList = dataStorage.readEntities(client, MonitoringList.class);
        MonitoringList result = new MonitoringList();

        for (BaseEntity entity : monitoringList) {
            if (entity instanceof MonitoringList && !monitoringList.isEmpty()) {
                result.setUrl(((MonitoringList) entity).getUrls());
            }
        }
        if (result.getUrls().contains(url)) {
            message.setStatus(Status.SUCCESS);
            message.setMessage(message.getMessage() + "Url не добавлен, потому что уже существует");
            logger.warning("url already exists");
            return message;
        } else {
            result.getUrls().add(url);
            logger.info("url added to client " + clientID);
        }

        if (dataStorage.updateEntity(result, client)) {
            message.setStatus(Status.SUCCESS);
            message.setMessage("Url добавлен");
            return message;
        } else {
            message.setStatus(Status.ERROR);
            message.setMessage("Ошибка выполнения");
            logger.warning("runtime error");
        }
        return message;

    }

    public Message deleteUrlFromMonitoring(String clientID, String url) {
        Logger logger = Logger.getLogger("saveNewMonitoringUrl");
        logger.info("delete url " + url + " at client " + clientID);
        Client client = new Client(clientID, null);
        List<BaseEntity> monitoringList = dataStorage.readEntities(client, MonitoringList.class);
        boolean result = false;
        for (BaseEntity baseEntity : monitoringList) {
            if (baseEntity instanceof MonitoringList) {
                result = ((MonitoringList) baseEntity).getUrls().remove(url);
                if (result)
                    dataStorage.updateEntity(baseEntity, client);
            }
        }
        if (result) {
            logger.warning("successful deleting");
            return new Message(Status.SUCCESS, "URL удален");
        } else {
            logger.warning("deletion error");
            return new Message(Status.ERROR, "Ошибка удаления");
        }

    }

    public List<String> getAllUrls(String clientID) {
        Client client = new Client(clientID, null);
        List<BaseEntity> baseEntityList = dataStorage.readEntities(client, MonitoringList.class);
        List<String> result = new ArrayList<>();
        if (baseEntityList.isEmpty()) {
            return null;
        } else {
            for (BaseEntity baseEntity : baseEntityList) {
                if (baseEntity instanceof MonitoringList) {
                    result.addAll(((MonitoringList) baseEntity).getUrls());
                }
            }
        }
        return result;
    }

    private static boolean isCorrectUrl(String url) {
        String reg = "^((ftp|http|https):\\/\\/)?(www\\.)?([A-Za-zА-Яа-я0-9]{1}[A-Za-zА-Яа-я0-9\\-]*\\.?)*\\.{1}[A-Za-zА-Яа-я0-9-]{2,8}(\\/([\\w#!:.?+=&%@!\\-\\/])*)?";
        Pattern pattern = Pattern.compile(reg);
        return pattern.matcher(url).find();
    }
}
