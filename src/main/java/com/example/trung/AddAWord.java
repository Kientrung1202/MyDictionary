package com.example.trung;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddAWord {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField englishField;

    @FXML
    private TextField pronunciationField;

    @FXML
    private TextArea vietnameseField;

    @FXML
    private ImageView back;

    @FXML
    private Button add;

    @FXML
    void addAWord(ActionEvent event) throws IOException {
        String englishText = englishField.getText();
        String vietnamText = vietnameseField.getText();
        if (englishText.isBlank() || vietnamText.isBlank()) {
            return;
        }
        String pronunciation = pronunciationField.getText();
        Word newWord = new Word(englishText, pronunciation, vietnamText);

        boolean ok = DictionaryManagement.addAWord(newWord);
        if(ok) {
            //reset 3 text fields
            englishField.setText("");
            pronunciationField.setText("");
            vietnameseField.setText("");
            Stage newStage = DictionaryApplication.addAScene("SucceededAdditionNoticeBox");
            newStage.show();
        } else {
            Stage newStage = DictionaryApplication.addAScene("FailedAdditionNoticeBox");
            newStage.show();
        }
    }

    @FXML
    void back(MouseEvent event) throws IOException {
        DictionaryApplication.setRoot("Home");
    }

    @FXML
    void clickOnPageHandle() {

    }
    @FXML
    void initialize() {
        assert add != null : "fx:id=\"add\" was not injected: check your FXML file 'Home.fxml'.";
    }
}
