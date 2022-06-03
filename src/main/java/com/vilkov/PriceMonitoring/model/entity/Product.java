package com.vilkov.PriceMonitoring.model.entity;

import java.util.Date;
import java.util.Objects;

public class Product implements BaseEntity {
    private int id;
    private String ulr;
    private String name;
    private Money price;
    private String shop;

    private Date date;
    private Message message;

    private String type;

    public Product(String name, Money price, String shop, String url, Date date, Message message) {
        this.name = name;
        this.price = price;
        this.shop = shop;
        this.ulr = url;
        this.date = date;
        this.message = message;
        this.id = hashCode();
        this.type = Product.class.toString();
    }

    public Product() {

    }

    public int getId() {
        return id;
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

    public String getType() {
        return type;
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
        return id == product.id && Objects.equals(ulr, product.ulr) && Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(shop, product.shop) && Objects.equals(date, product.date) && Objects.equals(message, product.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ulr, name, price, shop);
    }
}
