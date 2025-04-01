package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.model.Note;
import com.model.PitchClass;
/**
 * @author Christopher Ferguson
 */
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

    @Test
    public void testSetLocationNormal(){
        Note n = new Note(PitchClass.C, 3);
        assertTrue(n.setLocation(1, 3));
        assertEquals(1, n.getString());
        assertEquals(3, n.getFret());
        
    }

    @Test
    public void testSetLocationInvalid(){
        Note n = new Note(PitchClass.C, 3);
        n.restring(0);
        assertFalse(n.setLocation(1, 4)); // Wrong fret
        assertEquals(0, n.getString());
        assertEquals(8, n.getFret());
    }

    @Test
    public void testSetLocationNegative(){
        Note n = new Note(PitchClass.C, 3);
        n.restring(0);
        assertFalse(n.setLocation(-1, -1));
        assertEquals(0, n.getString());
        assertEquals(8, n.getFret());
    }

    @Test
    public void testSetLocationOutOfBounds(){
        Note n = new Note(PitchClass.C, 3);
        n.restring(0);
        assertFalse(n.setLocation(Integer.MAX_VALUE, Integer.MAX_VALUE)); 
        assertEquals(0, n.getString());
        assertEquals(8, n.getFret());
    }

    @Test
    public void  testTieNormal(){
        Note n1 = new Note(PitchClass.C, 3);
        Note n2 = n1.deepCopy();
        n1.tieFront(n2);
        assertEquals(n2 ,n1.getFrontTie());
        assertEquals(n1 ,n2.getBackTie());
    }

    @Test
    public void testTieRet(){
        Note n1 = new Note(PitchClass.C, 3);
        assertEquals(n1, n1.tieFront(null));
    }

    @Test
    public void testTieNull(){
        Note n1 = new Note(PitchClass.C, 3);
        assertNull(n1.tieFront(null).getFrontTie());
    }

    @Test
    public void testTieSameNoteDifferentString(){
        Note n1 = new Note(PitchClass.C, 3);
        n1.restring(0);
        Note n2 = n1.deepCopy();
        n2.restring(1);
        n1.tieFront(n2);
        assertNull(n1.getFrontTie());
    }

    @Test
    public void testTieDifferentNote(){
        Note n1 = new Note(PitchClass.C, 3);
        n1.restring(0);
        Note n2 = new Note(PitchClass.E, 2);
        n1.tieFront(n2);
        assertNull(n1.getFrontTie());
    }
    
}

