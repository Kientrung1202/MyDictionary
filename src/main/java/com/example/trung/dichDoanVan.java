package com.example.trung;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import org.json.JSONArray;

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
    private TextArea output;

    @FXML
    private ImageView btnOutput;

    @FXML
    private ImageView btnback;

    @FXML
    private Button translateButton;

    @FXML
    void process() throws Exception {
        String translatedText = input.getText();
        if (translatedText.isBlank()) return;
        String result = callURLAndParseResult("en", "vi", translatedText);
        output.setText(result);
    }

    @FXML
    void back() throws IOException {
        DictionaryApplication.setRoot("Home");
    }

    @FXML
    void soundInput(MouseEvent event) {

    }

    @FXML
    void soundOutput(MouseEvent event) {

    }

    @FXML
    void initialize() throws IOException {
        assert btnInput != null : "fx:id=\"btnInput\" was not injected: check your FXML file 'dichDoanVan.fxml'.";
        assert input != null : "fx:id=\"input\" was not injected: check your FXML file 'dichDoanVan.fxml'.";
        assert btnOutput != null : "fx:id=\"btnOutput\" was not injected: check your FXML file 'dichDoanVan.fxml'.";
        assert output != null : "fx:id=\"output\" was not injected: check your FXML file 'dichDoanVan.fxml'.";
        assert btnback != null : "fx:id=\"btnback\" was not injected: check your FXML file 'dichDoanVan.fxml'.";
    }

    private String callURLAndParseResult(String langFrom, String langTo, String word) throws Exception {
        String urlToString = "https://translate.googleapis.com/translate_a/single?"
                + "client=gtx&"
                + "sl=" + langFrom
                + "&tl=" + langTo
                + "&dt=t&q=" + URLEncoder.encode(word, "UTF-8");

        URL url = new URL(urlToString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        connection.disconnect();
        return parseResult(response.toString());
    }

    private String parseResult(String inputJson) throws Exception
    {
        JSONArray jsonArray = new JSONArray(inputJson);
        JSONArray jsonArray2 = (JSONArray) jsonArray.get(0);
        JSONArray jsonArray3 = (JSONArray) jsonArray2.get(0);

        return jsonArray3.get(0).toString();
    }
}
