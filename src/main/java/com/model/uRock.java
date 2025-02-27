package com.model;

public class uRock {
    public static void main(String[] args){
        Note n = new Note(Instrument.GUITAR, NoteValue.QUARTER, true, PitchClass.C, 4);
        System.out.println(n);
    }
}
