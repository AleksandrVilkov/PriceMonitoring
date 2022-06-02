package com.vilkov.PriceMonitoring.DataBaseAdapter;

import com.vilkov.PriceMonitoring.model.Product;

import java.beans.JavaBean;

@JavaBean
public class DataBaseAdapter implements com.vilkov.PriceMonitoring.model.dataStorage.DataStorage {
    //TODO реализовать подклчюение к монго

    @Override
    public void readProduct(String id) {
    }

    @Override
    public void saveProduct(Product product) {

    }

    @Override
    public void deleteProduct(String id) {

    }

    @Override
    public void updateProduct(String id) {

    }

    @Override
    public void clearDataBase() {

    }

    @Override
    public void createCollection(String name) {

    }

    @Override
    public void clearCollection(String collection) {

    }
}
