package com.model;

import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.midi.Sequence;

import org.jfugue.player.Player;
/**
 * @author Christopher Ferguson
 * A facade that hides the various functionalities of our program
 */
public class MusicSystemFACADE {
    private User user;
    private static MusicSystemFACADE musicSystemFACADE;
    private UserList userList;
    private LessonList lessonList;
    private SongList songList;
    private PlaylistList playlistList;
    /**
     * Constructs a MusicSystemFACADE(). All members other than user are singletons,
     * so there's no need for it to have parameters.
     */
    private MusicSystemFACADE(){
        userList = UserList.getInstance();
        lessonList = LessonList.getInstance();
        songList = SongList.getInstance();
        playlistList = PlaylistList.getInstance();
    }

    /**
     * Retrieves the user who is currently logged in
     * @return the user
     */
    public User getCurrentUser(){
        return user;
    }

    /**
     * Retrieves the instance of MusicSystemFACADE
     * @return the MusicSystemFACADE
     */
    public static MusicSystemFACADE getInstance(){
        if(musicSystemFACADE == null)
            musicSystemFACADE = new MusicSystemFACADE();
        return musicSystemFACADE;
    }

    /**
     * Attempts to login
     * @param username the user's handle
     * @param password the user's password
     * @return whether the login was successful
     */
    public boolean login(String username, String password){
        User user = userList.getUser(username, password);
        if(user == null)
            return false;
        this.user = user;
        return true;
    }

    /**
     * Attempts to register an account with the specified user data
     * @param teacher whether the user is signing up as a teacher
     * @param first the user's first name
     * @param last the user's last name
     * @param email the user's email
     * @param username the user's username
     * @param password the user's password
     * @return whether the account was registered
     */
    public boolean signUp(boolean teacher, String first, String last, String email, String username, String password){
        if(userList.contains(username)) return false; // This user exists
        if(!userList.createUser(teacher, first, last, email, username, password)) return false;
        DataWriter.saveTeachers(userList.getTeachers());
        DataWriter.saveUsers(userList.getUsers());
        return login(username, password);
    }

    /**
     * Retrieves all songs loaded into the program
     * @return the library
     */
    public ArrayList<Song> getLibrary(){
        return songList.getSongList();
    }

    /**
     * Attempts to assign a lesson to a class. Only works if you're logged in as a teacher.
     * @param classNumber the number of the class
     * @param lesson the lesson to be assigned
     * @return whether the assignment was successful
     */
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

