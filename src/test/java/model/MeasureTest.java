package model;

import org.junit.Test;

import com.model.Measure;
import com.model.NoteValue;
import com.model.Rational;
import com.model.Rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;


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
        IllegalArgumentException.class, () ->  
        Measure.collides(a, b));
    }

}
