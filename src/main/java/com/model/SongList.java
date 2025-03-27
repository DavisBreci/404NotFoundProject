package com.model;
import java.util.ArrayList;

/**
 * Class that manages the program's songs
 */
public class SongList {
    private static SongList songList;
    private ArrayList<Song> songs;

    /**
     * Pulls data from the json folder to construct the SongList
     * @author Davis Breci
     */
    private SongList() {
        songs = DataLoader.getAllSongs();
    }

    /**
     * Retrieves an instance of the SongList singleton
     * @return the songList instance
     * @author Davis Breci
     */
    public static SongList getInstance() {
        if(songList == null) {
            songList = new SongList();
        }
        return songList;
    }

    /**
     * Retrieves the song list's array of song. Not to be confused with getInstance()
     * @return the program's musical library
     * @author Davis Breci
     */
    public ArrayList<Song> getSongList() {
        return songs;
    }

    /**
     * Retrieves a song with the title specified
     * @param name the title to be searchhed for
     * @return the song (if it exists)
     * @author Davis Breci
     */
    public Song getSongByTitle(String name) {
        for(Song s : songs)
            if(s.getTitle().equals(name))
                return s;
        return null;
    }
    
    /**
     * Retrieves all songs of a certain difficulty
     * @param dv the desired level of difficulty
     * @return all songs of the specified difficulty
     * @author Davis Breci
     */
    public ArrayList<Song> getSongsByDifficulty(DifficultyLevel dv) {
        ArrayList<Song> ret = new ArrayList<Song>();
        for(Song s : songs)
            if(s.getDifficultyLevel() == dv)
                ret.add(s);
        return ret;
    }

    /**
     * Retrievevs all songs written in the given musical key
     * @param k a musical key
     * @return all songs in the key
     * @author Davis Breci
     */
    public ArrayList<Song> getSongsByKey(Key k) {
        ArrayList<Song> ret = new ArrayList<Song>();
        for(Song s : songs)
            if(s.getKey() == k)
                ret.add(s);
        return ret;
    }

    /**
     * Retrieves all songs in a genre
     * @param genre the genre to be searched for
     * @return all songs in the given genre
     * @author Davis Breci
     */
    public ArrayList<Song> getSongsByGenre(String genre) {
        ArrayList<Song> ret = new ArrayList<Song>();
        for(Song s : songs)
            if(s.getGenre().equals(genre))
                ret.add(s);
        return ret;
    }

    /**
     * Retrieves all songs by an artist
     * @param artist the artis whose songs you want
     * @return the songs by the artist
     * @author Christopher Ferguson & Ryan Mazzella
     */
    public ArrayList<Song> getSongsByArtist(String artist){
        ArrayList<Song> ret = new ArrayList<Song>();
        for(Song s : songs)
            if(s.getArtist().equals(artist))
                ret.add(s);
        return ret;
    }

    /**
     * Creates a new song based on the input and adds it to the list. 
     * @param title the song's title
     * @param artist the creator of the song
     * @param genre the genre of the song
     * @param key the song's musical key
     * @param difficulty the song's difficulty
     * @param instrument the instrument the song is written for
     * @param score the song's sheet music
     */
    public void createSong(String title, String artist, String genre, Key key, DifficultyLevel difficulty,
                        Instrument instrument, Score score) {
        songs.add(new Song(null, title, artist, genre, key, difficulty, instrument, score));
    }

    /**
     * Attempts to add a song to the list
     * @param s the song to be added
     * @return whether the addition was successful
     * @author Christopher Ferguson
     */
    public boolean add(Song s){
        return songs.add(s);
    }

    /**
     * Attempts to remove a song from the list
     * @param s the song to be removed
     * @return whether the removal was successful
     * @author Christopher Ferguson
     */
    public boolean removeSong(Song s){
        return songs.remove(s);
    }
}
