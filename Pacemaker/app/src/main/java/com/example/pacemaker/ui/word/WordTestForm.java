package com.example.pacemaker.ui.word;

public class WordTestForm {
    private String testNumber;
    private String testWord;
    private String answer;
    private String nAnswer1;
    private String nAnswer2;
    private String nAnswer3;

    public WordTestForm() {
    }

    public WordTestForm(String testWord, String answer, String nAnswer1, String nAnswer2, String nAnswer3){
        this.testWord = testWord;
        this.answer = answer;
        this.nAnswer1 = nAnswer1;
        this.nAnswer2 = nAnswer2;
        this.nAnswer3 = nAnswer3;
    }

    public String getTestNumber() { return testNumber; }
    public String getTestWord() { return testWord; }
    public String getAnswer() { return answer; }
    public String getnAnswer1() { return nAnswer1; }
    public String getnAnswer2() { return nAnswer2; }
    public String getnAnswer3() { return nAnswer3; }
}
