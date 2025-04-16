package com.urock;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.Lesson;
import com.model.MusicSystemFACADE;
import com.model.Playlist;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

public class UserHomeController implements Initializable {

    MusicSystemFACADE facade = MusicSystemFACADE.getInstance();

    @FXML
    private Button buttonCompose;

    @FXML
    private Button buttonSearch;

    @FXML
    private ScrollPane scrollLessons;

    @FXML
    private HBox scrollLessonsContent;

    @FXML
    private ScrollPane scrollPlaylists;

    @FXML
    private HBox scrollPlaylistsContent;

    @FXML
    void onLogout(ActionEvent event) throws IOException {
        facade.logout();
        App.setRoot("login");
        
        System.out.println("Logged out");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
       try {
            initializePlaylists();
            initializeLessons();
       } catch (IOException e) {
            e.printStackTrace();
       }
    }

    public void initializePlaylists() throws IOException {
        Button scrollingPlaylist = null;
            for(Playlist p : facade.getCurrentUser().getPlaylists()){
                scrollingPlaylist = (Button)App.loadFXML("TemplateScrollPlaylist");
                scrollingPlaylist.setText(p.getTitle());
                scrollingPlaylist.setOnAction(
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent arg0) {
                            System.out.println("Go to " + "\"" +  p.getTitle() + "\"");
                        }
                    }
                );
                scrollPlaylistsContent.getChildren().add(scrollingPlaylist);
            }
    }

    public void initializeLessons() throws IOException {
        Button scrollingPlaylist = null;
            for(Lesson l : facade.getCurrentUser().getAssignedLessons()){
                scrollingPlaylist = (Button)App.loadFXML("TemplateScrollLesson");
                scrollingPlaylist.setText(l.getTitle());
                scrollingPlaylist.setOnAction(
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent arg0) {
                            System.out.println("Go to " + "\"" +  l.getTitle() + "\"");
                        }
                    }
                );
                scrollLessonsContent.getChildren().add(scrollingPlaylist);
            }
    }

}