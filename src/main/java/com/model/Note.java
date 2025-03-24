package com.model;
/**
 * @author Christopher Ferguson
 * Class that represents a note on a fretted instrument
 */
public class Note extends BarObj {
    private Instrument instrument;
    private int fret;
    private int string;
    private PitchClass pitchClass;
    private int octave;
    private Note frontTie;
    private Note backTie;
    /**
     * Creates a note with the given duration and pitch. 
     * If the note is not on the fretboard, its initial location will be on the open string of the lowest fret.
     * Otherwise, it will be on the lowest string possible. It is highly recommended that you manually set the note's 
     * location with restring() or setLocation() after constructing it.
     * @param value the base note duration
     * @param dotted whether the note is dotted
     * @param instrument the instrument on which the note is to be played
     * @param pitchClass the letter name of the note
     * @param octave which octave the note is in
     */
    public Note(NoteValue value, boolean dotted, Instrument instrument, PitchClass pitchClass, int octave){
        super(value, dotted);
        this.string = 0;
        this.fret = 0;
        this.instrument = instrument;
        this.pitchClass = pitchClass;
        for(int i = 0; i < instrument.tuning.length; i++){
            if(restring(i)) break;
        }
        setOctave(octave);
    }

    /**
     * Constructs a note without specifying its duration. This is useful when pitch content is all that matters
     * @param pitchClass the letter name of the note
     * @param octave which octave the note is in
     */
    public Note(PitchClass pitchClass, int octave){ // For situations where pitch content is all that matters
        super(NoteValue.WHOLE, false);
        this.string = 0;
        this.fret = 0;
        this.instrument = Instrument.GUITAR;
        this.pitchClass = pitchClass;
        setOctave(octave);
    }

     /**
     * Retrieves the instrument the note belongs to
     * @return the fretted instrument
     */
    public Instrument getInstrument(){
        return this.instrument;
    }

    /**
     * Changes the instrument. Be careful with this because you could end up with unplayable notes.
     * @param instrument // the new instrument
     */
    public void setInstrument(Instrument instrument){
        this.instrument = instrument;
    }

    /**
     * Creates a new note object with the same values as this
     * @return the copy
     */
    public Note deepCopy(){
        Note copy = new Note(value, dotted, instrument, pitchClass, octave);
        copy.setLocation(string, fret);
        copy.setFrontTie(frontTie);
        copy.setBackTie(backTie);
        copy.setInstrument(instrument);
        return copy;
    }
    /**
     * Attempts to change the location of the note
     * @param string the new string
     * @param fret the new fret
     * @return whether the change was successful
     */
    public boolean setLocation(int string, int fret){
        Note temp = new Note(pitchClass, octave);
        temp.restring(string);
        if(temp.restring(string) && temp.getFret() == fret){
            this.string = string;
            this.fret = fret;
            return true;
        }
        return false;
    }
    /**
     * Attempts to change the string of the note
     * @param string string to be changed to
     * @return whether the change was successful
     */
    public boolean restring(int string){
        if(string >= instrument.tuning.length || string < 0)
        return false;
        Note open = instrument.tuning[string]; // The lowest note on the string
        int fret = open.stepsTo(this);
        if(fret >= instrument.frets || fret < 0)
            return false;
        this.string = string;
        this.fret = fret;
        return true;
    }
    /**
     * Retrieves this note's string
     * @return this note's string
     */
    public int getString(){
        return string;
    }
    /**
     * Retrieves this note's fret
     * @return this note's fret
     */
    public int getFret(){
        return fret;
    }
    /**
     * Retrieves this note's octave
     * @return this note's octave
     */
    public int getOctave(){
        return octave;
    }
    /**
     * Retrieves this note's pitch class
     * @return this note's pitch class
     */
    public PitchClass getPitchClass(){
        return pitchClass;
    }

    /**
     * Changes the notes octave if the provided octave is valid.
     * @param octave the new octave
     */
    public void setOctave(int octave){
        if (octave >= 0 && octave <= 9)
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
     * Calculates which octave corresponds to the given MIDI note number
     * @param noteNum a MIDI note number
     * @return the octave number
     */
    public static int noteNumToOctave(int noteNum){
        return noteNum / 12 - 1;
    }

    /**
     * Calculates which pitch class corresponds to the given MIDI note number
     * @param noteNum a MIDI note number
     * @return the pitch class
     */
    public static PitchClass noteNumToPitchClass(int noteNum){
        return PitchClass.values()[noteNum - 12 * (noteNum / 12)];
    }

    /**
     * Calculates the number of half steps from this note to another
     * @param n a note
     * @return signed interval expressed in half steps
     */
    public int stepsTo(Note n){
        return n.midiNoteNum() - midiNoteNum();
    }

    /**
     * Retrieves a reference to this note's front tie
     * @return the tied note
     */
    public Note getFrontTie(){
        return frontTie;
    }
    /**
     * Retrieves a reference to this note's back tie
     * @return the tied note
     */
    public Note getBackTie(){
        return backTie;
    }
    /**
     * Attempts to tie the front of this note to the given note
     * @param n a note for tying
     */
    public Note tieFront(Note n){
        if(n != null){
            n.setBackTie(this);
            setFrontTie(n);
        }
        return this;
    }
    /**
     * Attempts to tie the back of this note to the given note
     * @param n a note for tying
     */
    public Note tieBack(Note n){
        if(n != null){
            n.setFrontTie(this);
            setBackTie(n);
        }
        return this;
    }
     /**
     * Modifies this note's front tie without checks
     */
    protected void setFrontTie(Note n){
        this.frontTie = n;
    }
     /**
     * Modifies this note's back tie without checks
     */
    protected void setBackTie(Note n){
        this.backTie = n;
    }
    /**
     * Removes this note's front tie, if it exists
     */
    public void untieFront(){
        if(frontTie != null){
            frontTie.setBackTie(null);
            setFrontTie(null);
        }
    }
    /**
     * Removes this note's back tie, if it exists
     */
    public void untieBack(){
        if(backTie != null){
            backTie.setFrontTie(null);
            setBackTie(null);
        }
    }
    /**
     * Removes this note's front and back ties, if they exist
     */
    public void untie(){
        untieFront();
        untieBack();
    }
    /**
     * Returns a Staccato representation of the note
     */
    public String toString(){
        return pitchClass + "" + octave +  (backTie == null ? "" : "-") + timingString() + (frontTie == null ? "" : "-");
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
    /**
     * Method that attempts to shift a note up or down depending on the given number of steps.
     * Notes can only be shifted along a string, and successful transposition will affect the fret number.
     * @param steps
     * @return whether the transposition was successful
     */
    public boolean transpose(int steps){
        int newFret = fret + steps;
        int newNoteNum = midiNoteNum() + steps;
        if(newFret < 0 || newFret >= instrument.frets){
            return false;
        } 
            
        octave = noteNumToOctave(newNoteNum);
        pitchClass = noteNumToPitchClass(newNoteNum);
        fret = newFret;
        return true;
    }


	/**
     * @author brenskrz
     * @return Whether the note is tied in the front
     */
	public boolean hasFrontTie() {
		if(frontTie != null) {
            return true;
        }
        return false;
	}

    /**
     * @author brenskrz
     * @return Whether the note is ties in the back
     */
	public boolean hasBackTie() {
		if(backTie != null) {
            return true;
        }
        return false;
	}
}
