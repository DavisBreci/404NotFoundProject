package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import java.time.LocalDate;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;




public class DataWriter extends DataConstants {

    public static void saveUsers() {
        UserList userList = UserList.getInstance();
<<<<<<< HEAD
        ArrayList<User> users = userList.getAllUsers();
=======
        ArrayList<User> users = userList.getUsers();
>>>>>>> 1178810767975bb7fc1e6cd4c1b0a70bdd91526a
        
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

    public static void main(String args[]) {
        DataWriter.saveUsers();
    }


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

//     public static void saveSongs() {
//         SongList songList = SongList.getInstance();
//         ArrayList<Song> songs = songList.getSongs();

//         JSONArray jsonSongs = new JSONArray();

//         for(int i = 0; i<songs.size(); i++) {
//             jsonSongs.add(getSongJSON(songs.get(i)));
//         }

//         try (FileWriter file = new FileWriter(SONG_FILE_NAME)) {
//             file.write(jsonSongs.toJSONString());
//             file.flush();

//         }
//         catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     public static JSONObject getSongJSON(Song song) {
//         JSONObject songDetails = new JSONObject();

//         songDetails.put(SONG_ID, song.getId().toString());
//         songDetails.put(SONG_ARTIST, song.getArtist());
//         songDetails.put(SONG_GENRE, song.getGenre());
//         songDetails.put(SONG_DIFFICULTY_LEVEL, song.getDifficultyLevel());
//         songDetails.put(SONG_KEY, song.getKey());
//         songDetails.put(SONG_INSTRUMENT, song.getInstrument());
//         songDetails.put(SONG_SCORE, song.getScore());

//         return songDetails;
//     }
    

//     public boolean savePlaylists() {
//         return true;
//     }

//     public boolean saveLessons() {
//         return true;
//     }

}
