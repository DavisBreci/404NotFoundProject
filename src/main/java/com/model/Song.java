package com.model;
import java.util.*;

public class Song {
<<<<<<< HEAD
    private String id;
=======
    public String id;
>>>>>>> ed247b528051d41c99ebf6d93f71f82adfa7a5cd
    private String title;
    private String artist;
    private String genre;
    private Key key;
    private DifficultyLevel difficulty;
    private Instrument instrument;
    private Score score;

    public Song(String id, String title, String artist, String genre, Key key,
                DifficultyLevel difficulty, Instrument instrument, Score score){
<<<<<<< HEAD
        
=======
        this.id = id;
>>>>>>> ed247b528051d41c99ebf6d93f71f82adfa7a5cd
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.key = key;
        this.difficulty = difficulty;
        this.instrument = instrument;
        this.score = score;
    }
<<<<<<< HEAD
    public Score getScore(){
        return score;
    }
=======

     /**
     * @author brenskrz
     */
    public String getId() {
        return id;
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
    public Score getScore(){
        return score;
    }
    
>>>>>>> ed247b528051d41c99ebf6d93f71f82adfa7a5cd
}
