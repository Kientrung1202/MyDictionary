package com.example.trung;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Home {
    private static String searchedWord = null;
    private static Word result = new Word();

    public static Word getResult() {
        return result;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField wordLookUpField;

    @FXML
    private Button btnDichDoanVan;

    @FXML
    private Button search;

    @FXML
    private Button btnAdd;

    @FXML
    private Button lookedUp;

    @FXML
    void addAWord(ActionEvent event) throws IOException {
        Dictionary.setRoot("AddAWord");
    }

    @FXML
    void dichDoanVan(ActionEvent event) throws IOException {
        Dictionary.setRoot("dichDoanVan");
    }

    @FXML
    void searchAWord(ActionEvent event) throws IOException {
        searchedWord = wordLookUpField.getText();
        if(searchedWord.length() > 0) {
            result = DictionaryManagement.lookupWord(searchedWord);
            if (result != null){
                LookUpHistory.addIntoList(result);
            }
            else {
                result = new Word(searchedWord, "", "Khong co tu nay trong tu dien");
            }
            Dictionary.setRoot("translateAWord");
        }
    }

    @FXML
    void searchAWordByEnter(KeyEvent event) throws IOException {
        if(event.getCode() == KeyCode.ENTER) {
            searchedWord = wordLookUpField.getText();
            if(searchedWord.length() > 0) {
                result = DictionaryManagement.lookupWord(searchedWord);
                if (result != null){
                    LookUpHistory.addIntoList(result);
                }
                else {
                    result = new Word(searchedWord, "", "Khong co tu nay trong tu dien");
                }

                Dictionary.setRoot("translateAWord");
            }
        }
    }

    @FXML
    void wordLookedUp(ActionEvent event) throws  IOException {
        Dictionary.setRoot("LookUpHistory");
    }

    @FXML
    void initialize() throws IOException {
        assert wordLookUpField != null : "fx:id=\"wordLookUpField\" was not injected: check your FXML file 'Home.fxml'.";
        assert search != null : "fx:id=\"search\" was not injected: check your FXML file 'Home.fxml'.";
        assert btnDichDoanVan != null : "fx:id=\"btnDichDoanVan\" was not injected: check your FXML file 'Home.fxml'.";
    }
}
