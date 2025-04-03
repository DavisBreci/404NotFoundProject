package com.model;
/**
 * 
 * Class representing rational numbers
 * @author Christopher Ferguson
 */
public class Rational implements Comparable<Rational>{
    private int numerator  = 0;
    private int denominator = 1;

    /**
     * Constructs a rational number
     * @param numerator the numerator
     * @param denominator the denominator
     */
    public Rational(int numerator, int denominator){
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }

    /**
     * Constructs a rational number where the numerator and denominator are equal to the scalar
     * @param scalar the scalar
     */
    public Rational(int scalar){
        if (scalar == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }
        numerator = denominator = scalar;
    }
    
    /**
     * Constructs a rational number from a properly formatted string
     * @param literal string in the format "iNumerator/iDenominator"
     */
    public Rational(String literal){
        String [] values = literal.split("/");
		if(values.length != 2) {
			return;
		}
        if (Integer.parseInt(values[1]) == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }
		numerator = Integer.parseInt(values[0]);
		denominator = Integer.parseInt(values[1]);
    }

    /**
     * Multiplies this rational by another rational
     * @param r rational to multiply this by
     */
    public void times(Rational r){
        this.numerator = this.numerator * r.getNumerator();
        this.denominator = this.denominator * r.getDenominator(); 
    }

    /**
     * Adds a given rational number to this rational
     * @param r the rational to be added
     */
    public void plus(Rational r){
        if(denominator == r.getDenominator()) {
            numerator = numerator + r.getNumerator();
            return;
		}
		int lcm = lcm(denominator, r.getDenominator());
		int a = numerator * (lcm/denominator);
		int b = r.getNumerator() * (lcm/r.getDenominator());
        numerator = a + b;
        denominator = lcm;

    }

    /**
     * Subtracts a given rational number from this rational
     * @param r the rational to be subtracted
     */
    public void minus(Rational r) {
        Rational temp = r.deepCopy();
        temp.negative();
		plus(temp);
	}
	
    /**
     * Swaps the numerator and denominator of this rational
     */
    public void reciprocal(){
        int temp = numerator;
        numerator = denominator;
        denominator = temp;
    }

    /**
     * Divides this rational by the given rational
     * @param r the divisor
     */
    public void dividedBy(Rational r) {
        Rational temp = r.deepCopy();
        temp.reciprocal();
		times(temp);
	}

    /**
     * Negates this rational
     */
	public void negative() {
		times(new Rational(-1, 1));
	}

    /**
     * Simplifies this rational
     */
    public void simplify(){
        if(isZero()){
            numerator = 0;
            denominator = 1;
        }
        int gcd = gcd(numerator, denominator);
		numerator /= gcd;
        denominator /= gcd;
    }

    /**
     * Retrieves this rational's denominator
     * @return the denominator
     */
    public int getDenominator(){
        return denominator;
    }

    /**
     * Retrieves this rational's numerator
     * @return the numerator
     */
    public int getNumerator(){
        return numerator;
    }

    /**
     * Sets this rational's denominator
     * @param denominator the nenw denominator
     */
    public void setDenominator(int denominator){
        if (denominator != 0) { 
            this.denominator = denominator;
        } else {
           System.out.println("0 cannot be the denominator."); 
        }
    }

    /**
     * Sets this rational's numerator
     * @param numerator the new numerator
     */
    public void setNumerator(int numerator){
        this.numerator = numerator;
    }

    /**
     * Retrieves a single-precision floating point representation of the rational
     * @return the rational as a float
     */
    public float toFloat() {
		return (float) numerator / (float)denominator;
	}
	
    /**
     * Retrieves a double-precision floating point representation of the rational
     * @return the rational as a double
     */
	public double toDouble() {
		return (double) numerator / (double)denominator;
	}

    /**
     * Computes the least common multiple (LCM) of two numbers
     * @param a first number 
     * @param b second number
     * @return the LCM
     */
    public static int lcm(int a, int b) {
		return Math.abs(a * b) / gcd(a, b);
	}

    /**
     * Computues the greatest common divisor (GCD) of two numbers
     * @param a first number
     * @param b second number
     * @return the GCD
     */
	public static int gcd(int a, int b) {
		while(b != 0) {
		int remainder = a % b;
		a = b;
		b = remainder;
		}
		return a;
	}

    /**
     * Snaps a floating point number to a grid where gridlines are separated by 1/maxDenominator
     * @param fraction the double to be approximated
     * @param tolerance maximum error
     * @param maxDenominator the largest acceptable denominator (must be a power of 2)
     * @return a rational approximation of the double
     */
    public static Rational quantize(double fraction, double tolerance, int maxDenominator) { 
                if(fraction == 0.0) return new Rational("0/1");
                if(fraction == 1.0) return new Rational("1/1");
                Rational quantized = new Rational(1, 1);
                quantized.times(new Rational((int)fraction,1)); // Take out any whole notes
                quantized.plus(
                    quantize(
                        new Rational("0/1"), 
                        new Rational("1/1"), 
                        fraction - (int)fraction,
                        tolerance, maxDenominator
                        )
                );
                return quantized;
    }
    /**
     * Recursive method that conducts a binary search for a suitible rational approximation of a double
     * @param start rational start of the search area
     * @param end rational end of the search area
     * @param target the double to be approximated
     * @param tolerance the maximium error
     * @param maxDenominator the largest acceptable denominator (must be a power of 2)
     * @return the rational approximation
     */
    private static Rational quantize(Rational start, Rational end, double target, double tolerance, int maxDenominator){
        Rational middle = start.deepCopy();
        middle.plus(end);
        middle.times(new Rational(1, 2));
        middle.simplify();
        if(middle.getDenominator() >= maxDenominator)
            return middle;
        double dMiddle = middle.toDouble();
        if(Math.abs(dMiddle - target) <= tolerance)
            return middle;
        if (dMiddle < target)
            return quantize(middle, end, target, tolerance, maxDenominator);
        if(dMiddle > target)
            return quantize(start, middle, target, tolerance, maxDenominator);
        return middle; // Should never be reached
    }

    /**
     * Returns a new rational with the same numerator and denominator as this rational
     * @return the copy
     */
    public Rational deepCopy(){
        return new Rational(numerator, denominator);
    }

    /**
     * Compares rationals by value
     */
    @Override
	public int compareTo(Rational r) {
		int multiple = lcm(denominator, r.getDenominator());
		Rational temp1 = deepCopy();
        temp1.times(new Rational(multiple/denominator));
		Rational temp2 = r.deepCopy();
        temp2.times(new Rational(multiple/r.getDenominator()));
		if(temp1.numerator == temp2.numerator) return 0;
		else if(temp1.numerator < temp2.numerator) return -1;
		else return 1;
	}

    /**
     * Returns a String representation of the rational
     */
    public String toString() {
		return numerator + "/" + denominator;
	}

    /**
     * Returns whether numerator is zero
     * @return whether numerator is zero
     */
    public boolean isZero(){
        return numerator == 0;
    }

}
