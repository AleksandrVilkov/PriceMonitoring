package com.vilkov.PriceMonitoring.model.entity;

public class Money implements BaseEntity {
    private double amount;
    private Currency currency;
    private String type;

    public Money() {
        this.type = Money.class.toString();
    }

    public Money(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
        this.type = Money.class.toString();
    }

    public double getAmount() {
        return amount;
    }

    public Money setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Money setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Money{" +
                "amount=" + amount +
                ", currency=" + currency +
                '}';
    }

    @Override
    public String getType() {
        return type;
    }
}