    /**
     * Attempts to create a lesson
     * @param lessonName the lesson's name
     * @return the lesson or null
     */
    public Lesson createLesson(String lessonName){
        if(user instanceof Teacher){
            return lessonList.createLesson(null, lessonName);
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

    /**
     * Attempts to add a song to a lesson
     * @param song the song to be added
     * @param lesson the lesson
     */
    public void addSong(Song song, Lesson lesson){
        lesson.addSong(song);
    }

    /**
     * Attempts to remove a song from a lesson
     * @param song the song to be removed
     * @param lesson the lesson
     */
    public void removeSong(Song song, Lesson lesson){
        lesson.removeSong(song);
    }

    /**
     * Retrieves a list of all lessons in the program. 
     * @return the lessons
     */
    public ArrayList<Lesson> getLessons(){
        return lessonList.getLessons();
    }

    /**
     * Attempts to add a song to the library
     * @param s the song to be added
     * @return whether the addition was successful
     */
    public boolean addSongToLibrary(Song s){
        if(!songList.add(s))
            return false;
        DataWriter.saveSongs(songList.getSongList());
        return true;
    }

    /**
     * Attempts to create a new playlist
     * @param title the playlist's name
     * @param description a brief description
     */
    public void addPlaylist(String title, String description){
        if(user == null) return;
        playlistList.createPlaylist(title, user.getFirstName() + " " + user.getLastName(), description);
        user.getPlaylists().add(playlistList.getPlaylist(title, title, description, getLibrary(), 0));
    }

    /**
     * Attempts to remove a playlist from the user's library
     * @param playlist the playlist to be removed
     * @return whether the removal was successful
     */
    public boolean removePlaylist(Playlist playlist){
        if(user != null)
            return user.getPlaylists().remove(playlist);
        return false;
    }

    /**
     * Retrieves a song based on its title
     * @param title the song's title
     * @return the song or null
     */
    public Song getSong(String title){
        return songList.getSongByTitle(title);
    }

    /**
     * Retrieves all songs by the specified artist
     * @param artist the artist 
     * @return the matching songs
     */
    public ArrayList<Song> getSongsByArtist(String artist){
        return songList.getSongsByArtist(artist);
    }

    /**
     * Retrieves all songs with the specified difficulty level
     * @param difficultyLevel the desired difficulty level
     * @return the matching songs
     */
    public ArrayList<Song> getSongsByDifficulty(DifficultyLevel difficultyLevel){
        return songList.getSongsByDifficulty(difficultyLevel);
    }

    /**
     * Retrieves all songs with the specified genre
     * @param genre the desired genre
     * @return the matching songs
     */
    public ArrayList<Song> getSongsByGenre(String genre){
        return songList.getSongsByGenre(genre);
    }

    /**
     * Retrieves all songs with the specified key
     * @param genre the desired key
     * @return the matching songs
     */
    public ArrayList<Song> getSongsByKey(Key key){
        return songList.getSongsByKey(key);
    }

    /**
     * Retrieves the user's personal playlists
     * @return the user's playlists
     */
    public ArrayList<Playlist> getMyPlaylists(){
        if(user != null)
            return user.getPlaylists();
        return null;
    }

    /**
     * Saves info on exit
     */
    public void logout(){
        if(user == null) return;
        saveUserRelatedData();
        DataWriter.savePlaylists(playlistList.getAllPlaylists());
        DataWriter.saveLessons(lessonList.getLessons());
        DataWriter.saveSongs(songList.getSongList());
// I added this because I'm pretty sure there'd be a data leak otherwise -Davis
        user = null;
    }
    
    /**
     * Attempts to create a class. Only for teachers.
     * @return the number of the created class. Returns -1 if creation failed
     */
    public int createClass(){
        if(user instanceof Teacher)
            return ((Teacher)user).createClass();
        else
            return -1;
    }

    /**
     * Adds an arbitary number of students to the specified class
     * @param classNumber the class to be populated
     * @param students the students to be added
     */
    public void addToClass(int classNumber, User... students){
        if(classNumber < 0) return;
        if(user instanceof Teacher){
            for(User student : students)
                ((Teacher)user).addStudent(classNumber, student);
            saveUserRelatedData();
        }
    }

    /**
     * Saves all data related to users
     */
    private void saveUserRelatedData(){
        DataWriter.saveUsers(userList.getUsers());
        DataWriter.saveTeachers(userList.getTeachers());
    }

    /**
     * Retrieves a list of all registered users
     * @return the users
     */
    public ArrayList<User> getUsers(){
        return userList.getAllUsers();
    }

    /**
     * Retrieves a reference to the user with the specified name
     * @param username the name of the user
     * @return the user or null
     */
    public User fetchUser(String username){
        return userList.getUser(username);
    }

    /**
     * Retrieves a list of all users in the specified class
     * @param classNumber the number of the class
     * @return the users in the class
     */
    public ArrayList<User> getRoster(int classNumber){
        if(classNumber < 0) return null;
        if(user instanceof Teacher)
            return ((Teacher)user).getClasses().get(classNumber);
        return null;
    }

    /**
     * Blocking method that plays a song and updates the user's streak
     * @param s the song to be played
     * @author Christopher Ferguson, Davis Breci
     */
    public void playSong(Song s){
        if(user == null || s == null) return;
        Player songPlayer = new Player();
        Score toPlay = s.getScore();
        songPlayer.play(toPlay.getSequence(0, toPlay.size(), null, 1));
        user.updateStreak();
        user.addPlayedSong();
        saveUserRelatedData(); 
    }

    /**
     * Blocking method that plays a song and updates the user's streak
     * @param s the song to be played
     * @author Christopher Ferguson, Davis Breci
     */
    public void playSong(Song s, int start, int end){
        if(user == null || s == null) return;
        Score toPlay = s.getScore();
        Player songPlayer = new Player();
        songPlayer.play(toPlay.getSequence(start, end, null, 1));
        user.updateStreak();
        user.addPlayedSong();
        saveUserRelatedData(); 
    }
}
