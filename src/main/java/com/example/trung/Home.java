package com.example.trung;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.List;

public class Home {
    private static Word result = new Word();

    public static Word getResult() {
        return result;
    }

    public static void setResult(Word word) { //used for reopening word content from look up history.
        result = word;
    }

    @FXML
    public ListView suggestedWordListView;

    @FXML
    private TextField lookupField;

    @FXML
    private Label alertLabel;

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
    private Button informationButton;

    @FXML
    private ImageView searchIcon;

    /**
     * Open translation page
     * @throws IOException
     */
    @FXML
    void searchAWord() throws IOException {
        String enteredWord = lookupField.getText();
        if (!enteredWord.isBlank()) {
            result = DictionaryManagement.lookUp(enteredWord);
            if (result != null) {
                SearchHistory.addIntoList(result);
            } else {
                result = new Word(enteredWord, "", "Không có từ này trong từ điển.");
            }
            DictionaryApplication.setRoot("LookupWord");
        }
        else {
            alertLabel.setText("Chưa nhập từ cần tìm.");
        }
    }

    /**
     * Open translation page
     * @param event
     * @throws IOException
     */
    @FXML
    void searchAWordByEnter(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            searchAWord();
        }
    }

    /**
     * ListView
     */
    @FXML
    private void getClickedWord() {
        suggestedWordListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        String word = (String) suggestedWordListView.getSelectionModel().getSelectedItem();
        lookupField.setText(word);
        suggestedWordListView.getSelectionModel().clearSelection();
        hideSuggestedListView();
    }

    /**
     * ListView
     */
    @FXML
    private void addSuggestedWords() {
        String text = lookupField.getText();
        if (!text.isBlank()) {
            alertLabel.setText("");
        }
        List<String> suggestedWords = DictionaryManagement.getSuggestedWords(text);
        if (!suggestedWords.isEmpty()) {
            suggestedWordListView.getItems().clear();
            showSuggestedListView();

            //Set the size of suggestedWordListView.
            final int CELL_SIZE = 25; //height of each cell
            final int MAX_LISTVIEW_HEIGHT = 18;
            if (suggestedWords.size() > MAX_LISTVIEW_HEIGHT) {
                suggestedWordListView.setPrefHeight(MAX_LISTVIEW_HEIGHT * CELL_SIZE);
            }
            else {
                suggestedWordListView.setPrefHeight(suggestedWords.size() * CELL_SIZE);
            }

            suggestedWordListView.getItems().addAll(suggestedWords);
        } else {
            hideSuggestedListView();
        }
        suggestedWords.clear();
    }

    /**
     * Hide ListView
     */
    @FXML
    private void hideSuggestedListView() {
        suggestedWordListView.setVisible(false);
        suggestedWordListView.setDisable(true);
    }

    /**
     * Show ListView
     */
    @FXML
    private void showSuggestedListView() {
        suggestedWordListView.setVisible(true);
        suggestedWordListView.setDisable(false);
    }

    @FXML
    void OpenTextTranslatingPage(ActionEvent event) throws IOException {
        DictionaryApplication.setRoot("TranslateText");
    }

    @FXML
    void openSearchHistoryPage() throws IOException {
        DictionaryApplication.setRoot("SearchHistory");
    }

    @FXML
    void openWordAddingPage() throws IOException {
        DictionaryApplication.setRoot("AddWord");
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
    void openInformationPage() throws IOException {
        DictionaryApplication.setRoot("AboutPage");
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
        assert informationButton != null : "fx:id=\"informationButton\" was not injected: check your FXML file 'Home.fxml'.";
    }
}
