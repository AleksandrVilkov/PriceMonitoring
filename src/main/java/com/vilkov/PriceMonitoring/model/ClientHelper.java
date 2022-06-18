package com.vilkov.PriceMonitoring.model;

import com.vilkov.PriceMonitoring.model.dataStorage.DataStorage;
import com.vilkov.PriceMonitoring.model.entity.Client;
import com.vilkov.PriceMonitoring.model.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Logger;

@Component
public class ClientHelper {

    @Autowired
    DataStorage dataStorage;

    public Message createClient(Client client) {
        Logger logger = Logger.getLogger("ClientHelper.createClient");
        Properties properties = new Properties();
        Client admin;
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/admin.properties")) {
            properties.load(fileInputStream);
            String login = properties.getProperty("login");
            char[] password = properties.getProperty("password").toCharArray();
            admin = new Client(login, password);
        } catch (Exception e) {
            logger.warning("Can not get an admin account");
            return new Message(Status.ERROR, "Ошибка добавления клиента");
        }
        if (dataStorage.createEntity(client, admin)) {
            return new Message(Status.SUCCESS, "Клиент успешно создан");
        } else {
            return new Message(Status.ERROR, "Ошибка добавления клиента");
        }
    }
}
