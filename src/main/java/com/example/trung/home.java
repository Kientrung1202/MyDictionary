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

public class home {

    private static Word res = new Word();
    public static Word getRes() {
        return res;
    }
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField wordLookup;

    @FXML
    private Button btnDichDoanVan;
    @FXML
    private Button search;
    @FXML
    private Button btnAdd;

    @FXML
    void addAWord(ActionEvent event) throws IOException {
        HelloApplication.setRoot("AddAWord");
    }
    @FXML
    void dichDoanVan(ActionEvent event) throws IOException {
        HelloApplication.setRoot("dichDoanVan");
    }

    @FXML
    void searchAWord(ActionEvent event) throws IOException {
        String abc = wordLookup.getText();
        if(abc.length() > 0) {
            res = DictionaryManagement.lookupWord(abc);
            HelloApplication.setRoot("translateAWord");
        }
    }
    @FXML
    void searchAWordByEnter(KeyEvent event) throws IOException {
        if(event.getCode() == KeyCode.ENTER) {
            String abc = wordLookup.getText();
            if(abc.length() > 0) {
                res = DictionaryManagement.lookupWord(abc);
                HelloApplication.setRoot("translateAWord");
            }
        }
    }
    @FXML
    void initialize() {
        assert wordLookup != null : "fx:id=\"wordLookup\" was not injected: check your FXML file 'home.fxml'.";
        assert search != null : "fx:id=\"search\" was not injected: check your FXML file 'home.fxml'.";
        assert btnDichDoanVan != null : "fx:id=\"btnDichDoanVan\" was not injected: check your FXML file 'home.fxml'.";
    }
}
