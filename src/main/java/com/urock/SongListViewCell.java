package com.urock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;

import java.io.IOException;

import com.model.*;

public class SongListViewCell extends ListCell<Song>{

    @FXML
    private Label artist;

    @FXML
    private HBox cell;

    @FXML
    private Label difficulty;

    @FXML
    private Label instrument;

    @FXML
    private Label measures;

    @FXML
    private Label title;

    @FXML
    void onMouseClick(MouseEvent event) {
        try{
            App.setRoot("PlaylistViewerController");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    // public SongListViewCell() {
        
    // }
    // @Override
    // public void updateItem(Song song, boolean empty) {
    //     super.updateItem(song, empty);
    //     if(empty || song == null) {

    //         setText(null);
    //         setGraphic(null);

    //     } else {
    //         FXMLLoader loader = new FXMLLoader(getClass().getResource("TemplateSong.fxml"));
    //         try {
    //             Parent cellRoot = loader.load();
    //         } catch(Exception e) {}
    //         title.setText(song.getTitle());
    //         artist.setText(song.getArtist());
    //         instrument.setText(song.getInstrument().toString());
    //         difficulty.setText(song.getDifficultyLevel().toString());
    //         measures.setText(song.getScore().getMeasures().size() + " Measures");
    //     }
    // }
    public void setSong(Song song) {
        System.out.println("setSong method has been called!");
        title.setText(song.getTitle());
        artist.setText(song.getArtist());
        instrument.setText(song.getInstrument().toString());
        difficulty.setText(song.getDifficultyLevel().toString());
        measures.setText(song.getScore().getMeasures().size() + " Measures");
    }
}
