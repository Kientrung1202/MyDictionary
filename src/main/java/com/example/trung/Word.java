package com.example.trung;

public class Word {
    private String englishText = "";
    private String partOfSpeech = "";
    private String pronunciation = "";
    private String vietnamText = "Khong co tu nay trong tu dien";
    Word() { }

    Word(String englishText, String pronunciation, String vietnamText) {
        this.englishText = englishText;
        this.pronunciation = pronunciation;
        this.vietnamText = vietnamText;
    }

    public String getEnglishText(){
        return englishText;
    }
    public String getPartOfSpeech() { return partOfSpeech; }
    public String getPronunciation(){
        return pronunciation;
    }
    public String getVietnamText() {
        return vietnamText;
    }

    public void setEnglishText(String englishText) {
        this.englishText = englishText;
    }
    public void setPartOfSpeech(String partOfSpeech) { this.partOfSpeech = partOfSpeech; }
    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }
    public void setVietnamText(String vietnamText) {
        this.vietnamText = vietnamText;
    }
}
