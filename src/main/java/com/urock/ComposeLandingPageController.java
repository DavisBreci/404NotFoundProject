package com.urock;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.model.DifficultyLevel;
import com.model.Instrument;
import com.model.Key;
import com.model.Measure;
import com.model.MusicSystemFACADE;
import com.model.Rational;
import com.model.Score;
import com.model.Song;
import com.model.Teacher;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import javafx.scene.shape.Polygon;

/**
 * Controller for the ComposeLandingPage screen. Allows users to set parameters for a new song.
 * @author
 */
public class ComposeLandingPageController implements Initializable {

    @FXML
    private TextField artistInput;

    @FXML
    private AnchorPane bg;

    @FXML
    private Label createButton;

    @FXML
    private ComboBox<DifficultyLevel> difficultySelect;

    @FXML
    private TextField genreInput;

    @FXML
    private ComboBox<Instrument> instrumentSelect;

    @FXML
    private ComboBox<Key> keySelect;

    @FXML
    private TextField titleInput;

    @FXML
    private Label back_label;

    @FXML
    private Polygon back;

    /**
     * Initializes landing page controls
     * @param event The event triggered by clicking the create button.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @FXML
    void onCreate(MouseEvent event) throws IOException {
        String title;
        String genre;
        String artist;
        Instrument instrument;
        Key key;
        DifficultyLevel difficultyLevel;

        if((instrument = instrumentSelect.getSelectionModel().getSelectedItem()) == null) return;
        if((key = keySelect.getSelectionModel().getSelectedItem()) == null) return;
        if((difficultyLevel = difficultySelect.getSelectionModel().getSelectedItem()) == null) return;
        if((title = titleInput.getText()) == null ) return;
        if((genre = genreInput.getText()) == null ) return;
        if((artist = artistInput.getText()) == null ) return;
        Song song = new Song(null, title, artist, genre, key, difficultyLevel, instrument, new Score(null, instrument, 120));
        if(MusicSystemFACADE.getInstance().addSongToLibrary(song)){
            for(int i = 0; i < 32; i++)
                song.getScore().add(new Measure(instrument, new Rational(4)));
            ScoreEditorController.loadScore(song.getScore());
            App.setRoot("ScoreEditor");
        }
    }

    /**
     * Handles the back button event. Navigates to either the TecherHome or UserHome screen.
     * @param event The event triggered by clicking the back button.
     */
    @FXML
    void back(MouseEvent event) {
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
    }

    /**
     * Initializes the scene. Sets the scale of the UI elements based on the window size.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        keySelect.getItems().addAll(Key.values());

        keySelect.setConverter(
            new StringConverter<Key>() {
                @Override
                public Key fromString(String arg0) {
                    return null;
                }

                @Override
                public String toString(Key arg0) {
                   return arg0 != null ? titleFormatKey(arg0.toString()) : null;
                }
            }
        );

        instrumentSelect.getItems().addAll(Instrument.values());

        instrumentSelect.setConverter(
            new StringConverter<Instrument>() {
                @Override
                public Instrument fromString(String arg0) {
                    return null;
                }

                @Override
                public String toString(Instrument arg0) {
                   return arg0 != null ? titleFormat(arg0.toString()) : null;
                }
            }
        );

        difficultySelect.getItems().addAll(DifficultyLevel.values());

        difficultySelect.setConverter(
            new StringConverter<DifficultyLevel>() {
                @Override
                public DifficultyLevel fromString(String arg0) {
                    return null;
                }

                @Override
                public String toString(DifficultyLevel arg0) {
                   return arg0 != null ? titleFormat(arg0.toString()) : null;
                }
            }
        );
    }

    /**
     * 
     * @param text
     * @return
     */
    public static String titleFormat(String text){
        if(text == null) return null;
        StringBuilder formatted;
        String [] words = text.toLowerCase().split("_");
        for(int i = 0; i < words.length; i++){
            formatted = new StringBuilder(words[i]);
            formatted.setCharAt(0, Character.toUpperCase(formatted.charAt(0)));
            words[i] = formatted.toString();
        }
        return String.join(" ", words);
    }

    /**
     * 
     * @param text
     * @return
     */
    public static String titleFormatKey(String text){
        if(text == null) return null;
        StringBuilder formatted;
        String [] words = text.toLowerCase().split("_");
        for(int i = 0; i < words.length; i++){
            formatted = new StringBuilder(words[i]);
            formatted.setCharAt(0, Character.toUpperCase(formatted.charAt(0)));
            formatted.insert(1, " ");
            words[i] = formatted.toString();
        }
        return String.join(" / ", words);
    }

    

}