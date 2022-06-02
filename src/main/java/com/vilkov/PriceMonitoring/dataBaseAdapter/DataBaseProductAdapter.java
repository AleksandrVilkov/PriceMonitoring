package com.vilkov.PriceMonitoring.dataBaseAdapter;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.vilkov.PriceMonitoring.model.Product;
import com.vilkov.PriceMonitoring.model.dataStorage.ProductDataStorage;
import org.bson.Document;

import java.beans.JavaBean;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;

@JavaBean
public class DataBaseProductAdapter implements ProductDataStorage {

    private static final Logger logger = Logger.getLogger("DataBase");
    @Override
    public  List<Product> readProducts() {
        List<Product> result = new ArrayList<>();
        try (MongoClient mongoClient = Config.getMongoClient()) {
            MongoDatabase database = mongoClient.getDatabase(Config.DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(Product.class.toString());
            MongoCursor<Document> cursor = collection.find().cursor();
            while (cursor.hasNext()) {
                Document document = cursor.next();
                result.add(DataBaseProductHelper.fromDoc(document));
            }
            return result;
        } catch (Exception e) {
            logger.warning("Ошибка чтения: " + e.getMessage());
            return null;
        }
    }
    @Override
    public boolean createProduct(Product product) {
        try (MongoClient mongoClient = Config.getMongoClient()) {
            MongoDatabase database = mongoClient.getDatabase(Config.DATABASE_NAME);
            try {
                database.getCollection(product.getClass().toString()).insertOne(DataBaseProductHelper.toDoc(product));
                return true;
            } catch (Exception e) {
                logger.warning("Неудачная попытка добавления объекта в базу данных: " + e.getMessage());
                return false;
            }
        }
    }

    @Override
    public boolean deleteProduct(String url) {
        try (MongoClient mongoClient = (MongoClient) Config.getMongoClient()) {
            MongoDatabase database = mongoClient.getDatabase(Config.DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(Product.class.toString());
            collection.deleteOne(eq("url",url));
            return true;
        } catch (Exception e) {
            logger.warning("Не удалось удалить документ: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateProduct(Product product) {
        try (MongoClient mongoClient = (MongoClient) Config.getMongoClient()) {
            MongoDatabase database = mongoClient.getDatabase(Config.DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(Product.class.toString());
            collection.deleteOne(eq("url", product.getUlr()));
            collection.insertOne(DataBaseProductHelper.toDoc(product));
            return true;
        } catch (Exception e) {
            logger.warning("Ошибка обновления: " + e.getMessage());
            return false;
        }
    }
}
