package com.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A class representing a chord on a fretted instrument
 * @author Christopher Fergusons
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
    public Note [] getNotes(boolean deepCopy){
        Note [] notes = new Note[this.notes.length];
        for(int i = 0; i < notes.length; i++)
            if(this.notes[i] != null)
                notes[i] = deepCopy ? this.notes[i].deepCopy() : this.notes[i];
        return notes;
    }
    /**
     * Creates a new note object with the same state
     * @return the copy
     */
    public Chord deepCopy(){
        Chord copy = new Chord(value, dotted, instrument);
        Note [] notes = getNotes(true);
        for(int i = 0; i < notes.length; i++)
            if(notes[i] != null)
                copy.put(notes[i], notes[i].getString());
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
        if(note == null || notes[string] != null)
            return false;
        if(instrument != note.getInstrument())
            note.setInstrument(instrument);
        if(!note.restring(string))
            return false;
        if(duration != note.getDuration()){
            note.setValue(value);
            note.setDotted(dotted);
        }
        notes[string] = note;
        noteCount++;
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

    /**
     * Attempts to remove a note from the chord
     * @param n The note to be deleted
     * @return Whether the removal was successful
     */
    public boolean remove(Note n){
        if(n.getString() >= notes.length)
            return false;
        if(notes[n.getString()] == null)
            return false;
        if(!(notes[n.getString()] == n))
            return false;
        notes[n.getString()].untie();
        notes[n.getString()] = null;
        noteCount--;
        return true;
    }

    /**
     * Attempts to transpose the entire chrord
     * @param steps the signed number of frets to transpose by
     * @return whether the transposition was successful
     */
    public boolean transpose(int steps){
        Note [] temp = getNotes(true);
        for(int i = 0; i < temp.length; i++)
            if(temp[i] != null && !temp[i].transpose(steps))
                return false;
        for(int i = 0; i < notes.length; i++)
            if(notes[i] != null)
                notes[i].transpose(steps);
        return true;
    }

    /**
     * Attempts to move the chord shape across the fretboard
     * @param strings the signed number of strings to shift by
     * @return whether the shift was successful
     */
    public boolean shiftString(int strings){
        Note [] temp = getNotes(true);
        int newString = 0;
        Note newNote;
        for(int i = 0; i < temp.length; i++)
            if(temp[i] != null){
                if((newString = i + strings) >= temp.length || newString < 0){
                    return false;
                }
                newNote = instrument.tuning[newString].deepCopy();
                newNote.transpose(temp[i].getFret());
                temp[i] = newNote;
            }
        notes = new Note[notes.length];
        for(int i = 0; i < notes.length; i++)
            if(temp[i] != null){
                notes[i + strings] = temp[i];
                temp[i].setLocation(i + strings, temp[i].getFret());
            }
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

    /**
     * Rids the chord of notes
     */
    public void clear(){
        notes = new Note[instrument.tuning.length];
        noteCount = 0;
    }
    
    /**
     * @author brenskrz
     * @return the notelist of notes
     */
    public ArrayList<Note> getNotes(){
        ArrayList<Note> noteList = new ArrayList<>();
        for(int i = 0; i < this.notes.length; i++){
            if(this.notes[i] != null){
                noteList.add(this.notes[i]);
            }
        }
        return noteList;
    }
}
