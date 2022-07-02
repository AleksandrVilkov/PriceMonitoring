package com.vilkov.PriceMonitoring.model;

import com.vilkov.PriceMonitoring.model.logger.Logger;
import com.vilkov.PriceMonitoring.model.dataStorage.DataStorage;
import com.vilkov.PriceMonitoring.model.entity.BaseEntity;
import com.vilkov.PriceMonitoring.model.entity.Client;
import com.vilkov.PriceMonitoring.model.entity.Message;
import com.vilkov.PriceMonitoring.model.entity.MonitoringList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class MonitoringListHelper {
    @Autowired
    DataStorage dataStorage;
    Logger logger = new Logger("ClientController", "ClientControllerLog.txt");

    public Message saveNewMonitoringUrl(Client client, String url) {
        Message message = new Message();
        if (!isCorrectUrl(url)) {
            logger.save(new Date() + ": Получен не верный url от клиента " + client.getClientID());
            return new Message(Status.ERROR, "Прислан не корректный url");
        }
        logger.save(new Date() + ": Get new url " + url + " for client " + client.getClientID());
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
            logger.save(new Date() + ": Url " + url + " at " + client.getClientID() + " already exists");
            return message;
        } else {
            result.getUrls().add(url);
            logger.save(new Date() + ":url added to client " + client.getClientID());
        }

        if (dataStorage.updateEntity(result, client)) {
            message.setStatus(Status.SUCCESS);
            message.setMessage("Url добавлен");
            return message;
        } else {
            message.setStatus(Status.ERROR);
            message.setMessage("Ошибка выполнения");
            logger.save(new Date() + ": runtime error");
        }
        return message;

    }

    public Message deleteUrlFromMonitoring(Client client, String url) {
        logger.save(new Date() + ": delete url " + url + " at client " + client.getClientID());

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
            logger.save(new Date() + ": successful deleting url " + url + " by client " + client.getClientID());
            return new Message(Status.SUCCESS, "URL удален");
        } else {
            logger.save(new Date() + " deletion error: url - " + url + ", client - " + client.getClientID());
            return new Message(Status.ERROR, "Ошибка удаления");
        }
    }

    public List<String> getAllUrls(Client client) {
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
