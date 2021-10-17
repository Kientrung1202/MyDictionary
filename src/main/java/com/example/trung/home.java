package com.example.trung;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Home {
    private static Word result = new Word();

    public static Word getResult() {
        return result;
    }

    @FXML
    public ListView suggestedWordListView;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField lookupField;

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
        String enteredWord = lookupField.getText();
        if (enteredWord.length() > 0) {
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
            String enteredWord = lookupField.getText();
            if (enteredWord.length() > 0) {
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
    private void getClickedWord() {
        suggestedWordListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        String word = (String) suggestedWordListView.getSelectionModel().getSelectedItem();
        lookupField.setText(word);
        suggestedWordListView.getSelectionModel().clearSelection();
        hideSuggestedListView();
    }

    @FXML
    private void addSuggestedWords() {
        String text = lookupField.getText();
        List<String> suggestedWords = DictionaryManagement.getSuggestedWords(text);
        if (!suggestedWords.isEmpty()) {
            showSuggestedListView();
            suggestedWordListView.getItems().clear();
            suggestedWordListView.getItems().addAll(suggestedWords);
        } else {
            hideSuggestedListView();
        }
        suggestedWords.clear();
    }
    @FXML
    private void hideSuggestedListView() {
        suggestedWordListView.setVisible(false);
        suggestedWordListView.setDisable(true);
    }
    @FXML
    private void showSuggestedListView() {
        suggestedWordListView.setVisible(true);
        suggestedWordListView.setDisable(false);
    }

    @FXML
    void dichDoanVan(ActionEvent event) throws IOException {
        DictionaryApplication.setRoot("dichDoanVan");
    }

    @FXML
    void openSearchHistoryPage() throws IOException {
        DictionaryApplication.setRoot("LookUpHistory");
    }

    @FXML
    void openWordAddingPage() throws IOException {
        DictionaryApplication.setRoot("AddAWord");
    }

    @FXML
    void openWordEditingPage() throws IOException {
        DictionaryApplication.setRoot("EditWord");
    }

    @FXML
    void openWordRemovingPage() throws IOException {
        DictionaryApplication.setRoot("RemoveWord");
    }

    @FXML
    void initialize() {
        assert lookupField != null : "fx:id=\"lookupField\" was not injected: check your FXML file 'Home.fxml'.";
        assert searchingButton != null : "fx:id=\"searchingButton\" was not injected: check your FXML file 'Home.fxml'.";
        assert btnDichDoanVan != null : "fx:id=\"btnDichDoanVan\" was not injected: check your FXML file 'Home.fxml'.";
        assert wordAddingButton != null : "fx:id=\"wordAddingButton\" was not injected: check your FXML file 'Home.fxml'.";
        assert searchHistoryButton != null : "fx:id=\"searchHistoryButton\" was not injected: check your FXML file 'Home.fxml'.";
        assert wordEditingButton != null : "fx:id=\"wordEditingButton\" was not injected: check your FXML file 'Home.fxml'.";
        assert wordRemovingButton != null : "fx:id=\"wordRemovingButton\" was not injected: check your FXML file 'Home.fxml'.";
    }
}
