/**
 * @author Christopher Ferguson
 */
package com.model;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Class representing a single measure of tablature
 */
public class Measure {
    private Instrument instrument;
    private Rational timeSignature;
    private TreeMap<Rational, Chord> chords;
    private TreeMap<Rational, Rest> rests;
    
    

    /**
     * Creates an empty measure
     * @param instrument the measure's instrument
     * @param timeSignature the time signature the measure is in
     */
    public Measure(Instrument instrument, Rational timeSignature){
        Entry.comparingByKey();
        this.instrument = instrument;
        this.timeSignature = timeSignature;
        this.chords = new TreeMap<Rational, Chord>();
        this.rests = new TreeMap<Rational, Rest>();
        greedyRestFill(new Rational(0, 1), timeSignature);
    }
    /**
     * Calculates whether two notes are overlapping. The order of the input doesn't matter.
     * @param a note for collision check
     * @param b note for collision check
     * @return whether there's a collision
     */
    private boolean collides(Entry<Rational, ? extends BarObj> a, Entry<Rational, ? extends BarObj> b){
        Rational aStart = a.getKey();
        Rational aEnd = aStart.deepCopy();
        aEnd.plus(a.getValue().getDuration());
        Rational bStart = b.getKey();
        Rational bEnd = bStart.deepCopy();
        bEnd.plus(b.getValue().getDuration());
        return (bStart.compareTo(aEnd) == -1 && aStart.compareTo(bEnd) == -1) || (aStart.compareTo(bEnd) == -1 && bStart.compareTo(aEnd) == -1);
    }

    public void setTimeSignature(Rational timeSignature){
        if(timeSignature.compareTo(this.timeSignature) < 0){
            clear();
            updateRests();
        }
        this.timeSignature = timeSignature.deepCopy();
    }

    /**
     * Attempts to add a note to the measure. 
     * @param offset the chord's distance from the measure's start
     * @param note the note to be added
     * @return whether the addition was successful
     */

    public boolean put(Rational offset, Note note, int string){
        Chord container = null;
        if((container = chords.get(offset)) != null)
            return container.put(note, string);
        container = new Chord(note.getValue(), note.isDotted(), instrument);
        if(!container.put(note, string))
            return false;
        return put(offset, container);
    }

    /**
     * Attempts to add a chord to the measure
     * @param offset the chord's distance from the measure's start
     * @param chord the chord to be added
     * @return whether the addition was successful
     */
    public boolean put(Rational offset, Chord chord){
        if(offset == null || chord == null) return false;
        Entry<Rational, Chord> newChord = new AbstractMap.SimpleEntry<Rational, Chord>(offset, chord);
        Iterator<Entry<Rational, Chord>> iIterator = chordIterator();
        Entry<Rational, Chord> i;
        while(iIterator.hasNext()){
            i = iIterator.next();
            if(collides(newChord, i))
                return false;
        }
        if(!outOfBounds(offset, chord)){
            chords.put(offset, chord);
            updateRests();
            return true;
        }
        return false;
    }

    /**
     * Method for finding whether a musical object cannot be contained within the measure
     * @param offset the barObj's offset
     * @param barObj a musical object that has a duration
     * @return whether the BarObj cannot be contained within the measure
     */
    private boolean outOfBounds(Rational offset, BarObj barObj){
        if(offset.compareTo(new Rational("0/1")) == -1)
            return true;
        Rational noteEnd = offset.deepCopy();
        noteEnd.plus(barObj.getDuration());
        if(noteEnd.compareTo(timeSignature)  == 1)
            return true;
        return false;
    }

    /**
     * Attempts to remove a chord from the measure
     * @param offset the chord's offset
     * @param chord the chord for removal
     * @return whether the removal was successful
     */
    public boolean remove(Rational offset, Chord chord){
        return chords.remove(offset, chord);
    }

