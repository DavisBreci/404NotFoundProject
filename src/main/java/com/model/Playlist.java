package com.model;
import java.util.*;
/**
 * Class that represents a playlist of songs
 */
public class Playlist {
    public String id;
    private String title;
    private String author;
    private String description;
    private ArrayList<Song> songs;

    /**
     * Constructs a playlist
     * @param id the playlist's uuid (if null, a new uuid will be generated)
     * @param title the playlist's title
     * @param author the playlist's creator
     * @param description a brief description of the playlist
     * @param songs the songs contained by the playlists
     * @author Davis Breci
     */
    public Playlist(String id, String title, String author, String description, ArrayList<Song> songs){
        this.id = (id == null) ? UUID.randomUUID().toString(): id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.songs = songs;
    }
    /**
     * Sets the playlist's title
     * @param title the new title
     * @author Davis Breci
     */
    public void editTitle(String title){
        this.title = title;
    }

    /**
     * Adds a song to the playlist
     * @param song song to be added
     * @author Davis Breci
     */
    public void addSong(Song song){
        songs.add(song);
    }

    /**
     * Removes a song from the playlist
     * @param song 
     * @author Davis Breci
     */
    public void removeSong(Song song){
        for(int i = 0; i < songs.size(); i++){
            if(songs.get(i) == song){
                songs.remove(i);
                --i;
            }
        }
    }
    
    /**
     * Changes the playlist's description
     * @param description the new description
     */
    public void editDescription(String description){
        this.description = description;
    }

    /**
     * Puts the playlist in alphabetical order by title
     */
    public void sortByTitle(){
        songs.sort(new Comparator<Song>(){
            public int compare(Song o1, Song o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
    }

    /**
     * Puts the playlist in alphabetical order by artist
     */
    public void sortByArtist(){
        songs.sort(new Comparator<Song>(){
            public int compare(Song o1, Song o2) {
                return o1.getArtist().compareTo(o2.getArtist());
            }
        });
    }

    /**
     * Puts the playlist in alphabetical order by genre
     */
    public void sortByGenre(){
        songs.sort(new Comparator<Song>(){
            public int compare(Song o1, Song o2) {
                return o1.getGenre().compareTo(o2.getGenre());
            }
        });
    }
    
    /**
     * Sorts the playlist by key. Due to the structure of the 
     * key enumeration, songs in C major or A minor appear first, and
     * the subsequent keys progress through the circle of fifths clockwise.
     */
    public void sortByKey(){
        Comparator<Song> sortByKey = Comparator.comparingInt(s -> s.getKey().ordinal());
        Collections.sort(songs, sortByKey);
    }

    /**
     * Sorts the songs from easiest to hardest
     */
    public void sortByDifficulty(){
        Comparator<Song> sortByKey = Comparator.comparingInt(s -> s.getDifficultyLevel().ordinal());
        Collections.sort(songs, sortByKey);
    }
    /**
     * Authored the following methods
     * Getter for ID
     * @return the UUID for the playlist
     * @author brenskrz 
     */
    public String getId() {
        return this.id;
     }
     /**
      * Getter for Title
      * @return the title for the playlist
      * @author brenskrz
      */
     public String getTitle() {
        return title;
     }
     /**
      * Getter for Author
      * @return the author of the playlist
      * @author brenskrz
      */
     public String getAuthor() {
        return author;
     }
     /**
      * Getter for Description
      * @return the description for the playlist
      * @author brenskrz
      */
     public String getDescription() {
        return description;
     }
     /**
      * Getter for the songs in the playlist
      * @return the Array List of songs in the playlist
      * @author brenskrz
      */
     public ArrayList<Song> getSongs() {
        return songs;
     }
}
