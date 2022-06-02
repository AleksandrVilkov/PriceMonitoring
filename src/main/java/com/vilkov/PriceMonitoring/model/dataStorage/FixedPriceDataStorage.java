package com.vilkov.PriceMonitoring.model.dataStorage;

import com.vilkov.PriceMonitoring.model.FixedPrice;

public interface FixedPriceDataStorage {

    public void createFixedPrice(FixedPrice product);

    public void readFixedPrice(String id);

    public void updateFixedPrice(String id);

    public void deleteFixedPrice(String id);
}
