package com.example.trung;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Set;
import java.util.LinkedHashSet;
import java.net.URL;
import java.util.*;

public class LookUpHistory {
    private static final File logFile = new File("src/main/resources/data/lookuplog.txt");

    private static final int maxCapacity = 20;

    private static Set<String> wordSet = new LinkedHashSet<>(maxCapacity);

    private static String selectedWord;

    public static String getSelectedWord() {
        return selectedWord;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Word> tbview;

    @FXML
    private TableColumn<Word, String> english;

    @FXML
    private TableColumn<Word, String> pronunciation;

    @FXML
    private TableColumn<Word, String> partsOfSpeech;

    @FXML
    private TableColumn<Word, String> vietnamese;

    @FXML
    private ImageView back;

    @FXML
    void returnHome(MouseEvent event) throws IOException {
        DictionaryApplication.setRoot("Home");
    }

    @FXML
    void initialize() throws IOException {
        initTable();
    }

    public void initTable() {
        english.setCellValueFactory(new PropertyValueFactory<Word, String>("englishText"));
        pronunciation.setCellValueFactory(new PropertyValueFactory<Word, String>("pronunciation"));
        partsOfSpeech.setCellValueFactory(new PropertyValueFactory<Word, String>("partsOfSpeech"));
        vietnamese.setCellValueFactory(new PropertyValueFactory<Word, String>("vietnamText"));

        ObservableList<Word> List = getObservableList();
        tbview.setItems(List);
    }

    public ObservableList<Word> getObservableList() { //reverse the word wordSet here to get the desired order by latest time
        List<Word> wordArray = new ArrayList<>();
        wordSet.forEach(wordInLog -> {
            if(wordInLog.length() > 0) {
                Word tmpWord = DictionaryManagement.lookUp(wordInLog);
                if(tmpWord != null) wordArray.add(tmpWord);
            }
        });
        Collections.reverse(wordArray);
        ObservableList<Word> result = FXCollections.observableList(wordArray);
        return result;
    }

    public static void addIntoList(Word x) throws IOException {
        boolean isFull = false;
        if (wordSet.size() == maxCapacity) isFull = true;
        if (wordSet.add(x.getEnglishText())) {
            if (isFull) {
                String oldestSavedWord = wordSet.stream().toList().get(0);
                wordSet.remove(oldestSavedWord);
                writeToFile();
                return;
            }
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
            if(word.length() > 0) {
                wordSet.add(word);
            }
        }
        reader.close();
    }

    @FXML
    void getClickedWord() throws IOException {
        tbview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        Word selectedWord = tbview.getSelectionModel().getSelectedItem();
        tbview.getSelectionModel().clearSelection();
        Home.setResult(selectedWord);
        DictionaryApplication.setRoot("translateAWord");
    }

    public static void editWord(String replacedWord, String newWord) throws IOException {
        //look up the word, if it exists then change it and return true. Otherwise, return false.
        if (!wordSet.contains(replacedWord)) {
            return;
        }
        ArrayList<String> wordArray = new ArrayList<>();
        wordArray.addAll(wordSet);
        int index = wordArray.indexOf(replacedWord);
        wordArray.set(index, newWord);
        wordSet.clear();
        wordSet.addAll(wordArray);

        writeToFile();
    }

    public static void removeWord(String word) throws IOException {
        if (wordSet.remove(word)) {
            writeToFile();
        }
    }

    private static void writeToFile() throws IOException {
        if (wordSet.isEmpty()) return;

        FileWriter writer = new FileWriter(logFile);
        wordSet.forEach(wordInSet -> {
            try {
                writer.write(wordInSet + "\r\n");
            } catch(IOException e) {
                e.printStackTrace();
            }
        });
        writer.close();
    }
}
