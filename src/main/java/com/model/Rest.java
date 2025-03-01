package com.model;
/**
 * Class that represents a rest
 */
public class Rest extends BarObj{
    /**
     * Constructs a rest with the given duration
     * @param value base note vaue
     * @param dotted whether the rest is dotted
     */
    public Rest(NoteValue value, boolean dotted){
        super(value, dotted);
    }
    /**
     * Returns a Staccato representation of the note
     */
    public String toString(){
        return "R" + timingString();
    }
}
