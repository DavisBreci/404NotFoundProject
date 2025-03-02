/**
 * @Author Christopher Ferguson
 */
package com.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * A class representing a chord on a fretted instrument
 */
public class Chord extends BarObj{
    private Instrument instrument;
    private Note [] notes;
    private int noteCount;
    /**
     * Constructs an empty chord with the given duration and instrument
     * @param value the note's base duration
     * @param dotted whether the note is dotted
     * @param instrument which fretted instrument it's on
     */
    public Chord(NoteValue value, boolean dotted, Instrument instrument){
        super(value, dotted);
        this.instrument = instrument;
        this.dotted = dotted;
        notes = new Note[instrument.tuning.length];
        noteCount = 0;
    }
    /**
     * Retrieves the number of notes in a chord
     * @return the current note count
     */
    public int getNoteCount(){
        return noteCount;
    }
    /**
     * Retrieves the instrument the chord belongs to
     * @return the fretted instrument
     */
    public Instrument getInstrument(){
        return this.instrument;
    }
    /**
     * Retrieves the notes that compose the chord
     * @return a deep copy of the note array
     */
    public Note [] getNotes(){
        Note [] notes = new Note[this.notes.length];
        for(int i = 0; i < notes.length; i++)
            if(this.notes[i] != null)
                notes[i] = this.notes[i].deepCopy();
        return notes;
    }
    /**
     * Creates a new note object with the same state
     * @return the copy
     */
    public Chord deepCopy(){
        Chord copy = new Chord(value, dotted, instrument);
        Note [] notes = getNotes();
        for(Note n : notes)
            copy.put(n, n.getString());
        return copy;
    }

    /**
     * Attempts to place a note on the desired string. Fret assignment is automatic. 
     * If the note's duration doesn't match the chord's, it will be modified prior to inclusion. 
     * The same goes for the instrument.
     * @param note the note to be placed
     * @param string the string on which the note is to be played
     * @return whether the note was succesfully added to the chord
     */
    public boolean put(Note note, int string){
        if(notes[string] != null)
            return false;
        if(note.restring(string)){
            if(duration != note.getDuration()){
                note.setValue(value);
                note.setDotted(dotted);
            }
            if(instrument != note.getInstrument())
                note.setInstrument(instrument);
            notes[string] = note;
            noteCount++;
        }   
        return true;
    }
    /**
     * Attempts to remove a note from the chord
     * @param string The string whose note should be deleted
     * @return Whether the removal was successful
     */
    public boolean remove(int string){
        if(string >= notes.length)
            return false;
        if(notes[string] == null)
            return false;
        notes[string] = null;
        noteCount--;
        return true;
    }

    public boolean transpose(int steps){
        Note [] temp = getNotes();
        for(Note n : temp)
            if(!n.transpose(steps))
                return false;
        for(Note n : this.notes)
            n.transpose(steps);
        return true;
    }
    /**
     * Retrieves a Staccato representation of the chord
     */
    public String toString(){
        String staccato = String.join("+",
        new Iterable<String>() {
            public Iterator<String> iterator(){
                ArrayList<String> sNotes = new ArrayList<String>();
                for(Note n : notes)
                    if(n != null) sNotes.add(n.toString());
                return sNotes.iterator();
            }
        });
        return staccato.toString();
    }
}

