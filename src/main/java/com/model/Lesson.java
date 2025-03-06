package com.model;
import java.util.*;

public class Lesson {
    public UUID id;
    private ArrayList<Song> songs;
    private String title;

    public Lesson(UUID id, ArrayList<Song> songs, String title){
        this.id = id;
        this.songs = songs;
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
    /**
     * @author brenskrz
     */
    public UUID getId() {
        return id;
    }
    public ArrayList<Song> getSongs() {
        return songs;
    }
    public String getTitle() {
        return title;
    }
}
