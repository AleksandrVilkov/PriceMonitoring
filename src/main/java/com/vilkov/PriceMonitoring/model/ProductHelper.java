package com.vilkov.PriceMonitoring.model;

import com.vilkov.PriceMonitoring.model.dataStorage.DataStorage;

public class ProductHelper {
    public static DataStorage dataStorage;

    public static void saveProductToDataBase(Product product) {
        dataStorage.saveProduct(product);
    }

    public static void readProductInDataBase(String id) {
        dataStorage.readProduct(id);
    }

    public static void deleteProductInDataBase(String id) {
        dataStorage.deleteProduct(id);
    }

    public static void updateProductInDataBase(String id) {
        dataStorage.updateProduct(id);
    }

    public static void clearDataBase() {
        dataStorage.clearDataBase();
    }

    public void createCollection(String name) {
        dataStorage.createCollection(name);
    }

    public void clearCollection(String collection) {
        dataStorage.clearCollection(collection);
    }

}
