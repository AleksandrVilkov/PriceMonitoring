package com.vilkov.PriceMonitoring.model.entity;

import com.vilkov.PriceMonitoring.model.Status;

public class Message implements BaseEntity {
    private Status status;
    private String message;

    public Message(Status status, String message) {
        this.status = status;
        message = message;
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
}
