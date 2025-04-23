package com.urock;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;

import org.jfugue.player.ManagedPlayer;
import org.jfugue.player.Player;

import com.model.Chord;
import com.model.DataLoader;
import com.model.Instrument;
import com.model.Measure;
import com.model.Note;
import com.model.NoteValue;
import com.model.PitchClass;
import com.model.Rational;
import com.model.Score;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

public class ScoreEditorController implements Initializable{
    @FXML
    private VBox contentArea;
    @FXML
    private Button buttonGoHomeScoreEditor;

    @FXML
    private ListView<NoteValue> quaverSelect;

    @FXML
    private AnchorPane playbackPanel;

    @FXML
    private StackPane scoreArea;

    @FXML
    private ScrollPane scoreScroller;

    @FXML
    private AnchorPane toolbar;

    @FXML
    private Button stopButton;

    @FXML
    private ToggleButton metronomeButton;

    @FXML
    private ToggleButton playPause;

    private ObservableList<NoteValue> fill;
    
    private ManagedPlayer managedPlayer;

    static final int STROKE_WIDTH = 5;
    static final double STAFFLINE_HEIGHT = 10;
    static final double SPACING = 30;
    static final double FRET_WIDTH = 500;
    static final double INITIAL_MEASURE_WIDTH = 150;
    static final double QUAVER_SELECT_HEIGHT = 75;
    static final double BUTTON_SIZE = .80 * QUAVER_SELECT_HEIGHT;
    static final double QUAVER_SELECT_WIDTH = BUTTON_SIZE * NoteValue.values().length * 1.3;

    private static Score loadedScore;

