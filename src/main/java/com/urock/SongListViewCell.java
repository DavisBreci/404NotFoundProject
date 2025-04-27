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
            App.setRoot("UserHome");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void setSong(Song song) {
        title.setText(song.getTitle());
        artist.setText(song.getArtist());
        instrument.setText(song.getInstrument().toString());
        difficulty.setText(song.getDifficultyLevel().toString());
        measures.setText(song.getScore().getMeasures().size() + " Measures");
    }
    public void setWithd(double w) {
        cell.setPrefWidth(w);
        cell.setSpacing(w/6);
    }
}
