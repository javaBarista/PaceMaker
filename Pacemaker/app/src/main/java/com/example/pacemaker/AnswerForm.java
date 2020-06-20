package com.example.pacemaker;

public class AnswerForm {
    private String anum;
    private String userID;
    private String body;
    private String uploadDate;

    public AnswerForm(String anum, String userID, String body, String uploadDate){
        this.anum = anum;
        this.userID = userID;
        this.body = body;
        this.uploadDate = uploadDate;
    }

    public String getAnum() { return anum; }
    public String getUserID() { return userID; }
    public String getBody() { return body; }
    public String getUploadDate() { return uploadDate; }
}
