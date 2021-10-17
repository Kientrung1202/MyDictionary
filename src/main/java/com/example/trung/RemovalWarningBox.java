package com.example.trung;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class RemovalWarningBox {
    @FXML
    Button cancelButton;

    @FXML
    Button acceptButton;

    @FXML
    Button closeButton;

    @FXML
    Label notification;

    @FXML
    Label warningLabel;

    @FXML
    Label questionLabel;

    @FXML
    void closeStage() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void processAcceptButton() throws IOException {
        String input = RemoveWord.getRemovedWord();
        DictionaryManagement.removeWord(input);
        LookUpHistory.removeWord(input);
        setNotification();
    }

    @FXML
    void setNotification() {
        cancelButton.setVisible(false);
        cancelButton.setDisable(true);
        acceptButton.setVisible(false);
        acceptButton.setDisable(true);
        warningLabel.setVisible(false);
        questionLabel.setVisible(false);
        //show new content instead
        notification.setVisible(true);
        closeButton.setDisable(false);
        closeButton.setVisible(true);
    }

}
