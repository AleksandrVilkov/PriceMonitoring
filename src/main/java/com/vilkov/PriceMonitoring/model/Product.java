package com.vilkov.PriceMonitoring.model;

import java.util.Objects;

public class Product {
    String name;
    String price;
    String shop;

    public Product(String name, String price, String shop) {
        this.name = name;
        this.price = price;
        this.shop = shop;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
    public String getShop() {
        return shop;
    }


    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", shop='" + shop + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
