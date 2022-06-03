package com.vilkov.PriceMonitoring.dataBaseAdapter;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.vilkov.PriceMonitoring.model.MonitoringList;
import com.vilkov.PriceMonitoring.model.dataStorage.MonitoringListDataStorage;
import org.bson.Document;

import java.util.logging.Logger;

public class DataBaseMonitoringListAdapter implements MonitoringListDataStorage {
    private static final Logger logger = Logger.getLogger("DataBaseMonitoringListAdapter");
    @Override
    public boolean createMonitoringList(MonitoringList monitoringList) {
        try (MongoClient mongoClient = Config.getMongoClient()) {
            MongoDatabase database = mongoClient.getDatabase(Config.DATABASE_NAME);
            try {
                database.getCollection(monitoringList.getClass().toString()).insertOne(DataBaseMonitoringListHelper.toDoc(monitoringList));
                return true;
            } catch (Exception e) {
                logger.warning("Неудачная попытка добавления объекта в базу данных: " + e.getMessage());
                return false;
            }
        }
    }

    @Override
    public MonitoringList readMonitoringList() {
        MonitoringList result;
        try (MongoClient mongoClient = Config.getMongoClient()) {
            MongoDatabase database = mongoClient.getDatabase(Config.DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(MonitoringList.class.toString());
            MongoCursor<Document> cursor = collection.find().cursor();
            if (cursor.hasNext()) {
                Document document = cursor.next();
                result = DataBaseMonitoringListHelper.fromDoc(document);
                return result;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.warning("Ошибка чтения: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean updateMonitoringList(MonitoringList monitoringList) {
        try (MongoClient mongoClient = (MongoClient) Config.getMongoClient()) {
            MongoDatabase database = mongoClient.getDatabase(Config.DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(MonitoringList.class.toString());
            collection.drop();
            database.getCollection(monitoringList.getClass().toString()).insertOne(DataBaseMonitoringListHelper.toDoc(monitoringList));
            return true;
        } catch (Exception e) {
            logger.warning("Не удалось удалить коллекцию: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteMonitoringList() {
        try (MongoClient mongoClient = (MongoClient) Config.getMongoClient()) {
            MongoDatabase database = mongoClient.getDatabase(Config.DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(MonitoringList.class.toString());
            collection.drop();
            return true;
        } catch (Exception e) {
            logger.warning("Не удалось удалить коллекцию: " + e.getMessage());
            return false;
        }
    }
}
