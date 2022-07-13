package com.vilkov.PriceMonitoring.model.entity;

import com.vilkov.PriceMonitoring.model.Status;

public class Message implements BaseEntity {
    private Status status;
    private String message;
    private final String type;

    public Message() {
        this.type = Message.class.toString();
    }

    public Message(Status status, String message) {
        this.status = status;
        this.message = message;
        this.type = Message.class.toString();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "MESSAGE: " + this.status.name() + ": " + this.message;
    }
}
