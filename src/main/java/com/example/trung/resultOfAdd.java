package com.example.trung;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class resultOfAdd {
    // decide to ui of Object
    private static boolean result;
    public static void setResult(boolean a) {
        result = a;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button close;

    @FXML
    private Label res;

    @FXML
    private ImageView icon;

    // close Stage;
    @FXML
    void closeStage(ActionEvent event) {
         Stage stage = (Stage) close.getScene().getWindow();
         stage.close();
    }

    public void setRes() {
        if (result) {
            res.setText("Thêm thành công");
            res.setTextFill(Paint.valueOf("#2ac166"));
            Image image = new Image("C:\\Users\\ADMIN\\OneDrive\\Desktop\\trung\\src\\main\\resources\\images\\ok.png");
            icon.setImage(image);
        } else {
            res.setText("Lỗi!! Vui lòng nhập đủ 2 ô");
            res.setTextFill(Paint.valueOf("red"));
            Image image = new Image("C:\\Users\\ADMIN\\OneDrive\\Desktop\\trung\\src\\main\\resources\\images\\x.png");
            icon.setImage(image);
        }
    }
    @FXML
    void initialize() {
        assert close != null : "fx:id=\"close\" was not injected: check your FXML file 'resultOfAdd.fxml'.";
        assert res != null : "fx:id=\"res\" was not injected: check your FXML file 'resultOfAdd.fxml'.";
        assert icon != null : "fx:id=\"icon\" was not injected: check your FXML file 'resultOfAdd.fxml'.";
    }
}
