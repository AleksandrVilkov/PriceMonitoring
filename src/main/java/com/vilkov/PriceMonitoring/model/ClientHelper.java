package com.vilkov.PriceMonitoring.model;

import com.vilkov.PriceMonitoring.model.dataStorage.DataStorage;
import com.vilkov.PriceMonitoring.model.entity.Client;
import com.vilkov.PriceMonitoring.model.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class ClientHelper {

    @Autowired
    DataStorage dataStorage;
    Logger logger = Logger.getLogger("ClientHelper");

    public Message createClient(Client client) {
        Client admin = dataStorage.getAdmin();
        if (clientAlreadyExists(client.getClientID())) {
            return new Message(Status.ERROR, "Клиент уже существует");
        }

        if (dataStorage.createEntity(client, admin)) {
            return new Message(Status.SUCCESS, "Клиент успешно создан");
        } else {
            logger.warning("Ошибка добавления клиента" + client.getClientID());
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
