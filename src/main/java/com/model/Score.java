package com.model;

import java.util.ArrayList;
import java.util.Arrays;

import org.jfugue.pattern.Pattern;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;

import org.jfugue.player.Player;

public class Score {
    private ID uuid;
    private Instrument instrument;
    private ArrayList<Measure> measures;
    private int tempo;

    public static void main(String[] args) {
        Instrument in = Instrument.DISTORTION_GUITAR;
       
        Rational timeSignature = new Rational("5/4");
        Score s = new Score(null, in, 280);
        // Measure m1 = new Measure(in, timeSignature);
        // m1.put(new Rational("0/1"), new Note(PitchClass.E, 2), 0);
        // s.add(m1);
        // System.out.println(s.toString(false));
        Measure m = new Measure(Instrument.GUITAR, new Rational("16/4")); // We don't have a score yet 
        Chord powerChord = new Chord(NoteValue.EIGHTH, false, Instrument.GUITAR);
        powerChord.put(new Note(PitchClass.D, 3), 1);
        powerChord.put(new Note(PitchClass.A, 3), 2);
        m.put(new Rational("0/1"), powerChord.deepCopy());
            powerChord.shiftString(1);
            powerChord.transpose(-2);
        m.put(new Rational("1/4"), powerChord.deepCopy());
            powerChord.transpose(2);
        m.put(new Rational("2/4"), powerChord.deepCopy());
            powerChord.shiftString(-1);
        m.put(new Rational("7/8"), powerChord.deepCopy());
            powerChord.shiftString(1);
            powerChord.transpose(-2);
        m.put(new Rational("9/8"), powerChord.deepCopy());
            powerChord.transpose(3);
        m.put(new Rational("11/8"), powerChord.deepCopy());
            powerChord.transpose(-1);
        m.put(new Rational("12/8"), powerChord.deepCopy());
            powerChord.shiftString(-1);
        m.put(new Rational("16/8"), powerChord.deepCopy());
            powerChord.shiftString(1);
            powerChord.transpose(-2);
        m.put(new Rational("18/8"), powerChord.deepCopy());
            powerChord.transpose(2);
        m.put(new Rational("20/8"), powerChord.deepCopy());
            powerChord.transpose(-2);
        m.put(new Rational("23/8"), powerChord.deepCopy());
            powerChord.shiftString(-1);
            powerChord.transpose(2);
        m.put(new Rational("25/8"), powerChord.deepCopy());
        s.add(m);
        try {
            Player p = new Player();
            p.play(s.getSequence(null));
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }

        
    }

    public Score(String uuid, Instrument instrument, int tempo){
        measures = new ArrayList<Measure>();
        if(uuid == null)
            this.uuid = new ID();
        else
            this.uuid = new ID(uuid);
        this.instrument = instrument;
        this.tempo = tempo; 
    }

    public void add(Measure m){
        measures.add(m);
    }

    public void add(int index, Measure m){
        measures.add(index, m);
    }

    public boolean contains(Measure m){
        return  measures.contains(m);
    }

    public int indexOf(Measure m){
        return 0;
    }

    public boolean remove(int index){
        return true;
    }

    public boolean remove(Measure m){
        return true;
    }
    
    public int size(){
        return 0;
    }

    public String toString(){
        return "";
    }

    public String toString(boolean includeBars){
        StringBuilder staccato = new StringBuilder();
        for(Measure m : measures)
            staccato.append(m.toString(includeBars));
        return staccato.toString();
    }

    public Sequence getSequence(Rational extraPadding) throws MidiUnavailableException{
        // Generate Staccato from the score
        // System.out.println((new Player()).getStaccatoParser().g)
        String staccato = "I[" + instrument + "] T" + tempo + " " + toString(false);
        String [] tokens = staccato.split("\s"); // Separate the Staccato into events
        // Rational trailing rest duration
        Rational rightPad = (extraPadding == null) ? new Rational("0/1") : extraPadding.deepCopy(); 
        String [] components; // Symbols that make up a token
        for(int i = tokens.length - 1; i >= 0; i--){ // Find trailing rests by starting from last token
            if(java.util.regex.Pattern.matches("R[w,h,q,i,s,t,x,o]?.", tokens[i])){ // See if a note is a rest
                components = tokens[i].split("");
                switch(components[1]){ // Add the appropriate amount to right padding
                    case "w":
                        rightPad.plus(new Rational("1/1"));
                        break;
                    case "h":
                        rightPad.plus(new Rational("1/2"));
                        break;
                    case "q":
                        rightPad.plus(new Rational("1/4"));
                        break;
                    case "i":
                        rightPad.plus(new Rational("1/8"));
                        break;
                    case "s":
                        rightPad.plus(new Rational("1/16"));
                        break;
                    case "t":
                        rightPad.plus(new Rational("1/32"));
                        break;
                    case "x":
                        rightPad.plus(new Rational("1/64"));
                        break;
                    case "o":
                        rightPad.plus(new Rational("1/128"));
                        break;
                    default:
                        break;
                }
                if(components.length == 3) // Add dotted length if necessary
                    rightPad.times(new Rational("3/2"));
            } else {
                break; // If we run into a non-rest, then we've exhausted all the trailing rests
            }  
        }
        Player p = new Player();
        Sequence s = p.getSequence(staccato); // The end of track event still occurs at the last beat
        // Calculates how many midi ticks to offset the end of track event by
        // A sequence's resolution is how many midi ticks a quarter note lasts for
        int rightPadTicks = (int)(s.getResolution() * (rightPad.getNumerator() * (4.0 / rightPad.getDenominator())));
        Track t = s.getTracks()[0];
        MidiEvent endOfTrack = t.get(t.size() - 1);
        endOfTrack.setTick(endOfTrack.getTick() + rightPadTicks); // Offset end of track event
        return s;
    }
}
