package com.model;

import java.util.ArrayList;

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

    public boolean signUp(String first, String last, String email, String username, String password){
        if(userList.getUser(username, password) != null) return false; // This user exists
        userList.createUser(first, last, email, username, password);
        return login(username, password);
    }

    public ArrayList<Song> getLibrary(){
        return songList.getSongList();
    }

    public boolean assignLesson(int classNumber, Lesson lesson){
        if(user instanceof Teacher){
            Teacher myself = (Teacher)user;
            myself.assignLessons(classNumber, lesson);
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
        return true;
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
