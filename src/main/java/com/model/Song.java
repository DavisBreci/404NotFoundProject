package com.model;
import java.util.*;

public class Song {
    private String id;
    private String title;
    private String artist;
    private String genre;
    private Key key;
    private DifficultyLevel difficulty;
    private Instrument instrument;
    private Score score;

    public Song(String title, String artist, String genre, Key key,
                DifficultyLevel difficulty, Instrument instrument, Score score){
        
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.key = key;
        this.difficulty = difficulty;
        this.instrument = instrument;
        this.score = score;
    }
    public Score getScore(){
        return score;
    }
}
