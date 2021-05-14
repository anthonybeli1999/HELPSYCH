package com.example.helpsych.Model;

public class Article {

    private String article_Id, article_title, article_body, image;

    public Article()
    {

    }

    public Article(String article_Id, String article_title, String article_body, String image) {

        this.article_Id = article_Id;
        this.article_title = article_title;
        this.article_body = article_body;
        this.image = image;
    }

    public String getArticle_Id() {
        return article_Id;
    }

    public void setArticle_Id(String article_Id) {
        this.article_Id = article_Id;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getArticle_body() {
        return article_body;
    }

    public void setArticle_body(String article_body) {
        this.article_body = article_body;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
