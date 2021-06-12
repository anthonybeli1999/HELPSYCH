package com.example.helpsych.Model;

public class Rating {
    private String message, valuation, fromID, toID, fromName, messageID,time , date;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getValuation() {
        return valuation;
    }

    public void setValuation(String valuation) {
        this.valuation = valuation;
    }

    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public String getToID() {
        return toID;
    }

    public void setToID(String toID) {
        this.toID = toID;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Rating(String message, String valuation, String fromID, String toID, String fromName, String messageID, String time, String date) {
        this.message = message;
        this.valuation = valuation;
        this.fromID = fromID;
        this.toID = toID;
        this.fromName = fromName;
        this.messageID = messageID;
        this.time = time;
        this.date = date;
    }
    public Rating() {
        this.message = message;
        this.valuation = valuation;
        this.fromID = fromID;
        this.toID = toID;
        this.fromName = fromName;
        this.messageID = messageID;
        this.time = time;
        this.date = date;
    }
}
