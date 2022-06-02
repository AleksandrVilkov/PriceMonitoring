package com.vilkov.PriceMonitoring.model;

import java.util.Objects;

public class Product {
    private String ulr;
    private String name;
    private String price;
    private String shop;

    private Message message;

    public Product(String name, String price, String shop, String url, Message message) {
        this.name = name;
        this.price = price;
        this.shop = shop;
        this.ulr = url;
        this.message = message;
    }

    public String getUlr() {
        return ulr;
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

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
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
        return ulr.equals(product.ulr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ulr);
    }
}
