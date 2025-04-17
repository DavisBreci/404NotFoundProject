package com.urock;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.sound.midi.Sequence;

import com.model.Chord;
import com.model.DataLoader;
import com.model.Instrument;
import com.model.Measure;
import com.model.Note;
import com.model.PitchClass;
import com.model.Rational;
import com.model.Score;

import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

public class TeacherHomeController {
    
        @FXML
    private Button buttonSearchTeacherHome;

    @FXML
    private Button buttonComposeTeacherHome;

    @FXML
    private Button buttonLogOutTeacherHome;


        @FXML
    void search(ActionEvent event) throws IOException {
        System.out.println("Search page");
        App.setRoot("SearchPage");
    }

    @FXML
    void compose(ActionEvent event) throws IOException {
        System.out.println("Compose score");
        App.setRoot("ScoreEditor");
    }
    @FXML
    void logout(ActionEvent event) throws IOException {
        System.out.println("Logging out");
        App.setRoot("TeacherHome");
    }
}
