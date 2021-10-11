package com.example.trung;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private TextField english;

    @FXML
    private TextField vietnam;


    @FXML
    private ImageView back;

    @FXML
    private Button add;

    // buton add click
    @FXML
    void addAWord(ActionEvent event) throws IOException {
        String englishText = english.getText();
        String vietnamText = vietnam.getText();
        Word newWord = new Word(englishText, "", vietnamText);
        boolean ok = DictionaryManagement.addAWord(newWord);
        if(ok) {
            english.setText(""); //reset 2 text fields
            vietnam.setText("");
            Stage newStage = Dictionary.addAScene("SucceededAdditionNoticeBox");
            newStage.show();
        } else {
            Stage newStage = Dictionary.addAScene("FailedAdditionNoticeBox");
            newStage.show();
        }
//        if (ok) {
//            //hien asert ok
//            c
//            SucceededAdditionNoticeBox.setResult(true);
//            Stage newStage = Dictionary.addAScene("SucceededAdditionNoticeBox");
//            newStage.show();
//        } else {
//            //hien asert khong ok
//            SucceededAdditionNoticeBox.setResult(false);
//            Stage newStage = Dictionary.addAScene("SucceededAdditionNoticeBox");
//            newStage.show();
//        }
    }

    @FXML
    void back(MouseEvent event) throws IOException {
        Dictionary.setRoot("Home");
    }
    @FXML
    void initialize() {
        assert add != null : "fx:id=\"add\" was not injected: check your FXML file 'Home.fxml'.";
    }
}
