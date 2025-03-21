package com.model;
import java.util.*;

public class Lesson {
    public final String id;
    private ArrayList<Song> songs;
    private String title;

    public Lesson(String id, ArrayList<Song> songs, String title){
        ID temp = id == null ? new ID() : new ID(id);
        this.id = temp.uuid;
        if(songs != null)
            this.songs = songs;
        else 
            this.songs = new ArrayList<Song>();
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
    public ArrayList<Song> getSongs() {
        return songs;
    }
    public String getTitle() {
        return title;
    }
}
