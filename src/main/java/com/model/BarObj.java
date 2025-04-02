package com.model;
/**
 * Represents a music entity with only duration
 * @author Christopher Ferguson
 */
public abstract class BarObj implements Comparable<BarObj>{
    protected NoteValue value;
    protected Rational duration;
    protected boolean dotted;
    
    /**
     * Constructs a musical entity with only duration
     * @param value the entity's base duration
     * @param dotted whether the entity is dotted
     */
    protected BarObj(NoteValue value, boolean dotted){
        this.value = value;
        this.duration = calcDuration(value, dotted);
        this.dotted = dotted;
    }

    /**
     * Compares this entity's duration to another
     * @param b integer representing the comparison
     */
    public int compareTo(BarObj b){
        return duration.compareTo(b.duration);
    }

    /**
     * Retrieves the entity's base duration
     * @return the note's value
     */
    public NoteValue getValue(){
        return value;
    }

    /**
     * Whether the entity is dotted
     * @return if the entity is dotted
     */
    public boolean isDotted(){
        return dotted;
    }

    /**
     * Retrieves the entity's actual duration
     * @return duration
     */
    public Rational getDuration(){
        return duration;
    }

    /**
     * Sets the note's base duration
     * @param value the new value
     */
    public void setValue(NoteValue value){
        if (value == null) return;
        this.value = value;
        duration = calcDuration(value, dotted);
    }

    /**
     * Changes whether the note is dotted
     * @param dotted whether to dot the note
     */
    public void setDotted(boolean dotted){
        this.dotted = dotted;
        duration = calcDuration(value, dotted);
    }

    /**
     * Calculates the duration of an entity with the given value and duration
     * @param value the entity's base duration
     * @param dotted whether the entity is dotted
     * @return the entity's actual duration
     */
    public static Rational calcDuration(NoteValue value, boolean dotted){
         Rational duration = value.duration.deepCopy();
        if(dotted)
            duration.times(new Rational(3,2));
        return duration;
    }

    /**
     * Represents the duration in Staccato
     * @return staccato rhythmic indicator
     */
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

    public static void main(String[] args) {
        System.out.println(calcDuration(NoteValue.HALF, true));
    }
}
