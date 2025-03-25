package com.model;
import java.util.*;

public class Lesson {
    public final String id;
    private ArrayList<Song> songs;
    private String title;

    public Lesson(String id, ArrayList<Song> songs, String title){
        ID temp = id == null ? new ID() : new ID(id);
        this.id = temp.uuid;
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
     * Getter for the Songs in the lessons
     * @return the arrayList of songs
     */
    public ArrayList<Song> getSongs() {
        return songs;
    }
    /**
     * Getter for the Lesson Title
     * @return The Lesson title
     */
    public String getTitle() {
        return title;
    }
}
