package com.vilkov.PriceMonitoring.dataBaseAdapter;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;

public class Config {

    public static final String HOST = "localhost:27017";
    public static final String DATABASE_NAME = "PriceMonitoring";

    public static MongoClient getMongoClient() {
        return MongoClients.create();
    }



    public static DataBaseProductAdapter getProductDataStorage() {
        return new DataBaseProductAdapter();
    }
}
