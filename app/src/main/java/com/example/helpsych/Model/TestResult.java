package com.example.helpsych.Model;

public class TestResult {
    String idTestResult, resultValue, idUser, level;

    public TestResult(String idTestResult, String resultValue, String idUser, String level) {
        this.idTestResult = idTestResult;
        this.resultValue = resultValue;
        this.idUser = idUser;
        this.level = level;
    }
    public TestResult() {
        this.idTestResult = idTestResult;
        this.resultValue = resultValue;
        this.idUser = idUser;
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getIdTestResult() {
        return idTestResult;
    }

    public void setIdTestResult(String idTestResult) {
        this.idTestResult = idTestResult;
    }

    public String getResultValue() {
        return resultValue;
    }

    public void setResultValue(String resultValue) {
        this.resultValue = resultValue;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
