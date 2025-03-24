/**
 * Class for the end user, containing all their personal data and some managerial methods
 * @author Davis Breci
 */
package com.model;
import java.time.LocalDate;
import java.util.*;

public class User {
// UUID for json storage, constant
    public final String id;
// user's first name
    protected String firstName;
// user's last name
    protected String lastName;
// user's email address
    protected String email;
// user's selected username. must be unique.
    protected String username;
// user's selected password.
    protected String password;
// amount of days in a row a user has played
    protected int streak;
// total number of songs played by a user
    protected int songsPlayed;
// all of a user's playlists
    protected ArrayList<Playlist> playlists;
// all lessons assigned to a user from their enrolled classes
    protected ArrayList<Lesson> assignedLessons;
// last day that a user has played a song
    protected LocalDate lastPlayed;
    /**
     * @author Davis Breci
     * constructor. In all but one case, this is called by DataLoader, and thus expects every
     * instance variable as parameters
     * @param * every instance variable listed above
     */
    public User(String id, String first, String last, String email, String user,
             String pass, int streak, int songsPlayed, ArrayList<Playlist> playlists,
             ArrayList<Lesson> assignedLessons, LocalDate lastPlayed){
// when new users are created, a uuid must be created for them
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
    /**
     * @author Davis Breci
     * changes the password instance variable if the correct username is entered.
     * admittedly not super helpful because you must be logged in to change your password
     * @param email user entered email address. must match the instance variable
     * @param newPass new desired password
     */
    public void resetPassword(String email, String newPass){
        if(this.email == email && isValidPassword(newPass))
            this.password = newPass;
    }
    /**
     * @author Davis Breci
     * checks if a username is 1. unique 
     *                         2. within an acceptable amount of characters
     *                         3. contains no illegal characters
     * @param user String proposed username
     * @return boolean whether the name is valid or not
     */
    public static boolean isValidUsername(String user){
        if(user.length()<5 || user.length()>30)
            return false;
        for( char c : user.toCharArray())
            if(c < 33)
                return false;
        return !UserList.getInstance().contains(user);
    }
    /**
     * @author Davis Breci
     * checks is password is 1. within acceptable amount of characters 
     *                       2. contains a capital letter, special character, and number
     * @param pass proposed password. need not be unique
     * @return boolean whether or not the password is acceptable
     */
    public static boolean isValidPassword(String pass){
        if(pass.length()<5 || pass.length()>30)
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
    /**
     * @author Davis Breci
     * updates user streak, either keeping the same day, incrementing by one, or resetting to 0
     */
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
    /**
     * @author Davis Breci
     * increments every time a song is played
     */
    public void addPlayedSong(){
        ++songsPlayed;
    }
    /**
     * @author Davis Breci
     * adds a copy of a lesson to the user's assigned lessons list
     * @param lesson Lesson object created and assigned by teacher
     */
    public void assignLesson(Lesson lesson){
        assignedLessons.add(lesson);
    }
    /**
     * @author brenskrz
     * Getter for firstame
     * @return the user's first name
     */
    public String getFirstName() {
        return this.firstName;
    }
    /**
     * @author brenskrz
     * Getter for lastName
     * @return the users's last name
     */
    public String getLastName() {
        return this.lastName;
    }
    /**
     * @author brenskrz
     * Getter for username
     * @return the users's username
     */
    public String getUsername(){
        return username;
    }
    /**
     * @author brenskrz
     * Getter for password
     * @return the users's password
     */
    public String getPassword(){
        return password;
    }
    /**
     * @author brenskrz
     * Getter for e-mail
     * @return the users's email
     */
    public String getEmail(){
        return email;
    }
    /**
     * @author brenskrz
     * Getter for streak
     * @return the users's streak
     */
    public int getStreak(){
        return streak;
    }
    /**
     * @author brenskrz
     * Getter for songsPlayed
     * @return the songs the user has played
     */
    public int getSongsPlayed(){
        return songsPlayed;
    }
    /**
     * @author brenskrz
     * Getter for assignedLessons
     * @return the Array List of the users's assigned lessons
     */
    public ArrayList<Lesson> getAssignedLessons() {
        return assignedLessons;
    }
    /**
     * @author brenskrz
     * Getter for playlists
     * @return the Array List of the  users's playlists
     */
    public ArrayList<Playlist> getPlaylists(){
        return playlists;
    }
}