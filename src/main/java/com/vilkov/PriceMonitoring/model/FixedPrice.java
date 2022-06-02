package com.vilkov.PriceMonitoring.model;

import java.util.Date;

public class FixedPrice {
    Date date;
    Money price;

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
}
