package com.urock;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import com.model.*;

public class PlaylistController implements Initializable {
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