package com.example.trung;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class translateAWord {
    @FXML
    public ListView suggestedWordListView;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea vietnamText;

    @FXML
    private Label englishLabel;

    @FXML
    private Label pronunciationLabel;

    @FXML
    private ImageView btnBack;

    @FXML
    private TextField input;

    @FXML
    private Button search;

    @FXML
    void lookUpWord() throws IOException {
        String searchedWord = input.getText();
        Word result = DictionaryManagement.lookUp(searchedWord);
        if (result != null){
            LookUpHistory.addIntoList(result);
            showWordContent(result);
        }
        else {
            setEnglishLabel(searchedWord);
            setVietnamText("Không có từ này trong từ điển.");
        }
    }

    @FXML
    void lookUpWordByEnter(KeyEvent event) throws IOException {
        if(event.getCode() == KeyCode.ENTER) {
            lookUpWord();
        }
    }

    @FXML
    void back() throws IOException {
        DictionaryApplication.setRoot("Home");
    }

    @FXML
    private void getClickedWord() {
        suggestedWordListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        String word = (String) suggestedWordListView.getSelectionModel().getSelectedItem();
        input.setText(word);
        suggestedWordListView.getSelectionModel().clearSelection();
        hideSuggestedListView();
    }

    @FXML
    private void addSuggestedWords() {
        String text = input.getText();
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
//        suggestedWordListView.setDisable(true);
    }
    @FXML
    private void showSuggestedListView() {
        suggestedWordListView.setVisible(true);
//        suggestedWordListView.setDisable(false);
    }

    private void setVietnamText(String viet) {
        vietnamText.setText(viet);
    }

    private void setEnglishLabel(String eng) {
        englishLabel.setText(eng);
    }

    private void setPronunciationLabel(String pronunciation) { pronunciationLabel.setText(pronunciation); }

    private void showWordContent(Word word) {
        setEnglishLabel(word.getEnglishText());
        setPronunciationLabel(word.getPronunciation());
        setVietnamText(word.getVietnamText());
    }

    @FXML
    void initialize() {
        assert englishLabel != null : "fx:id=\"englishLabel\" was not injected: check your FXML file 'translateAWord.fxml'.";
        assert btnBack != null : "fx:id=\"btnBack\" was not injected: check your FXML file 'translateAWord.fxml'.";
        assert vietnamText != null : "fx:id=\"vietnamText\" was not injected: check your FXML file 'translateAWord.fxml'.";
        input.setText(Home.getResult().getEnglishText());
        showWordContent(Home.getResult());
    }
}
