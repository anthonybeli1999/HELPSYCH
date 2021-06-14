package com.example.helpsych.Model;

public class Test {
    String idQuestion, nameApproach, idApproach, question;

    public Test(String idQuestion, String nameApproach, String idApproach, String question) {
        this.idQuestion = idQuestion;
        this.nameApproach = nameApproach;
        this.idApproach = idApproach;
        this.question = question;
    }

    public Test() {
        this.idQuestion = idQuestion;
        this.nameApproach = nameApproach;
        this.idApproach = idApproach;
        this.question = question;
    }

    public String getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(String idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getNameApproach() {
        return nameApproach;
    }

    public void setNameApproach(String nameApproach) {
        this.nameApproach = nameApproach;
    }

    public String getIdApproach() {
        return idApproach;
    }

    public void setIdApproach(String idApproach) {
        this.idApproach = idApproach;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
