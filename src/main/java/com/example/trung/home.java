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
    private Button searchingButton;

    @FXML
    private Button btnDichDoanVan;

    @FXML
    private Button wordAddingButton;

    @FXML
    private Button searchHistoryButton;

    @FXML
    private Button wordEditingButton;

    @FXML
    private Button wordRemovingButton;

    @FXML
    void searchAWord(ActionEvent event) throws IOException {
        String enteredWord = wordLookUpField.getText();
        if (!enteredWord.isBlank()) {
            result = DictionaryManagement.lookUp(enteredWord);
            if (result != null) {
                LookUpHistory.addIntoList(result);
            } else {
                result = new Word(enteredWord, "", "Khong co tu nay trong tu dien");
            }
            DictionaryApplication.setRoot("translateAWord");
        }
    }

    @FXML
    void searchAWordByEnter(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            String enteredWord = wordLookUpField.getText();
            if (!enteredWord.isBlank()) {
                result = DictionaryManagement.lookUp(enteredWord);
                if (result != null) {
                    LookUpHistory.addIntoList(result);
                } else {
                    result = new Word(enteredWord, "", "Khong co tu nay trong tu dien");
                }

                DictionaryApplication.setRoot("translateAWord");
            }
        }
    }

    @FXML
    void dichDoanVan(ActionEvent event) throws IOException {
        DictionaryApplication.setRoot("dichDoanVan");
    }

    @FXML
    void openSearchHistoryPage(ActionEvent event) throws IOException {
        DictionaryApplication.setRoot("LookUpHistory");
    }

    @FXML
    void openWordAddingPage(ActionEvent event) throws IOException {
        DictionaryApplication.setRoot("AddAWord");
    }

    @FXML
    void openWordEditingPage(ActionEvent event) throws IOException {
        DictionaryApplication.setRoot("EditWord");
    }

    @FXML
    void openWordRemovingPage(ActionEvent event) throws IOException {
        DictionaryApplication.setRoot("RemoveWord");
    }

    @FXML
    void initialize() {
        assert wordLookUpField != null : "fx:id=\"wordLookUpField\" was not injected: check your FXML file 'Home.fxml'.";
        assert searchingButton != null : "fx:id=\"searchingButton\" was not injected: check your FXML file 'Home.fxml'.";
        assert btnDichDoanVan != null : "fx:id=\"btnDichDoanVan\" was not injected: check your FXML file 'Home.fxml'.";
        assert wordAddingButton != null : "fx:id=\"wordAddingButton\" was not injected: check your FXML file 'Home.fxml'.";
        assert searchHistoryButton != null : "fx:id=\"searchHistoryButton\" was not injected: check your FXML file 'Home.fxml'.";
        assert wordEditingButton != null : "fx:id=\"wordEditingButton\" was not injected: check your FXML file 'Home.fxml'.";
        assert wordRemovingButton != null : "fx:id=\"wordRemovingButton\" was not injected: check your FXML file 'Home.fxml'.";
    }
}
