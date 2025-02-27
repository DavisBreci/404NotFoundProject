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
        this.pitchClass = pitchClass;
        setOctave(octave);
    }

    public Note(PitchClass pitchClass, int octave){ // For situations where pitch content is all that matters
        super(NoteValue.WHOLE, false);
        this.string = 0;
        this.fret = 0;
        this.instrument = Instrument.GUITAR;
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
    
    
    /**
     * Calculates which MIDI note number corresponds to this pitch
     * @return MIDI note number
     */
    public int midiNoteNum(){ 
        return 12 + pitchClass.ordinal() + 12 * octave;
    }
    
    /**
     * Calculates the number of half steps from this note to another
     * @param n a note
     * @return signed interval expressed in half steps
     */
    public int stepsTo(Note n){
        return n.midiNoteNum() - midiNoteNum();
    }
    
    public Note getFrontTie(){
        return frontTie;
    }

    public Note getBackTie(){
        return backTie;
    }

    public void tieFront(Note n){
        if(n != null){
            n.setBackTie(this);
            setFrontTie(n);
        }
    }

    public void tieBack(Note n){
        if(n != null){
            n.setFrontTie(this);
            setBackTie(n);
        }
    }

    protected void setFrontTie(Note n){
        this.frontTie = n;
    }

    protected void setBackTie(Note n){
        this.backTie = n;
    }

    public void untieFront(){
        if(frontTie != null){
            frontTie.setBackTie(null);
            setFrontTie(null);
        }
    }

    public void untieBack(){
        if(backTie != null){
            backTie.setFrontTie(null);
            setBackTie(null);
        }
    }

    public void untie(){
        untieFront();
        untieBack();
    }

    public String toString(){
        return pitchClass + (backTie == null ? "" : "-") + timingString() + (frontTie == null ? "" : "-");
    }
    /**
     * Whether two notes have the same pitch and location
     * @param n note for comparison
     * @return whether there's a match
     */
    public boolean equals(Note n){
        return string == n.getString() && 
            fret == n.getFret() &&
            octave == n.getOctave() &&
            pitchClass == n.getPitchClass();
    }
    
}
