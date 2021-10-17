package com.example.trung;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class RemoveWord {
    private static String removedWord;

    public static String getRemovedWord() {
        return removedWord;
    }

    @FXML
    Label instruction1;

    @FXML
    Label notification;

    @FXML
    Button removeButton;

    @FXML
    TextArea wordMeaningField;

    @FXML
    TextField wordField;

    @FXML
    void initialize() {
        assert removeButton != null : "fx:id=\"removeButton\" was not injected: check your FXML file 'RemoveWord.fxml'.";
        assert wordMeaningField != null : "fx:id=\"wordMeaningField\" was not injected: check your FXML file 'RemoveWord.fxml'.";
        assert wordField != null : "fx:id=\"wordField\" was not injected: check your FXML file 'RemoveWord.fxml'.";
    }

    @FXML
    void back() throws IOException {
        DictionaryApplication.setRoot("Home");
    }

    @FXML
    void process() throws IOException {
        String input = wordField.getText();
        if (DictionaryManagement.lookUp(input) == null) {
            notification.setTextFill(Color.DARKSLATEGREY);
            notification.setText("Không có từ này trong từ điển.");
            return;
        }
        removedWord = input;
        Stage newStage = DictionaryApplication.addAScene("RemovalWarningBox");
        newStage.show();
    }

    @FXML
    void getMeaning() {
        notification.setText(""); //Set notification to blank at the same time

        String input = wordField.getText();
        Word removedWord = DictionaryManagement.lookUp(input);
        if (removedWord != null) {
            wordMeaningField.setText(removedWord.getPronunciation()
                    + "\n" + removedWord.getVietnamText());
        }
        else {
            wordMeaningField.setText("");
        }
    }
}
