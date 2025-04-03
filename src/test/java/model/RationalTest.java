package model;

import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.time.LocalDate;

import com.model.*;

/**
 * @author Ryan Mazzella
 */
public class RationalTest{
    //Rational constructs correctly from numerator and denominator
    @Test
    public void testConstructorWithNumeratorAndDenominator() {
        Rational r = new Rational(3, 4);
        assertEquals(3, r.getNumerator());
        assertEquals(4, r.getDenominator());
    }

    //Rational constructs correctly from scalar value
    @Test
    public void testConstructorWithScalar() {
        Rational r = new Rational(5);
        assertEquals(5, r.getNumerator());
        assertEquals(5, r.getDenominator());
    }

    //Rational constructs correctly from formatted string
    @Test
    public void testConstructorWithString() {
        Rational r = new Rational("7/8");
        assertEquals(7, r.getNumerator());
        assertEquals(8, r.getDenominator());
    }

    //Rational constructs handles an empty string
    @Test
    public void testConstructorWithEmptyString() {
        Rational r = new Rational("");
        assertEquals(0, r.getNumerator());
        assertEquals(1, r.getDenominator());
    }

    //Two positive rationals multiply together correctly
    @Test
    public void testTimes() {
        Rational r1 = new Rational(2, 3);
        Rational r2 = new Rational(3, 4);
        r1.times(r2);
        assertEquals(6, r1.getNumerator());
        assertEquals(12, r1.getDenominator());
    }

    //A postive and negative rational multiply together correctly
    @Test
    public void testTimesWithNegative() {
        Rational r1 = new Rational(-2, 5);
        Rational r2 = new Rational(5, 2);
        r1.times(r2);
        assertEquals(-10, r1.getNumerator());
        assertEquals(10, r1.getDenominator());
    }

    //A rational multiplies with 0 correctly
    @Test
    public void testTimesWithZero() {
        Rational r1 = new Rational(0, 1);
        Rational r2 = new Rational(4, 5);
        r1.times(r2);
        assertEquals(0, r1.getNumerator());
    }

    //Two rationals with the same denominator add correctly
    @Test
    public void testPlusWithSameDenominator() {
        Rational r1 = new Rational(1, 3);
        Rational r2 = new Rational(2, 3);
        r1.plus(r2);
        assertEquals(3, r1.getNumerator());
        assertEquals(3, r1.getDenominator());
    }

    //Two rationals with different denominators add correctly and simplify
    @Test
    public void testPlusWithDifferentDenominators() {
        Rational r1 = new Rational(1, 4);
        Rational r2 = new Rational(1, 6);
        r1.plus(r2);
        assertEquals(5, r1.getNumerator());
        assertEquals(12, r1.getDenominator());
    }

    //A positive rational and negative rational add correctly
    @Test
    public void testPlusWithNegativeRational() {
        Rational r1 = new Rational(3, 4);
        Rational r2 = new Rational(-1, 4);
        r1.plus(r2);
        assertEquals(2, r1.getNumerator());
        assertEquals(4, r1.getDenominator());
    }

    //Two rationals with the same denominator subtract correctly
    @Test
    public void testMinusSameDenominator() {
        Rational r1 = new Rational(5, 6);
        Rational r2 = new Rational(2, 6);
        r1.minus(r2);
        assertEquals(3, r1.getNumerator());
        assertEquals(6, r1.getDenominator());
    }

    //Two rationals with different denominators subtract correctly and simplify
    @Test
    public void testMinusDifferentDenominator() {
        Rational r1 = new Rational(3, 4);
        Rational r2 = new Rational(1, 2);
        r1.minus(r2);
        assertEquals(1, r1.getNumerator());
        assertEquals(4, r1.getDenominator());
    }

    //A positive rational and negative rational subtract correctly
    @Test
    public void testMinusResultingNegative() {
        Rational r1 = new Rational(1, 4);
        Rational r2 = new Rational(3, 4);
        r1.minus(r2);
        assertEquals(-2, r1.getNumerator());
        assertEquals(4, r1.getDenominator());
    }

