package com.example.pacemaker;

public class AnswerForm {
    private String userID;
    private String body;
    private String uploadDate;

    public AnswerForm(String userID, String body, String uploadDate){
        this.userID = userID;
        this.body = body;
        this.uploadDate = uploadDate;
    }

    public String getUserID() { return userID; }
    public String getBody() { return body; }
    public String getUploadDate() { return uploadDate; }
}
