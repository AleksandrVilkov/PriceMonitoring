package com.vilkov.PriceMonitoring.model;

public class Money {
    private double amount;
    private Currency currency;

    public Money() {
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

    @Override
    public String toString() {
        return "Money{" +
                "amount=" + amount +
                ", currency=" + currency +
                '}';
    }
}
