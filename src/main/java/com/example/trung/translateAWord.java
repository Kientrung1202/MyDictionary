package com.example.trung;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class translateAWord {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label english;

    @FXML
    private ImageView btnBack;

    @FXML
    public  Label  vietnam;
    @FXML
    private TextField input;

    @FXML
    private Button search;

    @FXML
    void seachAWord(ActionEvent event) throws IOException {
        String word = input.getText();
        Word result = DictionaryManagement.lookupWord(word);
        if (!result.getVietnamText().equals("Khong co tu nay trong tu dien")){
            wordLookedUp.addIntoList(result);
        }
        setVietnam(result.getVietnamText());
        setEng(result.getEnglishText()+"\n"+result.getPronunciation());
    }

    @FXML
    void searchByEnter(KeyEvent event) throws IOException {
        if(event.getCode() == KeyCode.ENTER) {
            String word = input.getText();
            Word result = DictionaryManagement.lookupWord(word);
            if (!result.getVietnamText().equals("Khong co tu nay trong tu dien")){
                wordLookedUp.addIntoList(result);
            } 
            setVietnam(result.getVietnamText());
            setEng(result.getEnglishText()+"\n"+result.getPronunciation());
        }
    }


    @FXML
    void back(MouseEvent event) throws IOException {
        HelloApplication.setRoot("home");
    }
    public  void setVietnam(String viet) {
        vietnam.setText(viet);
    }

    public  void setEng(String eng) {
        english.setText(eng);
    }
    public void showResult(Word a) {
        setVietnam(a.getVietnamText());
        setEng(a.getEnglishText() + "\n" +a.getPronunciation());
    }

    @FXML
    void initialize() {
        assert english != null : "fx:id=\"english\" was not injected: check your FXML file 'translateAWord.fxml'.";
        assert btnBack != null : "fx:id=\"btnBack\" was not injected: check your FXML file 'translateAWord.fxml'.";
        assert vietnam != null : "fx:id=\"vietnam\" was not injected: check your FXML file 'translateAWord.fxml'.";
        showResult(home.getRes());
        input.setPromptText(home.getRes().getEnglishText());
    }
}
