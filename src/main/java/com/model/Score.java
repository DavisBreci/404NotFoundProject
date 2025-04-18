/**
 * @author Christopher Ferguson
 */
package com.model;

import static org.junit.Assert.assertEquals;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.Map.Entry;
import java.util.UUID;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import javax.sound.midi.VoiceStatus;

import org.jfugue.player.EndOfTrackListener;
import org.jfugue.player.ManagedPlayer;
import org.jfugue.player.Player;
import org.jfugue.player.SequencerManager;
import org.jfugue.player.SynthesizerManager;

/**
 * Class representing tablature
 */
public class Score {
    public final String id;
    private Instrument instrument;
    private ArrayList<Measure> measures;
    private int tempo;
    
    /**
     * Constructs an object representing an emtpy sheet of tablature
     * @param id the score's UUID (make null to generate a new one)
     * @param instrument the score's MIDI instrument
     * @param tempo the score's tempo in beats per minute
     */

    public Score(String id, Instrument instrument, int tempo){
        measures = new ArrayList<Measure>();
        this.id = (id == null) ? UUID.randomUUID().toString(): id;
        this.instrument = instrument;
        setTempo(tempo);
    }
    
    public int getTempo(){
        return tempo;
    }

    public void setTempo(int tempo){
        if(tempo > 0)
            this.tempo = tempo;
    }
    /**
     * Retrieves the instrument the chord belongs to
     * @return the fretted instrument
     */
    public Instrument getInstrument(){
        return this.instrument;
    }

