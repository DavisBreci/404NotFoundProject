/**
 * @author Christopher Ferguson
 */
package com.model;

public class Rational implements Comparable<Rational>{
    private int numerator;
    private int denominator;

    public Rational(int numerator, int denominator){
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Rational(int scalar){
        numerator = denominator = scalar;
    }

    public Rational(String literal){
        String [] values = literal.split("/");
		if(values.length != 2) {
			numerator = 0;
			denominator = 1;
			return;
		}
		numerator = Integer.parseInt(values[0]);
		denominator = Integer.parseInt(values[1]);
    }

    public void times(Rational r){
        this.numerator = this.numerator * r.getNumerator();
        this.denominator = this.denominator * r.getDenominator(); 
    }

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

    public void minus(Rational r) {
        Rational temp = r.deepCopy();
        temp.negative();
		plus(temp);
	}
	
    public void reciprocal(){
        int temp = numerator;
        numerator = denominator;
        denominator = temp;
    }

    public void dividedBy(Rational r) {
        Rational temp = r.deepCopy();
        temp.reciprocal();
		times(temp);
	}

	public void negative() {
		times(new Rational(-1, 1));
	}

    public void simplify(){
        int gcd = gcd(numerator, denominator);
		numerator /= gcd;
        denominator /= gcd;
    }

    public int getDenominator(){
        return denominator;
    }

    public int getNumerator(){
        return numerator;
    }

    public void setDenominator(int denominator){
        this.denominator = denominator; // Allowing the denominator to be zero is useful for Stern-Brocot
    }

    public void setNumerator(int numerator){
        this.numerator = numerator;
    }

    public float toFloat() {
		return (float) numerator / (float)denominator;
	}
	
	public double toDouble() {
		return (double) numerator / (double)denominator;
	}

    public static int lcm(int a, int b) {
		return Math.abs(a * b) / gcd(a, b);
	}
	public static int gcd(int a, int b) {
		while(b != 0) {
		int remainder = a % b;
		a = b;
		b = remainder;
		}
		return a;
	}

    public static Rational quantize(double fraction, int specificity, boolean bounded) { 
                if(bounded)
                    return quantize(fraction, specificity, 2 * specificity + 1);
                else 
                    return quantize(fraction, specificity, Integer.MAX_VALUE);
            }
            
            private static Rational quantize(double fraction, int specificity, int noteValues) {
                if(fraction == 0) return new Rational(0, 1);
                int numerator = 1;
                int nearestNumerator = 0;
                final int denominator = (int)Math.pow(2, specificity - 1);
                double error = 0;
                double minimumError = Double.MAX_VALUE;
                int i;
                for(i = 0; i < noteValues; i++){
                    error = Math.abs(fraction - (numerator/(double)denominator) * (1 + 0.5 * (i % 2)));
                    if(error < minimumError){
                        nearestNumerator = numerator;
                        minimumError = error;
                    } else if(error > minimumError)
                        break;
                    if(i % 2 == 1)
                        numerator *= 2;
                }

                Rational quantized = new Rational(nearestNumerator, denominator);
                if(i % 2 == 0){ // Dot note if necessary
                    Rational dot = quantized.deepCopy();
                    dot.times(new Rational(1, 2));
                    quantized.plus(dot);
                }
                quantized.simplify();
                return quantized;
            }
            

    public Rational deepCopy(){
        return new Rational(numerator, denominator);
    }

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

    public String toString() {
		return numerator + "/" + denominator;
	}
    
    private static Rational mediant(Rational a, Rational b) {
		return new Rational(a.getNumerator() + b.getNumerator(), a.getDenominator() + b.getDenominator());
	}
    /*
     * This is a rational approximation algorithm that idk if we'll end up using.
     * // https://en.wikipedia.org/wiki/Stern%E2%80%93Brocot_tree#Mediants_and_binary_search
     */
    private static Rational sternBrocot(double q, double tolerance, int specificity) {
		Rational rL = new Rational(0, 1);
		Rational rH = new Rational(1, 0);
		Rational rM;
		double dM;
		while(true) {
			rM = mediant(rL, rH);
			dM = rM.toDouble();
			if(Math.abs(dM - q) <= tolerance)
				break;
			else if(dM < q)
				rL = rM;
			else 
				rH = rM;
		}
		return rM;
	}

}
