package com.model;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class Measure {
    private Instrument instrument;
    private Rational timeSignature;
    private TreeMap<Rational, Chord> chords;
    private TreeMap<Rational, Rest> rests;

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

    public boolean put(Rational offset, Note note){
        return true;
    }

    public boolean put(Rational offset, Chord note){
        return true;
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
        return "";
    }
}
