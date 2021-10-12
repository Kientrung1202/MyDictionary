package com.example.trung;

public class Word {
    private String englishText = "";
    private String partsOfSpeech = "";
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
    public String getPartsOfSpeech() { return partsOfSpeech; }
    public String getPronunciation(){
        return pronunciation;
    }
    public String getVietnamText() {
        return vietnamText;
    }

    /**
     * Setting parts of speech bases on vietnamText
     * NOTE: Just invoke this method only when vietnamText already been set/existed.
     */
    public void setPartsOfSpeech() {
        var tmpRef = new Object() {
            String partsOfSpeech = "";
        }; //Create a new temporary object just to use the string pOS in functional interface instantiation.

        vietnamText.lines().forEach(line -> {
            if (line.startsWith("* ")) {
                tmpRef.partsOfSpeech += line.substring(2) + "\n";
            }
        });
        this.partsOfSpeech = tmpRef.partsOfSpeech;
    }

    public void setEnglishText(String englishText) {
        this.englishText = englishText;
    }
    public void setPartsOfSpeech(String partsOfSpeech) { this.partsOfSpeech = partsOfSpeech; }
    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }
    public void setVietnamText(String vietnamText) {
        this.vietnamText = vietnamText;
    }
}
