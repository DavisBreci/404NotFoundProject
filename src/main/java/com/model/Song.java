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
        this.id = (id == null) ? UUID.randomUUID().toString(): id;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.key = key;
        this.difficulty = difficulty;
        this.instrument = instrument;
        this.score = score;
    }
    /**
     * @author brenskrz
     * Authored the follwoing methods
     * Getter for Title
     * @return the song's title
     */
    public String getTitle() {
        return title;
    }
    /**
     * @author brenskrz
     * Getter for Artist
     * @return the song's artist
     */
    public String getArtist() {
        return artist;
    }
    /**
     * @author brenskrz
     * Getter for Genre
     * @return the song's genre
     */
    public String getGenre(){
        return genre;
    }
    /**
     * @author brenskrz
     * Getter for Key
     * @return the song's key
     */
    public Key getKey(){
        return key;
    }
    /**
     * @author brenskrz
     * Getter for Difficulty Level
     * @return the song's Difficulty Level
     */
    public DifficultyLevel getDifficultyLevel(){
        return difficulty;
    }
    /**
     * @author brenskrz
     * Getter for Instrument
     * @return the song's Instrument
     */
    public Instrument getInstrument(){
        return instrument;
    }
    /**
     * @author brenskrz
     * Getter for Score
     * @return the song's Score
     */
    public Score getScore() {
        return score;
    }
    
}
