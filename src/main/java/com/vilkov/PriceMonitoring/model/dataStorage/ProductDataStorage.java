package com.vilkov.PriceMonitoring.model.dataStorage;

import com.vilkov.PriceMonitoring.model.Product;

import java.util.List;

public interface ProductDataStorage {

    public boolean createProduct(Product product);

    public List<Product> readProducts();

    public boolean updateProduct(Product product);

    public boolean deleteProduct(String url);


}
