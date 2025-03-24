package com.model;
/**
 * @author Christopher Ferguson
 * Enum that contains representations of fretted MIDI instruments
 */
public enum Instrument {
    GUITAR(0, 24, 24, 
        new Note(PitchClass.E, 2),
        new Note(PitchClass.A, 2),
        new Note(PitchClass.D, 3),
        new Note(PitchClass.G, 3),
        new Note(PitchClass.B, 3),
        new Note(PitchClass.E, 4) 
    ),
    STEEL_STRING_GUITAR(0, 25, GUITAR),
    ELECTRIC_JAZZ_GUITAR(0, 26, GUITAR),
    ELECTRIC_CLEAN_GUITAR(0, 27, GUITAR),
    ELECTRIC_MUTED_GUITAR(0, 28, GUITAR),
    OVERDRIVEN_GUITAR(0, 29, GUITAR),
    DISTORTION_GUITAR(0, 30, GUITAR),
    ACOUSTIC_BASS(0, 32, 24, 
        new Note(PitchClass.E, 1),
        new Note(PitchClass.A, 1),
        new Note(PitchClass.D, 2),
        new Note(PitchClass.G, 2)
    ),
    ELECTRIC_BASS_FINGER(0, 33, ACOUSTIC_BASS),
    ELECTRIC_BASS_PICK(0, 34, ACOUSTIC_BASS),
    FRETLESS_BASS(0, 35, ACOUSTIC_BASS),
    SLAP_BASS_1(0, 36, ACOUSTIC_BASS),
    SLAP_BASS_2(0, 37, ACOUSTIC_BASS),
    SOPRANO_UKULELE(1024, 24, 15,  // Currently non-functional
        new Note(PitchClass.G, 4),
        new Note(PitchClass.C, 4),
        new Note(PitchClass.E, 4),
        new Note(PitchClass.A, 4)
    );

    public final Note [] tuning; // Lowest to highest
    public final int frets;
    public final int bank;
    public final int patch;
    /**
     * Constructs a fretted MIDI instrument
     * @param bank the number of the instrument's containing soundbank
     * @param patch the instrument's numerical index within its bank
     * @param frets the number of frets the instrument has
     * @param tuning an array of notes that represents the instrument's tuning
     */
    private Instrument(int bank, int patch, int frets, Note ... tuning){
        this.bank = bank;
        this.patch = patch;
        this.frets = frets;
        for(Note n : tuning)
            n.setInstrument(this);
        this.tuning = tuning;
    }

    /**
     * This constructor lets you create a new instrument with the same instance variables as another
     * @param bank the number of the instrument's containing soundbank
     * @param patch the instrument's numerical index within its bank
     * @param template instrument to be copied
     */
    private Instrument(int bank, int patch, Instrument template){
        this.bank = bank;
        this.patch = patch;
        frets = template.frets;
        tuning = template.tuning;
    }

    /**
     * Returns the most significant byte of the instrument's bank number
     * @return
     */
    public int msb(){
        return bank >> 7;
    }

     /**
     * Returns the least significant byte of the instrument's bank number
     * @return
     */
    public int lsb(){
        return bank & 0x7F;
    }

}
