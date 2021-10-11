package com.example.trung;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Set;
import java.util.LinkedHashSet;
import java.net.URL;
import java.util.*;

public class LookUpHistory {
    private static final File logFile = new File("src/main/resources/data/lookuplog.txt");

    //key value is the word looked up
    private static Set<String> list = new LinkedHashSet<>(50);

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Word> tbview;

    @FXML
    private TableColumn<Word, String> english;

    @FXML
    private TableColumn<Word, String> pro;

    @FXML
    private TableColumn<Word, String> partOfSpeech;

    @FXML
    private TableColumn<Word, String> vietnam;

    @FXML
    private ImageView back;

    @FXML
    void returnHome(MouseEvent event) throws IOException {
        Dictionary.setRoot("Home");
    }

    @FXML
    void initialize() throws IOException {
        initTable();
    }

    public void initTable() {
        english.setCellValueFactory(new PropertyValueFactory<Word, String>("englishText"));
        pro.setCellValueFactory(new PropertyValueFactory<Word, String>("pronunciation"));
        partOfSpeech.setCellValueFactory(new PropertyValueFactory<Word, String>("partOfSpeech"));
        vietnam.setCellValueFactory(new PropertyValueFactory<Word, String>("vietnamText"));

        ObservableList<Word> List = getObservableList();
        tbview.setItems(List);
    }

    public ObservableList<Word> getObservableList() { //reverse the word list here to get the desired order by latest time
        List<Word> wordArray = new ArrayList<>();
        list.forEach(wordInLog -> {
            Word tmpWord = DictionaryManagement.lookupWord(wordInLog);
            wordArray.add(tmpWord);
        });
        Collections.reverse(wordArray);
        ObservableList<Word> result = FXCollections.observableList(wordArray);
        return result;
    }

    public static void addIntoList(Word x) throws IOException {
        if (list.add(x.getEnglishText())) {
            String wordToAppend = x.getEnglishText() + "\r\n";
            Files.write(Paths.get(logFile.toURI()), //append the word just looked up to file
                    wordToAppend.getBytes(),
                    StandardOpenOption.APPEND);
        }
    }

    public static void initList() throws IOException {
        if (!logFile.isFile()) {
            logFile.createNewFile();
            return;
        }
        BufferedReader reader = new BufferedReader(new FileReader(logFile));
        String word = "";
        while((word = reader.readLine()) != null) {
            list.add(word);
        }
        reader.close();
    }
}
