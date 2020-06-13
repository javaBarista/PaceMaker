package com.example.pacemaker;

public class QandAForm {

    String num;
    String userID;
    String year;
    String name;
    String imgUrl;
    String uploadDate;

    public QandAForm(String num, String userID, String year, String name, String imgUrl, String uploadDate){
        this.num = num;
        this.userID = userID;
        this.year = year;
        this.name = name;
        this.imgUrl = imgUrl;
        this.uploadDate = uploadDate;
    }

    public String getNum() { return num; }
    public String getUserID(){ return  userID; }
    public String getYear() { return year; }
    public String getName() { return name; }
    public String getImgUrl() { return imgUrl; }
    public String getUploadDate() { return uploadDate; }
}
