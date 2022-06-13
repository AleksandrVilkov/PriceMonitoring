package com.vilkov.PriceMonitoring.controllers.entity;

import java.util.Objects;

public class FixedPriceVO implements Comparable {
    String date;
    String price;
    String shop;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FixedPriceVO that = (FixedPriceVO) o;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    @Override
    public int compareTo(Object o) {
        return this.date.compareTo(((FixedPriceVO) o).getDate());
    }
}
