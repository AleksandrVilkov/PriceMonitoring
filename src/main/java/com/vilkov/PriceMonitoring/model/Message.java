package com.vilkov.PriceMonitoring.model;

public class Message {
    private Status status;
    private String message;

    public Message(Status status, String message) {
        this.status = status;
        message = message;
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
