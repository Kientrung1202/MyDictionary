package com.example.trung;

public class Word {
    private  String englishText;
    private  String pronunciation;
    private  String vietnamText;
    private String type;
    Word() {
        englishText = "";
        pronunciation = "";
        vietnamText = "Khong co tu nay trong tu dien";
        type = "";
    }
    Word(String englishText, String pronunciation, String vietnamText) {
        this.englishText = englishText;
        this.pronunciation = pronunciation;
        this.vietnamText = vietnamText;
    }
    public String getEnglishText(){
        return englishText;
    }
    public String getPronunciation(){
        return pronunciation;
    }
    public String getVietnamText() {
        return vietnamText;
    }

    public String getType() { return type; }

    public void setEnglishText(String englishText) {
        this.englishText = englishText;
    }
    public void setVietnamText(String vietnamText) {
        this.vietnamText = vietnamText;
    }
    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }
    public void setType(String type) { this.type = type; }
}
