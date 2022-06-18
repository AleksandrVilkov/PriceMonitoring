package com.vilkov.PriceMonitoring.model.entity;

public class Client implements BaseEntity {
    private final String clientID;
    private char[] password;
    private final String type;

    public Client(String clientID, char[] password) {
        this.clientID = clientID;
          this.password = password;
        this.type = Client.class.toString();
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public char[] getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public String getClientID() {
        return clientID;
    }


}
