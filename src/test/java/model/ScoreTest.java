package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;
import javax.sound.midi.VoiceStatus;

import org.jfugue.player.EndOfTrackListener;
import org.jfugue.player.ManagedPlayer;
import org.jfugue.player.Player;
import org.jfugue.player.SequencerManager;
import org.jfugue.player.SynthesizerManager;
import org.junit.Assert;
import com.model.*;
/**
 * @author Christopher Ferguson
 */
public class ScoreTest {
    @Test
    public void testBankSwitching() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException{
        class BankSwitchTester implements Receiver, EndOfTrackListener{
            Instrument instrument;
            Synthesizer synth;
            Sequencer sequencer;
            boolean noteActive;
            boolean result;

            BankSwitchTester(Instrument instrument, Synthesizer synth, Sequencer sequencer){
                this.instrument = instrument;
                this.synth = synth;
                this.sequencer = sequencer;
                noteActive = false;
                result = false;
            }

            public void send(MidiMessage message, long timeStamp) { // Called by the sequencer when it receives a MIDI event
                int status = message.getStatus();
                if(status != ShortMessage.NOTE_ON && status != ShortMessage.NOTE_OFF) return;
                if(noteActive){ noteActive = false; return;}
                noteActive = true;
                VoiceStatus [] voiceStatuses = synth.getVoiceStatus();
                for(VoiceStatus noteStatus : voiceStatuses){
                    if(noteStatus.active == false) continue;
                    if(instrument.bank == noteStatus.bank && instrument.patch == noteStatus.program)
                        result = true;
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
        Thread.sleep(1000);
        assertTrue(tester.result);
    }   

    @Test
    public void testGetIdealStringAllBusy(){
        Instrument instrument = Instrument.FRETLESS_BASS;
        Chord busyNotes = new Chord(NoteValue.WHOLE, false, instrument);
        for(int i = 0; i < instrument.tuning.length; i++)
            busyNotes.put(instrument.tuning[i], i);
        Note unplaceable = new Note(PitchClass.C, 4);
        int idealString = Score.getIdealString(busyNotes, instrument.tuning[0], unplaceable);
        assertEquals(-1, idealString);
    }

    @Test 
    public void testGetIdealStringOutOfRange(){
        Instrument instrument = Instrument.SOPRANO_UKULELE;
        Chord busyNotes = new Chord(NoteValue.WHOLE, false, instrument);
        Note tooLow = new Note(PitchClass.B, 3); // One note lower than lowest uke note
        int idealString = Score.getIdealString(busyNotes, instrument.tuning[0], tooLow);
        assertEquals(-1, idealString);
    }

    @Test
    public void testGetIdealStringScale(){
        Instrument instrument = Instrument.GUITAR;
        Note [] gMajScale = {
            new Note(PitchClass.G, 2),
            new Note(PitchClass.A, 2),
            new Note(PitchClass.B, 2),
            new Note(PitchClass.C, 3),
            new Note(PitchClass.D, 3),
            new Note(PitchClass.E, 3),
            new Note(PitchClass.Gb, 3),
            new Note(PitchClass.G, 3)
        };

        int [] idealStrings = new int [gMajScale.length];
        Chord busyNotes = new Chord(NoteValue.WHOLE, false, instrument);
        Note prevNote = instrument.tuning[0];
        for(int i = 0; i < idealStrings.length; i++){
            idealStrings[i] = Score.getIdealString(busyNotes, prevNote, gMajScale[i]);
            prevNote = gMajScale[i];
        }

        assertEquals(0, idealStrings[0]);
        assertEquals(1, idealStrings[1]);
        assertEquals(1, idealStrings[2]);
        assertEquals(1, idealStrings[3]);
        assertEquals(2, idealStrings[4]);
        assertEquals(2, idealStrings[5]);
        assertEquals(2, idealStrings[6]);
        assertEquals(3, idealStrings[7]);
    }

    @Test
    public void getIdealStringJazzChord(){
        Instrument instrument = Instrument.ELECTRIC_JAZZ_GUITAR;
        Note [] fMaj7add11 = {
            new Note(PitchClass.F, 2),
            new Note(PitchClass.C, 3),
            new Note(PitchClass.F, 3),
            new Note(PitchClass.A, 3),
            new Note(PitchClass.B, 3),
            new Note(PitchClass.E, 4)
        };

        int [] idealStrings = new int [instrument.tuning.length];
        Chord busyNotes = new Chord(NoteValue.WHOLE, false, instrument);
        Note prevNote = instrument.tuning[0];
        for(int i = 0; i < idealStrings.length; i++){
            idealStrings[i] = Score.getIdealString(busyNotes, prevNote, fMaj7add11[i]);
            prevNote = fMaj7add11[i];
            assertEquals(i, idealStrings[i]);
            busyNotes.put(fMaj7add11[i], idealStrings[i]);
        }
    }

    @Test
    public void getIdealStringHighestNote(){
        Instrument instrument = Instrument.DISTORTION_GUITAR;
        Note ceiling = new Note(PitchClass.E, 6);
        Chord busyNotes = new Chord(NoteValue.WHOLE, false, instrument);
        int idealString = Score.getIdealString(busyNotes, instrument.tuning[0], ceiling);
        assertEquals(instrument.tuning.length - 1, idealString);
    }

    @Test
    public void testMidiToScoreTuplet(){ // Method doesn't support tuplets, but they shouldn't break the parser
        Sequence rawMidi = DataLoader.loadSequence("triplet_test.mid");
        Score tripletTest = Score.midiToScore(rawMidi, 0, Instrument.ELECTRIC_JAZZ_GUITAR);
        assertEquals(" Rq G3s- G3-x G3s- G3-x Rx G3s- G3-x ",tripletTest.toString(0, 1, false));
    }

    @Test 
    public void testMidiToScoreEOT(){ // Method shouldn't let the end of track event add an empty measure at the end
        Sequence rawMidi = DataLoader.loadSequence("Teen_Town.mid");
        Score eotTest = Score.midiToScore(rawMidi, 0, Instrument.FRETLESS_BASS);
        Measure endOfTrack = eotTest.get(eotTest.size() - 1);
        assertFalse(endOfTrack.isEmpty());
    }

    @Test
    public void testMidiToScoreTied(){ // Method should ignore notes that cross barlines
        Sequence rawMidi = DataLoader.loadSequence("tiedNoteTest.mid");
        Score tiedTest = Score.midiToScore(rawMidi, 0, Instrument.FRETLESS_BASS);
        assertNotNull(tiedTest.get(0).get(new Rational(1, 4)));
        assertNotNull(tiedTest.get(1).get(new Rational(0, 1)));
    }

    @Test
    public void testMidiToScoreTimeSignatures(){ // Score should reflect time signature changes in midi 
        Sequence rawMidi = DataLoader.loadSequence("Larks_II_GuitarOnly.mid");
        Score timeSignatureTest = Score.midiToScore(rawMidi, 0, Instrument.OVERDRIVEN_GUITAR);
        Rational four = new Rational(4);
        Rational five = new Rational(5, 4);
        Map<Integer, Rational> testTimeMap = Map.ofEntries( // Where the score changes time signature
            entry(1, five),
            entry(4, four),
            entry(5, five),
            entry(6, four),
            entry(9, five),
            entry(10, four),
            entry(13, five),
            entry(14, four),
            entry(17, five),
            entry(18, four)
        );
        Rational prev = new Rational(0, 1);
        Rational current;
        for(int i = 0; i < 19; i++){
            current = timeSignatureTest.get(i).getTimeSignature();
            if(prev.compareTo(current) != 0){
                assertEquals(testTimeMap.get(i + 1).toString(), current.toString());
                prev = current;
            } else assertEquals(prev.toString(), current.toString());
        }
    }

    @Test
    public void testMidiToScoreWrongInstrument(){ // Out of range notes should be dropped
        Sequence rawMidi = DataLoader.loadSequence("DeanTown.mid");
        Score wrongInstrumentTest = Score.midiToScore(rawMidi, 0, Instrument.SOPRANO_UKULELE);
        for(Measure m : wrongInstrumentTest.getMeasures())
            assertTrue(m.isEmpty());
    }

    @Test
    public void testTransposeStaccatoNegative(){ // Shouldn't allow negative octaves
        String staccato = "C0";
        assertEquals(staccato, Score.transposeStaccato(staccato, -1));
    }

    @Test
    public void testTransposeStaccatoTooHigh(){ // Shouldn't allow octaves greater than 9
        String staccato = "G9";
        assertEquals(staccato, Score.transposeStaccato(staccato, 1));
    }

    @Test
    public void testTransposeStaccatoChord(){
        String staccato = "(C4+E4+G4)q"; // Parenthesized chord, equivalent to C4q+E4q+G4q
        try{
            assertEquals("(C5+E5+G5)q", Score.transposeStaccato(staccato, 1));
        } catch(Exception e ){
            fail();
        }
    }

    @Test
    public void testTransposeStaccatoImplicit(){
        String staccato = "C"; // Implicitly C4, even though octave is unstated
        assertEquals("C5", Score.transposeStaccato(staccato, 1));
    }

    @Test 
    public void testTransposeStaccatoTied(){
        String staccato = "C4-q C4-q";
        assertEquals("C5q- C5-q", Score.transposeStaccato(staccato, 1));
    }

    @Test
    public void testAsChordArrayEmpty(){
        Score test = new Score(null, Instrument.GUITAR, 120);
        Chord [] chordArray = test.asChordArray();
        assertEquals(0, chordArray.length);
    }

    @Test
    public void testAsChordArrayTied(){ // Two chords where all notes are tied should count as two
        Instrument instrument = Instrument.GUITAR;
        Score test = new Score(null, instrument, 120);
        Chord a = new Chord(NoteValue.WHOLE, false, instrument);
        a.put(new Note(PitchClass.E, 2), 0);
        Chord b = new Chord(NoteValue.WHOLE, false, instrument);
        b.put(new Note(PitchClass.E, 2), 0);
        a.getNotes(false)[0].tieFront(b.getNotes(false)[0]);
        Measure measureA = new Measure(instrument, new Rational(4));
        Measure measureB = new Measure(instrument, new Rational(4));
        measureA.put(new Rational(0, 1), a);
        measureB.put(new Rational(0, 1), b);
        test.add(measureA);
        test.add(measureB);
        assertEquals(2, test.asChordArray().length);
    }

    @Test
    public void testAsChordArrayGap(){
        Instrument instrument = Instrument.GUITAR;
        Score test = new Score(null, instrument, 120);

        Measure measureOne = new Measure(instrument, new Rational(4));
        Chord chordOne = new Chord(NoteValue.WHOLE, false, instrument);
        chordOne.put(new Note(PitchClass.E, 2), 0);
        measureOne.put(new Rational(0, 1), chordOne);

        Measure measureTwo = new Measure(instrument, new Rational(4));
        Chord chordTwo = chordOne.deepCopy();
        measureTwo.put(new Rational(0, 1), chordTwo);

        test.add(measureOne);
        test.add(new Measure(instrument, new Rational(4)));
        test.add(measureTwo);

        Chord [] chordArray = test.asChordArray();
        assertEquals(2, chordArray.length);
        assertEquals(chordOne, chordArray[0]);
        assertEquals(chordTwo, chordArray[1]);
    }

    @Test
    public void testGetSequencePaddingOnly(){ // Creating a score with nothing but padding
        Rational padding = new Rational(17,16);
        Score test = new Score(null, Instrument.GUITAR, 120);
        Sequence result = test.getSequence(0, test.size(), padding, 0);
        Track t = result.getTracks()[0];
        MidiEvent eot = t.get(t.size() - 1);
        assertEquals(padding.toString(), MIDIHelper.midiQuantize(0, eot, result.getResolution()).toString());
    }

    @Test
    public void testGetSequenceInvalidSlice(){ // An invalid slice should yield an empty sequence
        Score test = Score.midiToScore(DataLoader.loadSequence("Teen_Town.mid"), 0, Instrument.FRETLESS_BASS);
        Sequence result = test.getSequence(test.size(), 0 , null, 0);
        Track t = result.getTracks()[0];
        MidiEvent eot = t.get(t.size() - 1);
        assertEquals(0, eot.getTick());
    }

    @Test
    public void testGetSequenceControllerEvents(){ // Make sure JFugue controller functions are reflected in the sequence
        Instrument instrument = Instrument.SOPRANO_UKULELE;
        int tempo = 120;
        Score test = new Score(null,instrument , 120);
        test.add(new Measure(instrument, new Rational(17, 16)));
        test.get(0).put(new Rational(0, 1), new Note(PitchClass.C, 4), 1);
        Sequence result = test.getSequence(0, test.size(), null, 1);
        Track t = result.getTracks()[0];
        byte [] msg;
        boolean hasMSB = false;
        boolean hasLSB = false;
        boolean hasTempo = false;
        boolean hasInstrument = false;
        for(int i = 0; i < t.size(); i++){
            MidiMessage current = t.get(i).getMessage();
            msg = current.getMessage();
            switch(current.getStatus()){
                case ShortMessage.CONTROL_CHANGE:
                    if(Byte.toUnsignedInt(msg[1]) == 0){
                        assertEquals(instrument.msb(), Byte.toUnsignedInt(msg[2]));
                        hasMSB = true;
                    } else if(Byte.toUnsignedInt(msg[1]) == 0x20){
                        assertEquals(instrument.lsb(), Byte.toUnsignedInt(msg[2]));
                        hasLSB = true;
                    }   
                    break;
                case ShortMessage.PROGRAM_CHANGE:
                    assertEquals(instrument.patch, Byte.toUnsignedInt(msg[1]));
                    hasInstrument = true;
                    break;
                case MetaMessage.META:
                    if(msg[1] != MIDIHelper.TEMPO) continue;
                    assertEquals(tempo, MIDIHelper.mpqToBpm(MIDIHelper.getLong(msg, 3, 3)));
                    hasTempo = true;
                    break;
                default:
                    ;
            }
        }
        assertTrue(hasMSB && hasLSB && hasInstrument && hasTempo);   
    }

    @Test
    public void testToStringIncludeBars(){
        Score s = new Score(null, Instrument.GUITAR, 120);
        Measure m = new Measure(Instrument.GUITAR, new Rational(5, 4));
        m.put(new Rational(0, 1), new Note(PitchClass.C, 3), 0);
        s.add(m);
        s.add(new Measure(Instrument.GUITAR, new Rational(4)));
        assertEquals(" C3w Rq | Rw |", s.toString(0, s.size(), true));
    }

    @Test 
    public void testToStringSansBars(){
        Score s = new Score(null, Instrument.GUITAR, 120);
        Measure m = new Measure(Instrument.GUITAR, new Rational(5, 4));
        m.put(new Rational(0, 1), new Note(PitchClass.C, 3), 0);
        s.add(m);
        s.add(new Measure(Instrument.GUITAR, new Rational(4)));
        assertEquals(" C3w Rq  Rw ", s.toString(0, s.size(), false));
    }

    @Test
    public void testToStringSlice(){
        Score s = new Score(null, Instrument.GUITAR, 120);
        Measure m = new Measure(Instrument.GUITAR, new Rational(5, 4));
        m.put(new Rational(0, 1), new Note(PitchClass.C, 3), 0);
        s.add(m);
        assertEquals(" C3w Rq |", s.toString(0, 1, true));
    }

    @Test
    public void testToStringNegativeSlice(){
        Score s = new Score(null, Instrument.GUITAR, 120);
        Measure m = new Measure(Instrument.GUITAR, new Rational(5, 4));
        m.put(new Rational(0, 1), new Note(PitchClass.C, 3), 0);
        s.add(m);
        assertEquals("", s.toString(-2, -1, true));
    }

    @Test
    public void testToStringZeroSlice(){
        Score s = new Score(null, Instrument.GUITAR, 120);
        Measure m = new Measure(Instrument.GUITAR, new Rational(5, 4));
        m.put(new Rational(0, 1), new Note(PitchClass.C, 3), 0);
        s.add(m);
        assertEquals("", s.toString(0, 0, true));
    }

    @Test
    public void testGetMeasureTablatureNoteOrder(){ // Make sure note order for tabs is descending. It's what musicians expect
        String s = Score.getMeasureTablature(new Measure(Instrument.ELECTRIC_CLEAN_GUITAR, new Rational(4)));
        char [] descendingOrder = {'E', 'B', 'G', 'D', 'A', 'E'};
        String [] strings = s.split("\n");
        for(int i = 0; i < descendingOrder.length; i++)
            assertEquals(descendingOrder[i], strings[i].charAt(0));
    }

    @Test
    public void testGetMeasureTablatureEmpty(){
        String s = Score.getMeasureTablature(new Measure(Instrument.ELECTRIC_CLEAN_GUITAR, new Rational(4)));
        char [] descendingOrder = {'E', 'B', 'G', 'D', 'A', 'E'};
        String [] strings = s.split("\n");
        for(int i = 0; i < descendingOrder.length; i++)
            assertEquals(descendingOrder[i] + " |-", strings[i]);
    }

    @Test
    public void testGetMeasureTablatureNull(){
        assertNull(Score.getMeasureTablature(null));
    }

    @Test
    public void testGetTablatureNumbering(){ // Make sure measures aren't counted from zero
        Score s = new Score(null, Instrument.GUITAR, 120);
        s.add(new Measure(Instrument.ELECTRIC_CLEAN_GUITAR, new Rational(4))); 
        assertEquals("Measure #1:", s.getTablature().split("\n")[0]);
    }
}
