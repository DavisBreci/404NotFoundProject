package com.model;

import java.util.Arrays;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class ChristopherTester {
    public static void main(String[] args) {
        /* Creating and playing "Smoke on the Water" by Deep Purple with uRock  */
        Instrument instrument = Instrument.DISTORTION_GUITAR; // Choose our instrument
        Score smokeOnTheWater = new Score(null, instrument, 120); // Initialize the score

        for(int i = 0; i < 4; i++) // Add 4 measures of 4/4
            smokeOnTheWater.add(new Measure(instrument, new Rational(4)));

        Chord powerChord = new Chord(NoteValue.EIGHTH, false, instrument); // Create a power chord shape
        powerChord.put(new Note(PitchClass.D, 3), 1);
        powerChord.put(new Note(PitchClass.A, 3), 2);

        Measure m = smokeOnTheWater.get(0); // First measure
        m.put(new Rational("0/1"), powerChord.deepCopy()); // Add chord to measure
            powerChord.shiftString(1); // Move the chord shape around
            powerChord.transpose(-2);
        m.put(new Rational("1/4"), powerChord.deepCopy()); // repeat!
            powerChord.transpose(2);
        m.put(new Rational("2/4"), powerChord.deepCopy());
            powerChord.shiftString(-1);
        m.put(new Rational("7/8"), powerChord.deepCopy());
            powerChord.shiftString(1);
            powerChord.transpose(-2);
    
        m = smokeOnTheWater.get(1);  // Second measure
        m.put(new Rational("1/8"), powerChord.deepCopy());
            powerChord.transpose(3);
        m.put(new Rational("3/8"), powerChord.deepCopy());
            powerChord.transpose(-1);
        m.put(new Rational("1/2"), powerChord.deepCopy());
            powerChord.shiftString(-1);

        m = smokeOnTheWater.get(2); // Third measure
        m.put(new Rational("0/4"), powerChord.deepCopy());
            powerChord.shiftString(1);
            powerChord.transpose(-2);
        m.put(new Rational("1/4"), powerChord.deepCopy());
            powerChord.transpose(2);
        m.put(new Rational("1/2"), powerChord.deepCopy());
            powerChord.transpose(-2);
        m.put(new Rational("7/8"), powerChord.deepCopy());
            powerChord.shiftString(-1);
            powerChord.transpose(2);
        
        m = smokeOnTheWater.get(3); // Fourth measure
        m.put(new Rational("1/8"), powerChord.deepCopy());
        

        Player p = new Player(); // Play it!
        p.play(smokeOnTheWater.getSequence(0, smokeOnTheWater.size(), null, 0));
    }

}
