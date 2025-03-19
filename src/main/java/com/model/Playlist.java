package com.model;
import java.util.*;

public class Playlist {
    public UUID id;
    private String title;
    private String author;
    private String description;
    private ArrayList<Song> songs;

    public Playlist(UUID id, String title, String author, String description, ArrayList<Song> songs){
        this.id = id;
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

    }
    public void sortByArtist(){
        
    }
    public void sortByGenre(){
        
    }
    public void sortByKey(){
        
    }
    public void sortByDifficulty(){
        
    }

    /**
     * @author brenskrz
     */

     public UUID getId() {
        return this.id;
     }
     public String getTitle() {
        return title;
     }
     public String getAuthor() {
        return author;
     }
     public String getDescription() {
        return description;
     }
     public ArrayList<Song> getSongs() {
        return songs;
     }
     
}
