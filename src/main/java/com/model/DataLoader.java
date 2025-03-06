package com.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import main.java.com.model.DataConstants;
import java.time.LocalDate;

/*
 * @author ryanMazz
 */
public class DataLoader extends DataConstants {
    
    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();

        try {
            FileReader reader = new FileReader(USER_FILE_NAME);
			JSONArray usersJSON = (JSONArray)new JSONParser().parse(reader);
            
            for(int i=0; i < usersJSON.size(); i++) {
                JSONObject individual = (JSONObject)usersJSON.get(i);
                UUID id = UUID.fromString((String)individual.get(USER_ID));
                String userName = (String)individual.get(USER_USERNAME);
                String password = (String)individual.get(USER_PASSWORD);
                String email = (String)individual.get(USER_EMAIL);
                String firstName = (String)individual.get(USER_FIRST_NAME);
                String lastName = (String)individual.get(USER_LAST_NAME);
                int Streak = (int)individual.get(USER_STREAK);
                int songsPlayed = (int)individual.get(USER_SONGS_PLAYED);
                String[] playlistsInput = (String[])individual.get(USER_PLAYLISTS);
                String[] assignedLessonsInput = (String[])individual.get(USER_ASSIGNED_LESSONS);
                //Following two lines currently load empty data while I work on the data loaders for each.
                ArrayList<Playlist> playlistsOutput =  new ArrayList<Playlist>();
                ArrayList<Lesson> assignedLessonsOutut = new ArrayList<Lesson>();

                //Users.json does not current support a Last Played Date
                users.add(new User(id, firstName, lastName, email, userName, password, Streak, songsPlayed,
                playlistsOutput, assignedLessonsOutut, LocalDateTime.now()));      
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public static ArrayList<Teacher> getTeachers() {
        ArrayList<Teacher> teachers = new ArrayList<Teacher>();

        try {
            FileReader reader = new FileReader(TEACHER_FILE_NAME);
			JSONArray usersJSON = (JSONArray)new JSONParser().parse(reader);
            
            for(int i=0; i < usersJSON.size(); i++) {
                JSONObject individual = (JSONObject)usersJSON.get(i);
                UUID id = UUID.fromString((String)individual.get(USER_ID));
                String userName = (String)individual.get(USER_USERNAME);
                String password = (String)individual.get(USER_PASSWORD);
                String email = (String)individual.get(USER_EMAIL);
                String firstName = (String)individual.get(USER_FIRST_NAME);
                String lastName = (String)individual.get(USER_LAST_NAME);
                int Streak = (int)individual.get(USER_STREAK);
                int songsPlayed = (int)individual.get(USER_SONGS_PLAYED);
                String[] playlistsInput = (String[])individual.get(USER_PLAYLISTS);
                String[] assignedLessonsInput = (String[])individual.get(USER_ASSIGNED_LESSONS);
                String[] classesInput = (String[])individual.get(TEACHER_CLASSES);
                String[] lessonsInput = (String[])individual.get(TEACHER_LESSONS);
                //Following four lines currently load empty data while I work on the data loaders for each.
                ArrayList<Playlist> playlistsOutput =  new ArrayList<Playlist>();
                ArrayList<Lesson> assignedLessonsOutut = new ArrayList<Lesson>();
                ArrayList<ArrayList<User>> classesOutput = new ArrayList<ArrayList<User>>();
                ArrayList<Lesson> lessonsOutput =  new ArrayList<Lesson>();

                //Teachers.json does not current support a Last Played Date
                teachers.add(new Teacher(id, firstName, lastName, email, userName, password, Streak, songsPlayed,
                playlistsOutput, assignedLessonsOutut, LocalDateTime.now(), classesOutput, lessonsOutput));      
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return teachers;
    }

    public static Score midiToScore(String filename, Instrument instrument) {
        return null;
    }

    public static ArrayList<Song> getSongs() {
        return null;
    }

    public static ArrayList<Playlist> getPlaylists() {
        ArrayList<Playlist> playlists = new ArrayList<Playlist>();

        try {
            FileReader reader = new FileReader(PLAYLIST_FILE_NAME);
			JSONArray playlistsJSON = (JSONArray)new JSONParser().parse(reader);
            
            for(int i=0; i < playlistsJSON.size(); i++) {
                JSONObject individual = (JSONObject)playlistsJSON.get(i);
                UUID id = UUID.fromString((String)individual.get(USER_ID));
                String userName = (String)individual.get(USER_USERNAME);
                String password = (String)individual.get(USER_PASSWORD);
                String email = (String)individual.get(USER_EMAIL);
                String firstName = (String)individual.get(USER_FIRST_NAME);
                String lastName = (String)individual.get(USER_LAST_NAME);
                int Streak = (int)individual.get(USER_STREAK);
                int songsPlayed = (int)individual.get(USER_SONGS_PLAYED);
                String[] playlistsInput = (String[])individual.get(USER_PLAYLISTS);
                String[] assignedLessonsInput = (String[])individual.get(USER_ASSIGNED_LESSONS);
                String[] classesInput = (String[])individual.get(TEACHER_CLASSES);
                String[] lessonsInput = (String[])individual.get(TEACHER_LESSONS);
                //Following four lines currently load empty data while I work on the data loaders for each.
                ArrayList<Playlist> playlistsOutput =  new ArrayList<Playlist>();
                ArrayList<Lesson> assignedLessonsOutut = new ArrayList<Lesson>();
                ArrayList<ArrayList<User>> classesOutput = new ArrayList<ArrayList<User>>();
                ArrayList<Lesson> lessonsOutput =  new ArrayList<Lesson>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return playlists;
    }
}

                /* Pseudocode for my own sanity
                 * User stores playlist uuids. Load all playlists (load all scores) 
                 * for getPlaylist (If playlists[o].id == PlaylistsInput[o] add relevant playlist to playlistsoutput 
                 */
