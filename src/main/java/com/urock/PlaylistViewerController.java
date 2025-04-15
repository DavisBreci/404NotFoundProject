package com.urock;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.sound.midi.Sequence;

import com.model.Chord;
import com.model.DataLoader;
import com.model.Instrument;
import com.model.Measure;
import com.model.MusicSystemFACADE;
import com.model.Note;
import com.model.PitchClass;
import com.model.Playlist;
import com.model.Rational;
import com.model.Score;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;


public class PlaylistViewerController implements Initializable{
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
        
    }
}
