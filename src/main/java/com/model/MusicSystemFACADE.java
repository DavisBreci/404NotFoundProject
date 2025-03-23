package com.model;

import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.midi.Sequence;

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
        System.out.println("Printing all users...");
        for (User u : prog.userList.getAllUsers()){
            System.out.println("\t" + (u instanceof Teacher ? "Teacher: " : "Student: ") +
                u.getFirstName() + " " + u.getLastName());
        }   
        System.out.println("Printing all teachers...");
        for (User u : prog.userList.getTeachers()){
            System.out.println("\t" + (u instanceof Teacher ? "Teacher: " : "Student: ") +
                u.getFirstName() + " " + u.getLastName());
        }
        System.out.println("Printing all non-teachers...");
        for (User u : prog.userList.getUsers()){
            System.out.println("\t" + (u instanceof Teacher ? "Teacher: " : "Student: ") +
                u.getFirstName() + " " + u.getLastName());
        }
        System.out.println("Checking for lessons...");
        {
            for (User u : prog.userList.getAllUsers()){
                System.out.println("\t" + (u instanceof Teacher ? "Teacher: " : "Student: ") +
                    u.getFirstName() + " " + u.getLastName());
                    System.out.println(u.getAssignedLessons());
            }   
            
        }

       
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

    public boolean signUp(boolean teacher, String first, String last, String email, String username, String password){
        if(userList.getUser(username, password) != null) return false; // This user exists
        userList.createUser(teacher, first, last, email, username, password);
        DataWriter.saveTeachers(userList.getTeachers());
        DataWriter.saveUsers(userList.getUsers());
        return login(username, password);
    }

    public ArrayList<Song> getLibrary(){
        return songList.getSongList();
    }

    public boolean assignLesson(int classNumber, Lesson lesson){
        if(user instanceof Teacher){
            Teacher myself = (Teacher)user;
            myself.assignLessons(classNumber, lesson);
            saveUserRelatedData();
            DataWriter.saveLessons(lessonList.getLessons());
            return true;
        }
        return false;
    }

    public Lesson createLesson(String lessonName){
        if(user instanceof Teacher){
            lessonList.addLesson(lessonName, null);
            return lessonList.getLesson(lessonName);
        } 
        return null;
    }

    /**
     * Attempts to a song from a MIDI file.
     * @param filename The MIDI file's name (with extension)
     * @param title the song's name
     * @param artist the song's author
     * @param key the song's musical key
     * @param difficultyLevel how difficult the song is
     * @param genre which genre the song falls under
     * @param instrument which instrument the song is for
     * @return whether the import was successful
     */
    public boolean importSong(String filename, String title, String artist, Key key, DifficultyLevel difficultyLevel,  String genre, Instrument instrument){
        Sequence rawMidi = DataLoader.loadSequence(filename);
        if(rawMidi == null) return false;
        songList.createSong(title, artist, genre, key, difficultyLevel, instrument, Score.midiToScore(rawMidi, 0, instrument));
        DataWriter.saveSongs(songList.getSongList());
        return true;
    }

    public void addSong(Song song, Lesson lesson){
        lesson.addSong(song);
    }

    public void removeSong(Song song, Lesson lesson){
        lesson.removeSong(song);
    }

    public ArrayList<Lesson> getLessons(){
        return lessonList.getLessons();
    }

    public void addPlaylist(String title, String description){
        if(user == null) return;
        playlistList.addPlaylist(title, user.getFirstName() + " " + user.getLastName(), description);
        user.getPlaylists().add(playlistList.getPlaylist(title, title, description, getLibrary(), 0));
    }

    public boolean removePlaylist(Playlist playlist){
        if(user != null)
            return user.getPlaylists().remove(playlist);
        return false;
    }

    public Song getSong(String title){
        return songList.getSongByTitle(title);
    }
    public ArrayList<Playlist> getMyPlaylists(){
        if(user != null)
            return user.getPlaylists();
        return null;
    }

    public void logout(){
        if(user == null) return;
        saveUserRelatedData();
        DataWriter.savePlaylists(playlistList.getPlaylists());
        DataWriter.saveLessons(lessonList.getLessons());
        DataWriter.saveSongs(songList.getSongList());
    }
    
    public int createClass(){
        if(user instanceof Teacher)
            return ((Teacher)user).createClass();
        else
            return -1;
    }

    public void addToClass(int classNumber, User... students){
        if(user instanceof Teacher){
            for(User student : students)
                ((Teacher)user).addStudent(classNumber, student);
            saveUserRelatedData();
        }
    }

    private void saveUserRelatedData(){
        DataWriter.saveUsers(userList.getUsers());
        DataWriter.saveTeachers(userList.getTeachers());
    }
    public ArrayList<User> getUser(){
        return userList.getAllUsers();
    }

    public User fetchUser(String username){
        return userList.getUser(username);
    }

    public ArrayList<User> getRoster(int classNumber){
        if(user instanceof Teacher)
            return ((Teacher)user).getClasses().get(classNumber);
        return null;
    }
}
