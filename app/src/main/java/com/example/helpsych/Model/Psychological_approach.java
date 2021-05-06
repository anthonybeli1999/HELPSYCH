package com.example.helpsych.Model;

public class Psychological_approach {
    private String p_approachId, p_approachName;

    public String getP_approachId() {
        return p_approachId;
    }

    public void setP_approachId(String id) {
        this.p_approachId = id;
    }

    public String getP_approachName() {
        return p_approachName;
    }

    public void setP_approachName(String p_approachName) {
        this.p_approachName = p_approachName;
    }

    public Psychological_approach(String id, String p_approachName) {
        this.p_approachId = id;
        this.p_approachName = p_approachName;
    }

    public Psychological_approach() {
    }
}
