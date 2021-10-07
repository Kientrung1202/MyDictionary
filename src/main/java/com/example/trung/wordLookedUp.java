package com.example.trung;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class wordLookedUp {

    private static List<Word> list = new ArrayList<Word>(50);
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
    private TableColumn<Word, String> type;

    @FXML
    private TableColumn<Word, String> vietnam;

    @FXML
    private ImageView back;

    @FXML
    void returnHome(MouseEvent event) throws IOException {
        HelloApplication.setRoot("home");
    }

    public void initTable() {
        english.setCellValueFactory(new PropertyValueFactory<Word, String>("englishText"));
        pro.setCellValueFactory(new PropertyValueFactory<Word, String>("pronunciation"));
        type.setCellValueFactory(new PropertyValueFactory<Word, String>("type"));
        vietnam.setCellValueFactory(new PropertyValueFactory<Word, String>("vietnamText"));
        ObservableList<Word> List = getObersavableList();
        tbview.setItems(List);
    }
    public ObservableList<Word> getObersavableList() {
        ObservableList<Word> res = FXCollections.observableList(list);
        return res;
    }
    public static void addIntoList(Word x)  {
        list.add(0, x);
    }

    @FXML
    void initialize() {
        initTable();
    }
}
