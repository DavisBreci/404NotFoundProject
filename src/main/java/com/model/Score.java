package com.model;

import java.util.ArrayList;

public class Score {
    private ID uuid;
    private Instrument instrument;
    private ArrayList<Measure> measures;
    private int tempo;

    public Score(String uuid, Instrument instrument){

    }

    public void add(Measure m){

    }

    public void add(int index, Measure m){

    }

    public boolean contains(Measure m){
        return true;
    }

    public int indexOf(Measure m){
        return 0;
    }

    public boolean remove(int index){
        return true;
    }

    public boolean remove(Measure m){
        return true;
    }
    
    public int size(){
        return 0;
    }

    public String toString(){
        return "";
    }
}
