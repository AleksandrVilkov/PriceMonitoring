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
    Logger logger = new Logger("Clients_log", "log.txt");

    public Message createClient(Client client) {
        Client admin = dataStorage.getAdmin();
        if (clientAlreadyExists(client.getClientID())) {
            logger.save(new Date() + ": Клиент " + client.getClientID() + " уже существует");
            return new Message(Status.ALREADY_EXIST, " Клиент уже существует");
        }

        if (dataStorage.createEntity(client, admin)) {
            logger.save(new Date() + ": Клиент " + client.getClientID() + "создан");
            return new Message(Status.SUCCESS, " Клиент успешно создан");
        } else {
            logger.save(new Date() + ": Ошибка добавления клиента" + client.getClientID());
            return new Message(Status.ERROR, "Ошибка добавления клиента");
        }
    }
    private boolean clientAlreadyExists(String clientId) {
       List<Client> clientList = dataStorage.getAllClients();
       for (Client client: clientList) {
          if (client.getClientID().equals(clientId)) {
              return true;
          }
       }
       return false;
    }

}
