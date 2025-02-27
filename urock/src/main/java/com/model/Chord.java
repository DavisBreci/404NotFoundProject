package com.model;

public class Chord extends BarObj{
    Instrument instrument;
    Note [] notes;
    boolean dotted;
    
    public Chord(NoteValue value, boolean dotted, Instrument instrument){
        super(value, dotted);
    }


}