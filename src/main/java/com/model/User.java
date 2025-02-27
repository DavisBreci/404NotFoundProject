// stub complete
package com.model;
import java.util.*;

public class User {
    public UUID id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String username;
    protected String password;
    private int streak;
    private int songsPlayed;
    private ArrayList<Playlist> playlists;
    private ArrayList<Lesson> assignedLessons;

    public User(UUID id, String first, String last, String email, String user, String pass, int streak,
                int songsPlayed, ArrayList<Playlist> playlists, ArrayList<Lesson> assignedLessons){

    }
    public void resetPassword(String email, String newPass){
        if(this.email == email)
            this.password = newPass;
    }
    public boolean isValidUsername(String user){
        return true;
    }
    public boolean isValidPassword(String pass){
        return true;
    }
    public void updateStreak(boolean playedToday){

    }
    public void addPlayedSong(){
        ++songsPlayed;
    }
    public void assignLesson(Lesson lesson){
        assignedLessons.add(lesson);
    }
}
