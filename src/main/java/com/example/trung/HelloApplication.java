package com.example.trung;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFxml("home"));
        stage.setTitle("Dictionary");
        stage.setScene(scene);
        stage.show();
    }
    static void setRoot(String name) throws IOException {
        scene.setRoot(loadFxml(name));
    }
    private static Parent loadFxml(String name) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(name+".fxml"));
        return fxmlLoader.load();
    }
    public static void main(String[] args) {
        launch();
    }
}