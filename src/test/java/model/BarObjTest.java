package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import com.model.BarObj;
import com.model.NoteValue;
import com.model.Rational;

/**
 * @author Christopher Ferguson
 */
public class BarObjTest {
    @Test
    public void testCalcDurationNull(){
        assertThrows(
            IllegalArgumentException.class,
            new ThrowingRunnable() {
                @Override
                public void run() throws Throwable {
                    BarObj.calcDuration(null, false);
                }   
            }
        );
    }

    @Test
    public void testCalcDurationDotted(){
        Rational r = BarObj.calcDuration(NoteValue.HALF, false);
        Rational rDot = BarObj.calcDuration(NoteValue.HALF, true);
        Rational ratio = rDot.deepCopy();
        ratio.dividedBy(r);
        assertEquals(0, new Rational(3,2).compareTo(ratio));
    }

    @Test
    public void testCalcDurationLargest(){
        assertEquals(0, new Rational(3,2).compareTo(BarObj.calcDuration(NoteValue.WHOLE, true)));
    }

    @Test
    public void testCalcDurationSmallest(){
        assertEquals(0, new Rational(1,64).compareTo(BarObj.calcDuration(NoteValue.SIXTY_FOURTH, false)));
    }

    @Test
    public void testCalcDurationLog(){ // Make sure every duration's denominator is an integral power of 2
        for(NoteValue v : NoteValue.values())
            assertTrue(lg(BarObj.calcDuration(v, false).getDenominator()) % 1 == 0);

    }
    
    protected double lg(int n){
        return Math.log(n) / Math.log(2);
    }
}
