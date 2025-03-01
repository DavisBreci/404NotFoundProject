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

    public int getNoteCount(){
        return noteCount;
    }

    public Instrument getInstrument(){
        return this.instrument;
    }

    public Note [] getNotes(){
        Note [] notes = new Note[this.notes.length];
        for(int i = 0; i < notes.length; i++)
            if(this.notes[i] != null)
                notes[i] = this.notes[i].deepCopy();
        return notes;
    }
    
    public boolean put(Note note, int string){
        if(string >= notes.length || notes[string] != null)
            return false;
        Note open = instrument.tuning[string]; // The lowest note on the string
        int fret = open.stepsTo(note);
        if(fret >= instrument.frets)
            return false;
        notes[string] = note;
        note.setLocation(string, fret);
        noteCount++;
        return true;
    }

    public boolean remove(int string){
        if(string >= notes.length)
            return false;
        if(notes[string] == null)
            return false;
        notes[string] = null;
        noteCount--;
        return true;
    }

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

