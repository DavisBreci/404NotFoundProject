package com.model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.time.LocalDate;

/**
 * Loads data from JSON files into the program 
 * @author Ryan Mazzella
 */
public class DataLoader extends DataConstants {
    
    /**
     * Loads all users from the JSON file
     * @return ArrayList of all users
     */
    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();

        try {
            FileReader reader = new FileReader(USER_FILE_NAME);
			JSONArray usersJSON = (JSONArray)new JSONParser().parse(reader);
            
            for(int i=0; i < usersJSON.size(); i++) {
                JSONObject individual = (JSONObject)usersJSON.get(i);
                String id = "";
                id = (String)individual.get(USER_ID);
                String userName = "";
                userName = (String)individual.get(USER_USERNAME);
                String password = "";
                password = (String)individual.get(USER_PASSWORD);
                String email = "";
                email = (String)individual.get(USER_EMAIL);
                String firstName = "";
                firstName = (String)individual.get(USER_FIRST_NAME);
                String lastName = "";
                lastName = (String)individual.get(USER_LAST_NAME);
                long Streak = 0;
                Streak = (long)individual.get(USER_STREAK);
                long songsPlayed = 0;
                songsPlayed = (long)individual.get(USER_SONGS_PLAYED);
                JSONArray playlistIDs = (JSONArray)individual.get(USER_PLAYLISTS);
                ArrayList<Playlist> playlists =  new ArrayList<Playlist>();
                for(int j=0; j < playlistIDs.size(); j++) {
                    playlists.add(getPlaylistFromID((String)playlistIDs.get(j)));
                }  
                JSONArray assignedLessonIDs = (JSONArray)individual.get(USER_ASSIGNED_LESSONS);
                ArrayList<Lesson> assignedLessons =  new ArrayList<Lesson>();
                for(int j=0; j < assignedLessonIDs.size(); j++) {
                    assignedLessons.add(getLessonFromID((String)assignedLessonIDs.get(j)));
                }  
                String lastPlayedDate = "";
                lastPlayedDate = (String)individual.get(USER_LAST_PLAYED);
                users.add(new User(id, firstName, lastName, email, userName, password, (int)Streak, (int)songsPlayed,
                playlists, assignedLessons, LocalDate.parse(lastPlayedDate)));      
            }

            
        } catch (Exception e) {
            System.out.println(USER_FILE_NAME + " cannot be read. Data is missing or corrupt.");
            e.printStackTrace();
            return new ArrayList<User>();
        }

