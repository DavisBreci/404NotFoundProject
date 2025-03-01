package com.model;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Measure {
    private Instrument instrument;
    private Rational timeSignature;
    private TreeMap<Rational, Chord> chords;
    private TreeMap<Rational, Rest> rests;

    public Measure(Instrument instrument, Rational timeSignature){

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
