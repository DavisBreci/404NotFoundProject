package com.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.sound.midi.Sequence;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class ChristopherTester {
    public static void main(String[] args) {
        Player p = new Player();
        Sequence rawMidi = DataLoader.loadSequence("IdBeAFoolRightNow.mid");
        // Score jj1 = Score.midiToScore(rawMidi, 0, Instrument.ELECTRIC_BASS_FINGER);
        // p.play(jj1.getSequence(0, jj1.size(), null, 1));
        rawMidi = DataLoader.loadSequence("WhatsGoinOn.mid");
        Score jj2 = Score.midiToScore(rawMidi, 0, Instrument.ELECTRIC_BASS_FINGER);
        rawMidi = DataLoader.loadSequence("IHeardItThroughTheGrapevine.mid");
        Score jj3 = Score.midiToScore(rawMidi, 0, Instrument.ELECTRIC_BASS_FINGER);
        // p.play(jj3.getSequence(0, jj3.size(), null, 1));
    }
}   
