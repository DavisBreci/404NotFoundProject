package com.model;
import java.util.*;

/**
 * A class that represents a song's metadata. Not to be confused with Score, which represents a song's notation.
 */
public class Song {
    public final String id;
    private String title;
    private String artist;
    private String genre;
    private Key key;
    private DifficultyLevel difficulty;
    private Instrument instrument;
    private Score score;

    /**
     * Constructs a song
     * @param id the song's ID (can be left null to generate a new ID)
     * @param title the song's title
     * @param artist the creator of the song
     * @param genre the genre the song falls under
     * @param key the song's key
     * @param difficulty the song's difficulty
     * @param instrument the song's instrument
     * @param score the song's sheet music
     */
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
     * Getter for Title
     * @return the song's title
     * @author brenskrz
     */
    public String getTitle() {
        return title;
    }
    /**
     * Getter for Artist
     * @return the song's artist
     * @author brenskrz
     */
    public String getArtist() {
        return artist;
    }
    /**
     * Getter for Genre
     * @return the song's genre
     * @author brenskrz
     */
    public String getGenre(){
        return genre;
    }
    /**
     * Getter for Key
     * @return the song's key
     * @author brenskrz
     */
    public Key getKey(){
        return key;
    }
    /**
     * Getter for Difficulty Level
     * @return the song's Difficulty Level
     * @author brenskrz
     */
    public DifficultyLevel getDifficultyLevel(){
        return difficulty;
    }
    /**
     * Getter for Instrument
     * @return the song's Instrument
     * @author brenskrz
     */
    public Instrument getInstrument(){
        return instrument;
    }
    /**
     * Getter for Score
     * @return the song's Score
     * @author brenskrz
     */
    public Score getScore() {
        return score;
    }
    
}
