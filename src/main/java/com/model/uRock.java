package com.model;

public class uRock {
    public static void main(String[] args){
        Note n = new Note(NoteValue.QUARTER, true, Instrument.GUITAR, PitchClass.C, 4);
        System.out.println(n);
    }
}
