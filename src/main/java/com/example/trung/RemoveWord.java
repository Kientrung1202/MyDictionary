package com.example.trung;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Optional;

public class RemoveWord {
    private static String removedWord;

    @FXML
    Label instruction1;

    @FXML
    Label notification;

    @FXML
    Button removeButton;

    @FXML
    Button backButton;

    @FXML
    TextArea wordMeaningField;

    @FXML
    TextField wordField;

    @FXML
    private ImageView backImage;

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
        showAlert(removedWord);
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

    private void showAlert(String word) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText("Hey what'suppp bro");
        alert.setContentText("Bro muốn xóa từ \"" + word + "\" chứ?");

        ButtonType yesButton = new ButtonType("Yep", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("Thôi", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> buttonReceived = alert.showAndWait();

        if (buttonReceived.get().equals(yesButton)) {
            DictionaryManagement.removeWord(word);
            SearchHistory.removeWord(word);
        }
        if (buttonReceived.get().getButtonData().equals(ButtonBar.ButtonData.NO)) {
            return;
        }

        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert2.setTitle("Thông báo");
        alert2.setHeaderText("ok xoá xong rồi đó bờ rô");
        alert2.show();
    }
}
