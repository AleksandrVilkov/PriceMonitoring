package com.vilkov.PriceMonitoring.model.dataStorage;

import com.vilkov.PriceMonitoring.model.Product;

public interface ProductDataStorage {

    public void createProduct(Product product);

    public void readProduct(String id);

    public void updateProduct(String id);

    public void deleteProduct(String id);


}
