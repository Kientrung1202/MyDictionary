package com.example.trung;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ResourceBundle;



public class dichDoanVan {
    private static boolean EnToVi = true;
    public static void setEnToVi(boolean x) {
        EnToVi = x;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label label1;

    @FXML
    private Label label2;
    @FXML
    private ImageView btnback;

    @FXML
    private TextArea input;

    @FXML
    private Label output;

    @FXML
    private ImageView searchAPra;

    @FXML
    private ImageView convert;

    @FXML
    private ImageView btnInput;

    @FXML
    private ImageView btnOutput;

    @FXML
    void convert(MouseEvent event) {
        if(!EnToVi) {
            EnToVi = true;
            label1.setText("English");
            label2.setText("Vietnamese");
        } else {
            EnToVi = false;
            label1.setText("Vietnamese");
            label2.setText("English");
        }
    }

    @FXML
    void speakInput(MouseEvent event) {
        if(EnToVi) {
            DictionaryManagement.speakVoiceEn(input.getText());
        }
    }

    @FXML
    void speakOutput(MouseEvent event) {

    }

    //back
    @FXML
    void back(MouseEvent event) throws IOException {
        Dictionary.setRoot("Home");
    }

    @FXML
    void search(MouseEvent event) throws IOException, InterruptedException {
        if(EnToVi) translateEnToVi();
        else {
            translateViToEn();
        }
    }
//     sử dụng api của localhost
     void translateEnToVi() throws IOException, InterruptedException {
        String split = input.getText().trim().replaceAll(" ", "%20");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/?text=" + split))
                .header("Content-Type", "text/")
                .GET()
                .build() ;
         HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // in ra body cua res
        output.setText(response.body());
    }

    void translateViToEn() throws IOException, InterruptedException {
        String split = input.getText().trim().replaceAll(" ", "%20");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/vi?text=" + split))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        output.setText(response.body());
    }


    @FXML
    void initialize() {
    }

    void getTranslatedString() {
        if (input.getText() != null) output.setText("Da duoc dich");
    }
}
