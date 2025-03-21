package com.model;
import java.util.*;

public class Song {
    public final String id;
    private String title;
    private String artist;
    private String genre;
    private Key key;
    private DifficultyLevel difficulty;
    private Instrument instrument;
    private Score score;

    public Song(String id, String title, String artist, String genre, Key key,
                DifficultyLevel difficulty, Instrument instrument, Score score){
        ID temp = id == null ? new ID() : new ID(id);
        this.id = temp.uuid;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.key = key;
        this.difficulty = difficulty;
        this.instrument = instrument;
        this.score = score;
    }
    public String getTitle() {
        return title;
    }
    public String getArtist() {
        return artist;
    }
    public String getGenre(){
        return genre;
    }
    public Key getKey(){
        return key;
    }
    public DifficultyLevel getDifficultyLevel(){
        return difficulty;
    }
    public Instrument getInstrument(){
        return instrument;
    }
    public Score getScore() {
        return score;
    }
    
}
