package com.model;

import java.util.ArrayList;
import java.util.Comparator;

import javax.sound.midi.Sequence;

import org.jfugue.player.Player;

public class TempTester {
    public static void main(String[] args) {
        Player p = new Player();
        
        //Loading a score from a JSON file
        Score gMajChord = DataLoader.getScoreFromID("a42d710f-afcb-4bce-bfd7-ecb43e6a5a89");
        p.play(gMajChord.getSequence(0, gMajChord.size(), null, 1));
        // // Loading a score w/out chords from a MIDI file
        Sequence rawMidi;
        rawMidi = Score.loadSequence("src\\main\\midi\\Teen_Town.mid");
        Score teenTown = Score.midiToScore(rawMidi, 0, Instrument.FRETLESS_BASS);
        p.play(teenTown.getSequence(0, teenTown.size(), null, 1));
        // // Loading a chordal score from a MIDI file        
        // rawMidi = Score.loadSequence("src\\main\\midi\\Larks_II_GuitarOnly.mid");
        // Score ltia = Score.midiToScore(rawMidi, 0, Instrument.DISTORTION_GUITAR);
        // p.play(ltia.getSequence(0, ltia.size(), null, 1));
        // ArrayList<User> test = new ArrayList<User>();
        // test.sort(new Comparator<User>(){
        //     @Override
        //     public int compare(User o1, User o2) {
        //         return o1.getFirstName().compareTo(o2.getFirstName());
        //     }
        // }
        // );
    }
}
