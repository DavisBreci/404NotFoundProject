package com.urock;

import java.net.URL;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.sound.midi.Sequence;

import org.jfugue.player.Player;

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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

public class ScoreEditorController implements Initializable{
    @FXML
    private VBox contentArea;

    class FretView extends TextField {
        public Note note;
        public StaffLine line;
        public Rational offset;
        public FretView(StaffLine line, Rational offset, Note note){
            super();
            this.note = note;
            this.line = line;
            this.offset = offset;
            promptTextProperty().set(note.getFret() + "");
            // setText(getPromptText());
            setPrefColumnCount(2);
            setStyle("""
                    -fx-background-color: white; 
                    -fx-border-color : transparent;
                    -fx-background-insets: -0.2, 1, -1.4, 3;
                    -fx-background-radius: 0;
                    -fx-padding: 4 4 4 4;
                    -fx-prompt-text-fill: derive(-fx-control-inner-background,-30%);
                """);
            setOnKeyPressed( 
                new EventHandler<KeyEvent>(){
                    public void handle(KeyEvent arg0) {
                        if(arg0.getCode().equals(KeyCode.DELETE)){
                            FretView src = (FretView)arg0.getSource();
                            if(src.line.measure.remove(src.offset, src.note))
                                src.line.notes.getChildren().remove(src); 
                        }
                    }
                }
            );

            setOnAction(
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent arg0) {
                        FretView src = (FretView)arg0.getSource();
                        try{
                            src.note.setLocationAndPitch(src.note.getString(), Integer.parseInt(src.getText()));
                        } catch(NumberFormatException e){}
                        src.setText(src.note.getFret() + "");  
                        Player p = new Player();
                        p.play(src.line.measure.toString());
                    }
                }
            );
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
            Iterator<Entry<Rational, Chord>> cIterator = measure.chordIterator();
            Entry<Rational, Chord> c;
            while(cIterator.hasNext()){
                c = cIterator.next();
                Note [] n = c.getValue().getNotes(false);
                if(n[string] != null)
                    notes.getChildren().add(new FretView(this, c.getKey(), n[string]));
                }
        }
    }
    
    class MeasureView extends VBox{
        Measure measure;
        public MeasureView(Measure measure){
            this.measure = measure;
            StaffLine sl = null;
            for(int i = measure.getInstrument().tuning.length - 1; i >= 0 ; i--){
                sl = new StaffLine(measure, i);
                getChildren().add(sl);
            }
            spacingProperty().bind(heightProperty().divide(measure.getInstrument().tuning.length));
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Measure m = new Measure(Instrument.ELECTRIC_CLEAN_GUITAR, new Rational(4));
        // Sequence rawMidi = 
        Score s = DataLoader.getScoreFromID("a42d710f-afcb-4bce-bfd7-ecb43e6a5a89");
        // m.put(new Rational(0, 1), new Note(PitchClass.E, 2), 0);
        StaffLine l = new StaffLine(s.get(0), 2);
        MeasureView mv = new MeasureView(s.get(0));
        Pane p = new Pane();
        p.getChildren().add(mv);
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
        contentArea.getChildren().add(p);
    }
    
}
