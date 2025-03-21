package com.model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

import org.jfugue.theory.Note;
import org.jfugue.theory.TimeSignature;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.model.DataConstants;
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
                String id = (String)individual.get(USER_ID);
                String userName = (String)individual.get(USER_USERNAME);
                String password = (String)individual.get(USER_PASSWORD);
                String email = (String)individual.get(USER_EMAIL);
                String firstName = (String)individual.get(USER_FIRST_NAME);
                String lastName = (String)individual.get(USER_LAST_NAME);
                long Streak = (long)individual.get(USER_STREAK);
                long songsPlayed = (long)individual.get(USER_SONGS_PLAYED);
                JSONArray playlistIDs = (JSONArray)individual.get(USER_PLAYLISTS);
                ArrayList<Playlist> playlists =  new ArrayList<Playlist>();
                for(int j=0; j < playlistIDs.size(); j++) {
                    playlists.add(getPlaylistFromID((String)playlistIDs.get(j)));
                }  
                // JSONArray rawAssignedLessonsIDs = (JSONArray)individual.get(USER_ASSIGNED_LESSONS);
                // String[] assignedLessonsIDs = new String[rawAssignedLessonsIDs.size()];
                // for(int j=0; j < rawAssignedLessonsIDs.size(); j++) {
                //     assignedLessonsIDs[j] = (String)rawAssignedLessonsIDs.get(j);
                // }
                //Following line currently loads empty data while I work on the data loaders.
                ArrayList<Lesson> assignedLessonsOutut = new ArrayList<Lesson>();

                //Users.json does not current support a Last Played Date
                users.add(new User(id, firstName, lastName, email, userName, password, (int)Streak, (int)songsPlayed,
                playlists, assignedLessonsOutut, LocalDate.now()));      
            }

            
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TESTER CODE DELETE LATER PLS
        for(int i=0; i < users.size(); i++) {
            System.out.println("User : "+ i);
            System.out.println(users.get(i).id);
            System.out.println(users.get(i).username);
            System.out.println(users.get(i).password);
            System.out.println(users.get(i).email);
            System.out.println(users.get(i).firstName);
            System.out.println(users.get(i).lastName);
            System.out.println("\n");
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
                String id = (String)individual.get(USER_ID);
                String userName = (String)individual.get(USER_USERNAME);
                String password = (String)individual.get(USER_PASSWORD);
                String email = (String)individual.get(USER_EMAIL);
                String firstName = (String)individual.get(USER_FIRST_NAME);
                String lastName = (String)individual.get(USER_LAST_NAME);
                long Streak = (long)individual.get(USER_STREAK);
                long songsPlayed = (long)individual.get(USER_SONGS_PLAYED);
                JSONArray playlistIDs = (JSONArray)individual.get(USER_PLAYLISTS);
                ArrayList<Playlist> playlists =  new ArrayList<Playlist>();
                for(int j=0; j < playlistIDs.size(); j++) {
                    playlists.add(getPlaylistFromID((String)playlistIDs.get(j)));
                }  
                // JSONArray rawAssignedLessonsIDs = (JSONArray)individual.get(USER_ASSIGNED_LESSONS);
                // String[] assignedLessonsIDs = new String[rawAssignedLessonsIDs.size()];
                // for(int j=0; j < rawAssignedLessonsIDs.size(); j++) {
                //     assignedLessonsIDs[j] = (String)rawAssignedLessonsIDs.get(j);
                // }
                // JSONArray rawClassIDs = (JSONArray)individual.get(TEACHER_CLASSES);
                // String[] classIDs = new String[rawClassIDs.size()];
                // for(int j=0; j < rawClassIDs.size(); j++) {
                //     classIDs[j] = (String)rawClassIDs.get(j);
                // }
                // JSONArray rawLessonIDs = (JSONArray)individual.get(TEACHER_LESSONS);
                // String[] lessonIDs = new String[rawLessonIDs.size()];
                // for(int j=0; j < rawLessonIDs.size(); j++) {
                //     lessonIDs[j] = (String)rawLessonIDs.get(j);
                // }
                //Following three lines currently load empty data while I work on the data loaders for each.
                ArrayList<Lesson> assignedLessonsOutut = new ArrayList<Lesson>();
                ArrayList<ArrayList<User>> classesOutput = new ArrayList<ArrayList<User>>();
                ArrayList<Lesson> lessonsOutput =  new ArrayList<Lesson>();

                //NOTE: Teachers.json does not current support a Last Played Date
                teachers.add(new Teacher(id, firstName, lastName, email, userName, password, (int)Streak, (int)songsPlayed,
                playlists, assignedLessonsOutut, LocalDate.now(), classesOutput, lessonsOutput));      
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(int i=0; i < teachers.size(); i++) {
            System.out.println("User : "+ i);
            System.out.println(teachers.get(i).id);
            System.out.println(teachers.get(i).username);
            System.out.println(teachers.get(i).password);
            System.out.println(teachers.get(i).email);
            System.out.println(teachers.get(i).firstName);
            System.out.println(teachers.get(i).lastName);
            System.out.println("\n");
        }

        return teachers;
    }

    /**
     * Imports a MIDI file using Java's MIDI package. 
     * @author Christopher Ferguson
     * @param filename a MIDI File (includes extension)
     * @return the MIDI file as a Sequence (can be null)
     */
   public static Sequence loadSequence(String filename){ // For testing only. To be moved to dataLoader
		Sequence loadedSequence = null;
		try {
			loadedSequence = MidiSystem.getSequence(new File(MIDI_FOLDER + filename));
		} catch(IOException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
		return loadedSequence;
	}

    public static ArrayList<Song> getAllSongs() {
        ArrayList<Song> songs = new ArrayList<Song>();
        
        try {
            FileReader reader = new FileReader(SONG_FILE_NAME);
			JSONArray songJSON = (JSONArray)new JSONParser().parse(reader);
            for(int i=0; i < songJSON.size(); i++) {
                JSONObject individual = (JSONObject)songJSON.get(i);
                String id = (String)individual.get(SONG_ID);
                String title = (String)individual.get(SONG_TITLE);
                String artist = (String)individual.get(SONG_ARTIST);
                String genre = (String)individual.get(SONG_GENRE);
                String difficultyLevel = (String)individual.get(SONG_DIFFICULTY_LEVEL);
                String key = (String)individual.get(SONG_KEY);
                String instrument = (String)individual.get(SONG_INSTRUMENT);
                String scoreID = (String)individual.get(SONG_SCORE);
                songs.add(new Song(id, title, artist, genre, Key.valueOf(key), DifficultyLevel.valueOf(difficultyLevel), 
                Instrument.valueOf(instrument), getScoreFromID(scoreID)));
            }
            return songs;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Song getSongFromID(String inputID) {
        try {
            FileReader reader = new FileReader(SONG_FILE_NAME);
			JSONArray songJSON = (JSONArray)new JSONParser().parse(reader);

            String songID = "";

            for(int i=0; i < songJSON.size(); i++) {
                JSONObject individual = (JSONObject)songJSON.get(i);
                if (inputID.equals((String)individual.get(SONG_ID))) {
                    songID = (String)individual.get(SONG_ID);
                    String title = (String)individual.get(SONG_TITLE);
                    String artist = (String)individual.get(SONG_ARTIST);
                    String genre = (String)individual.get(SONG_GENRE);
                    String difficultyLevel = (String)individual.get(SONG_DIFFICULTY_LEVEL);
                    String key = (String)individual.get(SONG_KEY);
                    //String instrument = (String)individual.get(SONG_INSTRUMENT);
                    String instrument = "GUITAR"; //reminder, have this written in all caps via the data writer
                    String scoreID = (String)individual.get(SONG_SCORE);
                    return new Song(songID, title, artist, genre, Key.valueOf(key), DifficultyLevel.valueOf(difficultyLevel),
                    Instrument.valueOf(instrument), getScoreFromID(scoreID));
                }
            }
            if (songID == "") {
                System.out.println("No matching song found for ID: " + inputID);
                return null;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Score getScoreFromID(String inputID) {
        try {
            FileReader reader = new FileReader(SCORE_FILE_NAME);
            JSONArray scoreJSON = (JSONArray)new JSONParser().parse(reader);

            String scoreID = "";
            String instrument = "";
            String tempo = "0";

            for(int i=0; i<scoreJSON.size(); i++) { //search for score with matching id
                JSONObject individualScore = (JSONObject)scoreJSON.get(i);
                if (inputID.equals((String)individualScore.get(SCORE_ID))) {
                    System.out.println("Score identified for UUID: " + inputID);
                    //Matching ID found, generate score object.
                    scoreID = (String)individualScore.get(SCORE_ID);
                    instrument = (String)individualScore.get(SCORE_INSTRUMENT);
                    tempo = (String)individualScore.get(SCORE_TEMPO);
                    Score output = new Score(scoreID, Instrument.valueOf(instrument), Integer.valueOf(tempo));

                    //for each measure
                    JSONArray rawMeasures = (JSONArray)individualScore.get(SCORE_MEASURES);
                    for(int j=0; j<rawMeasures.size(); j++) { 
                        JSONObject individualMeasure = (JSONObject)rawMeasures.get(j);
                        String TimeSignature = (String)individualMeasure.get(MEASURE_TIME_SIGNATURE); //pull time signature
                        System.out.println(new Rational(TimeSignature));
                        Measure measure = new Measure(Instrument.valueOf(instrument), new Rational(TimeSignature));

                        //for each chord
                        JSONArray rawChords = (JSONArray) individualMeasure.get(MEASURE_CHORDS);
                        for (int  k=0; k<rawChords.size(); k++) {
                            JSONObject individualChord = (JSONObject) rawChords.get(k);
                            String offset = (String) individualChord.get(CHORD_OFFSET); //pull offset (long -> int)
                            String value = (String) individualChord.get(CHORD_VALUE); //pull value (NoteValue Enumerator)
                            String dotted = (String) individualChord.get(CHORD_DOTTED); //pull dotted (use .valueOf to convert to boolean)
                            Chord chord = new Chord(NoteValue.valueOf(value), Boolean.valueOf(dotted), Instrument.valueOf(instrument));
                            
                            //for each note
                            JSONArray rawNotes = (JSONArray) individualChord.get(CHORD_NOTES);
                            for (int l = 0; l < rawNotes.size(); l++) {
                                if (!rawNotes.get(l).equals("null")) { //If note is not empty, proceed
                                    JSONObject individualNote = (JSONObject) rawNotes.get(l);
                                    String pitchClass = (String) individualNote.get(NOTE_PITCH_CLASS); //pull pitchClass (PitchClass Enumerator)
                                    String octave = (String) individualNote.get(NOTE_OCTAVE); //pull octave (long -> int)
                                    String stringPos = (String) individualNote.get(NOTE_STRING_POSITION); //pull stringPos (long -> int)
                                    String frontTie = (String) individualNote.get(NOTE_FRONT_TIE); //pull frontTie (use .valueOf to convert to boolean)
                                    String backTie = (String) individualNote.get(NOTE_BACK_TIE); //pull backTie (use .valueOf to convert to boolean)
                                    com.model.Note note = new com.model.Note(PitchClass.valueOf(pitchClass), Integer.valueOf(octave));
                                    System.out.println("Note identified: " + note.toString() + " on string: " + stringPos);
                                    System.out.println(chord.put(note, Integer.valueOf(stringPos) - 1)); //add note to chord
                                }
                            }
                            System.out.println("Chord identified: " + chord.toString() + " at offset: " + offset);
                            System.out.println(measure.put(new Rational(offset), chord)); //add chord to measure
                        }
                        System.out.println("Measure identified: " + measure.toString());
                        output.add(measure);
                        System.out.println("Was measure added? " + output.contains(measure));
                    }
                    System.out.println("Score loaded sucessfully!:\n" + output.toString());
                    return output;     
                }
            }
            if (scoreID.equals("")) { //If no matching score is found, return dummy score
                System.out.println("No matching score found for ID: " + inputID);
                return null;
                //return new Score(null, Instrument.GUITAR, 0);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Playlist> getAllPlaylists() {
        ArrayList<Playlist> playlists = new ArrayList<Playlist>();

        try {
            FileReader reader = new FileReader(PLAYLIST_FILE_NAME);
			JSONArray playlistsJSON = (JSONArray)new JSONParser().parse(reader);
            
            for(int i=0; i < playlistsJSON.size(); i++) {
                JSONObject individual = (JSONObject)playlistsJSON.get(i);
                String id = (String)individual.get(PLAYLIST_ID);
                String title = (String)individual.get(PLAYLIST_TITLE);
                String author = (String)individual.get(PLAYLIST_AUTHOR);
                String desc = (String)individual.get(PLAYLIST_DESCRIPTION);
                JSONArray SongIDs = (JSONArray)individual.get(PLAYLIST_SONGS);
                ArrayList<Song> songOutput =  new ArrayList<Song>();
                for(int j=0; j < SongIDs.size(); j++) {
                    songOutput.add(getSongFromID((String)SongIDs.get(j)));
                    System.out.println("Pulled song from id: " + (String)SongIDs.get(j)); //debug
                }  

                
                playlists.add(new Playlist(id, title, author, desc, songOutput));
            }
            return playlists;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Playlist getPlaylistFromID(String inputID) {
        try {
            FileReader reader = new FileReader(PLAYLIST_FILE_NAME);
			JSONArray playlistJSON = (JSONArray)new JSONParser().parse(reader);

            String playlistID = "";

            for(int i=0; i < playlistJSON.size(); i++) {
                JSONObject individual = (JSONObject)playlistJSON.get(i);
                if (inputID.equals((String)individual.get(SONG_ID))) {
                    String id = (String)individual.get(PLAYLIST_ID);
                    String title = (String)individual.get(PLAYLIST_TITLE);
                    String author = (String)individual.get(PLAYLIST_AUTHOR);
                    String desc = (String)individual.get(PLAYLIST_DESCRIPTION);
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
            e.printStackTrace();
        }
        return null;
    }

    public static Lesson getAllLessons() {
        return null;
    }

    public static ArrayList<Lesson> getLessonFromID(String inputID) {
        return null;
    }
}