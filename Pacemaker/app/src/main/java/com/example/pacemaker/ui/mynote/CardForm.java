package com.example.pacemaker.ui.mynote;

public class CardForm {

    private String num1;
    private String num2;
    private String name1;
    private String name2;
    private String year1;
    private String year2;
    private String address1;
    private String address2;
    private String testNum1;
    private String testNum2;
    private String date1;
    private String date2;

    public CardForm(String name1, String name2){
        this.name1 = name1;
        this.name2 = name2;
    }

    public CardForm(String num1, String name1, String year1, String address1, String testNum1, String date1, String num2, String name2, String year2, String address2, String testNum2, String date2){
        this.num1 = num1;
        this.name1 = name1;
        this.year1 = year1;
        this.address1 = address1;
        this.testNum1 = testNum1;
        this.date1 = date1;

        this.num2 = num2;
        this.name2 = name2;
        this.year2 = year2;
        this.address2 = address2;
        this.testNum2 = testNum2;
        this.date2 = date2;
    }

    public String getName1() { return name1; }
    public String getName2() { return name2; }
    public String getYear1() { return year1; }
    public String getYear2() { return year2; }
    public String getAddress1() { return address1; }
    public String getAddress2() { return address2; }
    public String getTestNum1() { return testNum1; }
    public String getTestNum2() { return testNum2; }
    public String getNum1() { return num1; }
    public String getNum2() { return num2; }
    public String getDate1() { return date1; }
    public String getDate2() { return date2; }
    public String getTitle1() { return year1 + " " + name1 + " " + testNum1 + "번"; }
    public String getTitle2() { return year2 + " " + name2 + " " + testNum2 + "번"; }
}
