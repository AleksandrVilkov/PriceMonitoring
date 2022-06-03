package com.vilkov.PriceMonitoring.model;

import java.util.Date;
import java.util.Objects;

public class FixedPrice implements Comparable {
    Date date;
    Money price;

    String shop;

    public String getShop() {
        return shop;
    }

    public FixedPrice setShop(String shop) {
        this.shop = shop;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public FixedPrice setDate(Date date) {
        this.date = date;
        return this;
    }

    public Money getPrice() {
        return price;
    }

    public FixedPrice setPrice(Money price) {
        this.price = price;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FixedPrice that = (FixedPrice) o;
        return Objects.equals(date, that.date) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, price);
    }

    @Override
    public int compareTo(Object o) {
        return this.date.compareTo(((FixedPrice) o).getDate());
    }

    @Override
    public String toString() {
        return "FixedPrice{" +
                "date=" + date +
                ", price=" + price +
                '}';
    }
}
