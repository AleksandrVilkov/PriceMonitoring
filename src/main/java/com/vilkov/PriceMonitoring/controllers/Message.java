package com.vilkov.PriceMonitoring.controllers;


public class Message {
    private String status;
    private String messageText;

    public Message(String status, String messageText) {
        this.status = status;
        this.messageText = messageText;
    }

    public Message() {
        this.messageText= "";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    @Override
    public String toString() {
        return "Message{" +
                "status='" + status + '\'' +
                ", messageText='" + messageText + '\'' +
                '}';
    }
}
