package com.model;

import java.util.Arrays;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class ChristopherTester {
    public static void main(String[] args) {
        Chord gmaj = new Chord(NoteValue.WHOLE, false, Instrument.GUITAR);
        
        gmaj.put(new Note(PitchClass.G, 2), 0);
        gmaj.put(new Note(PitchClass.B, 2), 1);
        gmaj.put(new Note(PitchClass.D, 3), 2);
        gmaj.put(new Note(PitchClass.G, 3), 3);
        gmaj.put(new Note(PitchClass.B, 3), 4);
        gmaj.put(new Note(PitchClass.G, 4), 5);
        
        System.out.println(gmaj);

        for(Note n : gmaj.getNotes())
            if(n != null)
                System.out.println(n + " @ " + "(" + n.getString() + ", "+ n.getFret() + ")");

        Player p = new Player();
        Pattern patty = new Pattern(gmaj.toString());
        patty.setInstrument("Guitar");
        p.play(patty);
    }

}
