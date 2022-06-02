package com.vilkov.PriceMonitoring.DataBaseAdapter;

import com.vilkov.PriceMonitoring.model.Product;
import com.vilkov.PriceMonitoring.model.dataStorage.ProductDataStorage;

import java.beans.JavaBean;

@JavaBean
public class DataBaseAdapter implements ProductDataStorage {
    //TODO реализовать подклчюение к монго

    @Override
    public void readProduct(String id) {
    }

    @Override
    public void createProduct(Product product) {

    }

    @Override
    public void deleteProduct(String id) {

    }

    @Override
    public void updateProduct(String id) {

    }
}
