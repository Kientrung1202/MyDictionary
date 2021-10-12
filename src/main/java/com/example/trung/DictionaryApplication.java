package com.example.trung;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class, which contains main() method.
 */
public class DictionaryApplication extends Application {
    private static Scene scene;
    // de tao them scene con
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFxml("Home"));
        stage.setResizable(false);
        stage.setTitle("DictionaryApplication");
        stage.setScene(scene);
        stage.show();
    }

    // Open a new scene.
    static void setRoot(String name) throws IOException {
        scene.setRoot(loadFxml(name));
    }

    private static Parent loadFxml(String name) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DictionaryApplication.class.getResource(name+".fxml"));
        return fxmlLoader.load();
    }

    public static Stage addAScene(String url) throws  IOException {
        Scene newScene = new Scene(loadFxml(url));
        Stage newStage = new Stage();
        newStage.setTitle("Thông báo");
        newStage.setScene(newScene);
        newStage.initModality(Modality.WINDOW_MODAL);
//        newStage.initOwner(stage);
        return newStage;
    }

    public static void main(String[] args) throws IOException {
        DictionaryManagement.initialize();
        LookUpHistory.initList();
        launch();
    }
}