    //A positive rational's reciprocal is generated successfully
    @Test
    public void testReciprocalPositive() {
        Rational r = new Rational(2, 3);
        r.reciprocal();
        assertEquals(3, r.getNumerator());
        assertEquals(2, r.getDenominator());
    }

    //A negative rational's reciprocal is generated successfully
    @Test
    public void testReciprocalWithNegativeNumerator() {
        Rational r = new Rational(-4, 5);
        r.reciprocal();
        assertEquals(5, r.getNumerator());
        assertEquals(-4, r.getDenominator());
    }

    //Two positive rationals divide correctly
    @Test
    public void testDividedBy() {
        Rational r1 = new Rational(1, 2);
        Rational r2 = new Rational(3, 4);
        r1.dividedBy(r2);
        assertEquals(4, r1.getNumerator());
        assertEquals(6, r1.getDenominator());
    }

    //A positive rational and negative rational divide correctly 
    @Test
    public void testDividedByNegative() {
        Rational r1 = new Rational(3, 5);
        Rational r2 = new Rational(-2, 7);
        r1.dividedBy(r2);
        assertEquals(21, r1.getNumerator());
        assertEquals(-10, r1.getDenominator());
    }

    //A positive rational correctly simplifies
    @Test
    public void testSimplifyPositive() {
        Rational r = new Rational(10, 20);
        r.simplify();
        assertEquals(1, r.getNumerator());
        assertEquals(2, r.getDenominator());
    }

    //A rational equaling 0 simplifies correctly
    @Test
    public void testSimplifyZeroNumerator() {
        Rational r = new Rational(0, 15);
        r.simplify();
        assertEquals(0, r.getNumerator());
        assertEquals(1, r.getDenominator());
    }

    //A negative rational simplifies correctly
    @Test
    public void testSimplifyNegative() {
        Rational r = new Rational(-6, 9);
        r.simplify();
        assertEquals(-2, r.getNumerator());
        assertEquals(3, r.getDenominator());
    }

    //A positive rational is made negative correctly
    @Test
    public void testNegativePositiveValue() {
        Rational r = new Rational(2, 3);
        r.negative();
        assertEquals(-2, r.getNumerator());
        assertEquals(3, r.getDenominator());
    }

    //A negative rational is made negative, thus making it positive
    @Test
    public void testNegativeNegativeValue() {
        Rational r = new Rational(-5, 6);
        r.negative();
        assertEquals(5, r.getNumerator());
        assertEquals(6, r.getDenominator());
    }

    //A rational equaling 0 is ignored, as 0 is signless
    @Test
    public void testNegativeZero() {
        Rational r = new Rational(0, 7);
        r.negative();
        assertEquals(0, r.getNumerator());
        assertEquals(7, r.getDenominator());
    }

    //A rational that divides cleanly is converted correctly to a float
    @Test
    public void testToDouble() {
        Rational r = new Rational(3, 4);
        assertEquals(0.75, r.toDouble(), 1e-10);
    }

    //A rational that divides into a repeating decimal divides correctly (within a extremely small margin)
    @Test
    public void testToDoubleRepeating() {
        Rational r = new Rational(1, 3);
        assertEquals(1.0/3.0, r.toDouble(), 1e-10);
    }

    //A negative rational converts correctly into a negative double
    @Test
    public void testToDoubleNegative() {
        Rational r = new Rational(-1, 2);
        assertEquals(-0.5, r.toDouble(), 1e-10);
    }

    //A rational equaling zero coverts correctly to a double equaling 0.0
    @Test
    public void testToDoubleZero() {
        Rational r = new Rational(0, 5);
        assertEquals(0.0, r.toDouble(), 1e-10);
    }

    //A rational that divides cleanly is converted correctly to a float
    @Test
    public void testToFloat() {
        Rational r = new Rational(1, 4);
        assertEquals(0.25f, r.toFloat(), 1e-6f);
    }

