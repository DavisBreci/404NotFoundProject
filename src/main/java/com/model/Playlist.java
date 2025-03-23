package com.model;
import java.util.*;

public class Playlist {
    public String id;
    private String title;
    private String author;
    private String description;
    private ArrayList<Song> songs;

    public Playlist(String id, String title, String author, String description, ArrayList<Song> songs){
        ID temp = id == null ? new ID() : new ID(id);
        this.id = temp.uuid;
        this.title = title;
        this.author = author;
        this.description = description;
        this.songs = songs;
    }
    public void editTitle(String title){
        this.title = title;
    }
    public void addSong(Song song){
        songs.add(song);
    }
    public void removeSong(Song song){
        for(int i = 0; i < songs.size(); i++){
            if(songs.get(i) == song){
                songs.remove(i);
                --i;
            }
        }
    }
    public void editDescription(String description){
        this.description = description;
    }
    public void sortByTitle(){
        songs.sort(new Comparator<Song>(){
            public int compare(Song o1, Song o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
    }
    public void sortByArtist(){
        songs.sort(new Comparator<Song>(){
            public int compare(Song o1, Song o2) {
                return o1.getArtist().compareTo(o2.getArtist());
            }
        });
    }
    public void sortByGenre(){
        songs.sort(new Comparator<Song>(){
            public int compare(Song o1, Song o2) {
                return o1.getGenre().compareTo(o2.getGenre());
            }
        });
    }
    public void sortByKey(){
        Comparator<Song> sortByKey = Comparator.comparingInt(s -> s.getKey().ordinal());
        Collections.sort(songs, sortByKey);
    }
    public void sortByDifficulty(){
        Comparator<Song> sortByKey = Comparator.comparingInt(s -> s.getDifficultyLevel().ordinal());
        Collections.sort(songs, sortByKey);
    }

    /**
     * @author brenskrz 
     * Authored the following methods
     * Getter for ID
     * @return the UUID for the playlist
     */
    public String getId() {
        return this.id;
     }
     /**
      * Getter for Title
      * @return the title for the playlist
      */
     public String getTitle() {
        return title;
     }
     /**
      * Getter for Author
      * @return the author of the playlist
      */
     public String getAuthor() {
        return author;
     }
     /**
      * Getter for Description
      * @return the description for the playlist
      */
     public String getDescription() {
        return description;
     }
     /**
      * Getter for the songs in the playlist
      * @return the Array List of songs in the playlist
      */
     public ArrayList<Song> getSongs() {
        return songs;
     }
}
