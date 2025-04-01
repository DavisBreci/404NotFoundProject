package com.model;
import java.util.*;

/**
 * Class representing a musical lesson that consists of many songs
 */
public class Lesson {
    public final String id;
    private ArrayList<Song> songs;
    private String title;

    /**
     * Constructs a lesson
     * @param id the lesson's uuid (generates a new uuid if null)
     * @param songs the songs that make up the lesson
     * @param title the lesson's title
     * @author Davis Breci
     */
    public Lesson(String id, ArrayList<Song> songs, String title){
        this.id = (id == null) ? UUID.randomUUID().toString(): id;
        if(title == null || title.length() > 30)
            throw new IllegalArgumentException();
        if(songs == null)
            songs = new ArrayList<Song>();
        else if(songs.contains(null))
            throw new IllegalArgumentException();
        else
            this.songs = songs;
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

    /**
     * Adds a song to the lesson
     * @param song the song to be added
     * @author Davis Breci
     */
    public void addSong(Song song){
        if(song != null)
            songs.add(song);
    }

    /**
     * Attempts to remove a song from the lesson
     * @param song the song to be removed
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
     * Attempts to remove a song at a specified index from the lesson
     * @author Davis Breci
     * @param index int value of its location in the ArrayList
     */
    public void removeSong(int index){
        if(index >= 0 && index < songs.size())
            songs.remove(index);
    }
    /**
     * Getter for the Songs in the lessons
     * @return the arrayList of songs
     * @author brenskrz
     */
    public ArrayList<Song> getSongs() {
        return songs;
    }
    /**
     * Getter for the Lesson Title
     * @return The Lesson title
     * @author brenskrz
     */
    public String getTitle() {
        return title;
    }
}