    class FretView extends TextField {
        Note note;
        StaffLine line;
        Rational offset;
        
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
        StaffLine(double width, Measure measure, int string){
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
                updateSpacing();
            });
            getChildren().add(notes);
            // Iterator<Entry<Rational, Chord>> cIterator = measure.chordIterator();
            // Entry<Rational, Chord> c;
            // while(cIterator.hasNext()){
            //     c = cIterator.next();
            //     Note [] n = c.getValue().getNotes(false);
            //     if(n[string] != null){
            //         notes.getChildren().add(new FretView(this, c.getKey(), n[string]));
            //     }
            // } 
        }
        
        void addNote(Rational offset, Note n){
            notes.getChildren().add(new FretView(this, offset, n));
        }

        void updateSpacing(){
            for(Node node : notes.getChildren()){
                FretView fn = (FretView)node;
                fn.setLayoutX(fn.offset.toDouble() * getWidth() / fn.line.measure.getTimeSignature().toDouble());
            }
        }
    }
    
    class MeasureView extends VBox{
        Measure measure;
        MeasureView(double width, double height, Measure measure){
            this.measure = measure;
            StaffLine sl = null;
            for(int i = measure.getInstrument().tuning.length - 1; i >= 0 ; i--){
                sl = new StaffLine(width, measure, i);
                getChildren().add(sl);
            }
            
            setSpacing(SPACING);
            Iterator<Entry<Rational, Chord>> cIterator = measure.chordIterator();
            Entry<Rational, Chord> c;
            Note [] n;
            
            while(cIterator.hasNext()){
                c = cIterator.next();
                n = c.getValue().getNotes(false);
                for(int i = 0; i < measure.getInstrument().tuning.length; i++){
                    n = c.getValue().getNotes(false);
                    if(n[i] != null){
                        sl = (StaffLine)getChildren().get(measure.getInstrument().tuning.length - 1 - i);
                        sl.addNote(c.getKey(), n[i]);
                    }
                }
                sl.updateSpacing();

                setPrefWidth(sl.getPrefWidth() + FRET_WIDTH);

            } 
        }

        boolean addNote(Rational offset, Note note, int string){
            boolean expand = measure.get(offset) == null;
            if(measure.put(offset, note, string)){
                StaffLine line = (StaffLine)getChildren().get(measure.getInstrument().tuning.length - 1 - string);
                if(expand){
                   line.setPrefWidth(line.getPrefWidth() + FRET_WIDTH);
                   line.updateSpacing();
                }
                line.addNote(offset, note);
                return true;
            }
            System.out.println("Failure");
            return false;
        }
    }
    
    class ScoreView extends HBox {
        Score source;
        double measureWidth;
        double measureHeight;
        double barLineHeight;
        
        ScoreView(double measureWidth, double measureHeight, Score source){
            this.measureWidth = measureWidth;
            this.measureHeight = measureHeight;
            this.source = source;
            barLineHeight = STAFFLINE_HEIGHT * (source.getInstrument().tuning.length - 1) + SPACING * (source.getInstrument().tuning.length - 1);
            for(Measure m : source.getMeasures()){
                appendMeasure(m);
            }
        }

        void appendMeasure(Measure newMeasure){
            Line barLine = null;
            Pane barLineHolder = null;
            getChildren().add(new MeasureView(measureWidth, measureHeight, newMeasure));
            barLine = new Line();
            barLineHolder = new Pane();
            barLineHolder.getChildren().addAll(barLine);
            barLine.setStrokeWidth(STROKE_WIDTH);
            barLine.setEndY(barLineHeight);
            barLine.setStartY(STAFFLINE_HEIGHT / 2);
            getChildren().add(barLineHolder);
        }
        
    }

    public static void loadScore(Score score){
        if(score == null) return;
        loadedScore = score;
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) { 
        managedPlayer = new ManagedPlayer();
        initializeQuaverSelect();
        if(loadedScore == null) return;
        Pane p = new Pane();
        ScoreView tablature = new ScoreView(INITIAL_MEASURE_WIDTH, 200, loadedScore);
        p.getChildren().add(tablature);
        // scoreScroller.setContent(p);
        scoreArea.getChildren().add(p);
        scoreArea.prefWidthProperty().bind(p.prefWidthProperty());
        scoreArea.prefHeightProperty().bind(p.prefHeightProperty().multiply(10));
        scoreScroller.prefHeightProperty().bind(contentArea.heightProperty().subtract(toolbar.heightProperty()).subtract(playbackPanel.heightProperty()));
    }

    public void initializeQuaverSelect(){
        fill = FXCollections.observableArrayList(NoteValue.values());
        quaverSelect.setPrefWidth(QUAVER_SELECT_WIDTH);
        quaverSelect.setPrefHeight(QUAVER_SELECT_HEIGHT);
        quaverSelect.setItems(fill);
        quaverSelect.setCellFactory(listView -> new ListCell<>(){
            @Override
            public void updateItem(NoteValue value, boolean empty){
                super.updateItem(value, empty);
                if(empty || value == null){
                    setText(null);
                    setGraphic(null);
                } else {
                    try{
                        FXMLLoader loader = new FXMLLoader(App.class.getResource("TemplateQuaver.fxml"));
                        Parent cellRoot = loader.load();
                        QuaverListViewCell controller = loader.getController();
                        controller.setNoteValue(value);
                        controller.setDimensions(BUTTON_SIZE, BUTTON_SIZE);
                        setGraphic(cellRoot);
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        );
    }
    
    @FXML
    void goHome(ActionEvent event) throws IOException {
        App.setRoot("UserHome");
    }

    @FXML
    void onPlayPauseToggle(ActionEvent event) throws InvalidMidiDataException, MidiUnavailableException{
        if(playPause.isSelected()){
            try{
                managedPlayer.resume();
            } catch(IllegalStateException e){
                managedPlayer.start(loadedScore.getSequence(0, loadedScore.size(), null, 1));
            }
        } else{
            managedPlayer.pause();
        }
    }

    @FXML
    void onStop(ActionEvent event){
        System.out.println("Stopped");
        if(managedPlayer.isPlaying()){
            managedPlayer.pause();
            playPause.setSelected(false);
        }
        managedPlayer.reset();
    }


}
