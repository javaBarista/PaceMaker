package com.example.pacemaker.ui.mynote;

public class CardForm {

    private String num1;
    private String num2;
    private String name1;
    private String name2;
    private String address1;
    private String address2;
    private String text1;
    private String text2;

    public CardForm(String name1, String name2){
        this.name1 = name1;
        this.name2 = name2;
    }

    public CardForm(String num1, String name1, String address1, String text1, String num2, String name2, String address2, String text2){
        this.num1 = num1;
        this.name1 = name1;
        this.address1 = address1;
        this.text1 = text1;

        this.num2 = num2;
        this.name2 = name2;
        this.address2 = address2;
        this.text2 = text2;
    }

    public String getName1() { return name1; }
    public String getName2() { return name2; }
    public String getAddress1() { return address1; }
    public String getAddress2() { return address2; }
    public boolean isText1() { return text1.length() > 10 ? true : false; }
    public boolean isText2() { return text2.length() > 10 ? true : false; }
    public String getText1() { return text1; }
    public String getText2() { return text2; }
    public String getNum1() { return num1; }
    public String getNum2() { return num2; }
}
