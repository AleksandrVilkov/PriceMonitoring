package com.vilkov.PriceMonitoring.model.parsers;

import com.vilkov.PriceMonitoring.model.entity.Product;

public interface Parser {
    public Product getProduct(String url);
}
