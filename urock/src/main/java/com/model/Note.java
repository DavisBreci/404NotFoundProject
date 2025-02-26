package com.model;

public class Note extends BarObj {
    private Instrument instrument;
    private int fret;
    private int string;
    private PitchClass pitchClass;
    private int octave;
    private Note frontTie;
    private Note backTie;

    public Note(Instrument instrument, NoteValue noteValue, boolean dotted, PitchClass pitchClass, int octave){
        super(noteValue, dotted);
        this.string = 0;
        this.fret = 0;
        this.instrument = instrument;
        this.dotted = dotted;
        this.pitchClass = pitchClass;
        setOctave(octave);
    }

    public Note(PitchClass pitchClass, int octave){ // For situations where pitch content is all that matters
        super(NoteValue.WHOLE, false);
        this.pitchClass = pitchClass;
        setOctave(octave);
    }

    public boolean setLocation(int string, int fret){
        if(string < 0 || fret < 0 || string > instrument.tuning.length || fret > instrument.frets)
            return false;
        this.string = string;
        this.fret = fret;
        return true;

    }

    public int getString(){
        return string;
    }

    public int getFret(){
        return fret;
    }

    public int getOctave(){
        return octave;
    }

    public PitchClass getPitchClass(){
        return pitchClass;
    }

    public void setOctave(int octave){
        if (octave >= 0)
            this.octave = octave;
        else
            this.octave = 0;
    }

    public String toString(){
        return pitchClass + (backTie == null ? "" : "-") + timingString() + (frontTie == null ? "" : "-");
    }

    public boolean equals(Note n){
        return string == n.getString() && 
            fret == n.getFret() &&
            octave == n.getOctave() &&
            pitchClass == n.getPitchClass();
    }
    
}
