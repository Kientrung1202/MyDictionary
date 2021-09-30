package com.example.trung;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class dichDoanVan {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView btnInput;

    @FXML
    private TextArea input;

    @FXML
    private ImageView btnOutput;

    @FXML
    private TextArea output;

    @FXML
    private ImageView btnback;

    @FXML
    void back(MouseEvent event) throws IOException {
        HelloApplication.setRoot("home");
    }

    @FXML
    void soundInput(MouseEvent event) {

    }

    @FXML
    void soundOutput(MouseEvent event) {

    }

    @FXML
    void initialize() {
        assert btnInput != null : "fx:id=\"btnInput\" was not injected: check your FXML file 'dichDoanVan.fxml'.";
        assert input != null : "fx:id=\"input\" was not injected: check your FXML file 'dichDoanVan.fxml'.";
        assert btnOutput != null : "fx:id=\"btnOutput\" was not injected: check your FXML file 'dichDoanVan.fxml'.";
        assert output != null : "fx:id=\"output\" was not injected: check your FXML file 'dichDoanVan.fxml'.";
        assert btnback != null : "fx:id=\"btnback\" was not injected: check your FXML file 'dichDoanVan.fxml'.";

    }
}