        return users;
    }
    
    /**
     * Loads a single user from the JSON file
     * @param inputID The ID of the user to load
     * @return The user with matching ID
     */
    public static User getUserFromID(String inputID) {
        try {
            FileReader reader = new FileReader(USER_FILE_NAME);
            JSONArray usersJSON = (JSONArray)new JSONParser().parse(reader);

            String userID = "";

            for ( int i=0; i < usersJSON.size(); i++) {
                JSONObject individual = (JSONObject)usersJSON.get(i);
                if (inputID.equals((String)individual.get(USER_ID))) {
                    userID = (String)individual.get(USER_ID);
                    String userName = "";
                    userName = (String)individual.get(USER_USERNAME);
                    String password = "";
                    password = (String)individual.get(USER_PASSWORD);
                    String email = "";
                    email = (String)individual.get(USER_EMAIL);
                    String firstName = "";
                    firstName = (String)individual.get(USER_FIRST_NAME);
                    String lastName = "";
                    lastName = (String)individual.get(USER_LAST_NAME);
                    long Streak = 0;
                    Streak = (long)individual.get(USER_STREAK);
                    long songsPlayed = 0;
                    songsPlayed = (long)individual.get(USER_SONGS_PLAYED);
                    JSONArray playlistIDs = (JSONArray)individual.get(USER_PLAYLISTS);
                    ArrayList<Playlist> playlists =  new ArrayList<Playlist>();
                    for(int j=0; j < playlistIDs.size(); j++) {
                        playlists.add(getPlaylistFromID((String)playlistIDs.get(j)));
                    }  
                    JSONArray assignedLessonIDs = (JSONArray)individual.get(USER_ASSIGNED_LESSONS);
                    ArrayList<Lesson> assignedLessons =  new ArrayList<Lesson>();
                    for(int j=0; j < assignedLessonIDs.size(); j++) {
                        assignedLessons.add(getLessonFromID((String)assignedLessonIDs.get(j)));
                    }  
                    String lastPlayedDate = "";
                    lastPlayedDate = (String)individual.get(USER_LAST_PLAYED);
                    return new User(userID, firstName, lastName, email, userName, password, (int)Streak, (int)songsPlayed,
                    playlists, assignedLessons, LocalDate.parse(lastPlayedDate));
                }
            }
            if (userID == "") {
                System.out.println("No matching user found for ID: " + inputID);
                return null;
            }
        } catch (Exception e) {
            System.out.println(USER_FILE_NAME + " cannot be read. Data is missing or corrupt.");
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * Loads all teachers from the JSON file
     * @return ArrayList of all teachers
     */
    public static ArrayList<Teacher> getTeachers() {
        ArrayList<Teacher> teachers = new ArrayList<Teacher>();

        try {
            FileReader reader = new FileReader(TEACHER_FILE_NAME);
			JSONArray usersJSON = (JSONArray)new JSONParser().parse(reader);
            
            for(int i=0; i < usersJSON.size(); i++) {
                JSONObject individual = (JSONObject)usersJSON.get(i);
                String id = "";
                id = (String)individual.get(USER_ID);
                String userName = "";
                userName = (String)individual.get(USER_USERNAME);
                String password = "";
                password = (String)individual.get(USER_PASSWORD);
                String email = "";
                email = (String)individual.get(USER_EMAIL);
                String firstName = "";
                firstName = (String)individual.get(USER_FIRST_NAME);
                String lastName = "";
                lastName = (String)individual.get(USER_LAST_NAME);
                long Streak = 0;
                Streak = (long)individual.get(USER_STREAK);
                long songsPlayed = 0;
                songsPlayed = (long)individual.get(USER_SONGS_PLAYED);
                JSONArray playlistIDs = (JSONArray)individual.get(USER_PLAYLISTS);
                ArrayList<Playlist> playlists =  new ArrayList<Playlist>();
                for(int j=0; j < playlistIDs.size(); j++) {
                    playlists.add(getPlaylistFromID((String)playlistIDs.get(j)));
                }  
                JSONArray assignedLessonIDs = (JSONArray)individual.get(USER_ASSIGNED_LESSONS);
                ArrayList<Lesson> assignedLessons =  new ArrayList<Lesson>();
                for(int j=0; j < assignedLessonIDs.size(); j++) {
                    assignedLessons.add(getLessonFromID((String)assignedLessonIDs.get(j)));
                }
                JSONArray lessonIDs = (JSONArray)individual.get(TEACHER_LESSONS);
                ArrayList<Lesson> lessons =  new ArrayList<Lesson>();
                for(int j=0; j < lessonIDs.size(); j++) {
                    lessons.add(getLessonFromID((String)lessonIDs.get(j)));
                } 
                JSONArray classIDs = (JSONArray)individual.get(TEACHER_CLASSES);
                ArrayList<ArrayList<User>> classes =  new ArrayList<ArrayList<User>>();
                for(int j=0; j < classIDs.size(); j++) {
                    JSONArray classUserIDs = (JSONArray)classIDs.get(j);
                    ArrayList<User> classUsers = new ArrayList<User>();
                    for (int k=0; k < classUserIDs.size(); k++) {
                        classUsers.add(getUserFromID((String) classUserIDs.get(k)));
                    }
                    classes.add(classUsers);
                } 
                String lastPlayedDate = "";
                lastPlayedDate = (String)individual.get(USER_LAST_PLAYED);
                teachers.add(new Teacher(id, firstName, lastName, email, userName, password, (int)Streak, (int)songsPlayed,
                playlists, assignedLessons, LocalDate.parse(lastPlayedDate), classes, lessons));      
            }
        } catch (Exception e) {
            System.out.println(TEACHER_FILE_NAME + " cannot be read. Data is missing or corrupt.");
            e.printStackTrace();
            return new ArrayList<Teacher>();
        }
        return teachers;
    }

    /**
     * Loads all songs from the JSON file
     * @return ArrayList of all songs
     */
    public static ArrayList<Song> getAllSongs() {
        ArrayList<Song> songs = new ArrayList<Song>();
        
        try {
            FileReader reader = new FileReader(SONG_FILE_NAME);
			JSONArray songJSON = (JSONArray)new JSONParser().parse(reader);
            for(int i=0; i < songJSON.size(); i++) {
                JSONObject individual = (JSONObject)songJSON.get(i);
                String id = "";
                id = (String)individual.get(SONG_ID);
                String title = "";
                title = (String)individual.get(SONG_TITLE);
                String artist = "";
                artist = (String)individual.get(SONG_ARTIST);
                String genre = "";
                genre = (String)individual.get(SONG_GENRE);
                String difficultyLevel = "BEGINNER";
                difficultyLevel = (String)individual.get(SONG_DIFFICULTY_LEVEL);
                String key = "GMAJOR_EMINOR";
                key = (String)individual.get(SONG_KEY);
                String instrument = "GUITAR";
                instrument = (String)individual.get(SONG_INSTRUMENT);
                String scoreID = "";
                scoreID = (String)individual.get(SONG_SCORE);
                songs.add(new Song(id, title, artist, genre, Key.valueOf(key), DifficultyLevel.valueOf(difficultyLevel), 
                Instrument.valueOf(instrument), getScoreFromID(scoreID)));
            }
        } catch(Exception e) {
            System.out.println(SONG_FILE_NAME + " cannot be read. Data is missing or corrupt.");
            e.printStackTrace();
            return new ArrayList<Song>();
        }
        return songs;
    }

    /**
     * Loads a single song from the JSON file
     * @param inputID The ID of the song to load
     * @return The song with matching ID
     */
    public static Song getSongFromID(String inputID) {
        try {
            FileReader reader = new FileReader(SONG_FILE_NAME);
			JSONArray songJSON = (JSONArray)new JSONParser().parse(reader);

            String songID = "";

            for(int i=0; i < songJSON.size(); i++) {
                JSONObject individual = (JSONObject)songJSON.get(i);
                if (inputID.equals((String)individual.get(SONG_ID))) {
                    songID = (String)individual.get(SONG_ID);
                    String title = "";
                    title = (String)individual.get(SONG_TITLE);
                    String artist = "";
                    artist = (String)individual.get(SONG_ARTIST);
                    String genre = "";
                    genre = (String)individual.get(SONG_GENRE);
                    String difficultyLevel = "BEGINNER";
                    difficultyLevel = (String)individual.get(SONG_DIFFICULTY_LEVEL);
                    String key = "GMAJOR_EMINOR";
                    key = (String)individual.get(SONG_KEY);
                    String instrument = "GUITAR";
                    instrument = (String)individual.get(SONG_INSTRUMENT);
                    String scoreID = "";
                    scoreID = (String)individual.get(SONG_SCORE);
                    return new Song(songID, title, artist, genre, Key.valueOf(key), DifficultyLevel.valueOf(difficultyLevel),
                    Instrument.valueOf(instrument), getScoreFromID(scoreID));
                }
            }
            if (songID == "") {
                System.out.println("No matching song found for ID: " + inputID);
                return null;
            }
        } catch(Exception e) {
            System.out.println(SONG_FILE_NAME + " cannot be read. Data is missing or corrupt.");
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * Loads a single score from the JSON file
     * @param inputID The ID of the score to load
     * @return The score with matching ID
     */
    public static Score getScoreFromID(String inputID) {
        try {
            FileReader reader = new FileReader(SCORE_FILE_NAME);
            JSONArray scoreJSON = (JSONArray)new JSONParser().parse(reader);

            String scoreID = "";
            String instrument = "";
            String tempo = "0";

            for(int i=0; i<scoreJSON.size(); i++) { 
                JSONObject individualScore = (JSONObject)scoreJSON.get(i);
                if (inputID.equals((String)individualScore.get(SCORE_ID))) {
                    scoreID = (String)individualScore.get(SCORE_ID);
                    instrument = (String)individualScore.get(SCORE_INSTRUMENT);
                    tempo = (String)individualScore.get(SCORE_TEMPO);
                    Score output = new Score(scoreID, Instrument.valueOf(instrument), Integer.valueOf(tempo));
                    JSONArray rawMeasures = (JSONArray)individualScore.get(SCORE_MEASURES);
                    for(int j=0; j<rawMeasures.size(); j++) { 
                        JSONObject individualMeasure = (JSONObject)rawMeasures.get(j);
                        String TimeSignature = (String)individualMeasure.get(MEASURE_TIME_SIGNATURE);
                        Measure measure = new Measure(Instrument.valueOf(instrument), new Rational(TimeSignature));
                        JSONArray rawChords = (JSONArray) individualMeasure.get(MEASURE_CHORDS);
                        for (int  k=0; k<rawChords.size(); k++) {
                            JSONObject individualChord = (JSONObject) rawChords.get(k);
                            String offset = (String) individualChord.get(CHORD_OFFSET);
                            String value = (String) individualChord.get(CHORD_VALUE); 
                            String dotted = (String) individualChord.get(CHORD_DOTTED); 
                            Chord chord = new Chord(NoteValue.valueOf(value), Boolean.valueOf(dotted), Instrument.valueOf(instrument));
                            JSONArray rawNotes = (JSONArray) individualChord.get(CHORD_NOTES);
                            for (int l = 0; l < rawNotes.size(); l++) {
                                if (!rawNotes.get(l).equals("null")) { 
                                    JSONObject individualNote = (JSONObject) rawNotes.get(l);
                                    String pitchClass = (String) individualNote.get(NOTE_PITCH_CLASS); 
                                    String octave = (String) individualNote.get(NOTE_OCTAVE);
                                    String stringPos = (String) individualNote.get(NOTE_STRING_POSITION);
                                    String frontTie = (String) individualNote.get(NOTE_FRONT_TIE);
                                    String backTie = (String) individualNote.get(NOTE_BACK_TIE); 
                                    com.model.Note note = new com.model.Note(PitchClass.valueOf(pitchClass), Integer.valueOf(octave));
                                    chord.put(note, Integer.valueOf(stringPos) - 1);
                                }
                            }
                            measure.put(new Rational(offset), chord);
                        }
                        output.add(measure);
                    }
                    return output;     
                }
            }
            if (scoreID.equals("")) {
                System.out.println("No matching score found for ID: " + inputID);
                return null;
            }
        } catch(Exception e) {
            System.out.println(SCORE_FILE_NAME + " cannot be read. Data is missing or corrupt.");
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * Loads all playlists from the JSON file
     * @return ArrayList of all playlists
     */
    public static ArrayList<Playlist> getAllPlaylists() {
        ArrayList<Playlist> playlists = new ArrayList<Playlist>();

        try {
            FileReader reader = new FileReader(PLAYLIST_FILE_NAME);
			JSONArray playlistsJSON = (JSONArray)new JSONParser().parse(reader);
            
            for(int i=0; i < playlistsJSON.size(); i++) {
                JSONObject individual = (JSONObject)playlistsJSON.get(i);
                String id = "";
                id = (String)individual.get(PLAYLIST_ID);
                String title = "";
                title = (String)individual.get(PLAYLIST_TITLE);
                String author = "";
                author = (String)individual.get(PLAYLIST_AUTHOR);
                String desc = "";
                desc = (String)individual.get(PLAYLIST_DESCRIPTION);
                JSONArray SongIDs = (JSONArray)individual.get(PLAYLIST_SONGS);
                ArrayList<Song> songOutput =  new ArrayList<Song>();
                for(int j=0; j < SongIDs.size(); j++) {
                    songOutput.add(getSongFromID((String)SongIDs.get(j)));
                }
                playlists.add(new Playlist(id, title, author, desc, songOutput));
            }
        } catch (Exception e) {
            System.out.println(SCORE_FILE_NAME + " cannot be read. Data is missing or corrupt.");
            e.printStackTrace();
            return new ArrayList<Playlist>();
        }
        return playlists;
    }

    /**
     * Loads a single playlist from the JSON file
     * @param inputID The ID of the playlist to load
     * @return The playlist with matching ID
     */
    public static Playlist getPlaylistFromID(String inputID) {
        try {
            FileReader reader = new FileReader(PLAYLIST_FILE_NAME);
			JSONArray playlistJSON = (JSONArray)new JSONParser().parse(reader);

            String playlistID = "";

            for(int i=0; i < playlistJSON.size(); i++) {
                JSONObject individual = (JSONObject)playlistJSON.get(i);
                if (inputID.equals((String)individual.get(SONG_ID))) {
                    String id = "";
                    id = (String)individual.get(PLAYLIST_ID);
                    String title = "";
                    title = (String)individual.get(PLAYLIST_TITLE);
                    String author = "";
                    author = (String)individual.get(PLAYLIST_AUTHOR);
                    String desc = "";
                    desc = (String)individual.get(PLAYLIST_DESCRIPTION);
                    JSONArray SongIDs = (JSONArray)individual.get(PLAYLIST_SONGS);
                    ArrayList<Song> songOutput =  new ArrayList<Song>();
                    for(int j=0; j < SongIDs.size(); j++) {
                        songOutput.add(getSongFromID((String)SongIDs.get(j)));
                    }
                    return new Playlist(id, title, author, desc, songOutput);
                }
            }
            if (playlistID == "") {
                System.out.println("No matching playlist found for ID: " + inputID);
                return null;
            }
        } catch(Exception e) {
            System.out.println(SCORE_FILE_NAME + " cannot be read. Data is missing or corrupt.");
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * Loads all lessons from the JSON file
     * @return ArrayList of all lessons
     */
    public static ArrayList<Lesson> getAllLessons() {
        ArrayList<Lesson> lessons = new ArrayList<Lesson>();

        try {
            FileReader reader = new FileReader(LESSONS_FILE_NAME);
            JSONArray lessonsJSON = (JSONArray)new JSONParser().parse(reader);
            for (int i=0; i < lessonsJSON.size(); i++) {
                JSONObject individual = (JSONObject)lessonsJSON.get(i);
                String id = "";
                id = (String)individual.get(LESSONS_ID);
                String title = "";
                title = (String)individual.get(LESSONS_TITLE);
                JSONArray songIDs = (JSONArray)individual.get(LESSONS_SONGS);
                ArrayList<Song> songs =  new ArrayList<Song>();
                for(int j=0; j < songIDs.size(); j++) {
                    songs.add(getSongFromID((String)songIDs.get(j)));
                }  
                lessons.add(new Lesson(id, songs, title));
            }
        } catch (Exception e) {
            System.out.println(LESSONS_FILE_NAME + " cannot be read. Data is missing or corrupt.");
            e.printStackTrace();
            return new ArrayList<Lesson>();
        }   
        return lessons;
    }

    /**
     * Loads a single lesson from the JSON file
     * @param inputID The ID of the lesson to load
     * @return The lesson with matching ID
     */
    public static Lesson getLessonFromID(String inputID) {
        try {
            FileReader reader = new FileReader(LESSONS_FILE_NAME);
            JSONArray lessonsJSON = (JSONArray)new JSONParser().parse(reader);

            String lessonID = "";

            for(int i=0; i < lessonsJSON.size(); i++) {
                JSONObject individual = (JSONObject)lessonsJSON.get(i);
                if (inputID.equals((String)individual.get(LESSONS_ID))) {
                    String id = "";
                    id = (String)individual.get(LESSONS_ID);
                    String title = "";
                    title = (String)individual.get(LESSONS_TITLE);
                    JSONArray songIDs = (JSONArray)individual.get(LESSONS_SONGS);
                    ArrayList<Song> songs =  new ArrayList<Song>();
                    for(int j=0; j < songIDs.size(); j++) {
                        songs.add(getSongFromID((String)songIDs.get(j)));
                    }  
                    return new Lesson(id, songs, title);
                }
            }
            if (lessonID == "") {
                System.out.println("No matching lesson found for ID: " + inputID);
                return null;
            }
        } catch (Exception e) {
            System.out.println(LESSONS_FILE_NAME + " cannot be read. Data is missing or corrupt.");
            e.printStackTrace();
            return null;
        }
        
        return null;
    }

    /**
     * Creates a sequence from a MIDI file
     * @param filename the name of the MIDI file
     * @return the sequence created from the MIDI file
     * @author Christopher and Ryan
     */
    public static Sequence loadSequence(String filename){
		Sequence loadedSequence = null;
		try {
			loadedSequence = MidiSystem.getSequence(new File(MIDI_FOLDER + filename));
		} catch(IOException e) {
            System.out.println("Could not find \"" + filename + "\" in " + MIDI_FOLDER + ".");
            e.printStackTrace();
		} catch (InvalidMidiDataException e) {
            System.out.println(filename + " could not be read. The file may be corrupt.");
			e.printStackTrace();
		}
		return loadedSequence;
	}


    //MADE SOLELY FOR DATAWRITER TESTING PURPOSES. TO BE REMOVED POST-TESTING
    public static ArrayList<Score> getAllScores(String filePath) {
        ArrayList<Score> scores = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser();
            JSONArray scoreArray = (JSONArray) parser.parse(new FileReader(filePath));

            for (Object obj : scoreArray) {
                JSONObject jsonScore = (JSONObject) obj;

                String scoreID = (String) jsonScore.get("scoreID");
                String instrument = (String) jsonScore.get("instrument");
                String tempo = String.valueOf(jsonScore.get("tempo"));
                Instrument instrumentEnum = Instrument.valueOf(instrument.toUpperCase()); 
                int tempoValue = Integer.parseInt(tempo);
                scores.add(new Score(scoreID, instrumentEnum, tempoValue));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return scores;
    }
}

