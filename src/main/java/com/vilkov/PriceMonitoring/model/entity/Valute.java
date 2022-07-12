package com.vilkov.PriceMonitoring.model.entity;

public class Valute {
    private String date;
    private String charCode;
    private String value;
    private String previous;
    private String numCode;
    private String nominal;
    private String name;
    private Message message;


    public String getDate() {
        return date;
    }

    public Valute setDate(String date) {
        this.date = date;
        return this;
    }

    public String getCharCode() {
        return charCode;
    }

    public Valute setCharCode(String charCode) {
        this.charCode = charCode;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Valute setValue(String value) {
        this.value = value;
        return this;
    }

    public String getPrevious() {
        return previous;
    }

    public Valute setPrevious(String previous) {
        this.previous = previous;
        return this;
    }


    public String getNumCode() {
        return numCode;
    }

    public Valute setNumCode(String numCode) {
        this.numCode = numCode;
        return this;
    }

    public String getNominal() {
        return nominal;
    }

    public Valute setNominal(String nominal) {
        this.nominal = nominal;
        return this;
    }

    public String getName() {
        return name;
    }

    public Valute setName(String name) {
        this.name = name;
        return this;
    }

    public Message getMessage() {
        return message;
    }

    public Valute setMessage(Message message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return name + "-" + value + " на " + date;
    }
}
