package com.example.trung;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class RemovalWarningBox {
    String removedWord;

    @FXML
    Button cancelButton;

    @FXML
    Button acceptButton;

    @FXML
    Button turnToEditWordButton;

    @FXML
    void closeStage() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void processAcceptButton() throws IOException {
        String input = RemoveWord.getRemovedWord();
        DictionaryManagement.removeWord(input);
        Stage stage = (Stage)acceptButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void processEditWordButton() {
        System.out.println("edit word");
    }

}
