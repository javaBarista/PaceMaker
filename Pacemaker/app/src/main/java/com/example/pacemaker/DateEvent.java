package com.example.pacemaker;

public class DateEvent {
    private int year;
    private int month;
    private int date;

    public DateEvent(int year, int month, int date){
        this.year = year;
        this.month = month;
        this.date = date;
    }

    public int[] getDate(){
        int[] result = new int[3];
        result[0] = year;
        result[1] = month;
        result[2] = date;
        return result;
    }

    public String getKey(){
        return "CalendarDay{" + year + "-" + month + "-" + date + "}";
    }
}
