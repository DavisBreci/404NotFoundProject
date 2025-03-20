package com.model;

import javax.sound.midi.Sequence;

import org.jfugue.player.Player;

public class TempTester {
    public static void main(String[] args) {
        Player p = new Player();
        Sequence rawMidi;
        //Loading a score from a JSON file
        Score gMajChord = DataLoader.getScoreFromID("3a6c83d2-2235-4fff-84dc-7ad6ec2dabf8");
        p.play(gMajChord.getSequence(0, gMajChord.size(), null, 1));
        // Loading a score w/out chords from a MIDI file
        rawMidi = Score.loadSequence("src\\main\\midi\\Teen_Town.mid");
        Score teenTown = Score.midiToScore(rawMidi, 0, Instrument.FRETLESS_BASS);
        p.play(teenTown.getSequence(0, teenTown.size(), null, 1));
        // Loading a chordal score from a MIDI file        
        rawMidi = Score.loadSequence("src\\main\\midi\\Larks_II_GuitarOnly.mid");
        Score ltia = Score.midiToScore(rawMidi, 0, Instrument.DISTORTION_GUITAR);
        p.play(ltia.getSequence(0, ltia.size(), null, 1));
    }
}
