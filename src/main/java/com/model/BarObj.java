package com.model;
/**
 * Represents a music entity with only duration
 */
public abstract class BarObj implements Comparable<BarObj>{
    protected NoteValue value;
    protected Rational duration;
    protected boolean dotted;

    protected BarObj(NoteValue value, boolean dotted){
        this.value = value;
        this.duration = calcDuration(value, dotted);
        this.dotted = dotted;
    }

    public int compareTo(BarObj b){
        return duration.compareTo(b.duration);
    }

    public static Rational calcDuration(NoteValue value, boolean dotted){
         Rational duration = new Rational(
            (int)Math.pow(2, value.ordinal()), 
            (int)Math.pow(2, NoteValue.values().length -1));
        if(dotted)
            duration.plus(new Rational(
                duration.getNumerator(), 
                duration.getDenominator() * 2)
            );
        return duration;
    }

    protected String timingString(){
        String staccato = "";
        switch(value){
            case SIXTY_FOURTH:
                staccato += "x";
                break;
            case THIRTY_SECOND:
                staccato += "t";
                break;
            case SIXTEENTH:
                staccato += "s";
                break;
            case EIGHTH:
                staccato += "i";
                break;
            case QUARTER:
                staccato += "q";
                break;
            case HALF:
                staccato += "h";
                break;
            case WHOLE:
                staccato += "w";
                break;
            default:
                staccato += "q";
                break;
        }

        return staccato + (dotted ? "." : "");
    }
}
