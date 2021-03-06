package com.example.pacemaker;

public class ListViewItem {
    private String college_name;
    private String college_dday;
    private String college_date;

    public ListViewItem() {}

    public ListViewItem(String college_name, String college_dday){
        this.college_name = college_name;
        this.college_dday = college_dday;
    }

    public void setCollege_name(String college_name) {
        this.college_name = college_name;
    }
    public void setCollege_dday(String college_dday) {
        this.college_dday = college_dday;
    }
    public void setCollege_date(String college_date) { this.college_date = college_date; }

    public String getCollege_name() {
        return this.college_name;
    }
    public String getCollege_dday() {
        return this.college_dday;
    }
    public String getCollege_date() { return college_date; }
}
