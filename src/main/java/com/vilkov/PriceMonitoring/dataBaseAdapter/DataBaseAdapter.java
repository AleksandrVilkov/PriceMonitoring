package com.vilkov.PriceMonitoring.dataBaseAdapter;

import com.mongodb.client.*;
import com.vilkov.PriceMonitoring.model.dataStorage.ClientDataStorageInterface;
import com.vilkov.PriceMonitoring.model.dataStorage.DataStorageInterface;
import com.vilkov.PriceMonitoring.model.entity.BaseEntity;
import com.vilkov.PriceMonitoring.model.entity.Client;
import com.vilkov.PriceMonitoring.model.entity.MonitoringList;
import com.vilkov.PriceMonitoring.model.entity.Product;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class DataBaseAdapter implements DataStorageInterface, ClientDataStorageInterface {
    private static final Logger logger = Logger.getLogger("DataBaseMonitoringListAdapter");

    @Override
    public boolean createEntity(BaseEntity baseEntity, Client client) {
        try (MongoClient mongoClient = MongoClients.create()) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(client.getClientID());
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(baseEntity.getClass().toString());
            if (baseEntity instanceof MonitoringList) {
                deleteEntity(client, MonitoringList.class);
            }
            mongoCollection.insertOne(DataBaseHelper.toDoc(baseEntity));
            return true;
        } catch (Exception e) {
            logger.warning("Неудачная попытка добавления объекта в базу данных: " + e.getMessage());
            return false;
        }
    }


    @Override
    public List<BaseEntity> readEntity(Client client, Class cls) {
        List<BaseEntity> result = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create()) {
            MongoDatabase database = mongoClient.getDatabase(client.getClientID());
            MongoCollection<Document> collection = database.getCollection(cls.toString());
            MongoCursor<Document> cursor = collection.find().cursor();
            while (cursor.hasNext()) {
                Document document = cursor.next();
                result.add(DataBaseHelper.fromDoc(document));
            }
            return result;
        } catch (Exception e) {
            logger.warning("Ошибка чтения: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean updateEntity(BaseEntity baseEntity, Client client) {
        if (baseEntity instanceof Product)
            return false;

        if (baseEntity instanceof MonitoringList) {
            deleteEntity(client, MonitoringList.class);
            createEntity(baseEntity, client);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteEntity(Client client, Class cls) {
        try (MongoClient mongoClient = MongoClients.create()) {
            MongoDatabase database = mongoClient.getDatabase(client.getClientID());
            MongoCollection<Document> collection = database.getCollection(cls.toString());
            collection.drop();
            return true;
        } catch (Exception e) {
            logger.warning("Не удалось удалить коллекцию" + e.getMessage());
            return false;
        }
    }

    public List<Client> getAllClients() {
        try (var mongoClient = MongoClients.create()) {
            MongoIterable<String> allNames = mongoClient.listDatabaseNames();
            List<Client> clients = new ArrayList<>();
            for (String name : allNames) {
                clients.add(new Client(name));
            }
            return clients;
        }
    }
}
