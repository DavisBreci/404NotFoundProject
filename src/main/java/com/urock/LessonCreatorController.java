package com.urock;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.model.MusicSystemFACADE;
import com.model.NoteValue;
import com.model.Playlist;
import com.model.PlaylistList;
import com.model.Song;
import com.model.Lesson;
import com.model.LessonList;
import com.model.SongList;
import com.model.Teacher;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class LessonCreatorController implements Initializable{

    @FXML
    private ImageView logo;

    @FXML
    private TextField searchBar;

     @FXML
    private ListView<Song> addedSongs;

    @FXML
    private Label createLesson;

    @FXML
    private Label createPlaylist;

    @FXML
    private TextField titleInput;

    @FXML
    private ListView<Song> searchResults;
    MusicSystemFACADE facade = MusicSystemFACADE.getInstance();

/**
 * class governing the displayed search results
 */
    abstract class SearchResult {
        String primary;
        String secondary;
        String tertiary;
/**
 * constructor; initializes instance variables
 * @param primary first result
 * @param secondary second result
 * @param tertiary third result
 */
        SearchResult(String primary, String secondary, String tertiary){
            this.primary = primary;
            this.secondary = secondary; 
            this.tertiary = tertiary;
        }
/**
 * method will be overwritten
 * @throws IOException
 */
        public abstract void link() throws IOException;
    }
/**
 * sets root to either teacher or student home depending on the classification of the user
 * @param event mouse is clicked
 * @throws IOException root doesn't exist
 */
    @FXML
    void home(MouseEvent event) throws IOException {
        MusicSystemFACADE facade = MusicSystemFACADE.getInstance();
            try {
                Teacher test = (Teacher)facade.getCurrentUser();
                try {
                    App.setRoot("TeacherHome");
                } catch(Exception e) {
                    e.printStackTrace();
                }
            } catch(ClassCastException cce) {
                try {
                    App.setRoot("UserHome");
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
    }
/**
 * checks user's search and adds all matching songs to the LIstView of Songs
 * @param event enter key
 */
    @FXML
    void onSearch(ActionEvent event){
        String searchString = searchBar.getText();
        if(searchString == null) return;
        searchResults.getItems().clear();
        Song song = SongList.getInstance().getSongByTitle(searchString);
        if(song == null) return;
        searchResults.getItems().add(song);
    }

/**
 * initializes the scene
 */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
// custom result styling in the ListView
        searchResults.setCellFactory(listView -> new ListCell<>(){
            @Override
            public void updateItem(Song song, boolean empty){
                super.updateItem(song, empty);
                if(empty || song == null){
                    setText(null);
                    setGraphic(null);
                } else {
                    try{
                        FXMLLoader loader = new FXMLLoader(App.class.getResource("TemplateSearchResult.fxml"));
                        Parent cellRoot = loader.load();
                        SearchResultCell controller = loader.getController();
                        controller.setLabels(song.getTitle(), song.getArtist(), ComposeLandingPageController.titleFormat(song.getInstrument().toString()));
                        setGraphic(cellRoot);
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        );
// custom styling for added songs
        addedSongs.setCellFactory(listView -> new ListCell<>(){
            @Override
            public void updateItem(Song song, boolean empty){
                super.updateItem(song, empty);
                if(empty || song == null){
                    setText(null);
                    setGraphic(null);
                } else {
                    try{
                        FXMLLoader loader = new FXMLLoader(App.class.getResource("TemplateSearchResult.fxml"));
                        Parent cellRoot = loader.load();
                        SearchResultCell controller = loader.getController();
                        controller.setLabels(song.getTitle(), song.getArtist(), ComposeLandingPageController.titleFormat(song.getInstrument().toString()));
                        setGraphic(cellRoot);
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        );
/**
 * adds song to the added songs ListView
 */
        searchResults.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Song sr = searchResults.getSelectionModel().getSelectedItem();
                    if(sr != null)
                        addedSongs.getItems().add(sr);
            }
        });
    }
/**
 * creates a new lesson from the songs, adds it to the teachers' assigned lessons, and her students'
 * assigned lessons
 * @param event mouse click
 * @throws IOException invalid root set
 */
    @FXML
    void onCreateLesson(MouseEvent event) throws IOException {
        if(titleInput.getText() == null) return;
        Lesson lesson = LessonList.getInstance().createLesson(toSongArrayList(addedSongs.getItems()), titleInput.getText());
        if(lesson == null){
            MusicSystemFACADE facade = MusicSystemFACADE.getInstance();
            try {
                Teacher test = (Teacher)facade.getCurrentUser();
                try {
                    
                    App.setRoot("TeacherHome");
                } catch(Exception e) {
                    e.printStackTrace();
                }
            } catch(ClassCastException cce) {
                try {
                    App.setRoot("UserHome");
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }else{
            facade.getCurrentUser().getAssignedLessons().add(lesson);
            try{
                for(int i = 0; i < ((Teacher)facade.getCurrentUser()).getClasses().size(); i++){
                    facade.assignLesson(i, lesson);
                }
                
            } catch (Exception e ){

            }
            PlaylistViewerController.setLesson(lesson);
            App.setRoot("PlaylistViewer");
        }
       
    }
/**
 * creates a playlist from the songs, adds playlist to user's playlists
 * @param event mouse click
 * @throws IOException invalid root set
 */
    @FXML
    void onCreatePlaylist(MouseEvent event) throws IOException {
        if(titleInput.getText() == null) return;
        Playlist playlist = PlaylistList.getInstance().createPlaylist(titleInput.getText(), facade.getCurrentUser().getUsername(), "");
        if(playlist == null){
            System.out.println("clicked");
            MusicSystemFACADE facade = MusicSystemFACADE.getInstance();
            
            try {
                Teacher test = (Teacher)facade.getCurrentUser();
                try {
                    App.setRoot("TeacherHome");
                } catch(Exception e) {
                    e.printStackTrace();
                }
            } catch(ClassCastException cce) {
                try {
                    App.setRoot("UserHome");
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        } else{
            for(Song song : addedSongs.getItems())
                playlist.addSong(song);
            facade.getCurrentUser().getPlaylists().add(playlist);
            PlaylistViewerController.setPlaylist(playlist);
            App.setRoot("PlaylistViewer");
        }
        
    }
/**
 * converts an observable list to an arraylist
 * @param ol the observable list
 * @return the new arraylist
 */
    private static ArrayList<Song> toSongArrayList(ObservableList<Song> ol){
        ArrayList<Song> songs = new ArrayList<Song>();
        for(Song song : ol)
            songs.add(song);
        return songs;
    }



}
