package com.urock;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.sound.midi.Sequence;

import com.model.*;

import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.util.Callback;


public class PlaylistViewerController implements Initializable{
    
    @FXML
    private ListView<Song> allSongs;
    
    private static Playlist p;
    public static void setCurrent(Playlist curr){
        p = curr;
    }
    MusicSystemFACADE facade = MusicSystemFACADE.getInstance();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        try {
            initializeSongs();
       } catch (IOException e) {
            e.printStackTrace();
       }
    }

    public void initializeSongs() throws IOException {
        ObservableList<Song> fill = FXCollections.observableArrayList(p.getSongs());
        allSongs.setItems(fill);
        allSongs.setCellFactory(listView -> new ListCell<>() {
            @Override
            public void updateItem(Song song, boolean empty) {
                super.updateItem(song, empty);
                if(empty || song == null) {

                    setText(null);
                    setGraphic(null);

                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(App.class.getResource("TemplateSong.fxml"));                        
                        Parent cellRoot = loader.load();
                        SongListViewCell controller = loader.getController();
                        controller.setSong(song);
                        setGraphic(cellRoot);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
