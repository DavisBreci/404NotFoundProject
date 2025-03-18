package com.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;
import org.jfugue.player.Player;

/**
 * Class representing tablature
 */
public class Score {
    private ID uuid;
    private Instrument instrument;
    private ArrayList<Measure> measures;
    private int tempo;

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
        // p.play(smokeOnTheWater.getSequence(0, smokeOnTheWater.size(), null));
        // System.out.println(
        //    Arrays.toString(smokeOnTheWater.asChordArray())
        // );
        Score.midiToScore(
            loadSequence("C:\\Users\\CTFer\\eclipse-workspace\\MIDIExperiment\\src\\MIDI\\Alphys.mid"), 0
        );
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
    
    private static Sequence loadSequence(String filename){ // For testing only. To be moved to dataLoader
		Sequence loadedSequence = null;
		try {
			loadedSequence = MidiSystem.getSequence(new File(filename));
		} catch(IOException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
		return loadedSequence;
	}

    public static Score midiToScore(Sequence src, int trackIndex, Instrument instrument){
        if(src == null) return null;
		if(trackIndex < 0 || trackIndex >= src.getTracks().length) return null; 
		// MIDI properties
		final int resolution = src.getResolution();
		MidiEvent [] noteMemo = initNoteMemo();
		Track track = src.getTracks()[trackIndex]; 
		MidiEvent currentEvent;
		byte [] currentMessage;
		// Note properties
		// TODO: Note previousNote = null;
		Rational duration;
		Rational offset;
		byte noteNum = 0;
		// Measure properties
		ArrayList<Measure> measures = new ArrayList<Measure>();
		// Measure m = new Measure(); 
		Rational timeSignature = new Rational(4, 4);
		int barCount = 1;
		long tempo = 0;
		long barStart = 0;
		long barDuration = resolution * 4;
		long barEnd = barDuration;
		long barsLept = 0;
		
		for(int i = 0; i < track.size(); i++) {
			currentEvent = track.get(i);
			currentMessage = currentEvent.getMessage().getMessage();
			if(currentEvent.getTick() == barEnd){
                barStart = barEnd;
                barCount++;
                /* TODO:
                 * Add existing measure to score
                 * construct new measure
                 */
            } else if (currentEvent.getTick() > barEnd){
            	barsLept = ((currentEvent.getTick() - barStart)/barDuration);
            	barCount += barsLept;
                barStart += barsLept * barDuration;
                // TODO: Add existing measure to score and any empty measures that we skipped over
            }
			if(currentMessage[0] == MIDIHelper.META){
				if(currentMessage[1] == MIDIHelper.TIME_SIGNATURE) {
                    timeSignature.setNumerator(Byte.toUnsignedInt(currentMessage[3]));
                    timeSignature.setDenominator(2 << (Byte.toUnsignedInt(currentMessage[4]) - 1)); // Power of 2
                    barStart = currentEvent.getTick();
                    barDuration = MIDIHelper.getBarDuration(resolution, timeSignature);
				} else if(currentMessage[1] == MIDIHelper.TEMPO) {
					tempo = MIDIHelper.getLong(currentMessage, 3, 3);
				}
				/* TODO:
                 * Add existing measure to score
                 * construct new measure
                 */
			} else if(MIDIHelper.isNoteOn(currentMessage[0]) || MIDIHelper.isNoteOff(currentMessage[0])) {
				noteNum = currentMessage[1];
				if(noteMemo[noteNum] != null) {
					System.out.println("\tNote off event detected...");
					// Calculate rational duration
                    // midiQuantize(noteMemo[noteNum], currentEvent, resolution)
					duration = MIDIHelper.midiQuantize(noteMemo[noteNum], currentEvent, resolution);
					// Calculate rational offset
					offset = MIDIHelper.midiQuantize(barStart, noteMemo[noteNum], resolution);
					// Get pitch class and octave
					System.out.print(
							Note.noteNumToPitchClass(noteNum) + "" + Note.noteNumToOctave(noteNum) +
							" of length " + duration + 
							" at an offset of " + offset + 
							" from the start of bar " + barCount + "\n"
							);
					// Calculate fret to use
					// Add note to measure
					
					noteMemo[noteNum] = null;
				} else {
					System.out.println("\tNote on event detected...");
					noteMemo[noteNum] = currentEvent;
				}
			}
			barEnd = barStart + barDuration;
		}
		// In case trailing fermata creates erroneous trailing measure
		// TODO: if (score.getLastMeasure().isEmpty()) score.delete(lastMeasure);
        return null;
    }

    private static MidiEvent [] initNoteMemo(){
		MidiEvent [] memo = new MidiEvent[MIDIHelper.MIDI_NOTE_RANGE];
		for(int i = 0; i < MIDIHelper.MIDI_NOTE_RANGE; i++) 
				memo[i]= null;
		return memo;
	}
}
