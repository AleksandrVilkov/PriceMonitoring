package com.vilkov.PriceMonitoring.dataBaseAdapter;

import com.mongodb.client.*;
import com.vilkov.PriceMonitoring.model.dataStorage.DataStorageInterface;
import com.vilkov.PriceMonitoring.model.entity.*;
import com.vilkov.PriceMonitoring.model.logger.Logger;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DataBaseAdapter implements DataStorageInterface {

    Logger logger = new Logger("Data_Base_log", "log.txt");

    @Override
    public boolean createEntity(BaseEntity baseEntity, Client client) {
        try (MongoClient mongoClient = MongoClients.create()) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(client.getClientID());
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(baseEntity.getClass().toString());
            if (baseEntity instanceof MonitoringList) {
                deleteEntity(client, MonitoringList.class, null);
            }
            mongoCollection.insertOne(DataBaseHelper.toDoc(baseEntity));
            logger.save( new Date() + ": createEntity successful: client - " + client.getClientID() + ". baseEntity: " + baseEntity.getClass());
            return true;
        } catch (Exception e) {
            logger.save(">>>ERROR " + new Date() + ": error createEntity: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<BaseEntity> readEntities(Client client, Class cls) {
        List<BaseEntity> result = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create()) {
            MongoDatabase database = mongoClient.getDatabase(client.getClientID());
            MongoCollection<Document> collection = database.getCollection(cls.toString());
            MongoCursor<Document> cursor = collection.find().cursor();
            while (cursor.hasNext()) {
                Document document = cursor.next();
                result.add(DataBaseHelper.fromDoc(document));
            }
            logger.save( new Date() + ": readEntities successful: client - " + client.getClientID());
            return result;
        } catch (Exception e) {
            logger.save(">>>ERROR " + new Date() + ": error readEntities: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean updateEntity(BaseEntity baseEntity, Client client) {
        if (baseEntity instanceof Product) return false;

        if (baseEntity instanceof MonitoringList) {
            deleteEntity(client, MonitoringList.class, null);
            createEntity(baseEntity, client);
            return true;
        }
        return false;
    }


    @Override
    public boolean deleteEntity(Client client, Class cls, String id) {
        try (MongoClient mongoClient = MongoClients.create()) {
            MongoDatabase database = mongoClient.getDatabase(client.getClientID());
            MongoCollection<Document> collection = database.getCollection(cls.toString());
            collection.drop();
            logger.save( new Date() + ": deleteEntity successful: client - " + client.getClientID());
            return true;
        } catch (Exception e) {
            logger.save(">>>ERROR " + new Date() + "error deleteEntity: " + e.getMessage());
            return false;
        }
    }

}
