package com.model;

public class Chord extends BarObj{
    private Instrument instrument;
    private Note [] notes;
    private int noteCount;
    
    public Chord(NoteValue value, boolean dotted, Instrument instrument){
        super(value, dotted);
    }

    public boolean put(Note note, int String){
        return true;
    }

    public String toString(){
        return "";
    }
}

