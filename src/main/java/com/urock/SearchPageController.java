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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Contoller for Search Page Controller
 */
public class SearchPageController implements Initializable{

    @FXML
    private ImageView logo;

    @FXML
    private TextField searchBar;

    @FXML
    private ComboBox<String> searchSelect;

    @FXML
    private ListView<SearchResult> searchResults;

    private final String SONGS = "Songs";
    private final String PLAYLISTS = "Playlists";
    private final String LESSONS = "Lessons";

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

    /**
     * Method that upon MouseEvent, switches the root screen to User Home screen
     * @param event
     * @throws IOException
     */
    @FXML
    void home(MouseEvent event) throws IOException {
        App.setRoot("UserHome");
    }

    /**
     * Method that covers the Search function on the Search Screen
     * For each case such as Playlists, songs, or lessons, it populates the list with clickable UI Components that will bring the user to that Screen to view the object
     * @param event
     */
    @FXML
    void onSearch(ActionEvent event){
        String searchMode = searchSelect.getSelectionModel().getSelectedItem();
        String searchString = searchBar.getText();
        if(searchMode == null || searchString == null) return;
        searchResults.getItems().clear();
        switch (searchMode) {
            case PLAYLISTS:
                Playlist playlist = PlaylistList.getInstance().getPlaylistByTitle(searchString);
                if(playlist == null) return;
                searchResults.getItems().add(new SearchResult(playlist.getTitle(), playlist.getAuthor(), playlist.getSongs().size() + " Songs"){
                    @Override
                    public void link() throws IOException {
                        PlaylistViewerController.setPlaylist(playlist);
                        App.setRoot("PlaylistViewer");
                    }});
                break;
            case SONGS:
                Song song = SongList.getInstance().getSongByTitle(searchString);
                if(song == null) return;
                searchResults.getItems().add(new SearchResult(song.getTitle(), song.getArtist(), ComposeLandingPageController.titleFormat(song.getInstrument().toString())){
                @Override
                public void link() throws IOException {
                    ScoreEditorController.loadScore(song.getScore());
                    App.setRoot("ScoreEditor");
                }});
                break;
            case LESSONS:
                Lesson lesson = LessonList.getInstance().getLesson(searchString);
                if(lesson == null) return;
                searchResults.getItems().add(new SearchResult(lesson.getTitle(), lesson.getSongs() + " Songs", ""){
                @Override
                public void link() throws IOException {
                    PlaylistViewerController.setLesson(lesson);
                    App.setRoot("PlaylistViewer");
                }});
                break;
            default:
                break;
        }
    }


    /**
     * Method that initializes the UI components and Scaling
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        searchSelect.getItems().addAll("Songs", "Playlists", "Lessons");

        searchResults.setCellFactory(listView -> new ListCell<>(){
            @Override
            public void updateItem(SearchResult value, boolean empty){
                super.updateItem(value, empty);
                if(empty || value == null){
                    setText(null);
                    setGraphic(null);
                } else {
                    try{
                        FXMLLoader loader = new FXMLLoader(App.class.getResource("TemplateSearchResult.fxml"));
                        Parent cellRoot = loader.load();
                        SearchResultCell controller = loader.getController();
                        controller.setLabels(value.primary, value.secondary, value.tertiary);
                        setGraphic(cellRoot);
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        );

        /**
         *A Mouse click based method that when an item in the searchResults list is clicked tries to go to that Results Screen
         */
        searchResults.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                SearchResult sr = searchResults.getSelectionModel().getSelectedItem();
                    if(sr != null) 
                        try {
                            sr.link();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
            }
        });
    }


}
