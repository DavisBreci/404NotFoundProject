package com.model;

import java.util.ArrayList;
import java.util.Arrays;

import org.jfugue.pattern.Pattern;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

import org.jfugue.player.Player;
import org.jfugue.player.SynthesizerManager;

/**
 * Class representing tablature
 */
public class Score {
    private ID uuid;
    private Instrument instrument;
    private ArrayList<Measure> measures;
    private int tempo;

    public static void main(String[] args) {
        Instrument in = Instrument.ELECTRIC_MUTED_GUITAR;
        Rational timeSignature = new Rational("5/4");
        Score s = new Score(null, in, 120);
        // Measure m1 = new Measure(in, timeSignature);
        // m1.put(new Rational("0/1"), new Note(PitchClass.E, 2), 0);
        // s.add(m1);
        // System.out.println(s.toString(false));
        Measure m = new Measure(in, new Rational("16/4")); // We don't have a score yet 
        Chord powerChord = new Chord(NoteValue.EIGHTH, false, in);
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

        Player p = new Player();
        p.play(s.getSequence(0, s.size(), null));
       
    }
    
    /**
     * Constructs an object representing an emtpy sheet of tablature
     * @param uuid the score's UUID (make null to generate a new one)
     * @param instrument the score's MIDI instrument
     * @param tempo the score's tempo in beats per minute
     */
    public Score(String uuid, Instrument instrument, int tempo){
        measures = new ArrayList<Measure>();
        if(uuid == null)
            this.uuid = new ID();
        else
            this.uuid = new ID(uuid);
        this.instrument = instrument;
        this.tempo = tempo; 
    }

    /**
     * Adds a measure to the end of the Score
     * @param m measure to be added
     */
    public void add(Measure m){
        measures.add(m);
    }

    /**
     * Adds a measure at the specified location within the Score
     * @param index location for addition
     * @param m measure to be added
     */
    public void add(int index, Measure m){
        measures.add(index, m);
    }

    /**
     * Reports whether the score contains the given measure
     * @param m a measure
     * @return whether the score contains the given measure
     */
    public boolean contains(Measure m){
        return measures.contains(m);
    }

    /**
     * Retrieves the index of the given measure
     * @param m a measure
     * @return the given measure's index
     */
    public int indexOf(Measure m){
        return measures.indexOf(m);
    }

    /**
     * Attempts to remove the measure at the given index
     * @param index the target index
     * @return whether the removal was successful
     */
    public boolean remove(int index){
        if(index < 0 || index >= measures.size() - 1)
            return false;
        measures.remove(index);
        return true;
    }

    /**
     * Attempts to remove a measure from the score
     * @param m the measure to be removed
     * @return whether the removal was successful
     */
    public boolean remove(Measure m){
        return measures.remove(m);
    }
    
    /**
     * Retrieves the number of measures in the Score
     * @return the Score's size
     */
    public int size(){
        return measures.size();
    }

    /**
     * Returns a Staccato representation of the entire Score, barlines included
     */
    public String toString(){
        return toString(0, measures.size() - 1, true);
    }

     /**
     * Returns a Staccato representation of the given slice of the Score.
     * @param start the inclusive starting measure
     * @param end the exclusive ending measure
     * @param includeBars whether to include barlines
     */
    public String toString(int start, int end, boolean includeBars){
        if(start < end && start >= 0 && end >= 0 && start < measures.size() && end <= measures.size()){
            StringBuilder staccato = new StringBuilder();
            for(int i = start; i < end; i++)
                staccato.append(measures.get(i).toString(includeBars));
            return staccato.toString();
        } else return "";
    }

    /**
     * Builds a string of arguments that configures the JFugue synthesizer to match the score's instrument and tempo
     * @return
     */
    private String getControllerString(){
        return ":Controller(0," + instrument.msb() +
        ") :Controller(32," + instrument.lsb() + 
        ") I" + instrument.patch +
        " T" + tempo + " ";
    }

    /**
     * Retrieves the MIDI sequence that corresponds to the given slice of the score
     * @param start the inclusive starting measure
     * @param end the exclusive ending measure
     * @param extraPadding optional (can be null) rest duration added to end of score
     * @return the MIDI Sequence
     */
    public Sequence getSequence(int start, int end, Rational extraPadding){
        // Generate Staccato from the score
        String staccato = getControllerString() + toString(start, end, false);
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
        int rightPadTicks = (int)(s.getResolution() * rightPad.getNumerator() * 4.0 / rightPad.getDenominator());
        Track t = s.getTracks()[0];
        MidiEvent endOfTrack = t.get(t.size() - 1);
        endOfTrack.setTick(endOfTrack.getTick() + rightPadTicks); // Offset end of track event
        return s;
    }
}
