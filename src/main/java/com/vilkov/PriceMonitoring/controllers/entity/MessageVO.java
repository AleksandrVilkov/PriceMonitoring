package com.vilkov.PriceMonitoring.controllers.entity;


public class MessageVO {
    private String status;
    private String messageText;

    public MessageVO(String status, String messageText) {
        this.status = status;
        this.messageText = messageText;
    }

    public MessageVO() {
        this.messageText = "";
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
