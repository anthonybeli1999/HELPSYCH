package com.example.helpsych.Model;

public class Mood {
    private String comment, date, from, mood;

    public Mood(String comment, String date, String from, String mood) {
        this.comment = comment;
        this.date = date;
        this.from = from;
        this.mood = mood;
    }
    public Mood() {
        this.comment = comment;
        this.date = date;
        this.from = from;
        this.mood = mood;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }
}
