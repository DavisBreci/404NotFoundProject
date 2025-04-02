package model;

import org.junit.Test;

import com.model.BarObj;
import com.model.Chord;
import com.model.Instrument;
import com.model.Measure;
import com.model.Note;
import com.model.NoteValue;
import com.model.PitchClass;
import com.model.Rational;
import com.model.Rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;
import java.util.Iterator;

/**
 * @author Christopher Ferguson
 */
public class MeasureTest {

    @Test
    public void testCollidesPerfectOverlap(){
        Entry<Rational, Rest> test = new SimpleEntry<Rational, Rest>(new Rational(0, 1), new Rest(NoteValue.WHOLE, false));
        assertTrue(Measure.collides(test, test));
    }

    @Test
    public void testCollidesNesting(){
        Entry<Rational, Rest> a = new SimpleEntry<Rational, Rest>(new Rational(0, 1), new Rest(NoteValue.WHOLE, false));
        Entry<Rational, Rest> b = new SimpleEntry<Rational, Rest>(new Rational(1, 2), new Rest(NoteValue.EIGHTH, false));
        assertTrue(Measure.collides(a, b));
    }

    @Test
    public void testCollidesNull(){
       assertThrows(
        IllegalArgumentException.class, () ->  
        Measure.collides(null, null));
    }

    @Test
    public void testCollidesAdjacent(){
        Entry<Rational, Rest> a = new SimpleEntry<Rational, Rest>(new Rational(0, 1), new Rest(NoteValue.HALF, false));
        Entry<Rational, Rest> b = new SimpleEntry<Rational, Rest>(new Rational(1, 2), new Rest(NoteValue.HALF, false));
        assertFalse(Measure.collides(a, b));
    }

    @Test 
    public void testCollidesNegative(){
        Entry<Rational, Rest> a = new SimpleEntry<Rational, Rest>(new Rational(0, 1), new Rest(NoteValue.HALF, false));
        Entry<Rational, Rest> b = new SimpleEntry<Rational, Rest>(new Rational(-1, 2), new Rest(NoteValue.HALF, false));
        assertThrows(
        IllegalArgumentException.class, 
        () ->  Measure.collides(a, b));
    }

    @Test
    public void testBiteNoteCrossingBarline(){
        try{
            Instrument instrument = Instrument.ELECTRIC_JAZZ_GUITAR;
            Measure a = new Measure(instrument, new Rational(4));
            Measure b = new Measure(instrument, new Rational(4));
            Rational duration = BarObj.calcDuration(NoteValue.WHOLE, false);
            PitchClass p = PitchClass.E;
            int octave = 2;
            int string = 0;
            SimpleEntry<Rational, Note> residue = a.bite(null, new Rational(1, 2), p, octave, duration, string);
            b.bite(residue.getValue(), new Rational(0, 1), p, octave, residue.getKey(), string);
            Note nLeft = a.get(new Rational(1, 2)).getNotes(false)[string];
            Note nRight = b.get(new Rational(0, 1)).getNotes(false)[string];
            assert(nLeft.getDuration().compareTo(new Rational(1, 2)) == 0);
            assert(nRight.getDuration().compareTo(new Rational(1, 2)) == 0);
            assert(nLeft.getFrontTie() == nRight);
        } catch (Exception e){
            fail();
        }
        
    }

