package com.vilkov.PriceMonitoring.model;

import com.vilkov.PriceMonitoring.model.dataStorage.FixedPriceDataStorage;


public class FixedPriceHelper {
    private static FixedPriceDataStorage dataStorage;

    public static void createProductToDataBase(FixedPrice product) {
        dataStorage.createFixedPrice(product);
    }

    public static void readFixedPriceInDataBase(String id) {
        dataStorage.readFixedPrice(id);
    }

    public static void deleteFixedPriceInDataBase(String id) {
        dataStorage.deleteFixedPrice(id);
    }

    public static void updateFixedPriceInDataBase(String id) {
        dataStorage.updateFixedPrice(id);
    }


}
