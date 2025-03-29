/**
 * @author Christopher Ferguson
 */
package com.model;

import static org.junit.Assert.assertEquals;

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
            Measure m = null; // The current measure
            int barCount = 1; // How many bars are in the piece
            long barStart = 0; // MIDI tick the current bar starts at
            long barDuration = resolution * 4; // Length of current bar in ticks
            long barEnd = barDuration; // MIDI tick the current bar ends at
            long barsLept = 0; // Used when note being processed doesn't start at measure beginning
		// TODO: Should be able to switch to new measure based on measure content
		for(int i = 0; i < track.size(); i++) {
			currentEvent = track.get(i);
			currentMessage = currentEvent.getMessage().getMessage();
            /*
                Before putting notes in the measure, we must establish the context.
             */ 
			if(currentEvent.getTick() == barEnd){ // TODO: Fix note off changing measure
                barStart = barEnd;
                barCount++;
                score.add(m);
                m = new Measure(instrument, timeSignature.deepCopy());
            } else if (currentEvent.getTick() > barEnd){
            	barsLept = ((currentEvent.getTick() - barStart)/barDuration);
            	barCount += barsLept;
                barStart += barsLept * barDuration;
                score.add(m);
                for(int j = 0; j < barsLept - 2; j++){
                    score.add(new Measure(instrument, timeSignature.deepCopy()));
                }
                m = new Measure(instrument, timeSignature);
            }
			if(currentMessage[0] == MIDIHelper.META){
				if(currentMessage[1] == MIDIHelper.TIME_SIGNATURE) {
                    timeSignature.setNumerator(Byte.toUnsignedInt(currentMessage[3]));
                    timeSignature.setDenominator(2 << (Byte.toUnsignedInt(currentMessage[4]) - 1)); // Power of 2
                    barStart = currentEvent.getTick();
                    barDuration = MIDIHelper.getBarDuration(resolution, timeSignature);
                    if(m == null){
                        m = new Measure(instrument, timeSignature);
                    } else{
                        m.setTimeSignature(timeSignature);
                    }    
				} else if(currentMessage[1] == MIDIHelper.TEMPO) { // Please avoid MIDI files with tempo changes
					score.setTempo(MIDIHelper.mpqToBpm(MIDIHelper.getLong(currentMessage, 3, 3)));
				}
			} else if(MIDIHelper.isNoteOn(currentMessage[0]) || MIDIHelper.isNoteOff(currentMessage[0])) {
				noteNum = currentMessage[1];
				if(noteMemo[noteNum] != null) { // Note off
					duration = MIDIHelper.midiQuantize(noteMemo[noteNum].noteEvent, currentEvent, resolution);
                    duration.times(new Rational(64 / duration.getDenominator()));
                    double lg = Math.log(duration.getNumerator())/ Math.log(2); // Index NoteValue via log2
                    // System.out.println("Barcount: " + barCount + " Duration: " + duration + " Offset: " + offset);
                    Note n = new Note(
                        NoteValue.values()[(int)lg],
                        lg % 1 != 0, // lg will be fractional if there's a dot
                        instrument,
                        Note.noteNumToPitchClass(noteNum), 
                        Note.noteNumToOctave(noteNum)
                    );
                    int string = getIdealString(currentChord, prevNote, n);
                    if(string == -1){
                        noteMemo[noteNum] = null;
                        continue;
                    }
                    Note tempNote = n.deepCopy(); // necessary because we're not changing the chord's duration
                    currentChord.put(tempNote, string);
                    n.setLocation(string, tempNote.getFret());
                    prevNote = n;
					m.put(noteMemo[noteNum].offset.deepCopy(), n, string); // Add note to measure
					noteMemo[noteNum] = null;
				} else { // Note on
                    if(currentEvent.getTick() != lastNoteOnTick){ // On different chord 
                        currentChord.clear();
                        lastNoteOnTick = currentEvent.getTick();
                        offset = MIDIHelper.midiQuantize(barStart, currentEvent, resolution);
                    } 
					noteMemo[noteNum] = new MemoEntry(currentEvent, barCount, offset.deepCopy());
				}
			}
			barEnd = barStart + barDuration;
		}
		// TODO: fix trailing fermata creates erroneous trailing measure 
        score.add(m);
        return score;
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
                if(!Pattern.matches("[ABCDEFG]b?[0-9][w,h,q,i,s,t,x,o]?\\.?", tokens[i])){
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

    public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException {
        testBankSwitching();
    }


     public static void testBankSwitching() throws MidiUnavailableException, InvalidMidiDataException{
        class BankSwitchTester implements Receiver, EndOfTrackListener{
            Instrument instrument;
            Synthesizer synth;
            Sequencer sequencer;
            boolean noteActive;
            boolean passedTest;

            BankSwitchTester(Instrument instrument, Synthesizer synth, Sequencer sequencer){
                this.instrument = instrument;
                this.synth = synth;
                this.sequencer = sequencer;
                noteActive = false;
                passedTest = false;
            }

            public void send(MidiMessage message, long timeStamp) { // Called by the sequencer when it receives a MIDI event
                int status = message.getStatus();
                if(status != ShortMessage.NOTE_ON && status != ShortMessage.NOTE_OFF) return;
                if(noteActive){ noteActive = false; return;}
                noteActive = true;
                VoiceStatus [] voiceStatuses = synth.getVoiceStatus();
                for(VoiceStatus noteStatus : voiceStatuses){
                    if(noteStatus.active == false) continue;
                    if(instrument.bank == noteStatus.bank && instrument.patch == noteStatus.program);
                        passedTest = true;
                }
            }
            
            public void close() {}

            @Override
            public void onEndOfTrack() {
                synth.close();
                sequencer.close();
            }
        }

        Instrument instrument = Instrument.SOPRANO_UKULELE;
        Synthesizer synth = SynthesizerManager.getInstance().getSynthesizer();
        Sequencer sequencer =  SequencerManager.getInstance().getSequencer();
        synth.open(); // Allow the synth to receive info
        sequencer.getTransmitters().get(0).setReceiver(synth.getReceiver()); // Manually connect sequencer to singleton synthesizer
        BankSwitchTester tester = new BankSwitchTester(instrument, synth, sequencer);
        sequencer.getTransmitter().setReceiver(tester); // Connect sequencer to tester
        SequencerManager.getInstance().addEndOfTrackListener(tester);
        Score testScore  = new Score(null, instrument, 120);
        Measure testMeasure = new Measure(instrument, new Rational(4));
        testMeasure.put(new Rational(0, 1), new Note(PitchClass.C, 4), 1);
        testScore.add(testMeasure);
        ManagedPlayer p = new ManagedPlayer();
        p.start(testScore.getSequence(0, testScore.size(), null, 1));
        
    }
}
