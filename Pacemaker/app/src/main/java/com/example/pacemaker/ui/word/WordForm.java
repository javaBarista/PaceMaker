package com.example.pacemaker.ui.word;

public class WordForm {
    private String Id;
    private String Word;
    private String pronunciation;
    private String partSpeech;
    private String meaning;

    public WordForm(String Id, String Word, String pronunciation, String partSpeech, String meaning){
        this.Id = Id;
        this.Word = Word;
        this.pronunciation = pronunciation;
        this.partSpeech = partSpeech;
        this.meaning = meaning;
    }

    public String getWord(){ return Word; }
    public String getPron(){ return pronunciation; }
    public String getGram(){ return partSpeech; }
    public String getMean(){ return meaning; }
}
