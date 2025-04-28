package com.urock;

import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.MidiUnavailableException;

import org.jfugue.realtime.RealtimePlayer;

import com.model.Instrument;
import org.jfugue.theory.Note;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Pair;

/**
 * Class that allows you to play the guitar with your keyboard
 */
public class Keytar extends RealtimePlayer {

    public static final String [][] ROWS = {
        {"Z", "X", "C", "V", "B", "N", "M", "COMMA", "PERIOD", "SLASH"},
        {"A", "S", "D", "F", "G", "H", "J", "K", "L", "SEMICOLON", "QUOTE"},
        {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "OPEN_BRACKET", "CLOSE_BRACKET"},
        {"DIGIT1", "DIGIT2", "DIGIT3", "DIGIT4", "DIGIT5", "DIGIT6", "DIGIT7", "DIGIT8", "DIGIT9", "DIGIT0", "MINUS", "PLUS"}
    };
    private Map<String, Pair<Integer, Integer>> keyboard;
    private Instrument instrument;
    boolean [][] busyNotes;

    /**
     * Creates a keytar instance
     * @param instrument the fretted instrument to be played
     * @throws MidiUnavailableException triggers if the instrument is unrecognized
     */
    public Keytar(Instrument instrument) throws MidiUnavailableException{
        super();
        this.instrument = instrument != null ? instrument : Instrument.GUITAR;
        changeInstrument(instrument.bank == 0 ? instrument.toString() : Instrument.GUITAR.toString());
        keyboard = new HashMap<String, Pair<Integer, Integer>>();
        for(int i = 0; i < ROWS.length; i++){
            for(int j = 0; j < ROWS[i].length; j++){
                keyboard.put(ROWS[i][j], new Pair<Integer,Integer>(i, j));
            }
        }

        busyNotes = new boolean[instrument.tuning.length][instrument.frets];
    }

    /**
     * Generates a JFugue note from the given string and fret
     * @param string string location of note
     * @param fret fret location of note
     * @return JFugue note representation
     */
    public Note resolveNote(int string, int fret){
        if(string < 0 || string > instrument.tuning.length || fret < 0 || fret > instrument.frets)
            return null;
        com.model.Note n = instrument.tuning[string].deepCopy();
        n.transpose(fret + 12);
        return new org.jfugue.theory.Note(n.toString());
    }

    /**
     * Attempts to start a note based on a key event. Does nothing if key isn't mapped to a note.
     * @param event keyEvent to be processed
     */
    public void startNote(KeyEvent event){
        Note note;
        Pair<Integer, Integer> mapping = keyboard.get(event.getCode().toString());
        if(mapping == null)
            return;
        int string  = event.isShiftDown() ? instrument.tuning.length - 4 + mapping.getKey().intValue() : mapping.getKey().intValue();
        int fret = mapping.getValue().intValue();
        if((note = resolveNote(string, fret)) != null && !busyNotes[string][fret]){
            super.startNote(note);
            busyNotes[string][fret] = true;
        }
    }

    /**
     * Attempts to stop a note based on a key event. Does nothing if key isn't mapped to a note.
     * @param event keyEvent to be processed
     */
    public void stopNote(KeyEvent event){
        Note note;
        Pair<Integer, Integer> mapping = keyboard.get(event.getCode().toString());
        if(mapping == null)
            return;
        int string  = event.isShiftDown() ? instrument.tuning.length - 4 + mapping.getKey().intValue() : mapping.getKey().intValue();
        int fret = mapping.getValue().intValue();
        if((note = resolveNote(string, fret)) != null && busyNotes[string][fret]){
            super.stopNote(note);
            busyNotes[string][fret] = false;
        }
    }

}
