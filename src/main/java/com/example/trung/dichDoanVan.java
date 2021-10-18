package com.example.trung;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import org.json.JSONArray;

public class dichDoanVan {
    private VoiceManagement voiceManagement;

    private final String ENGLISH = "en";
    private final String VIETNAMESE = "vi";

    private String fromLanguage = ENGLISH;
    private String toLanguage = VIETNAMESE;

    @FXML
    private Button engSpeakButton;

    @FXML
    private Button backButton;

    @FXML
    private Button translateButton;

    @FXML
    private Button swapButton;

    @FXML
    private TextArea input;

    @FXML
    private TextArea output;

    @FXML
    private Label originLanguage;

    @FXML
    private Label destLanguage;

    @FXML
    void process() throws Exception {
        String translatedText = input.getText();
        if (translatedText.isBlank()) return;
//        String result = callURLAndParseResult("en", "vi", translatedText);
        output.setText(getResult(translatedText, fromLanguage, toLanguage));
    }

    @FXML
    void back() throws IOException {
        DictionaryApplication.setRoot("Home");
    }

    @FXML
    void initialize() {
        assert engSpeakButton != null : "fx:id=\"engSpeakButton\" was not injected: check your FXML file 'dichDoanVan.fxml'.";
        assert input != null : "fx:id=\"input\" was not injected: check your FXML file 'dichDoanVan.fxml'.";
        assert output != null : "fx:id=\"output\" was not injected: check your FXML file 'dichDoanVan.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'dichDoanVan.fxml'.";
        assert translateButton != null : "fx:id=\"translateButton\" was not injected: check your FXML file 'dichDoanVan.fxml'.";
        voiceManagement = new VoiceManagement();
    }

    @FXML
    private void getVoice() {
        String text = null;
        if (fromLanguage.equals(ENGLISH)) text = input.getText();
        else text = output.getText();
        voiceManagement.speak(text);
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

    private String getResult(String translatedText, String fromLang, String toLang) throws Exception {
        translatedText = translatedText.strip();
        String[] statementsArray = translatedText.split("\\. ");
        StringBuilder result = new StringBuilder("");

        for (int i = 0; i < statementsArray.length; i++) {
            if (i == statementsArray.length - 1) {
                result.append(callURLAndParseResult(fromLang, toLang, statementsArray[i]));
            }
            else result.append(callURLAndParseResult(fromLang, toLang, statementsArray[i]) + ". ");
        }

        return result.toString();
    }

    @FXML
    private void processSwap() {
        String tmp1 = originLanguage.getText();
        String tmp2 = destLanguage.getText();
        originLanguage.setText(tmp2);
        destLanguage.setText(tmp1);

        String tmp3 = fromLanguage;
        fromLanguage = toLanguage;
        toLanguage = tmp3;

        if (toLanguage.equals(ENGLISH)) engSpeakButton.setLayoutY(390);
        else engSpeakButton.setLayoutY(128);
    }
}
