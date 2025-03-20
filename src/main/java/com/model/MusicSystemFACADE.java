package com.model;

import java.util.ArrayList;

public class MusicSystemFACADE {
    private User user;
    public boolean login(String username, String password){
        return false;
    }
    public boolean signUp(String username){
        return false;
    }

    public ArrayList<Song> getLibrary(){
        return null;
    }

    public ArrayList<Song> importSong(){
        return null;
    }

    public void assignLesson(int classNumber, Lesson lesson){

    }
    public Lesson createLesson(String lessonName){
        return null;
    }

    public boolean importSong(String filename, String title, String artist, String genre, Instrument instrument){
        return false;
    }

    public void addSong(Song song, Lesson lesson){

    }

    public void removeSong(Song song, Lesson lesson){

    }

    public ArrayList<Lesson> getLessons(){
        return null;
    }

    public void addPlaylist(String title, String description){
        ;
    }
}