    @Test
    public void testBiteChordCrossingBarline(){
        Instrument instrument = Instrument.ELECTRIC_JAZZ_GUITAR;
        Measure a = new Measure(instrument, new Rational(4));
        Measure b = new Measure(instrument, new Rational(4));
        Rational duration = BarObj.calcDuration(NoteValue.WHOLE, false);
        Note [] fMaj7add11 = {
            new Note(PitchClass.F, 2),
            new Note(PitchClass.C, 3),
            new Note(PitchClass.F, 3),
            new Note(PitchClass.A, 3),
            new Note(PitchClass.B, 3),
            new Note(PitchClass.E, 4)
        };
        Note nLeft;
        Note nRight;
        for(int i = 0; i < fMaj7add11.length; i++){
            SimpleEntry<Rational, Note> residue = a.bite(null, new Rational(1, 2), fMaj7add11[i].getPitchClass(), fMaj7add11[i].getOctave(), duration, i);
            b.bite(residue.getValue(), new Rational(0, 1), fMaj7add11[i].getPitchClass(), fMaj7add11[i].getOctave(), residue.getKey(), i);
            nLeft = a.get(new Rational(1, 2)).getNotes(false)[i];
            nRight = b.get(new Rational(0, 1)).getNotes(false)[i];
            assert(nLeft.getDuration().compareTo(new Rational(1, 2)) == 0);
            assert(nRight.getDuration().compareTo(new Rational(1, 2)) == 0);
            assert(nLeft.getFrontTie() == nRight);
        }
    }

    @Test
    public void testBiteCollision(){ // Bite doesn't return null when a note can't be placed
        Instrument instrument = Instrument.GUITAR;
        Measure m = new Measure(instrument, new Rational(4));
        Note n = new Note(NoteValue.QUARTER, false, instrument, PitchClass.E, 2);
        m.put(new Rational(3, 4),n , 0);
        SimpleEntry<Rational, Note> residue = m.bite(null, new Rational(0, 1), PitchClass.F, 2, new Rational(4), 0);
        assertEquals(null, residue);
    }

    @Test
    public void testBiteNormal(){
        try{
        Instrument instrument = Instrument.GUITAR;
        Measure m = new Measure(instrument, new Rational(4));
        SimpleEntry<Rational, Note> firstResidue = m.bite(null, new Rational(0, 1), PitchClass.E, 2, new Rational(1, 2), 0);
        SimpleEntry<Rational, Note> secondResidue = m.bite(null, new Rational(1, 2), PitchClass.E, 2, new Rational(1, 2), 0);
        firstResidue.getKey().simplify();
        secondResidue.getKey().simplify();
        assertEquals("0/1", firstResidue.getKey().toString());
        assertEquals("0/1", secondResidue.getKey().toString());
        assertEquals("E2h E2h ", m.toString(false));
        } catch(Exception e){
            fail();
        }
    }

    @Test
    public void testBiteIllegalArgs(){
        Measure m = new Measure(Instrument.ELECTRIC_JAZZ_GUITAR, new Rational(4));
        assertThrows(IllegalArgumentException.class, () -> m.bite(null, null, null, -1, null, -1));
    }

    // The functionalites of testGreedyRestFill and updateRests are so linked that we need only test one of them
    @Test
    public void testGreedyRestFillOddMeter(){ 
        Measure m = new Measure(Instrument.ELECTRIC_BASS_PICK, new Rational(17, 16));
        // greedyRestFill is called automatically when a measure is constructed
        Iterator<Entry<Rational, ? extends BarObj>> iterator = m.barIterator();
        assertEquals(NoteValue.WHOLE, iterator.next().getValue().getValue());
        assertEquals(NoteValue.SIXTEENTH,  iterator.next().getValue().getValue());
    }

    @Test
    public void testGreedyRestFillOutOfBounds(){
        Measure m = new Measure(Instrument.SOPRANO_UKULELE, new Rational(4));
        assertThrows(IllegalArgumentException.class, () -> m.greedyRestFill(new Rational(0, 1), new Rational(5, 4)));
    }

    @Test
    public void testGreedyRestFillAroundDottedNote(){
        Measure m = new Measure(Instrument.SOPRANO_UKULELE, new Rational(4));
        Note n = new Note(NoteValue.EIGHTH, true, Instrument.SOPRANO_UKULELE, PitchClass.C, 4);
        m.put(new Rational(0, 1), n, 1);
        Iterator<Entry<Rational, ? extends BarObj>> iterator = m.barIterator();
        iterator.next(); // skip the note we placed
        assertEquals("Rh.", iterator.next().getValue().toString());
        assertEquals("Rs", iterator.next().getValue().toString());
    }

