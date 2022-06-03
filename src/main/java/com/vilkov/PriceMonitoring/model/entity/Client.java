package com.vilkov.PriceMonitoring.model.entity;

public class Client implements BaseEntity {
    private String clientID;

    public Client(String clientID) {
        this.clientID = clientID;
    }

    public String getClientID() {
        return clientID;
    }
}
