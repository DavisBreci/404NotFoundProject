// class complete
package com.model;
import java.time.LocalDate;
import java.util.*;

public class User {
    public String id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String username;
    protected String password;
    private int streak;
    private int songsPlayed;
    private ArrayList<Playlist> playlists;
    private ArrayList<Lesson> assignedLessons;
    private LocalDate lastPlayed;

    public User(String id, String first, String last, String email, String user,
             String pass, int streak, int songsPlayed, ArrayList<Playlist> playlists,
             ArrayList<Lesson> assignedLessons, LocalDate lastPlayed){
        ID temp = id == null ? new ID() : new ID(id);
        this.id = temp.uuid;
        this.firstName = first;
        this.lastName = last;
        this.email = email;
        this.username = user;
        this.password = pass;
        this.streak = streak;
        this.songsPlayed = songsPlayed;
        this.playlists = playlists;
        this.assignedLessons = assignedLessons;
        this.lastPlayed = lastPlayed;
    }
    public void resetPassword(String email, String newPass){
        if(this.email == email && isValidPassword(newPass))
            this.password = newPass;
    }
    public boolean isValidUsername(String user){
        if(user.length()<5 || user.length()>20)
            return false;
        for( char c : user.toCharArray())
            if(c < 33)
                return false;
//      return !UserList.getInstance().contains(user); //re-add when UserList is written
        return true;
    }
    public boolean isValidPassword(String pass){
        if(pass.length()<5 || pass.length()>20)
            return false;
        boolean capitalLetter = false;
        boolean specialCharacter = false;
        boolean number = false;
        for(char c : pass.toCharArray()){
            if(c < 33)
                return false;
            if(c >= 48 && c <= 57)
                number = true;
            else if(c >= 65 && c <= 90)
                capitalLetter = true;
            else if(c >= 97 && c <= 122)
                ;
            else
                specialCharacter = true;
        }
        return number && capitalLetter && specialCharacter;
    }
    public void updateStreak(){
//same day
        if(lastPlayed.compareTo(LocalDate.now()) == 0){
            ;
//one day has passed
        }else if(lastPlayed.plusDays(1).compareTo(LocalDate.now())==0){
            lastPlayed = LocalDate.now();
            ++streak;
//reset streak
        }else{
            streak = 0;
        }
    }
    public void addPlayedSong(){
        ++songsPlayed;
    }
    public void assignLesson(Lesson lesson){
        assignedLessons.add(lesson);
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getEmail(){
        return email;
    }
    public int getStreak(){
        return streak;
    }
    public int getSongsPlayed(){
        return songsPlayed;
    }
    public ArrayList<Playlist> getPlaylists(){
        return playlists;
    }
}