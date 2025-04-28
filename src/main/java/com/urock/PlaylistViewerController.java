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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.util.Callback;

/**
 * Controller for the PlaylistViewer screen. Displays the songs in a playlist.
 * @author
 */
public class PlaylistViewerController implements Initializable{
    
    @FXML
    private ListView<Song> allSongs;

    @FXML
    private Label author;

    @FXML
    private Polygon backButton;

    @FXML
    private Label playlist_title;

    @FXML
    private Pane root;

    @FXML
    private Region header_box;

    /**
     * Handles the back button event. Navigates to either the TecherHome or UserHome screen.
     * @param event The event triggered by clicking the back button.
     */
    @FXML
    void back(MouseEvent event) {
        try{
            App.setRoot("UserHome");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * Sets the current playlist to be displayed in the PlaylistViewer.
     * @param curr The playlist to be displayed.
     */
    private static Playlist p;
    public static void setPlaylist(Playlist curr) {
        p = curr;
    }

    /**
     * Sets the current lesson to be displayed if necessary.
     * @param curr The lesson to be displayed.
     */
    public static void setLesson(Lesson curr) {
        String title = curr.getTitle();
        String[] split = title.split("~");
        title = split[split.length-1];
        p = new Playlist(null, title, "", "", curr.getSongs());
    }
    MusicSystemFACADE facade = MusicSystemFACADE.getInstance();

    /**
     * Initializes the scene. Sets the scale of the UI elements based on the window size.
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        allSongs.prefWidthProperty().bind(root.widthProperty().subtract(allSongs.getLayoutX()*1.5));
        header_box.prefWidthProperty().bind(root.widthProperty().subtract(allSongs.getLayoutX()*2));
        try {
            initializeSongs();
       } catch (IOException e) {
            e.printStackTrace();
       }
       playlist_title.setText(p.getTitle());
       author.setText(p.getAuthor());
    }

    /**
     * Initializes the ListView of songs. Sets the cell factory to display each song in a custom format.
     * @throws IOException If the FXML template file cannot be loaded.
     */
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
                        controller.setWithd(allSongs.getWidth()-30-allSongs.getInsets().getLeft());
                        setGraphic(cellRoot);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
