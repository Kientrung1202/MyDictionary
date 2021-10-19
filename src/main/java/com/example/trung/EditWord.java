package com.example.trung;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.io.IOException;

public class EditWord {
    @FXML
    private TextField replacedWordField;

    @FXML
    private TextField replacingWordField;

    @FXML
    private TextArea meaningField;

    @FXML
    private TextField pronunciationField;

    @FXML
    private Button editButton;

    @FXML
    private Button backButton;

    @FXML
    private Label warningForReplacedWord;

    @FXML
    private Label warningForReplacingWord;

    @FXML
    private Label processResultLabel;

    @FXML
    private ImageView backImage;

    @FXML
    void initialize() {
        assert editButton != null : "fx:id=\"editButton\" was not injected: check your FXML file 'OpenTextTranslatingPage.fxml'.";
        assert warningForReplacedWord != null : "fx:id=\"warningForReplacedWord\" was not injected: check your FXML file 'OpenTextTranslatingPage.fxml'.";
        assert processResultLabel != null : "fx:id=\"processResultLabel\" was not injected: check your FXML file 'OpenTextTranslatingPage.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'OpenTextTranslatingPage.fxml'.";

        meaningField.setPromptText("* từ loại \r- nghĩa");
    }

    /**
     * for warningForReplacedWord label display
     */
    public void checkReplacedWord() {
        String receivedWord = replacedWordField.getText();
        if (receivedWord.isBlank()) {
            warningForReplacedWord.setText("Điền từ vào đây");
            return;
        }
        Word result = DictionaryManagement.lookUp(receivedWord);
        if (result != null) {
            warningForReplacedWord.setText("");
        }
        else {
            warningForReplacedWord.setText("Từ này không có trong từ điển");
        }
    }

    /**
     * for warningForReplacingWord label display
     */
    public void checkReplacingWord() {
        String receivedWord = replacingWordField.getText();
        if (receivedWord.equals(replacedWordField.getText())) {
            warningForReplacingWord.setText("Trùng với từ hiện tại");
        } else {
            warningForReplacingWord.setText("");
        }
    }

    /**
     * Main method of this class. It will run when clicking on Edit button.
     * @throws IOException
     */
    @FXML
    private void process() throws IOException {
        String replacedWord = replacedWordField.getText();
        String newWord = replacingWordField.getText();
        if (replacedWord.isBlank()) {
            processResultLabel.setTextFill(Color.RED);
            processResultLabel.setText("Chưa nhập từ cần sửa");
            return;
        }
        String pronunciation = pronunciationField.getText();
        String meaning = meaningField.getText();

        Word tempReplacedWord = DictionaryManagement.lookUp(replacedWord);
        Word tempNewWord = new Word(newWord, pronunciation, meaning);

        if (pronunciation.equals(tempReplacedWord.getPronunciation())
                && meaning.equals(tempReplacedWord.getVietnamText())
                && newWord.isBlank()) {
            processResultLabel.setTextFill(Color.RED);
            processResultLabel.setText("Từ hoặc nội dung của từ chưa được thay đổi");
            return;
        }

        if(DictionaryManagement.editWord(tempReplacedWord, tempNewWord)) {
            processResultLabel.setTextFill(Color.GREEN);
            processResultLabel.setText("Sửa từ thành công!");

            //The word to replace old one is blank means that only content of the old word change.
            if (!newWord.isBlank()) {
                SearchHistory.editWord(replacedWord, newWord);
            }

            replacedWordField.setText("");
            replacingWordField.setText("");
        }
        else {
            processResultLabel.setTextFill(Color.RED);
            processResultLabel.setText("Sửa từ thất bại.");
        }
    }

    @FXML
    private void processByPressEnter(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            process();
        }
    }

    @FXML
    private void getContent() {
        String word = replacedWordField.getText();
        Word result = DictionaryManagement.lookUp(word);
        if (result == null) return;
        meaningField.setText(result.getVietnamText());
        pronunciationField.setText(result.getPronunciation());
    }

    @FXML
    private void back() throws IOException {
        DictionaryApplication.setRoot("Home");
    }

    @FXML
    private  void clickOnPage() {}
}
