package com.example.pacemaker.ui.calender;

import java.util.HashMap;
import java.util.Map;

public class CalenderListItem {
    private String college;
    private String todo;
    private String startDate;
    private String endDate;
    private Map<String, String> map;

    public void setMap(){
        map = new HashMap<>();
        if(startDate.length() > 5) {
            map.put("sYear", startDate.substring(0, startDate.indexOf("/")));
            map.put("sMonth", startDate.substring(startDate.indexOf("/") + 1, startDate.lastIndexOf("/")));
            map.put("sDay",(startDate.substring(startDate.lastIndexOf("/") + 1, startDate.length())));
        }
        else {
            map.put("sYear", "0");
            map.put("sMonth", "0");
            map.put("sDay", "0");
        }
        map.put("eYear", endDate.substring(0, endDate.indexOf("/")));
        map.put("eMonth", endDate.substring(endDate.indexOf("/") + 1, endDate.lastIndexOf("/")));
        map.put("eDay", (endDate.substring(endDate.lastIndexOf("/") + 1, endDate.length())));
    }

    public CalenderListItem(String college, String todo, String startDate, String endDate){
        this.college = college;
        this.todo = todo;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getCollege(){return this.college; }
    public String getTodo(){ return "(" + this.todo + ")"; }
    public String getStartDate() { return startDate.length() > 4 ? this.startDate + " ~ " : null; }
    public String getEndDate(){ return this.endDate; }
    public int getMap(String name){
       return Integer.parseInt(map.get(name));
    }

    public int[] getMonth() {

        int[] result = new int[2];

        result[0] = Integer.parseInt(endDate.substring(endDate.indexOf("/") + 1, endDate.lastIndexOf("/")));

        if (startDate.length() > 4) {
            result[1] = Integer.parseInt(startDate.substring(startDate.indexOf("/") + 1, startDate.lastIndexOf("/")));
        }
        return result;
    }
    public int getDue() {
        int month = Integer.parseInt(endDate.substring(endDate.indexOf("/") + 1, endDate.lastIndexOf("/")));
        int day = Integer.parseInt(endDate.substring(endDate.lastIndexOf("/") + 1, endDate.length()));

        return (month == 1 ? 13 : month) * 100 + day;
    }
}
