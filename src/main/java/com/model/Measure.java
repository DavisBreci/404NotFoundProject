package com.model;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Measure {
    private Instrument instrument;
    private Rational timeSignature;
    private TreeMap<Rational, Chord> chords;
    private TreeMap<Rational, Rest> rests;

    public static void main(String [] args){
        Measure m = new Measure(Instrument.GUITAR, new Rational("4/4"));
        Chord gmaj = new Chord(NoteValue.HALF, false, Instrument.GUITAR);
        
        gmaj.put(new Note(PitchClass.G, 2), 0);
        gmaj.put(new Note(PitchClass.B, 2), 1);
        gmaj.put(new Note(PitchClass.D, 3), 2);
        gmaj.put(new Note(PitchClass.G, 3), 3);
        gmaj.put(new Note(PitchClass.B, 3), 4);
        gmaj.put(new Note(PitchClass.G, 4), 5);

        Chord dmaj = gmaj.deepCopy();
        dmaj.transpose(7);
        System.out.println(m.put(new Rational("0/1"), gmaj.deepCopy()));
        System.out.println(m.put(new Rational("1/2"), gmaj.deepCopy()));
        System.out.println(m.put(new Rational("1/8"), dmaj.deepCopy()));
        System.out.println(m);
    }

    public Measure(Instrument instrument, Rational timeSignature){
        this.instrument = instrument;
        this.timeSignature = timeSignature;
        this.chords = new TreeMap<Rational, Chord>();
        this.rests = new TreeMap<Rational, Rest>();
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
        Iterator<Entry<Rational, Chord>> iIterator = entryIterator();
        Entry<Rational, Chord> i;
        while(iIterator.hasNext()){
            i = iIterator.next();
            if(collides(newChord, i))
                return false;
        }
        if(!outOfBounds(offset, chord)){
            chords.put(offset, chord);
            return true;
        }
        return false;
    }

    private boolean outOfBounds(Rational offset, BarObj barObj){
        if(offset.compareTo(new Rational("0/1")) == -1)
            return true;
        Rational noteEnd = offset.deepCopy();
        noteEnd.plus(barObj.getDuration());
        Rational barEnd = timeSignature.deepCopy();
        barEnd.plus(new Rational(1, timeSignature.getDenominator()));
        if(noteEnd.compareTo(barEnd)  == 1)
            return true;
        return false;
    }

    public boolean remove(Rational offset, Chord chord){
        return true;
    }

    public Chord get(Rational offset){
        return null;
    }

    public Set<Map.Entry<Rational, Chord>>  chordEntrySet(){
        return null;
    }
    
    public Set<Map.Entry<Rational, Rest>>  restEntrySet(){
        return null;
    }

    public boolean isEmpty(){
        return true;
    }

    public void clear(){

    }

    public void updateRests(){

    }

    public int beatOf(Rational offset){
        return 1;
    }

    public boolean fill(Rational offset, Note n, boolean forward){
        return true;
    }

    public Note bite(Rational offset, Note n){
        return null;
    }

    public String toString(){
        StringBuilder staccato = new StringBuilder();
        Iterator<Entry<Rational, ? extends BarObj>> iIterator = barIterator();
        Entry<Rational, ? extends BarObj> i;
        while(iIterator.hasNext()){
            i = iIterator.next();
            staccato.append(i.getValue().toString() + " ");

        }
        return staccato.append("|").toString();
    }

    private Iterator<Entry<Rational, Chord>> entryIterator(){
        TreeSet<Entry<Rational, Chord>> ts = new TreeSet<Entry<Rational, Chord>>(Comparator.comparing(Entry::getKey));
        ts.addAll(chords.entrySet());
        return ts.iterator();
    }

    private Iterator<Entry<Rational, ? extends BarObj>> barIterator(){
        TreeSet<Entry<Rational, ? extends BarObj>> ts = new TreeSet<Entry<Rational, ? extends BarObj>>(Comparator.comparing(Entry::getKey));
        ts.addAll(chords.entrySet());
        ts.addAll(rests.entrySet());
        return ts.iterator();
    }
}
