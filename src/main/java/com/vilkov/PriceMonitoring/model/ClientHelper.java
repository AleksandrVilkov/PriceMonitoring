package com.vilkov.PriceMonitoring.model;

import com.vilkov.PriceMonitoring.model.dataStorage.DataStorage;
import com.vilkov.PriceMonitoring.model.entity.Client;
import com.vilkov.PriceMonitoring.model.entity.Message;
import com.vilkov.PriceMonitoring.model.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Component
public class ClientHelper {

    @Autowired
    DataStorage dataStorage;
    private final Logger logger = new Logger(Logger.getLoggerProperties().getProperty("modelLogFolder"),
            Logger.getLoggerProperties().getProperty("modelLogFileName"));

    public Message createClient(Client client) {
        Client admin = dataStorage.getAdmin();
        if (clientAlreadyExists(client.getClientID())) {
            Message message = new Message(Status.ALREADY_EXIST, " Клиент " + client.getClientID() + " уже существует");
            logger.save(new Date() + ": " + message);
            return message;
        }

        if (dataStorage.createEntity(client, admin)) {
            return new Message(Status.SUCCESS, " Клиент успешно создан");
        } else {
            Message message = new Message(Status.ERROR, "Ошибка добавления клиента " + client.getClientID());
            logger.save(new Date() + ": " + message.toString());
            return message;
        }
    }

    private boolean clientAlreadyExists(String clientId) {
        List<Client> clientList = dataStorage.getAllClients();
        for (Client client : clientList) {
            if (client.getClientID().equals(clientId)) {
                return true;
            }
        }
        return false;
    }

}