    @Test 
    public void testGreedyRestFillNegative(){
        Measure m = new Measure(Instrument.SOPRANO_UKULELE, new Rational(4));
        assertThrows(IllegalArgumentException.class, () -> m.greedyRestFill(new Rational(-1, 2), new Rational(1, 2)));
    }

    @Test
    public void testGreedyRestFillNull(){
        Measure m = new Measure(Instrument.SOPRANO_UKULELE, new Rational(4));
        assertThrows(IllegalArgumentException.class, () -> m.greedyRestFill(null, null));
    }

    @Test
    public void testRemoveIndirectChord(){ // Removing all the notes from a chord should remove the chord itself
        Instrument instrument = Instrument.ELECTRIC_CLEAN_GUITAR;
        Chord c = new Chord(NoteValue.WHOLE, false, instrument);
        Measure m = new Measure(instrument, new Rational(4));
        Note [] fMaj7add11 = {
            new Note(PitchClass.F, 2),
            new Note(PitchClass.C, 3),
            new Note(PitchClass.F, 3),
            new Note(PitchClass.A, 3),
            new Note(PitchClass.B, 3),
            new Note(PitchClass.E, 4)
        };

        for(int i = 0; i < fMaj7add11.length; i++){
            c.put(fMaj7add11[i], i);
        }

        m.put(new Rational(0, 1), c);
        
        for(int i = 0; i < fMaj7add11.length; i++){
            m.remove(new Rational(0, 1), fMaj7add11[i]);
        }
        assertEquals(0, m.getChords().size());
    }

    @Test
    public void testPutChordEmpty(){ // You shouldn't be able to add an empty chord to a measure
        assertFalse(new Measure(Instrument.GUITAR, new Rational(4)).put(new Rational(0, 1), new Chord(NoteValue.WHOLE, false, Instrument.GUITAR)));
    }

    @Test
    public void testPutChordWrongInstrument(){ // You shouldn't be able to add a chord of the wrong instrument
        Chord c = new Chord(NoteValue.WHOLE, false, Instrument.ELECTRIC_BASS_PICK);
        c.put(new Note(PitchClass.E, 2), 1);
        assertFalse(new Measure(Instrument.SOPRANO_UKULELE, new Rational(4)).put(new Rational(0, 1), c));
    }

    @Test
    public void testPutNoteWrongInstrument(){ // You shouldn't be able to add a chord of the wrong instrument
        assertFalse(new Measure(Instrument.SOPRANO_UKULELE, new Rational(4)).put(new Rational(0, 1), new Note(NoteValue.WHOLE, false, Instrument.ELECTRIC_JAZZ_GUITAR, PitchClass.C, 4), 1));
    }

    @Test 
    public void testPutOutofBoundsNote(){
        assertFalse(new Measure(Instrument.SOPRANO_UKULELE, new Rational(4)).put(new Rational(0, 1), new Note(NoteValue.WHOLE, false, Instrument.ELECTRIC_JAZZ_GUITAR, PitchClass.E, 2), 0));
    }


    @Test
    public void testRemoveNonexistant(){
        Measure m = new Measure(Instrument.SLAP_BASS_1, new Rational(4));
        assertFalse(m.remove(new Rational(0, 1), new Chord(NoteValue.WHOLE, false, Instrument.SLAP_BASS_1)));
    }

    @Test
    public void testRemoveNegative(){
        Measure m = new Measure(Instrument.SLAP_BASS_1, new Rational(4));
        assertFalse(m.remove(new Rational(-1, 2), new Chord(NoteValue.WHOLE, false, Instrument.SLAP_BASS_1)));
    }
    
}