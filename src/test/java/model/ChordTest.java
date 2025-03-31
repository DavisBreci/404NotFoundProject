package model;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.model.Chord;
import com.model.Instrument;
import com.model.Note;
import com.model.NoteValue;
import com.model.PitchClass;

public class ChordTest {

    @Test
    public void testGetNotesDeepCopy(){
        Note n = new Note(PitchClass.E, 2);
        Chord c = new Chord(NoteValue.WHOLE, false, Instrument.GUITAR);
        c.put(n, 0);
        assertTrue(n != c.getNotes(true)[0]);
    }

    @Test
    public void testGetNotesShallowCopy(){
        Note n = new Note(PitchClass.E, 2);
        Chord c = new Chord(NoteValue.WHOLE, false, Instrument.GUITAR);
        c.put(n, 0);
        assertTrue(n == c.getNotes(false)[0]);
    }

    @Test 
    public void testDeepCopy(){
        Note n = new Note(PitchClass.E, 2);
        Chord c = new Chord(NoteValue.WHOLE, false, Instrument.GUITAR);
        c.put(n, 0);
        Chord cc = c.deepCopy();
        assertTrue(c != cc);
        assertTrue(n != cc.getNotes(false)[0]);
    }

    @Test
    public void testTransposeTooLow(){
        Chord c = new Chord(NoteValue.WHOLE, false, Instrument.DISTORTION_GUITAR);
        c.put(new Note(PitchClass.E, 2), 0);
        c.put(new Note(PitchClass.A, 2), 1);
        assertFalse(c.transpose(-1));
    }

    @Test
    public void testTransposeTooHigh(){
        Chord c = new Chord(NoteValue.WHOLE, false, Instrument.DISTORTION_GUITAR);
        c.put(new Note(PitchClass.Eb, 4), 0);
        c.put(new Note(PitchClass.Ab, 4), 1);
        assertFalse(c.transpose(1));
    }

    @Test
    public void testTransposeMixedValidity(){
        Chord c = new Chord(NoteValue.WHOLE, false, Instrument.DISTORTION_GUITAR);
        c.put(new Note(PitchClass.Eb, 4), 0);
        c.put(new Note(PitchClass.G, 4), 1);
        c.put(new Note(PitchClass.B, 4), 2);
        c.put(new Note(PitchClass.Eb, 5), 3);
        c.put(new Note(PitchClass.Gb, 5), 4);
        c.put(new Note(PitchClass.Bb, 5), 5);
        assertFalse(c.transpose(1));
    }

    @Test
    public void testTransposeEmpty(){
        assertTrue(new Chord(NoteValue.WHOLE, false, Instrument.DISTORTION_GUITAR).transpose(2));
    }

    @Test 
    public void testTransposeNormal(){
        Chord c = new Chord(NoteValue.WHOLE, false, Instrument.ELECTRIC_CLEAN_GUITAR);

        Note [] fMaj7add11 = {
            new Note(PitchClass.F, 2),
            new Note(PitchClass.C, 3),
            new Note(PitchClass.F, 3),
            new Note(PitchClass.A, 3),
            new Note(PitchClass.B, 3),
            new Note(PitchClass.E, 4)
        };

        for(int i = 0; i < fMaj7add11.length; i++)
            c.put(fMaj7add11[i].deepCopy(), i);

        assertTrue(c.transpose(6));
        Note [] additions = c.getNotes(false);

        for(int i = 0; i < fMaj7add11.length; i++)
            assertEquals(6, fMaj7add11[i].stepsTo(additions[i]));
    }

    @Test
    public void testShiftStringFullChord(){
        Instrument instrument = Instrument.GUITAR;
        Chord c = new Chord(NoteValue.WHOLE, false, instrument);
        for(int i = 0; i < instrument.tuning.length; i++)
            c.put(instrument.tuning[i].deepCopy(),i);
        assertFalse(c.shiftString(1));
        assertFalse(c.shiftString(-1));
    }

    @Test
    public void testShiftStringTooLow(){
        Instrument instrument = Instrument.GUITAR;
        Chord c = new Chord(NoteValue.WHOLE, false, instrument);
        for(int i = 2; i < instrument.tuning.length - 2; i++)
            c.put(instrument.tuning[i].deepCopy(),i);
        assertFalse(c.shiftString(-3));
    }

    @Test
    public void testShiftStringTooFar(){
        Instrument instrument = Instrument.GUITAR;
        Chord c = new Chord(NoteValue.WHOLE, false, instrument);
        for(int i = 2; i < instrument.tuning.length - 1; i++)
            c.put(instrument.tuning[i].deepCopy(),i);
        assertFalse(c.shiftString(2));
    }
    
    @Test
    public void testShiftStringEmpty(){
        assertTrue(new Chord(NoteValue.WHOLE, false, Instrument.GUITAR).shiftString(Integer.MAX_VALUE));
    }

    @Test
    public void testShiftStringNormal(){
        Instrument instrument = Instrument.GUITAR;
        Chord c = new Chord(NoteValue.WHOLE, false, instrument);
        c.put(instrument.tuning[0].deepCopy(), 0);
        c.put(instrument.tuning[1].deepCopy(), 1);
        c.shiftString(2);
        assertNull(c.getNotes(true)[0]);
        assertNull(c.getNotes(true)[1]);
        assertEquals(instrument.tuning[2].toString(), c.getNotes(true)[2].toString());
        assertEquals(instrument.tuning[3].toString(), c.getNotes(true)[3].toString());
    }

    @Test
    public void testPutInstrumentMutation(){ // Putting a note in a chord of a different instrument should change the note's instrument
        Note n = new Note(PitchClass.C, 4); // Note is guitar note by default
        assertEquals(Instrument.GUITAR, n.getInstrument());
        Chord c = new Chord(NoteValue.WHOLE, false, Instrument.SOPRANO_UKULELE);
        c.put(n, 1);
        assertEquals(Instrument.SOPRANO_UKULELE, n.getInstrument());
    }

    @Test
    public void testPutNoteValueMutation(){ // Putting a note in a chord with a different value should change the note's value
        Note n = new Note(PitchClass.C, 4);
        assertEquals(NoteValue.WHOLE, n.getValue());
        Chord c = new Chord(NoteValue.EIGHTH, true, Instrument.GUITAR);
        c.put(n, 1);
        assertEquals(NoteValue.EIGHTH, n.getValue());
        assertTrue(n.isDotted());
    }

    @Test
    public void testPutWrongString(){
        Note n = new Note(PitchClass.C, 4); // C4 is a valid ukulele note
        Chord c = new Chord(NoteValue.EIGHTH, true, Instrument.SOPRANO_UKULELE);
        assertFalse(c.put(n, 3)); //  but it isn't on string 4
    }

    @Test
    public void testPutUnplayableNote(){
        Note n = new Note(PitchClass.E, 2); // Not playable on uke
        Chord c = new Chord(NoteValue.WHOLE, false, Instrument.SOPRANO_UKULELE);
        assertFalse(c.put(n, 0));
    }

    @Test
    public void testPutNull(){
        Chord c = new Chord(NoteValue.WHOLE, false, Instrument.SOPRANO_UKULELE);
        assertFalse(c.put(null, 0));
    }
    
}
