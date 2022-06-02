package com.vilkov.PriceMonitoring.model.dataStorage;

import com.vilkov.PriceMonitoring.model.Product;

public interface DataStorage {

    public void readProduct(String id);

    public void saveProduct(Product product);

    public void deleteProduct(String id);

    public void updateProduct(String id);

    public void clearDataBase();

    public void createCollection(String name);

    public void clearCollection(String collection);

}
