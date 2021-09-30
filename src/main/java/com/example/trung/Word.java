package com.example.trung;

public class Word {
    private  String englishText;
    private  String pronunciation;
    private  String vietnamText;
    Word() {
        englishText = "";
        pronunciation = "";
        vietnamText = "Khong co tu nay trong tu dien";
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
    public void setEnglishText(String englishText) {
        this.englishText = englishText;
    }
    public void setVietnamText(String vietnamText) {
        this.vietnamText = vietnamText;
    }
    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }
}
