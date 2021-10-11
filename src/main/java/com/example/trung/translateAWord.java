package com.example.trung;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class translateAWord {
    String searchedWord = null;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    public  Label  vietnam;

    @FXML
    private Label english;

    @FXML
    private ImageView btnBack;

    @FXML
    private TextField input;

    @FXML
    private Button search;

    @FXML
    void lookUpWord() throws IOException {
        searchedWord = input.getText();
        Word result = DictionaryManagement.lookupWord(searchedWord);
        if (result != null){
            LookUpHistory.addIntoList(result);
            setEng(result.getEnglishText() + "\n" + result.getPronunciation());
            setVietnam(result.getVietnamText());
        }
        else {
            setEng(searchedWord);
            setVietnam("Khong co tu nay trong tu dien");
        }
    }

    @FXML
    void lookUpWordByEnter(KeyEvent event) throws IOException {
        if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.ESCAPE) {
            searchedWord = input.getText();
            Word result = DictionaryManagement.lookupWord(searchedWord);
            if (result != null){
                LookUpHistory.addIntoList(result);
                setEng(result.getEnglishText() + "\n" + result.getPronunciation());
                setVietnam(result.getVietnamText());
            }
            else {
                setEng(searchedWord);
                setVietnam("Khong co tu nay trong tu dien");
            }
        }
    }

    @FXML
    void back() throws IOException {
        Dictionary.setRoot("Home");
    }

    public  void setVietnam(String viet) {
        vietnam.setText(viet);
    }

    public  void setEng(String eng) {
        english.setText(eng);
    }

    public void showWordContentFromHomePage(Word word) {
        setEng(word.getEnglishText() + "\n" +word.getPronunciation());
        setVietnam(word.getVietnamText());
    }

    @FXML
    void initialize() {
        assert english != null : "fx:id=\"english\" was not injected: check your FXML file 'translateAWord.fxml'.";
        assert btnBack != null : "fx:id=\"btnBack\" was not injected: check your FXML file 'translateAWord.fxml'.";
        assert vietnam != null : "fx:id=\"vietnam\" was not injected: check your FXML file 'translateAWord.fxml'.";
        input.setText(Home.getResult().getEnglishText());
        showWordContentFromHomePage(Home.getResult());
    }
}