    //A rational that divides into a repeating decimal divides correctly (within the acceptable float margin)
    @Test
    public void testToFloatRepeating() {
        Rational r = new Rational(1, 3);
        assertEquals(1.0f/3.0f, r.toFloat(), 1e-6f);
    }

    //A negative rational converts correctly into a negative float
    @Test
    public void testToFloatNegative() {
        Rational r = new Rational(-3, 2);
        assertEquals(-1.5f, r.toFloat(), 1e-6f);
    }

    //A rational equaling zero coverts correctly to a float equaling 0.0
    @Test
    public void testToFloatZero() {
        Rational r = new Rational(0, 1);
        assertEquals(0.0f, r.toFloat(), 1e-6f);
    }

    //The least common multiple of two positive integers is calculated correctly
    @Test
    public void testLcm() {
        assertEquals(12, Rational.lcm(4, 6));
    }

    //The least common multiple of zero and another number is always zero
    //0 is usually excluded from LCM calculations, but in this case where it is specifically invoked, it is included
    @Test
    public void testLcmWithZero() {
        assertEquals(0, Rational.lcm(0, 7));
    }

    //The least common multiple of a positive integer and negative integer is calculated correctly
    @Test
    public void testLcmWithNegatives() {
        assertEquals(15, Rational.lcm(-5, 3));
    }

    //The greatest common denominator is calculated correctly
    @Test
    public void testGcd() {
        assertEquals(2, Rational.gcd(6, 8));
    }

    //The greatest common factor of zero and another number is always the other number
    @Test
    public void testGcdWithZero() {
        assertEquals(9, Rational.gcd(9, 0));
    }

    //The greatest common factor of a positive integer and negative integer is calculated correctly
    @Test
    public void testGcdWithNegatives() {
        assertEquals(4, Rational.gcd(-8, 12));
    }

    //A rational is quantized correctly
    @Test
    public void testQuantizeSimpleFraction() {
        Rational r = Rational.quantize(0.5, 0.01, 16);
        r.simplify();
        assertEquals("1/2", r.toString());
    }

    //A rational is is successfully copied to a secondary object
    @Test
    public void testDeepCopyProducesEqualValue() {
        Rational original = new Rational(7, 9);
        Rational copy = original.deepCopy();
        assertEquals(original.getNumerator(), copy.getNumerator());
        assertEquals(original.getDenominator(), copy.getDenominator());
    }

    //The copied rational is a seperate object and not a reference to the original
    @Test
    public void testDeepCopyIsDistinctObject() {
        Rational original = new Rational(3, 4);
        Rational copy = original.deepCopy();
        copy.setNumerator(99);
        assertNotEquals(original.getNumerator(), copy.getNumerator());
    }

    //Two rationals that are equal each other when divided are compared correctly (0 output value)
    @Test
    public void testCompareToEqual() {
        Rational a = new Rational(2, 3);
        Rational b = new Rational(4, 6);
        assertEquals(0, a.compareTo(b));
    }

    //A lower value rational is compared to a higher value rational correctly (output value is less than 0)
    @Test
    public void testCompareToLessThan() {
        Rational a = new Rational(1, 3);
        Rational b = new Rational(2, 3);
        assertTrue(a.compareTo(b) < 0);
    }

    //A higher value rational is compared to a lower value rational correctly (output value is greater than 0)
    @Test
    public void testCompareToGreaterThan() {
        Rational a = new Rational(5, 6);
        Rational b = new Rational(2, 3);
        assertTrue(a.compareTo(b) > 0);
    }

    //A rational is converted to a string in the correct format
    @Test
    public void testToString() {
        Rational r = new Rational(3, 5);
        assertEquals("3/5", r.toString());
    }

    //A rational is converted to a string correctly with a negative factor
    @Test
    public void testToStringNegative() {
        Rational r = new Rational(4, -7);
        assertEquals("4/-7", r.toString());
    }

    //A rational equaling zero is converted to a string correctly, while keeping the denominator intact
    @Test
    public void testToStringZeroNumerator() {
        Rational r = new Rational(0, 9);
        assertEquals("0/9", r.toString());
    }
}