package com.model;

/**
 * @author brenskrz
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import java.time.LocalDate;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;




public class DataWriter extends DataConstants {

    public static void saveUsers() {
        // UserList userList = UserList.getInstance();
        // ArrayList<User> users = userList.getUsers();

        // hard code testing
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(USER_ID, USER_FIRST_NAME, USER_LAST_NAME, USER_EMAIL, USER_ASSIGNED_LESSONS, USER_PASSWORD, 0, 0, null, null, null));

        JSONArray jsonUsers = new JSONArray();

        for(int i = 0; i<users.size(); i++) {
            jsonUsers.add(getUserJSON(users.get(i)));
        }

        try (FileWriter file = new FileWriter(USER_TEMP_FILE_NAME)) {
            file.write(jsonUsers.toJSONString());
            file.flush();

        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static JSONObject getUserJSON(User user) {
        
        JSONObject userDetails = new JSONObject();
        userDetails.put(USER_ID, user.getId().toString());
        userDetails.put(USER_USERNAME, user.getUsername());
        userDetails.put(USER_PASSWORD, user.getPassword());
        userDetails.put(USER_EMAIL, user.getEmail());
        userDetails.put(USER_FIRST_NAME, user.getFirstName());
        userDetails.put(USER_LAST_NAME, user.getLastName());
        userDetails.put(USER_STREAK, user.getStreak());
        userDetails.put(USER_SONGS_PLAYED, user.getSongsPlayed());
        userDetails.put(USER_ASSIGNED_LESSONS, user.getAssignedLessons());
        userDetails.put(USER_PLAYLISTS, user.getPlaylists());

        return userDetails;
    }

    // public static void main(String args[]) {
    //     DataWriter.saveUsers();
    // }


    public static void saveTeachers() {
        UserList teacherList = UserList.getInstance();
        ArrayList<User> teachers = teacherList.getUsers();
        
        JSONArray jsonTeachers = new JSONArray();

        for(int i = 0; i<teachers.size(); i++) {
            jsonTeachers.add(getUserJSON(teachers.get(i)));
        }

        try (FileWriter file = new FileWriter(TEACHER_FILE_NAME)) {
            file.write(jsonTeachers.toJSONString());
            file.flush();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject getTeacherJSON(Teacher teacher) {
        
        JSONObject teacherDetails = new JSONObject();
        teacherDetails.put(TEACHER_CLASSES, teacher.getClasses());
        teacherDetails.put(TEACHER_LESSONS, teacher.getLessons());
        

        return teacherDetails;
    }

    public static void saveSongs() {
        SongList songList = SongList.getInstance();
        ArrayList<Song> songs = songList.getSongs();

         // hard code testing
        // ArrayList<Song> songs = new ArrayList<>();
        // Instrument testInstrument = Instrument.ACOUSTIC_BASS;
        // int testTempo = 120;
        // Score testScore = new Score(null, testInstrument, testTempo);
        // songs.add(new Song(SONG_ID, SONG_TITLE, SONG_ARTIST, SONG_GENRE, Key.AMAJOR_GbMINOR, DifficultyLevel.ADVANCED, Instrument.ACOUSTIC_BASS, testScore));

        JSONArray jsonSongs = new JSONArray();

        for(int i = 0; i<songs.size(); i++) {
            jsonSongs.add(getSongJSON(songs.get(i)));
        }

        try (FileWriter file = new FileWriter(SONG_TEMP_FILE_NAME)) {
            file.write(jsonSongs.toJSONString());
            file.flush();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject getSongJSON(Song song) {
        JSONObject songDetails = new JSONObject();

        songDetails.put(SONG_ID, song.getId().toString());
        songDetails.put(SONG_TITLE, song.getTitle());
        songDetails.put(SONG_ARTIST, song.getArtist());
        songDetails.put(SONG_GENRE, song.getGenre());
        songDetails.put(SONG_DIFFICULTY_LEVEL, song.getDifficultyLevel().toString());
        songDetails.put(SONG_KEY, song.getKey().toString());
        songDetails.put(SONG_INSTRUMENT, song.getInstrument().toString());
        songDetails.put(SONG_SCORE, song.getScore().toString());

        return songDetails;
    }

    // public static void main(String args[]) {
    //     DataWriter.saveSongs();
    // }
    

    public static void savePlaylists() {
        PlaylistList playlistList = PlaylistList.getInstance();
        ArrayList<Playlist> playlists = playlistList.getPlaylists();

        //hard code testing
        // PlaylistList playlistList = new PlaylistList();
        // Playlist playlist1 = new Playlist(UUID.randomUUID(), "brendan's playlist", "brendan", "a great playlist", new ArrayList<>());
        // Playlist playlist2 = new Playlist(UUID.randomUUID(), "ryan's playlist", "ryan", "an amazing playlist", new ArrayList<>());

        // playlistList.addPlaylist(playlist1.getTitle(), playlist1.getAuthor(), playlist1.getDescription());
        // playlistList.addPlaylist(playlist2.getTitle(), playlist2.getAuthor(), playlist2.getDescription());
        // ArrayList<Playlist> playlists = playlistList.getPlaylists();

        JSONArray jsonPlaylists = new JSONArray();


        for( Playlist playlist : playlists) {
            jsonPlaylists.add(getPlaylistJSON(playlist));
        }

        try (FileWriter file = new FileWriter(PLAYLIST_FILE_TEMP_NAME)) {
            file.write(jsonPlaylists.toJSONString());
            file.flush();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public static JSONObject getPlaylistJSON(Playlist playlist) {
        JSONObject playlistDetails = new JSONObject();

        playlistDetails.put(PLAYLIST_ID, playlist.getId().toString());
        playlistDetails.put(PLAYLIST_TITLE, playlist.getTitle());
        playlistDetails.put(PLAYLIST_AUTHOR, playlist.getAuthor());
        playlistDetails.put(PLAYLIST_DESCRIPTION, playlist.getDescription());
        playlistDetails.put(PLAYLIST_SONGS, playlist.getSongs());

        return playlistDetails;
    }

    // public static void main(String args[]) {
    //         DataWriter.savePlaylists();
    //     }

    public static void saveLessons() {
        LessonList lessonList = LessonList.getInstance();
        ArrayList<Lesson> lessons = lessonList.getLessons();

        JSONArray jsonLessons = new JSONArray();

        //hard code for testing
        // LessonList lessonList = new LessonList();
        // ArrayList<Song> lessonSongs1 = new ArrayList<>();
        // ArrayList<Song> lessonSongs2 = new ArrayList<>();

        // lessonSongs1.add(new Song("9", LESSONS_TITLE, SONG_ARTIST, SONG_GENRE, null, null, null, null));
        // lessonSongs1.add(new Song("10", LESSONS_TITLE, SONG_ARTIST, SONG_GENRE, null, null, null, null));
        // lessonSongs2.add(new Song("11", LESSONS_TITLE, SONG_ARTIST, SONG_GENRE, null, null, null, null));
        // lessonSongs2.add(new Song("12", LESSONS_TITLE, SONG_ARTIST, SONG_GENRE, null, null, null, null));

        // lessonList.addLesson("Lesson 1", lessonSongs1);
        // lessonList.addLesson("Lesson 2", lessonSongs2);

        // ArrayList<Lesson> lessons = lessonList.getLessons();

        for(Lesson lesson : lessons) {
            jsonLessons.add(getLessonJSON((lesson)));
            
        }

        try (FileWriter file = new FileWriter(LESSONS_FILE_TEMP_NAME)) {
            file.write(jsonLessons.toJSONString());
            file.flush();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static JSONObject getLessonJSON(Lesson lesson) {
        JSONObject lessonDetails = new JSONObject();

        lessonDetails.put(LESSONS_ID, lesson.getId().toString());
        lessonDetails.put(LESSONS_SONGS, lesson.getSongs().toString());
        lessonDetails.put(LESSONS_TITLE, lesson.getTitle());

        return lessonDetails;
    }
    // public static void main(String args[]) {
    //     DataWriter.saveLessons();
    // }

    public static void saveScores() {
        ScoreList
    }



}
