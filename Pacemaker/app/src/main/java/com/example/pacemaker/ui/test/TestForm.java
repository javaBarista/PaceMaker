package com.example.pacemaker.ui.test;

public class TestForm {
    private String num;
    private String address;
    private String main_text;
    private String part;
    private String ans;

    public TestForm(String num, String address, String main_text, String part, String ans){
        this.num = num;
        this.address = address;
        this.main_text = main_text;
        this.part = part;
        this.ans = ans;
    }

    public String getNum(){ return num; }
    public String getAddress(){ return address; }
    public String getMain_text(){ return main_text; }
    public boolean isMain_text(){
        if(main_text.length() > 20) return true;
        return false;
    }
    public String getPart(){ return part; }
    public int getAnswer (){ return Integer.valueOf(ans); }
}
