package com.urock;

import java.io.IOException;
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
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
    @FXML
    private Button buttonGoHomeScoreEditor;

    static final int STROKE_WIDTH = 5;
    static final double STAFFLINE_HEIGHT = 10;
    static final double SPACING = 30;

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
            setMaxHeight(getHeight());
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
        
        DoubleProperty cumulativeNoteWidth;
        Measure measure;
        int string;
        Line line;
        Pane notes;
        public StaffLine(double width, Measure measure, int string){
            super();
            this.measure = measure;
            this.string = string;
            setPrefWidth(width);
            setPrefHeight(STAFFLINE_HEIGHT);
            line = new Line();
            line.setStrokeWidth(STROKE_WIDTH);
            getChildren().add(line);
            line.endXProperty().bind(widthProperty());
            line.setStartX(0);
            line.startYProperty().bind(heightProperty().divide(2));
            line.endYProperty().bind(heightProperty().divide(2));  
            notes = new Pane();
            notes.widthProperty().addListener((obs, oldVal, newVal) -> {
                for(Node n : notes.getChildren()){
                    FretView fn = (FretView)n;
                    fn.setLayoutX(fn.offset.toDouble() * getWidth() / fn.line.measure.getTimeSignature().toDouble());
                }
            });
            getChildren().add(notes);
            Iterator<Entry<Rational, Chord>> cIterator = measure.chordIterator();
            Entry<Rational, Chord> c;
            while(cIterator.hasNext()){
                c = cIterator.next();
                Note [] n = c.getValue().getNotes(false);
                if(n[string] != null){
                    notes.getChildren().add(new FretView(this, c.getKey(), n[string]));
                }
                }
        }
    }
    
    class MeasureView extends VBox{
        Measure measure;
        public MeasureView(double width, double height, Measure measure){
            this.measure = measure;
            StaffLine sl = null;
            // setPrefHeight(height);
            // setPrefWidth(width);
            for(int i = measure.getInstrument().tuning.length - 1; i >= 0 ; i--){
                sl = new StaffLine(width, measure, i);
                getChildren().add(sl);
            }
            
            setSpacing(SPACING);
            // spacingProperty().bind(heightProperty().divide(measure.getInstrument().tuning.length));
        }
    }
    
    class ScoreView extends HBox {
        Score source;
        double measureWidth;
        double measureHeight;
        double barLineHeight;

        public ScoreView(double measureWidth, double measureHeight, Score source){
            this.measureWidth = measureWidth;
            this.measureHeight = measureHeight;
            this.source = source;
            barLineHeight = STAFFLINE_HEIGHT * (source.getInstrument().tuning.length - 1) + SPACING * (source.getInstrument().tuning.length - 1);
            Line barLine = null;
            Pane barLineHolder = null;
            for(Measure m : source.getMeasures()){
                getChildren().add(new MeasureView(measureWidth, measureHeight, m));
                barLine = new Line();
                barLineHolder = new Pane();
                barLineHolder.getChildren().addAll(barLine);
                barLine.setStrokeWidth(STROKE_WIDTH);
                barLine.setEndY(barLineHeight);
                barLine.setStartY(STAFFLINE_HEIGHT / 2);
                getChildren().add(barLineHolder);
            }

        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Sequence rawMidi = 
        Score s = DataLoader.getScoreFromID("a42d710f-afcb-4bce-bfd7-ecb43e6a5a89");
        // m.put(new Rational(0, 1), new Note(PitchClass.E, 2), 0);
        // StaffLine l = new StaffLine(s.get(0), 2);
        // MeasureView mv = new MeasureView(200, 200, s.get(0));
        // HBox score = new HBox();
        // for(Measure m : s.getMeasures()){
        //     score.getChildren().add(new MeasureView(200, 200, m));
        // }
       
        Pane p = new Pane();
        
        p.getChildren().add(new ScoreView(200, 200, s));

        contentArea.getChildren().add(p);

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

        
    }
    @FXML
    void goHome(ActionEvent event) throws IOException {
        App.setRoot("UserHome");
    }
}
