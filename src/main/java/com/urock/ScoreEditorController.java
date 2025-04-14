package com.urock;

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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

public class ScoreEditorController implements Initializable{
    @FXML
    private VBox contentArea;

    class FretView extends TextField {
        Note n;
        public FretView(Note n){
            super();
            promptTextProperty().set(n.getFret() + "");
            setPrefColumnCount(2);
            setStyle("""
                    -fx-background-color: white; 
                    -fx-border-color : transparent;
                    -fx-background-insets: -0.2, 1, -1.4, 3;
                    -fx-background-radius: 0;
                    -fx-padding: 4 4 4 4;
                    -fx-prompt-text-fill: derive(-fx-control-inner-background,-30%);
                """);
        }   
    }

    class StaffLine extends Pane{
        static final int STROKE_WIDTH = 5;
        DoubleProperty cumulativeNoteWidth;
        Measure measure;
        int string;
        Line line;
        HBox notes;
        public StaffLine(Measure measure, int string){
            super();
            this.measure = measure;
            this.string = string;
            line = new Line();
            line.setStrokeWidth(STROKE_WIDTH);
            getChildren().add(line);
            line.endXProperty().bind(widthProperty());
            line.setStartX(0);
            line.startYProperty().bind(heightProperty().divide(2));
            line.endYProperty().bind(heightProperty().divide(2));  
            notes = new HBox();
            getChildren().add(notes);
            notes.setSpacing(20);
            for(Chord c: measure.getChords()){
                Note [] n = c.getNotes(false);
                if(n[string] != null)
                    notes.getChildren().add(new FretView(n[string]));
            }
        }
    }
    
    class MeasureView extends HBox{

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Measure m = new Measure(Instrument.ELECTRIC_CLEAN_GUITAR, new Rational(4));
        // Sequence rawMidi = 
        Score s = DataLoader.getScoreFromID("a42d710f-afcb-4bce-bfd7-ecb43e6a5a89");
        // m.put(new Rational(0, 1), new Note(PitchClass.E, 2), 0);
        StaffLine l = new StaffLine(s.get(0), 2);
        /*
         * .fret { 
        -fx-background-color: white;
        -fx-border-color : transparent;
        -fx-background-insets: -0.2, 1, -1.4, 3;
        -fx-background-radius: 0;
        -fx-padding: 4 4 4 4;
        -fx-prompt-text-fill: derive(-fx-control-inner-background,-30%);
        -fx-pref-columns: 2;
        }
         */
        // l.setPrefHeight(contentArea.getHeight());
        // l.setPrefWidth(contentArea.getWidth());
        // l.prefHeightProperty().bind(contentArea.prefHeightProperty());
        // l.prefWidthProperty().bind(contentArea.prefWidthProperty());
        contentArea.getChildren().add(l);
    }
    
}
