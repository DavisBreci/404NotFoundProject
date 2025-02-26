package com.model;
import com.model.PitchClass;

public enum Instrument {
    GUITAR(24, 
        new Note(PitchClass.E, 2),
        new Note(PitchClass.A, 2),
        new Note(PitchClass.D, 3),
        new Note(PitchClass.G, 3),
        new Note(PitchClass.B, 3),
        new Note(PitchClass.E, 4) 
        );

    public final Note [] tuning;
    public final int frets;
    private Instrument(int frets, Note ... tuning){
        this.frets = frets;
        this.tuning = tuning;
    }
}