    /**
     * Attempts to remove a note from the measure 
     * @param offset the note's offset
     * @param n the note for removal
     * @return whether the removal was successful
     */
    public boolean remove(Rational offset, Note n){
        Chord container = chords.get(offset);
        if(container == null)
            return false;
        return container.remove(n);
    }

    /**
     * Retrieves the chord at the specified offset, if it exists
     * @return the chord
     */
    public Chord get(Rational offset){
        return chords.get(offset);
    }

    /**
     * Retrieves an entry set of chords
     * @return the entry set
     */
    public Set<Map.Entry<Rational, Chord>>  chordEntrySet(){
        return chords.entrySet();
    }
    
    /**
     * Retrieves an entry set of rests
     * @return the entry set
     */
    public Set<Map.Entry<Rational, Rest>>  restEntrySet(){
        return rests.entrySet();
    }

    /**
     * Returns true if the measure contains no notes
     * @return whether the measure is empty
     */
    public boolean isEmpty(){
        return chords.isEmpty();
    }
    
    /**
     * Gets rid of all the notes within a measure
     */
    public void clear(){
        chords.clear();
        rests.clear();
        greedyRestFill(new Rational(0, 1), timeSignature);
    }

    /**
     * Fills gaps between notes with rests
     */
    public void updateRests(){
        rests.clear();
        Rational gapStart = new Rational("0/1");
        Rational gapEnd;
        Iterator<Entry<Rational, Chord>> iIterator = chordIterator();
        Entry<Rational, Chord> currentEntry;
        while(iIterator.hasNext()){
            currentEntry = iIterator.next();
            gapEnd = currentEntry.getKey();
            if(gapStart.compareTo(gapEnd) == -1) // Gap between notes
                greedyRestFill(gapStart, gapEnd); 
            gapStart = gapEnd.deepCopy();
            gapStart.plus(currentEntry.getValue().getDuration());
        }
        gapEnd = timeSignature;
        if(gapStart.compareTo(gapEnd) == -1)
            greedyRestFill(gapStart, gapEnd);
    }

    /**
     * Greedily fills the space between the start and end with rests
     * @param start where to begin filling
     * @param end where to end filling
     */
    private void greedyRestFill(Rational start, Rational end){
        Rational remainder = end.deepCopy();
        Rational offset = start.deepCopy();
        remainder.minus(start);
        double noteIndex;
        NoteValue value;
        Rational dot;
        Rational temp;
        boolean dotted;
        Rest rest;
        while(!remainder.isZero()){
            remainder.times(new Rational(64/remainder.getDenominator()));
            noteIndex = Math.log(remainder.getNumerator()) / Math.log(2);
            value = NoteValue.values()[
                Math.min(NoteValue.values().length - 1, (int)noteIndex)
            ];
            remainder.minus(value.duration);
            dot = value.duration.deepCopy();
            dot.times(new Rational(1, 2));
            temp = remainder.deepCopy();
            temp.minus(dot);
            if(dotted = (remainder.compareTo(dot) <= 0) && temp.compareTo(new Rational("0/1")) == 1)
                remainder = temp;
            // System.out.println("Bottom Remainder " + remainder);
            rest = new Rest(value, dotted);
            // System.out.println("Top Remainder " + remainder);
            offset.simplify();
            rests.put(offset.deepCopy(), rest);
            offset.plus(rest.getDuration());
        }
    }

    /**
     * Method that finds which beat of the measure a given offset falls on
     * @param offset the distance from the start of the measure
     * @return the 
     */
    public int beatOf(Rational offset){
        return (offset.getNumerator() * timeSignature.getDenominator())/offset.getDenominator() + 1;
    }

