package com.vilkov.PriceMonitoring.dataBaseAdapter;

import com.mongodb.client.*;
import com.vilkov.PriceMonitoring.model.dataStorage.DataStorageInterface;
import com.vilkov.PriceMonitoring.model.entity.BaseEntity;
import com.vilkov.PriceMonitoring.model.entity.Client;
import com.vilkov.PriceMonitoring.model.entity.MonitoringList;
import com.vilkov.PriceMonitoring.model.entity.Product;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataBaseAdapter implements DataStorageInterface {


    @Override
    public boolean createEntity(BaseEntity baseEntity, Client client) {
        try (MongoClient mongoClient = MongoClients.create()) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(client.getClientID());
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(baseEntity.getClass().toString());
            if (baseEntity instanceof MonitoringList) {
                deleteEntity(client, MonitoringList.class, null);
            }
            mongoCollection.insertOne(DataBaseHelper.toDoc(baseEntity));
            return true;
        } catch (Exception e) {
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
            return result;
        } catch (Exception e) {
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
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