    /**
     * Adds a measure to the end of the Score
     * @param m measure to be added
     */
    public boolean add(Measure m){
        if(m != null)
            return measures.add(m);
        return false;
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
     * Retrieves the measure at the specified index
     * @param i
     * @return
     */
    public Measure get(int i){
        if(i < 0 || i >= size())
            return null;
        return measures.get(i);
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
        return toString(0, measures.size(), true);
    }

     /**
     * Returns a Staccato representation of the given slice of the Score.
     * @param start the inclusive starting measure
     * @param end the exclusive ending measure
     * @param includeBars whether to include barlines
     */
    public String toString(int start, int end, boolean includeBars){
        if(start <= end && start >= 0 && end >= 0 && start < measures.size() && end <= measures.size()){
            StringBuilder staccato = new StringBuilder();
            do {
                staccato.append(" " + measures.get(start).toString(includeBars));
                start++;
            } while (start < end);            
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
    public Sequence getSequence(int start, int end, Rational extraPadding, int octaves){
        // Generate Staccato from the score
        String staccato = getControllerString() + Score.transposeStaccato(toString(start, end, false), octaves);
        String [] tokens = staccato.split("\s"); // Separate the Staccato into events
        // Rational trailing rest duration
        Rational rightPad = (extraPadding == null) ? new Rational("0/1") : extraPadding.deepCopy(); 
        Rational newPad = new Rational("0/1");
        String [] components; // Symbols that make up a token
        for(int i = tokens.length - 1; i >= 0; i--){ // Find trailing rests by starting from last token
            if(Pattern.matches("R[w,h,q,i,s,t,x,o]?.", tokens[i])){ // See if a note is a rest
                components = tokens[i].split("");
                switch(components[1]){ // Add the appropriate amount to right padding
                    case "w":
                        newPad = new Rational("1/1");
                        break;
                    case "h":
                        newPad = new Rational("1/2");
                        break;
                    case "q":
                        newPad = new Rational("1/4");
                        break;
                    case "i":
                        newPad = new Rational("1/8");
                        break;
                    case "s":
                        newPad = new Rational("1/16");
                        break;
                    case "t":
                        newPad = new Rational("1/32");
                        break;
                    case "x":
                        newPad = new Rational("1/64");
                        break;
                    case "o":
                        newPad = new Rational("1/128");
                        break;
                    default:
                        break;
                }
                if(components.length == 3) // Add dotted length if necessary
                    newPad.times(new Rational("3/2"));
                rightPad.plus(newPad);
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

    /**
     * Creates a representation of the score as an array of chords without timing information
     * @return the chord array
     */
    public Chord [] asChordArray(){
        ArrayList<Chord> temp = new ArrayList<Chord>();
        for(Measure m : measures){
            Iterator<Entry<Rational, Chord>> c = m.chordIterator();
            while(c.hasNext())
                temp.add(c.next().getValue());              
        }
        Chord [] ret = new Chord[0];
        return temp.toArray(ret);
    }
    
    /**
     * Converts a MIDI Sequence into a Score object
     * @param src MIDI sequence for conversion
     * @param trackIndex the track of the sequence to be converted
     * @param instrument the instrument the score should use
     * @return the score
     */
    public static Score midiToScore(Sequence src, int trackIndex, Instrument instrument){ 
        class MemoEntry {
            MidiEvent noteEvent;
            int measureIndex;
            Rational offset;
            MemoEntry(MidiEvent noteEvent, int measureIndex, Rational offset){
                this.noteEvent = noteEvent;
                this.measureIndex = measureIndex;
                this.offset = offset;
            }
            static MemoEntry [] initNoteMemo(){
                MemoEntry [] memo = new MemoEntry[MIDIHelper.MIDI_NOTE_RANGE];
                for(int i = 0; i < MIDIHelper.MIDI_NOTE_RANGE; i++) 
                        memo[i]= null;
                return memo;
            }
        }
        if(src == null) return null;
		if(trackIndex < 0 || trackIndex >= src.getTracks().length) return null; 
		/* MIDI properties */
            final int resolution = src.getResolution(); // Number of MIDI ticks in a quarter note
            MemoEntry [] noteMemo = MemoEntry.initNoteMemo(); // Used for matching note off and note on events
            Track track = src.getTracks()[trackIndex]; 
            MidiEvent currentEvent;
            long lastNoteOnTick = 0;
		byte [] currentMessage;
		/* Note properties */
            Rational duration;
            Rational offset = new Rational("0/1");
            byte noteNum = 0;
            Score score = new Score(null, instrument, 120);
            // The duration of this chord doesn't matter bc it just indicates which strings are busy
            Chord currentChord = new Chord(NoteValue.WHOLE, false, instrument); // Never put in a measure
            Note prevNote = instrument.tuning[0]; // Starts on the instrument's lowest note
		/* Measure properties */
            Rational timeSignature = new Rational(4, 4);
            Measure currentMeasure = null;// The current measure
            // score.add(m);
            long barStart = 0; // MIDI tick the current bar starts at
            long barDuration = resolution * 4; // Length of current bar in ticks
            long barEnd = barStart; // MIDI tick the current bar ends at
            long barsLept = 0; // Used when note being processed doesn't start at measure beginning
        /* Variables for bite() */
            SimpleEntry<Rational, Note> prevBite;
            Note backTie;
            Rational remainder;
            int biteMeasure;   
            Rational biteOffset;
            PitchClass pitch;
            int octave;
            int string;
		for(int i = 0; i < track.size(); i++) {
			currentEvent = track.get(i);
			currentMessage = currentEvent.getMessage().getMessage();
   
            if (currentEvent.getTick() >= barEnd && !MIDIHelper.isEOT(currentEvent)){
            	barsLept = (currentEvent.getTick() - barEnd)/barDuration + 1;
                barStart = barEnd + (barsLept - 1) * barDuration;
                for(int j = 0; j < barsLept; j++)
                    score.add(new Measure(instrument, timeSignature));
                currentMeasure = score.get(score.size() - 1);
            }
			
            if(currentMessage[0] == MIDIHelper.META){
				if(currentMessage[1] == MIDIHelper.TIME_SIGNATURE) {
                    timeSignature = MIDIHelper.getTimeSignature(currentMessage);
                    barDuration = MIDIHelper.getBarDuration(resolution, timeSignature);
                    currentMeasure.setTimeSignature(timeSignature);
				} else if(currentMessage[1] == MIDIHelper.TEMPO) { // Please avoid MIDI files with tempo changes
					score.setTempo(MIDIHelper.mpqToBpm(MIDIHelper.getLong(currentMessage, 3, 3)));
			    }
            }

			barEnd = barStart + barDuration;

			if(MIDIHelper.isNoteOn(currentMessage[0]) || MIDIHelper.isNoteOff(currentMessage[0])) {
				noteNum = currentMessage[1];
				if(noteMemo[noteNum] != null) { // Note off
					duration = MIDIHelper.midiQuantize(noteMemo[noteNum].noteEvent, currentEvent, resolution);
                    pitch = Note.noteNumToPitchClass(noteNum);
                    octave = Note.noteNumToOctave(noteNum);
                    string = getIdealString(currentChord, prevNote, new Note(pitch, octave));
                    
                    if(string == -1){
                        noteMemo[noteNum] = null;
                        continue;
                    } 

                    prevBite = null;
                    backTie = null;
                    remainder = duration.deepCopy();
                    biteMeasure = noteMemo[noteNum].measureIndex;
                    biteOffset = noteMemo[noteNum].offset;
                    prevBite = score.get(biteMeasure - 1).bite(backTie, biteOffset , pitch, octave, remainder, string);
                    
                    if(prevBite != null && !prevBite.getKey().isZero()){
                        biteOffset = new Rational(0, 1);
                        backTie = prevBite.getValue();
                        remainder = prevBite.getKey();
                        do{
                            prevBite = score.get(biteMeasure).bite(backTie, biteOffset , pitch, octave, remainder, string);
                            if(prevBite == null) break;
                            backTie = prevBite.getValue();
                            remainder = prevBite.getKey();
                            biteMeasure++;
                        } while(!prevBite.getKey().isZero());
                    } 
                    
                    currentChord.put(new Note(pitch, octave), string);
                    noteMemo[noteNum] = null;
				} else { // Note on
                    if(currentEvent.getTick() != lastNoteOnTick){ // On different chord 
                        currentChord.clear();
                        lastNoteOnTick = currentEvent.getTick();
                        offset = MIDIHelper.midiQuantize(barStart, currentEvent, resolution);
                    } 
					noteMemo[noteNum] = new MemoEntry(currentEvent, score.size(), offset.deepCopy());
				}
			}
		}
        return score;
    }

    public static void main(String[] args)  {
        Score s = Score.midiToScore(DataLoader.loadSequence("Teen_Town.mid"), 0, Instrument.FRETLESS_BASS);
        Player p = new Player();
        System.out.println(s);
        p.play(s.getSequence(0, s.size(), null, 1));
       
    }

    /**
     * Decides which string a note should be placed on. Returns -1 if the note can't be placed.
     * @param currentChord The chord the note should be added to
     * @param prevNote the algorithm will choose the fret that minimizes the distance to this note
     * @param currentNote the note to choose a string for
     * @return the best string to place the current note on
     */
    public static int getIdealString(Chord currentChord, Note prevNote, Note currentNote){
        int idealString = -1;
        double minSteps = Double.MAX_VALUE;
        double currentSteps = 0;
        Note [] openNotes = currentChord.getInstrument().tuning;
        Note [] notes = currentChord.getNotes(false); // method isn't destructive

        for(int i = 0; i < openNotes.length; i++){
            if(notes[i] != null) 
                continue; // String is busy
            currentSteps = openNotes[i].stepsTo(currentNote); // Prospect fret
            if(currentSteps < 0 || currentSteps > currentChord.getInstrument().frets)
                continue; // Note not on current string
            currentSteps -= (int)Math.abs(prevNote.getFret()); // Fret dist btwn previous note and current 
            if(i == prevNote.getString())
                currentSteps *= 1.5; // Disincentivize remaining on the same string
            if(currentSteps >= minSteps)
                continue; // This string isn't a better option
            minSteps = currentSteps;
            idealString = i;
        }

        return idealString;
    }

    /**
     * Transposes a staccato string by the given number of octaves
     * @param staccato JFugue staccato string
     * @param octaves octaves to transpose
     * @return transposed staccato string
     */
    public static String transposeStaccato(String staccato, int octaves){
        String [] tokens = staccato.split("\s");
        StringBuilder transposed = new StringBuilder();
        String [] chordComponents;
        String [] transposedChordComponents;
        String [] noteComponents;
        for(int i = 0; i < tokens.length; i++){
            if(tokens[i].contains("+")){ // Chord
                chordComponents = tokens[i].split("\\+");
                transposedChordComponents = new String [chordComponents.length];
                for(int j = 0; j < chordComponents.length; j++){
                    noteComponents = chordComponents[j].split("");
                    if(noteComponents[1].equals("b")){
                        noteComponents[2] = (Integer.parseInt(noteComponents[2]) + octaves) + "";
                    } else {
                        noteComponents[1] = (Integer.parseInt(noteComponents[1]) + octaves) + "";
                    }
                    transposedChordComponents[j] = String.join("", noteComponents);
                } 
                transposed.append(String.join("+", transposedChordComponents) + " ");
            } else{
                if(!Pattern.matches("[ABCDEFG]b?[0-9]-?[w,h,q,i,s,t,x,o]\\.?-?", tokens[i])){
                    transposed.append(tokens[i]+ " ");
                    continue;
                }
                noteComponents = tokens[i].split("");
                if(noteComponents[1].equals("b")){
                    noteComponents[2] = (Integer.parseInt(noteComponents[2]) + octaves) + "";
                } else {
                    noteComponents[1] = (Integer.parseInt(noteComponents[1]) + octaves) + "";
                }
                transposed.append(String.join("", noteComponents) + " ");
            }
        }
        return transposed.toString();
    }

    public ArrayList<Measure> getMeasures() {
        return new ArrayList<>(measures);
    }

    /**
     * Returns a neatly formated string representing tabs for this score.
     * The returned tabs don't convey rhythmic information beyond sequence and simultaneity.
     * @return the tablature
     */
    public String getTablature(){
        StringBuilder tablature = new StringBuilder();
        for(int i = 0; i < measures.size(); i++){
            tablature.append("Measure #" + (i + 1) + ":\n");
            tablature.append(getMeasureTablature(measures.get(i)));
        }
        return tablature.toString();
    }

    /**
     * Creates tablature for a single measure
     * @param m the measure to be translated
     * @return the tablature
     */
    public static String getMeasureTablature(Measure m){
        if(m == null) return null;
        StringBuilder tablature = new StringBuilder();
        Note n;
        String toAppend;
        Note [] tuning = m.getInstrument().tuning;
        for(int i =  tuning.length - 1; i >= 0 ; i--){
            tablature.append(tuning[i].getPitchClass() + " |-");
            for(Chord c : m.getChords()){
                n = c.getNotes(false)[i];
                if(n == null)
                    toAppend = "---";
                else {
                    if(n.getFret() < 10)
                        toAppend = n.getFret() + "--";
                    else
                        toAppend = n.getFret() + "-";
                }
                tablature.append(toAppend);
            }
            tablature.append("\n");
        }
        return tablature.toString();
    }

    

    
}
