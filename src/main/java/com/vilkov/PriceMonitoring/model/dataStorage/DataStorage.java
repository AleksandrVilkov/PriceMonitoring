package com.vilkov.PriceMonitoring.model.dataStorage;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoIterable;
import com.vilkov.PriceMonitoring.model.entity.BaseEntity;
import com.vilkov.PriceMonitoring.model.entity.Client;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

@Component
public class DataStorage {
    @Autowired
    private DataStorageInterface dataBaseAdapter;

    Logger logger = Logger.getLogger("DataStorage");

    public boolean createEntity(BaseEntity baseEntity, Client client) {
        return dataBaseAdapter.createEntity(baseEntity, client);
    }

    public List<BaseEntity> readEntities(Client client, Class cls) {
        if (isPasswordCorrect(client)) {
            return dataBaseAdapter.readEntities(client, cls);
        }
        logger.warning("Authorisation Error");
        return null;
    }

    public boolean updateEntity(BaseEntity baseEntity, Client client) {
        if (!isPasswordCorrect(client)) {
            logger.warning("Authorisation Error");
            return false;
        }
        return dataBaseAdapter.updateEntity(baseEntity, client);
    }

    public boolean deleteEntity(Client client, Class cls, String id) {
        if (!isPasswordCorrect(client)) {
            logger.warning("Authorisation Error");
            return false;
        }
        return dataBaseAdapter.deleteEntity(client, cls, id);
    }


    public boolean isPasswordCorrect(Client client) {
        Client admin = getAdmin();
        if (client.getClientID().equals(admin.getClientID()))
            return true;
        List<BaseEntity> allClients = readEntities(admin, Client.class);
        for (BaseEntity baseEntity : allClients) {
            if (baseEntity instanceof Client && ((Client) baseEntity).getClientID().equals(client.getClientID())) {
                String clientPassword = getStringFromCharArray(client.getPassword());
                String passwordFromDataBase = getStringFromCharArray(((Client) baseEntity).getPassword());
                return clientPassword.equals(passwordFromDataBase);
            }
        }
        return false;
    }

    public Client getAdmin() {
        Properties properties = new Properties();
        Client admin;
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/admin.properties")) {
            properties.load(fileInputStream);
            String login = properties.getProperty("login");
            char[] password = properties.getProperty("password").toCharArray();
            admin = new Client(login, password);
        } catch (Exception e) {
            logger.warning("Can not get an admin account");
            return null;
        }
        return admin;
    }

    public List<Client> getAllClients() {
       List<BaseEntity> baseEntities = dataBaseAdapter.readEntities(getAdmin(), Client.class);
       List<Client> clients = new ArrayList<>();
        for (BaseEntity baseEntity: baseEntities) {
            if (baseEntity instanceof Client) {
                clients.add((Client) baseEntity);
            }
        }
        return clients;
    }
    private String getStringFromCharArray(char[] chars) {
        String result = "";
        for (int i = 0; i < chars.length; i++) {
            result += chars[i];
        }
        return result;
    }
}
