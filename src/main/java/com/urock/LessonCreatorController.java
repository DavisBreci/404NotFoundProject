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

    abstract class SearchResult {
        String primary;
        String secondary;
        String tertiary;

        SearchResult(String primary, String secondary, String tertiary){
            this.primary = primary;
            this.secondary = secondary; 
            this.tertiary = tertiary;
        }

        public abstract void link() throws IOException;
    }

    @FXML
    void home(MouseEvent event) throws IOException {
        App.setRoot("UserHome");
    }

    @FXML
    void onSearch(ActionEvent event){
        String searchString = searchBar.getText();
        if(searchString == null) return;
        searchResults.getItems().clear();
        Song song = SongList.getInstance().getSongByTitle(searchString);
        if(song == null) return;
        searchResults.getItems().add(song);
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
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

        searchResults.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Song sr = searchResults.getSelectionModel().getSelectedItem();
                    if(sr != null)
                        addedSongs.getItems().add(sr);
            }
        });
    }

    @FXML
    void onCreateLesson(MouseEvent event) throws IOException {
        if(titleInput.getText() == null) return;
        Lesson lesson = LessonList.getInstance().createLesson(toSongArrayList(addedSongs.getItems()), titleInput.getText());
        if(lesson == null){
            try{
                Teacher t =(Teacher)facade.getCurrentUser();
                App.setRoot("TeacherHome");
            } catch (ClassCastException e){
                App.setRoot("UserHome");
            }
        }else{
            PlaylistViewerController.setLesson(lesson);
            App.setRoot("PlaylistViewer");
        }
       
    }

    @FXML
    void onCreatePlaylist(MouseEvent event) throws IOException {
        if(titleInput.getText() == null) return;
        Playlist playlist = PlaylistList.getInstance().createPlaylist(titleInput.getText(), facade.getCurrentUser().getUsername(), "");
        if(playlist == null){
            App.setRoot("UserHome");
        } else{
            for(Song song : addedSongs.getItems())
                playlist.addSong(song);
            PlaylistViewerController.setPlaylist(playlist);
            App.setRoot("PlaylistViewer");
        }
        
    }

    private static ArrayList<Song> toSongArrayList(ObservableList<Song> ol){
        ArrayList<Song> songs = new ArrayList<Song>();
        for(Song song : ol)
            songs.add(song);
        return songs;
    }



}
