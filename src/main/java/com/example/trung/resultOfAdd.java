package com.example.trung;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class resultOfAdd {
    // decide to ui of Object
    private static boolean result = true;
    public static void setResult(boolean a) {
        result = a;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button close;


    // close Stage;
    @FXML
    void closeStage(ActionEvent event) {
         Stage stage = (Stage) close.getScene().getWindow();
         stage.close();
    }

    @FXML
    void initialize() {
        assert close != null : "fx:id=\"close\" was not injected: check your FXML file 'resultOfAdd.fxml'.";
    }
}
