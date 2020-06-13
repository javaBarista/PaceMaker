package com.example.pacemaker;

public class QandAForm {

    String num;
    String userID;
    String year;
    String name;
    String testNum;
    String uploadDate;

    public QandAForm(String num, String userID, String year, String name, String testNum, String uploadDate){
        this.num = num;
        this.userID = userID;
        this.year = year;
        this.name = name;
        this.testNum = testNum;
        this.uploadDate = uploadDate;
    }

    public String getNum() { return num; }
    public String getUserID(){ return  userID; }
    public String getYear() { return year; }
    public String getName() { return name; }
    public String getTestNum() { return testNum; }
    public String getUploadDate() { return uploadDate; }
}
