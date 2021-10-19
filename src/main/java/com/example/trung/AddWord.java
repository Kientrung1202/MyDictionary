package com.example.trung;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;

public class AddWord {
    @FXML
    private TextField englishField;

    @FXML
    private TextField pronunciationField;

    @FXML
    private TextArea vietnameseField;

    @FXML
    private Button backButton;

    @FXML
    private Button addButton;

    @FXML
    private Label alertLabel;

    @FXML
    private ImageView backImage;

    @FXML
    private void back(MouseEvent event) throws IOException {
        DictionaryApplication.setRoot("Home");
    }

    @FXML
    private void initialize() {
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'AddAWord.fxml'.";
    }

    @FXML
    private void addAWord(ActionEvent event) throws IOException {
        String englishText = englishField.getText();
        String vietnamText = vietnameseField.getText();
        if (englishText.isBlank() || vietnamText.isBlank()) {
            alertLabel.setTextFill(Color.RED);
            alertLabel.setText("Từ hoặc nghĩa còn trống");
            return;
        }

        String pronunciation = pronunciationField.getText();
        Word newWord = new Word(englishText, pronunciation, vietnamText);

        boolean ok = DictionaryManagement.addWord(newWord);
        if(ok) {
            //reset 3 text fields
            englishField.setText("");
            pronunciationField.setText("");
            vietnameseField.setText("");
            showSuccessAlert(newWord.getEnglishText());
        } else {
            showFailureAlert(newWord.getEnglishText());
        }
    }

    @FXML
    private void setAlertLabelBlank() {
        alertLabel.setText("");
    }

    private void showSuccessAlert(String wordAdded) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText("Okeyy đã thêm từ \"" + wordAdded + "\" thành công." );
        alert.show();
    }

    private void showFailureAlert(String wordAdded) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText("\"" + wordAdded + "\" đã tồn tại rồi, bro có thể dùng tính năng sửa từ nhé.");
        alert.show();
    }
}
