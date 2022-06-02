package com.vilkov.PriceMonitoring.model;

import java.util.Date;
import java.util.Objects;

public class Product {
    private String ulr;
    private String name;
    private Money price;
    private String shop;

    private Date date;
    private Message message;

    public Product(String name, Money price, String shop, String url, Date date, Message message) {
        this.name = name;
        this.price = price;
        this.shop = shop;
        this.ulr = url;
        this.date = date;
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public String getUlr() {
        return ulr;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
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
