package com.urock;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.swing.Action;

import org.jfugue.player.ManagedPlayer;
import org.jfugue.player.Player;

import com.model.BarObj;
import com.model.Chord;
import com.model.DataLoader;
import com.model.Instrument;
import com.model.Lesson;
import com.model.Measure;
import com.model.MusicSystemFACADE;
import com.model.Note;
import com.model.NoteValue;
import com.model.PitchClass;
import com.model.Playlist;
import com.model.Rational;
import com.model.Score;
import com.model.Teacher;
import com.model.User;
import com.model.User;

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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

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

    @FXML
    private Label noteInserter;

    @FXML
    private TextField measureInput;

    @FXML 
    private TextField offsetInput;

    @FXML
    private TextField stringInput;

    @FXML
    private HBox noteInsertionPanel;

    @FXML
    private HBox playPauseStop;

    @FXML
    private Button insertMeasure;

    @FXML
    private Button deleteMeasure;

    @FXML
    private TextField timeSignatureInput;

    private ObservableList<NoteValue> fill;
    
    private ManagedPlayer managedPlayer;
    private Keytar keytar;

    static final int STROKE_WIDTH = 5;
    static final double STAFFLINE_HEIGHT = 10;
    static final double SPACING = 30;
    static final double FRET_WIDTH = 500;
    static final double INITIAL_MEASURE_WIDTH = 150;
    static final double QUAVER_SELECT_HEIGHT = 75;
    static final double BUTTON_SIZE = .80 * QUAVER_SELECT_HEIGHT;
    static final double QUAVER_SELECT_WIDTH = BUTTON_SIZE * NoteValue.values().length * 1.3;

    private static Score loadedScore;
    private ScoreView tablature;
    private ImageView play;
    private ImageView pause;
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
        
        void deleteLastMeasure(){
            if(getChildren().size() >= 2){
                source.remove(source.size() - 1);
                getChildren().removeLast();
                getChildren().removeLast();
                source.remove(source.size() - 1);
                source.remove(source.size() - 1);
            }
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

        if(loadedScore == null){
            loadedScore = new Score(null, Instrument.GUITAR, 120);
            for(int i = 0; i < 32; i++){
                loadedScore.add(new Measure(Instrument.GUITAR, new Rational(4)));
            }
        }
        
        Pane p = new Pane();
        tablature = new ScoreView(INITIAL_MEASURE_WIDTH, 200, loadedScore);
        p.getChildren().add(tablature);
        VBox contentBox = new VBox(p);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.prefHeightProperty().bind(scoreScroller.heightProperty());
        scoreArea.getChildren().add(contentBox);
        scoreArea.prefWidthProperty().bind(contentBox.prefWidthProperty());
        scoreArea.prefHeightProperty().bind(contentBox.prefHeightProperty().multiply(1.5));
        scoreScroller.prefHeightProperty().bind(contentArea.heightProperty().subtract(toolbar.heightProperty()).subtract(playbackPanel.heightProperty()));
        quaverSelect.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                noteInsertionPanel.setLayoutY(quaverSelect.getLayoutY() + 8);
                }
            } 
        );

        quaverSelect.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                noteInsertionPanel.setLayoutX(quaverSelect.getWidth() + quaverSelect.getLayoutX() + 30);
            }            
        });


        noteInserter.setMinSize(BUTTON_SIZE, BUTTON_SIZE);
        noteInserter.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
        noteInserter.setMaxSize(BUTTON_SIZE, BUTTON_SIZE);

        try {
            keytar = new Keytar(loadedScore.getInstrument());
            contentArea.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent arg0) {
                        keytar.startNote(arg0);
                    }
                    
                }
            );
            contentArea.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent arg0) {
                        keytar.stopNote(arg0);
                    }
                    
                }
            );

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }

        play = new ImageView(new Image(App.class.getResourceAsStream("Images/PlayButton/Play.png")));
        pause = new ImageView(new Image(App.class.getResourceAsStream("Images/PlayButton/Pause.png")));
        play.setEffect(new ColorAdjust(0, 0, 1, 0));
        play.setFitWidth(BUTTON_SIZE *.30);
        play.setFitHeight(BUTTON_SIZE * .30);
        pause.setEffect(new ColorAdjust(0, 0, 1, 0));
        pause.setFitWidth(BUTTON_SIZE *.30);
        pause.setFitHeight(BUTTON_SIZE * .30);

        playPause.setGraphic(play);
        playPause.setPrefWidth(BUTTON_SIZE);
        playPause.setPrefHeight(BUTTON_SIZE);
        ImageView stop = new ImageView(new Image(App.class.getResourceAsStream("Images/PlayButton/Stop.png")));
        stop.setFitWidth(BUTTON_SIZE *.30);
        stop.setFitHeight(BUTTON_SIZE * .30);
        stop.setEffect(new ColorAdjust(0, 0, 1, 0));
        stopButton.setGraphic(stop);
        stopButton.setPrefWidth(BUTTON_SIZE);
        stopButton.setPrefHeight(BUTTON_SIZE);
        contentArea.widthProperty().addListener(
            new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                    playPauseStop.setLayoutX(contentArea.getWidth() / 2.0  - playPauseStop.getPrefWidth() /2.0);
                }
                
            }
        );

        ImageView measureInsert = new ImageView(new Image(App.class.getResourceAsStream("Images/MeasureButtons/AddMeasure.png")));
        measureInsert.setFitWidth(BUTTON_SIZE * .70);
        measureInsert.setFitHeight(BUTTON_SIZE * .70);
        insertMeasure.setPrefWidth(BUTTON_SIZE );
        insertMeasure.setPrefHeight(BUTTON_SIZE);
        insertMeasure.setMaxSize(FRET_WIDTH, BUTTON_SIZE);
        insertMeasure.setGraphic(measureInsert);

        ImageView measureDelete = new ImageView(new Image(App.class.getResourceAsStream("Images/MeasureButtons/RemoveMeasure.png")));
        measureDelete.setFitWidth(BUTTON_SIZE * .70);
        measureDelete.setFitHeight(BUTTON_SIZE * .70);
        deleteMeasure.setPrefWidth(BUTTON_SIZE );
        deleteMeasure.setPrefHeight(BUTTON_SIZE);
        deleteMeasure.setMaxSize(FRET_WIDTH, BUTTON_SIZE);
        deleteMeasure.setGraphic(measureDelete);
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
        onStop(event);
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

    @FXML
    void onPlayPauseToggle(ActionEvent event) throws InvalidMidiDataException, MidiUnavailableException{
        if(playPause.isSelected()){
            try{
                managedPlayer.resume();
            } catch(IllegalStateException e){
                managedPlayer.start(loadedScore.getSequence(0, loadedScore.size(), null, 1));
            }
            playPause.setGraphic(pause);
        } else{
            managedPlayer.pause();
            playPause.setGraphic(play);

        }
    }

    @FXML
    void onStop(ActionEvent event){
        if(managedPlayer.isPlaying()){
            managedPlayer.pause();
            playPause.setSelected(false);
            playPause.setGraphic(play);
        }
        managedPlayer.reset();
    }

    @FXML
    void onInsertNote(MouseEvent event){
        NoteValue selected = null;
        if((selected = quaverSelect.getSelectionModel().getSelectedItem()) != null){
            try{
                Rational offset = new Rational(offsetInput.getText());
                offset.simplify();
                double lg2 = (Math.log(offset.getDenominator()) / Math.log(2));
                if(Math.ceil(lg2) != Math.floor(lg2)) throw new Exception();
                int measure = Integer.parseInt(measureInput.getText()) - 1;
                if(measure < 0 || measure >= loadedScore.getMeasures().size()) throw new Exception();
                int string = Integer.parseInt(stringInput.getText()) - 1;
                if(string < 0 || string >= loadedScore.getInstrument().tuning.length) throw new Exception();
                Note template = loadedScore.getInstrument().tuning[string].deepCopy();
                Note toAdd = new Note(selected, false, loadedScore.getInstrument(),template.getPitchClass(), template.getOctave());
                ((MeasureView)tablature.getChildren().get(measure * 2)).addNote(offset, toAdd , string);
            } catch (Exception e){
                
            }
            measureInput.clear();
            offsetInput.clear();
            stringInput.clear();
        }
    }

    @FXML
    void onInsertMeasure(ActionEvent event){
        try{
            Rational timeSig = new Rational(timeSignatureInput.getText());
            tablature.appendMeasure(new Measure(loadedScore.getInstrument(), timeSig));
        } catch(Exception e){
            
        }
    }

    @FXML
    void onDeleteMeasure(ActionEvent e){
        tablature.deleteLastMeasure();
    }
}
