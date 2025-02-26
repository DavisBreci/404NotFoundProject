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
        ),

    BASS(24, 
        new Note(PitchClass.E, 1),
        new Note(PitchClass.A, 1),
        new Note(PitchClass.D, 2),
        new Note(PitchClass.G, 2)
    ),
    
    UKULELE(15,  // It's a soprano ukulele
        new Note(PitchClass.G, 4),
        new Note(PitchClass.C, 4),
        new Note(PitchClass.E, 4),
        new Note(PitchClass.A, 4)
    );

    public final Note [] tuning; // Lowest to highest
    public final int frets;
    private Instrument(int frets, Note ... tuning){
        this.frets = frets;
        this.tuning = tuning;
    }
}
