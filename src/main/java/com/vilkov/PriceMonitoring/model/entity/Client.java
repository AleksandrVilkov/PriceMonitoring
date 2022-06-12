package com.vilkov.PriceMonitoring.model.entity;

public class Client implements BaseEntity {
    private final String clientID;
    private final String type;

    public Client(String clientID) {
        this.clientID = clientID;
        this.type = Client.class.toString();
    }

    public String getType() {
        return type;
    }

    public String getClientID() {
        return clientID;
    }


}
