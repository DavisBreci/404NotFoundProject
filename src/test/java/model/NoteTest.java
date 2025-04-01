package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.model.Note;
import com.model.PitchClass;

public class NoteTest {
    @Test
    public void testDeepCopy(){
        Note n = new Note(PitchClass.C, 4);
        Note nCopy = n.deepCopy();
        assertTrue(n != nCopy);
        assertTrue(n.equals(nCopy));
    }

    @Test
    public void testRestringNormal(){
        Note n = new Note(PitchClass.C, 5);
        n.restring(4);
        assertEquals(13, n.getFret());
    }

    @Test
    public void testRestringWrongString(){
        Note n = new Note(PitchClass.C, 5);
        assertFalse(n.restring(0)); // No C5 on guitar string 0
        assertEquals(0, n.getFret());
    }

    @Test
    public void testRestringNegative(){
        Note n = new Note(PitchClass.C, 5);
        assertFalse(n.restring(-1));
    }

    @Test
    public void testRestringTooHigh(){
        Note n = new Note(PitchClass.C, 5);
        assertFalse(n.restring(Integer.MAX_VALUE));
    }

    @Test
    public void testRestringSame(){
        Note n = new Note(PitchClass.E, 2);
        assertTrue(n.restring(0));
        assertEquals(0, n.getFret());
    }

    @Test
    public void testNoteNumToPitchClassBottom(){
        assertEquals(PitchClass.A, Note.noteNumToPitchClass(21));
    }

    @Test
    public void testNoteNumToPitchClassTop(){
        assertEquals(PitchClass.G, Note.noteNumToPitchClass(127));
    }

    @Test
    public void testNoteNumToOctaveBottom(){
        assertEquals(0, Note.noteNumToOctave(21));
    }

    @Test
    public void testNoteNumToOctaveTop(){
        assertEquals(9, Note.noteNumToOctave(127));
    }

    

}
