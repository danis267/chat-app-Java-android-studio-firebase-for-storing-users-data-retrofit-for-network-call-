package com.example.whatsapp.Models;

public class Messages {

    String uId, message, messageId;
    Long timestamp;
    String Timestamp2;

    public Messages() {}

    public Messages(String uId, String message, Long timestamp, String Timestamp2, String messageId) {
        this.uId = uId;
        this.message = message;
        this.timestamp = timestamp;
        this.Timestamp2 = Timestamp2;
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp2() {
        return Timestamp2;
    }

    public void setTimestamp2(String timestamp2) {
        Timestamp2 = timestamp2;
    }
}
