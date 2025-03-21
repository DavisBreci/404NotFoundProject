package com.model;

import java.util.ArrayList;

public class MusicSystemFACADE {
    private User user;
    private static MusicSystemFACADE musicSystemFACADE;
    private UserList userList;
    private LessonList lessonList;
    private SongList songList;
    private PlaylistList playlistList;

    public static void main(String[] args){
        MusicSystemFACADE prog = MusicSystemFACADE.getInstance();
        System.out.println("/////////////////////////////////");
        String username = "ctferg";
        String password = "247sucks";
        System.out.println("Attempting to login as \"" + username + "\"...");
        System.out.println("Login success: " + prog.login(username, password));
    }
    private MusicSystemFACADE(){
        userList = UserList.getInstance();
        lessonList = LessonList.getInstance();
        songList = SongList.getInstance();
        playlistList = PlaylistList.getInstance();
    }

    public static MusicSystemFACADE getInstance(){
        if(musicSystemFACADE == null)
            musicSystemFACADE = new MusicSystemFACADE();
        return musicSystemFACADE;
    }

    public boolean login(String username, String password){
        User user = userList.getUser(username, password);
        if(user == null)
            return false;
        this.user = user;
        return true;
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
