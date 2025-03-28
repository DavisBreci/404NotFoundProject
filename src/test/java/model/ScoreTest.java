package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;
import javax.sound.midi.VoiceStatus;

import org.jfugue.player.EndOfTrackListener;
import org.jfugue.player.ManagedPlayer;
import org.jfugue.player.Player;
import org.jfugue.player.SequencerManager;
import org.jfugue.player.SynthesizerManager;
import org.junit.Assert;
import com.model.*;

public class ScoreTest {
    @Test
    public void testBankSwitching() throws MidiUnavailableException, InvalidMidiDataException{
        class BankSwitchTester implements Receiver, EndOfTrackListener{
            Instrument instrument;
            Synthesizer synth;
            Sequencer sequencer;
            boolean noteActive;

            BankSwitchTester(Instrument instrument, Synthesizer synth, Sequencer sequencer){
                this.instrument = instrument;
                this.synth = synth;
                this.sequencer = sequencer;
                noteActive = false;
            }

            public void send(MidiMessage message, long timeStamp) { // Called by the sequencer when it receives a MIDI event
                int status = message.getStatus();
                if(status != ShortMessage.NOTE_ON && status != ShortMessage.NOTE_OFF) return;
                if(noteActive){ noteActive = false; return;}
                noteActive = true;
                VoiceStatus [] voiceStatuses = synth.getVoiceStatus();
                for(VoiceStatus noteStatus : voiceStatuses){
                    if(noteStatus.active == false) continue;
                    assertEquals(instrument.bank, noteStatus.bank);
                    assertEquals(instrument.patch, noteStatus.program);
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
        assertEquals(" Rq G3s. Ri Rt ",tripletTest.toString(0, 1, false));
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
        assertTrue(tiedTest.get(0).isEmpty());
        assertTrue(tiedTest.get(1).isEmpty());
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
            }
        }
    }

    @Test
    public void testMidiToScoreWrongInstrument(){ // Out of range notes should be dropped
        Sequence rawMidi = DataLoader.loadSequence("DeanTown.mid");
        Score wrongInstrumentTest = Score.midiToScore(rawMidi, 0, Instrument.SOPRANO_UKULELE);
        for(Measure m : wrongInstrumentTest.getMeasures())
            assertTrue(m.isEmpty());
    }

}