    /**
     * This method places the portion of the note that will fit within the measure within it.
     * If necessary, the method constructs the bitten portion as several notes tied together.
     * If there are other notes obstructing placement, this method places what it can and returns null.
     * This is useful for notes of irregular duration and notes that cross barlines.
     * @param backTie note for the first note created by this method to be tied to
     * @param offset where the note begins
     * @param pitchClass the note's pitch
     * @param octave the note's octave
     * @param duration the note's raw duration
     * @return a pair containing the unbitten duration and a reference to the last note bitten
     */
    public AbstractMap.SimpleEntry<Rational, Note> bite(Note backTie, Rational offset, PitchClass pitchClass, int octave, Rational duration, int string){
        Rational remainder = duration.deepCopy(); // How much of the note is left for processing
        Rational _offset = offset.deepCopy();
        double noteIndex;
        Note prevNote = null;
        Note currentNote = null;
        NoteValue value;
        Rational dot;
        Rational temp;
        boolean dotted;
        while(_offset.compareTo(timeSignature) == -1){ // Stop when you've exhausted all space within the measure
            remainder.times(new Rational(64/remainder.getDenominator())); // Normalize the remainder
            noteIndex = Math.log(remainder.getNumerator()) / Math.log(2); // Calculate duration
            value = NoteValue.values()[
                Math.min(NoteValue.values().length - 1, (int)noteIndex)
            ];
            remainder.minus(value.duration);
            dot = new Rational(
                value.duration.getNumerator()/2, value.duration.getDenominator()
            );
            temp = remainder.deepCopy();
            temp.minus(dot);
            if((dotted = remainder.compareTo(dot) <= 0 && temp.compareTo(new Rational("0/1")) == 1))
                remainder = temp;
            currentNote = new Note(value, dotted, instrument, pitchClass, octave); // Create a new note and handle ties
            currentNote.tieBack(prevNote);
            _offset.simplify();
            if(!put(_offset.deepCopy(), currentNote, string))
                return null;
            prevNote = currentNote;
            _offset.plus(currentNote.getDuration());
        }
        return new AbstractMap.SimpleEntry<Rational, Note>(remainder.deepCopy(), currentNote);
    }

    /**
     * Returns a staccato representation of the note
     */
    public String toString(){
        return toString(true);
    }

    /**
     * Version of toString() that lets the user choose whether they want barlines
     * @param includeBars whether to append a barline to the measure's end
     * @return the Staccato string
     */
    public String toString(boolean includeBars){
        StringBuilder staccato = new StringBuilder();
        Iterator<Entry<Rational, ? extends BarObj>> iIterator = barIterator();
        Entry<Rational, ? extends BarObj> i;
        while(iIterator.hasNext()){
            i = iIterator.next();
            staccato.append(i.getValue().toString() + " ");

        }
        return includeBars ? staccato.append("|").toString() : staccato.toString();
    }

    /**
     * Retrieves an iterator that iterates over chords only
     * @return the chord iterator
     */
    public Iterator<Entry<Rational, Chord>> chordIterator(){
        TreeSet<Entry<Rational, Chord>> ts = new TreeSet<Entry<Rational, Chord>>(Comparator.comparing(Entry::getKey));
        ts.addAll(chords.entrySet());
        return ts.iterator();
    }

    /**
     * Retrieves an iterator that iterates over rests and chords
     * @return the bar iterator
     */
    private Iterator<Entry<Rational, ? extends BarObj>> barIterator(){
        TreeSet<Entry<Rational, ? extends BarObj>> ts = new TreeSet<Entry<Rational, ? extends BarObj>>(Comparator.comparing(Entry::getKey));
        ts.addAll(rests.entrySet());
        ts.addAll(chords.entrySet());   
        return ts.iterator();
    }

    public ArrayList<Chord> getChords() {
        Collection<Chord> chordCollection = chords.values();
        return new ArrayList<>(chordCollection);
    }
    /**
     * @author brenskrz
     * Getter for Time Signature
     * @return the rational Time Signature
     */
    public Rational getTimeSignature() {
        return timeSignature;
    }
    /**
     * @author brenskrz
     * Getter for Time Signature as a String
     * @return TIme Signature as a String
     */
    public String getTimeSignatureString() {
        return timeSignature.getNumerator() + "/" + timeSignature.getDenominator();
    }
}
