/**
 * @author brenskrz
 */
package com.model;



public class DataConstants {
    protected static final String USER_FILE_NAME = "JSON/users.json";
    // protected static final String USER_TEMP_FILE_NAME = "JSON/users.jason";
    // protected static final String USER_FILE_NAME = "JSON/users.jason";
    protected static final String USER_TEMP_FILE_NAME = "JSON/users_temp.json";
    protected static final String USER_ID = "id";
    protected static final String USER_USERNAME = "username";
    protected static final String USER_PASSWORD = "password";
    protected static final String USER_EMAIL = "email";
    protected static final String USER_FIRST_NAME = "firstName";
    protected static final String USER_LAST_NAME = "lastName";
    protected static final String USER_STREAK = "streak";
    protected static final String USER_SONGS_PLAYED = "songsPlayed";
    protected static final String USER_ASSIGNED_LESSONS = "assignedLessons";
    protected static final String USER_PLAYLISTS = "playlists";
    protected static final String USER_LAST_PLAYEd = "lastPlayed";

    protected static final String TEACHER_FILE_NAME = "JSON/teachers.json";
    protected static final String TEACHER_TEMP_FILE_NAME = "JSON/teachers_temp.json";
    protected static final String TEACHER_CLASSES = "classes";
    protected static final String TEACHER_LESSONS = "lessons";

    protected static final String SONG_FILE_NAME = "JSON/songs.json";
    protected static final String SONG_TEMP_FILE_NAME = "JSON/songs_temp.json";
    protected static final String SONG_ID = "id";
    protected static final String SONG_TITLE = "title";
    protected static final String SONG_ARTIST = "artist";
    protected static final String SONG_GENRE = "genre";
    protected static final String SONG_DIFFICULTY_LEVEL = "difficultyLevel";
    protected static final String SONG_KEY = "key";
    protected static final String SONG_INSTRUMENT = "instrument";
    protected static final String SONG_SCORE = "score";

    protected static final String PLAYLIST_FILE_NAME = "JSON/playlists.json";
    protected static final String PLAYLIST_FILE_TEMP_NAME = "JSON/playlists_temp.json";   
    protected static final String PLAYLIST_ID = "id";
    protected static final String PLAYLIST_TITLE = "title";
    protected static final String PLAYLIST_AUTHOR = "author";
    protected static final String PLAYLIST_DESCRIPTION = "description";
    protected static final String PLAYLIST_SONGS = "songs";

    protected static final String LESSONS_FILE_NAME = "JSON/lessons.json";
    protected static final String LESSONS_FILE_TEMP_NAME = "JSON/lessons_temp.json";
    protected static final String LESSONS_ID = "id";
    protected static final String LESSONS_SONGS = "songs";
    protected static final String LESSONS_TITLE = "title";

    /**
     * Additional data constants
     * @author ryanMazz
     */
    protected static final String SCORE_FILE_NAME = "JSON/scores.json";
    protected static final String SCORE_TEMP_FILE_NAME = "JSON/scores_temp.json";

    protected static final String SCORE_ID = "uuid";
    protected static final String SCORE_INSTRUMENT = "instrument";
    protected static final String SCORE_TEMPO = "tempo";
    protected static final String SCORE_MEASURES = "measures";

    protected static final String MEASURE_TIME_SIGNATURE = "timeSignature";
    protected static final String MEASURE_CHORDS = "chords";

    protected static final String CHORD_OFFSET = "offset";
    protected static final String CHORD_VALUE = "value";
    protected static final String CHORD_DOTTED = "dotted";
    protected static final String CHORD_NOTES = "notes";

    protected static final String NOTE_PITCH_CLASS = "pitchClass";
    protected static final String NOTE_OCTAVE = "octave"; 
    protected static final String NOTE_STRING_POSITION = "string";
    protected static final String NOTE_FRONT_TIE = "frontTie";
    protected static final String NOTE_BACK_TIE = "backTie"; 
    /**@author Christopher Ferguson */  
    protected static final String MIDI_FOLDER = "src/main/midi/";
}
