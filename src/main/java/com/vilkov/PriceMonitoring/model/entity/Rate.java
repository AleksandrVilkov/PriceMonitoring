package com.vilkov.PriceMonitoring.model.entity;

import java.util.Date;

public class Rate {
    private Currency firstCurrency;
    private Currency secondCurrency;
    private String rate;
    private Date date;
    private Message message;

    public Rate(Currency firstCurrency, Currency secondCurrency, String rate, Date date, Message message) {
        this.firstCurrency = firstCurrency;
        this.secondCurrency = secondCurrency;
        this.rate = rate;
        this.date = date;
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Currency getFirstCurrency() {
        return firstCurrency;
    }

    public void setFirstCurrency(Currency firstCurrency) {
        this.firstCurrency = firstCurrency;
    }

    public Currency getSecondCurrency() {
        return secondCurrency;
    }

    public void setSecondCurrency(Currency secondCurrency) {
        this.secondCurrency = secondCurrency;
    }

    public String  getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
