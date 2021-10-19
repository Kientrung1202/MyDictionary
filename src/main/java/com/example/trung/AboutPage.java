package com.example.trung;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutPage {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private ImageView backImage;

    @FXML
    void back() throws IOException {
        DictionaryApplication.setRoot("Home");
    }

    @FXML
    void initialize() {
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'contact.fxml'.";
        assert backImage != null : "fx:id=\"backImage\" was not injected: check your FXML file 'contact.fxml'.";
    }
}
