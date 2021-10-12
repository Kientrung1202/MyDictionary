package com.example.trung;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.IOException;

public class EditWord {
    @FXML
    private TextField replacedWordField;

    @FXML
    private TextField replacingWordField;

    @FXML
    private Button editButton;

    @FXML
    private Label warningForReplacedWord;

    @FXML
    private Label warningForReplacingWord;

    @FXML
    private Label resultOfProcessing;

    @FXML
    private ImageView backButton;

    @FXML
    void initialize() {
        assert editButton != null : "fx:id=\"editButton\" was not injected: check your FXML file 'dichDoanVan.fxml'.";
        assert warningForReplacedWord != null : "fx:id=\"warningForReplacedWord\" was not injected: check your FXML file 'dichDoanVan.fxml'.";
        assert resultOfProcessing != null : "fx:id=\"resultOfProcessing\" was not injected: check your FXML file 'dichDoanVan.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'dichDoanVan.fxml'.";
    }

    public void checkReplacedWord() {
        String receivedWord = replacedWordField.getText();
        if (receivedWord.length() == 0) {
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

    public void checkReplacingWord() {
        String receivedWord = replacingWordField.getText();
        if (receivedWord.length() == 0) {
            warningForReplacingWord.setText("Điền từ vào đây");
            return;
        } else {
            warningForReplacingWord.setText("");
        }
        return;
    }

    public void process() throws IOException { //this method runs when clicking on Edit button.
        String replacedWord = replacedWordField.getText();
        String newWord = replacingWordField.getText();
        if (replacedWord.length() == 0 || newWord.length() == 0) {
            resultOfProcessing.setText("Còn ô trống chưa điền");
            return;
        }
        if(DictionaryManagement.editWord(replacedWord, newWord)) {
            resultOfProcessing.setTextFill(Color.GREEN);
            resultOfProcessing.setText("Sửa từ thành công!");
            LookUpHistory.editWord(replacedWord, newWord);
            replacedWordField.setText("");
            replacingWordField.setText("");
        }
        else {
            resultOfProcessing.setTextFill(Color.RED);
            resultOfProcessing.setText("Sửa từ thất bại.");
        }
    }

    public void back() throws IOException {
        DictionaryApplication.setRoot("Home");
    }
}
