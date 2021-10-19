package com.example.trung;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.*;

public class SearchHistory {
    private static final File logFile = new File("src/main/resources/data/lookuplog.txt");

    private static final int maxCapacity = 50;

    private static Set<String> wordSet = new LinkedHashSet<>(maxCapacity);

    private static ObservableList<Word> obsList;
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
    private TableColumn<Word, CheckBox> selection;

    @FXML
    private Button deleteButton;

    @FXML
    private Button backButton;

    @FXML
    private ImageView backImage;

    @FXML
    CheckBox checkBoxToSetAll;

    @FXML
    Label alertLabel;

    @FXML
    void back() throws IOException {
        DictionaryApplication.setRoot("Home");
    }

    @FXML
    void initialize() throws IOException {
        initTable();
        checkBoxToSetAll = new CheckBox();
    }

    public void initTable() {
        english.setCellValueFactory(new PropertyValueFactory<Word, String>("englishText"));
        pronunciation.setCellValueFactory(new PropertyValueFactory<Word, String>("pronunciation"));
        partsOfSpeech.setCellValueFactory(new PropertyValueFactory<Word, String>("partsOfSpeech"));
        vietnamese.setCellValueFactory(new PropertyValueFactory<Word, String>("vietnamText"));
        selection.setCellValueFactory(new PropertyValueFactory<Word, CheckBox>("checkBox"));

        obsList = getObservableList();
        tbview.setItems(obsList);

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

        //Set new check box for all elements
        for (int i = 0; i < wordArray.size(); i++) {
            wordArray.get(i).setCheckBox(new CheckBox());
        }

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
    void getClickedWord(MouseEvent mouseEvent) throws IOException {
        tbview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        if (mouseEvent.getClickCount() == 2) {
            Word selectedWord = tbview.getSelectionModel().getSelectedItem();
            tbview.getSelectionModel().clearSelection();
            Home.setResult(selectedWord);
            DictionaryApplication.setRoot("LookupWord");
        }
    }

    @FXML
    void getWordByPressEnter(KeyEvent keyEvent) throws IOException {
        tbview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            Word selectedWord = tbview.getSelectionModel().getSelectedItem();
            tbview.getSelectionModel().clearSelection();
            Home.setResult(selectedWord);
            DictionaryApplication.setRoot("LookupWord");
        }
    }

    @FXML
    private void removeSelection() throws IOException {
        String alertBoxHeader;
        ObservableList<Word> selectedWordList = FXCollections.observableArrayList();

        int numberOfSelectedWords = 0;
        for (int i = 0; i < tbview.getItems().size(); i++) {
            Word word = tbview.getItems().get(i);
            if (word.getCheckBox().isSelected()) {
                selectedWordList.add(word);
                numberOfSelectedWords += 1;
            }
        }

        if (numberOfSelectedWords == 0) {
            alertLabel.setVisible(true);
            return;
        }
        else {
            alertLabel.setVisible(false);
            if (numberOfSelectedWords == tbview.getItems().size()) {
                alertBoxHeader = "Bạn chắc chắn xóa toàn bộ danh sách không?";
            }
            else {
                alertBoxHeader = "Bạn muốn xóa các từ đã chọn chứ?";
            }
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText(alertBoxHeader);

        ButtonType yesButton = new ButtonType("Yep", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("không", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == yesButton) {
            for (Word selectedWord : selectedWordList) {
                removeWord(selectedWord.getEnglishText());
            }
            tbview.getItems().removeAll(selectedWordList);
        }
    }
//
//    @FXML
//    private void removeAll() throws IOException {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Xác nhận");
//        alert.setHeaderText("Bạn chắc chắn muốn xóa tất cả không?");
//        alert.setContentText("");
//
//        ButtonType yesButton = new ButtonType("Chắc", ButtonBar.ButtonData.YES);
//        ButtonType noButton = new ButtonType("À mà thôi", ButtonBar.ButtonData.NO);
//        alert.getButtonTypes().setAll(yesButton, noButton);
//
//        Optional<ButtonType> result = alert.showAndWait();
//
//        if (result.get().getButtonData() == ButtonBar.ButtonData.YES) {
//            tbview.getItems().clear();
//            wordSet.clear();
//            writeToFile();
//        }
//    }

    @FXML
    private void selectAll() {
        if (!checkBoxToSetAll.isSelected()) {
            for (int i = 0; i < tbview.getItems().size(); i++) {
                Word word = tbview.getItems().get(i);
                word.setSelected(true);
            }
            checkBoxToSetAll.setSelected(true);
        }
        else {
            for (int i = 0; i < tbview.getItems().size(); i++) {
                Word word = tbview.getItems().get(i);
                word.setSelected(false);
            }
            checkBoxToSetAll.setSelected(false);
        }
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
