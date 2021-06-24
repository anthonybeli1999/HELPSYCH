package com.example.helpsych.Model;

public class Article {

    private String Id;
    private String title;
    private String subtitle;
    private String body;
    private String date;
    private String author;
    private String label;
    private String image;
    private String creationdate;
    private String approach;
    private String state;


    public Article(String id, String title, String body, String date, String author, String image, String creationdate, String approach, String state) {
        Id = id;
        this.title = title;
        this.body = body;
        this.date = date;
        this.author = author;
        this.image = image;
        this.creationdate = creationdate;
        this.approach = approach;
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getApproach() {
        return approach;
    }

    public void setApproach(String approach) {
        this.approach = approach;
    }

    public Article()
    {

    }

    public Article(String article_Id, String article_title, String article_subtitle, String article_body, String article_date,
                   String article_author, String article_label, String image, String article_creationdate, String approach, String state) {

        this.Id = article_Id;
        this.title = article_title;
        this.subtitle = article_subtitle;
        this.body = article_body;
        this.date = article_date;
        this.author = article_author;
        this.label = article_label;
        this.image = image;
        this.approach = approach;
        this.creationdate = article_creationdate;
        this.state = state;
    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(String creationdate) {
        this.creationdate = creationdate;
    }
}
