package com.vilkov.PriceMonitoring.dataBaseAdapter;

import com.mongodb.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.vilkov.PriceMonitoring.model.*;
import org.bson.Document;

import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

public class DataBaseProductHelper {
    static Logger logger = Logger.getLogger("DBHelper");

    public static boolean createCollection(Class cls) {
        try (com.mongodb.client.MongoClient mongoClient = Config.getMongoClient()) {
            MongoDatabase database = (MongoDatabase) mongoClient.getDatabase(Config.DATABASE_NAME);
            database.createCollection(cls.toString());
            return true;
        } catch (Exception e) {
            logger.warning("Не удалось создать коллекцию: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteCollection(Class cls) {
        try (MongoClient mongoClient = Config.getMongoClient()) {
            MongoDatabase database = (MongoDatabase) mongoClient.getDatabase(Config.DATABASE_NAME);
            database.getCollection(cls.toString()).drop();
            return true;
        } catch (Exception e) {
            logger.warning("Не удалось удалить коллекцию: " + e.getMessage());
            return false;
        }
    }

    public static Document toDoc(Product product) {
        return new Document(Map.of(
                "_id", product.getId(),
                "url", product.getUlr(),
                "name", product.getName(),
                "amountPrice", product.getPrice().getAmount(),
                "currency", product.getPrice().getCurrency().toString(),
                "shop", product.getShop(),
                "date", product.getDate()
        ));
    }

    public static Product fromDoc(Document document) {
        String url = (String) document.get("url");
        String name = (String) document.get("name");
        double amountPrice = (double) document.get("amountPrice");
        Currency currency;
        if (Currency.RUB.name().equals((String) document.get("currency"))) {
            currency = Currency.RUB;
        } else {
            currency = null;
        }
        String shop = (String) document.get("shop");

        Date date = (Date) document.get("date");
        Money price = new Money().setAmount(amountPrice).setCurrency(currency);
       return new Product(name, price, shop, url, date, new Message(Status.SUCCESS, "Derived from MongoDB"));

    }
}
