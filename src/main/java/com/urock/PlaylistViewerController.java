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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;


public class PlaylistViewerController implements Initializable{
    
    @FXML
    private ListView<HBox> allSongs;

    @FXML
    private HBox song;
    
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
        allSongs = new ListView<HBox>();
        ObservableList<HBox> fill = FXCollections.observableArrayList();
        HBox song = null;
        for(Song s : p.getSongs()) {
            song = (HBox)App.loadFXML("TemplateSong");
            ObservableList<Node> rootChildren = song.getChildren();
            ObservableList<Node> items = ((AnchorPane)rootChildren.get(0)).getChildren();
            ((Label)items.get(0)).setText(s.getTitle());
            ((Label)items.get(1)).setText(s.getArtist());
            items = ((AnchorPane)rootChildren.get(1)).getChildren();
            ((Label)items.get(0)).setText(s.getInstrument().toString());
            ((Label)items.get(1)).setText(s.getDifficultyLevel().toString());
            items = ((AnchorPane)rootChildren.get(2)).getChildren();
            ((Label)items.get(0)).setText(s.getScore().getMeasures().size()+" Measures");
            fill.add(song);
        }
        allSongs.setItems(fill);
        
    }
}
