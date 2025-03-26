package com.model;
import java.util.*;

public class Lesson {
    public final String id;
    private ArrayList<Song> songs;
    private String title;

    public Lesson(String id, ArrayList<Song> songs, String title){
        this.id = (id == null) ? UUID.randomUUID().toString(): id;
        if(songs != null)
            this.songs = songs;
        else 
            this.songs = new ArrayList<Song>();
        if(title.charAt(0) == '~'){
            this.title = title;
        }else{
            int tempNum = 0;
            char[] tempArr = this.id.toCharArray();
            for(Character c : tempArr)
                tempNum += (int)((1.0 + Math.random())*c);
            this.title = "~"+(tempNum)+"~ "+title;
        }
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
     * Getter for the Songs in the lessons
     * @author brenskrz
     * @return the arrayList of songs
     */
    public ArrayList<Song> getSongs() {
        return songs;
    }
    /**
     * Getter for the Lesson Title
     * @author brenskrz
     * @return The Lesson title
     */
    public String getTitle() {
        return title;
    }
}
