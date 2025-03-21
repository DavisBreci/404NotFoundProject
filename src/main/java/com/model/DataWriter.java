package com.model;

/**
 * @author brenskrz
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.Iterator;

import java.time.LocalDate;

import org.jfugue.theory.TimeSignature;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javafx.scene.chart.PieChart.Data;





public class DataWriter extends DataConstants {

    public static void saveUsers(ArrayList<User> users) {
        // UserList userList = UserList.getInstance();
        // ArrayList<User> users = userList.getUsers();

        // hard code testing
        // ArrayList<User> users = new ArrayList<>();
        // users.add(new User(USER_ID, USER_FIRST_NAME, USER_LAST_NAME, USER_EMAIL, USER_USERNAME, USER_PASSWORD, 0, 0, null, null, null));

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
        userDetails.put(USER_ID, user.id);
        userDetails.put(USER_USERNAME, user.getUsername());
        userDetails.put(USER_PASSWORD, user.getPassword());
        userDetails.put(USER_EMAIL, user.getEmail());
        userDetails.put(USER_FIRST_NAME, user.getFirstName());
        userDetails.put(USER_LAST_NAME, user.getLastName());
        userDetails.put(USER_STREAK, user.getStreak());
        userDetails.put(USER_SONGS_PLAYED, user.getSongsPlayed());
        userDetails.put(USER_ASSIGNED_LESSONS, user.getAssignedLessons());
        JSONArray playlistIds = new JSONArray();
    if (user.getPlaylists() != null) {  
        for (Playlist playlist : user.getPlaylists()) {
            playlistIds.add(playlist.id);
        }
    }
    userDetails.put(USER_PLAYLISTS, playlistIds); 

        return userDetails;
    }

    // public static void main(String args[]) {
    //     DataWriter.saveUsers();
    // }


    public static void saveTeachers(ArrayList<Teacher> teachers) {
        // UserList teacherList = UserList.getInstance();
        // ArrayList<User> teachers = teacherList.getUsers();
        
        JSONArray jsonTeachers = new JSONArray();

        for(int i = 0; i<teachers.size(); i++) {
            jsonTeachers.add(getUserJSON(teachers.get(i)));
            jsonTeachers.add(getTeacherJSON(teachers.get(i)));
        }

        try (FileWriter file = new FileWriter(TEACHER_TEMP_FILE_NAME)) {
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
        // teacherDetails.put(USER_ASSIGNED_LESSONS, teacher.getAssignedLessons());
        teacherDetails.put(TEACHER_LESSONS, teacher.getLessons());

        

        return teacherDetails;
    }
    // public static void main(String args[]) {
    //     DataWriter.saveTeachers();
    // }

    public static void saveSongs(ArrayList<Song> songs) {
        // SongList songList = SongList.getInstance();
        // ArrayList<Song> songs = songList.getSongs();

         // hard code testing
        // ArrayList<Song> songs = new ArrayList<>();
        // Instrument testInstrument = Instrument.ACOUSTIC_BASS;
        // int testTempo = 120;
        // Score testScore = new Score("id", testInstrument, testTempo);
        // songs.add(new Song(SONG_ID, SONG_TITLE, SONG_ARTIST, SONG_GENRE, Key.AMAJOR_GbMINOR, DifficultyLevel.ADVANCED, Instrument.ACOUSTIC_BASS, testScore));

        JSONArray jsonSongs = new JSONArray();
        JSONArray jsonScores = new JSONArray();

        for(Song song : songs){
            jsonSongs.add(getSongJSON(song));
            jsonScores.add(getScoreJSON(song.getScore()));

        }

        try (
            FileWriter songFile = new FileWriter(SONG_TEMP_FILE_NAME);
            FileWriter  scoreFile = new FileWriter(SCORE_TEMP_FILE_NAME);
        ) {
            songFile.write(jsonSongs.toJSONString());
            songFile.flush();
            scoreFile.write(jsonScores.toJSONString());
            scoreFile.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject getSongJSON(Song song) {
        JSONObject songDetails = new JSONObject();

        songDetails.put(SONG_ID, song.id);
        songDetails.put(SONG_TITLE, song.getTitle());
        songDetails.put(SONG_ARTIST, song.getArtist());
        songDetails.put(SONG_GENRE, song.getGenre());
        songDetails.put(SONG_DIFFICULTY_LEVEL, song.getDifficultyLevel().toString());
        songDetails.put(SONG_KEY, song.getKey().toString());
        songDetails.put(SONG_INSTRUMENT, song.getInstrument().toString());
        songDetails.put(SONG_SCORE, song.getScore().id);

        return songDetails;
    }

    // public static void main(String args[]) {
    //     DataWriter.saveSongs();
    // }
    

    public static void savePlaylists(ArrayList<Playlist> playlists) {
        // PlaylistList playlistList = PlaylistList.getInstance();
        // ArrayList<Playlist> playlists = playlistList.getPlaylists();

        //hard code testing
        // PlaylistList playlistList = new PlaylistList();
        // Playlist playlist1 = new Playlist(UUID.randomUUID(), "brendan's playlist", "brendan", "a great playlist", new ArrayList<>());
        // Playlist playlist2 = new Playlist(UUID.randomUUID(), "ryan's playlist", "ryan", "an amazing playlist", new ArrayList<>());

        // playlistList.addPlaylist(playlist1.getTitle(), playlist1.getAuthor(), playlist1.getDescription());
        // playlistList.addPlaylist(playlist2.getTitle(), playlist2.getAuthor(), playlist2.getDescription());
        // ArrayList<Playlist> playlists = playlistList.getPlaylists();

        JSONArray jsonPlaylists = new JSONArray();


        for( Playlist playlist : playlists) {
            jsonPlaylists.add(getPlaylistJSON(playlist, PLAYLIST_FILE_TEMP_NAME));
        }

        try (FileWriter file = new FileWriter(PLAYLIST_FILE_TEMP_NAME)) {
            file.write(jsonPlaylists.toJSONString());
            file.flush();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public static JSONObject getPlaylistJSON(Playlist playlist, String filename) {
        JSONObject playlistDetails = new JSONObject();

        playlistDetails.put(PLAYLIST_ID, playlist.id);
        playlistDetails.put(PLAYLIST_TITLE, playlist.getTitle());
        playlistDetails.put(PLAYLIST_AUTHOR, playlist.getAuthor());
        playlistDetails.put(PLAYLIST_DESCRIPTION, playlist.getDescription());
        JSONArray songIds = new JSONArray();
        if(playlist.getSongs() != null) {
            for(Song song: playlist.getSongs()) {
                songIds.add(song.id);
            }
        }
        playlistDetails.put(PLAYLIST_SONGS, songIds);
    

        return playlistDetails;
    }

    // public static void main(String args[]) {
    //         DataWriter.savePlaylists(DataLoader.getPlaylists());
    //     }

    public static void saveLessons() {
        // LessonList lessonList = LessonList.getInstance();
        // ArrayList<Lesson> lessons = lessonList.getLessons();

        JSONArray jsonLessons = new JSONArray();

        //hard code for testing
        LessonList lessonList = new LessonList();
        ArrayList<Song> lessonSongs1 = new ArrayList<>();
        ArrayList<Song> lessonSongs2 = new ArrayList<>();

        lessonSongs1.add(new Song(UUID.randomUUID().toString(), LESSONS_TITLE, SONG_ARTIST, SONG_GENRE, null, null, null, null));
        lessonSongs1.add(new Song(UUID.randomUUID().toString(), LESSONS_TITLE, SONG_ARTIST, SONG_GENRE, null, null, null, null));
        lessonSongs2.add(new Song(UUID.randomUUID().toString(), LESSONS_TITLE, SONG_ARTIST, SONG_GENRE, null, null, null, null));
        lessonSongs2.add(new Song(UUID.randomUUID().toString(), LESSONS_TITLE, SONG_ARTIST, SONG_GENRE, null, null, null, null));

        lessonList.addLesson("Lesson 1", lessonSongs1);
        lessonList.addLesson("Lesson 2", lessonSongs2);

        ArrayList<Lesson> lessons = lessonList.getLessons();

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

        lessonDetails.put(LESSONS_ID, lesson.id);
        lessonDetails.put(LESSONS_TITLE, lesson.getTitle());
        ArrayList<String> songIds = new ArrayList<>();
        for(Song song : lesson.getSongs()) {
            songIds.add(song.id);
        }
        lessonDetails.put(LESSONS_SONGS, songIds);
        return lessonDetails;
    }
    // public static void main(String args[]) {
    //     DataWriter.saveLessons();
    // }

    public static void saveNewScore(Score newScore, String filename) {

        JSONObject jsonScore = new JSONObject();
        jsonScore.put("uuid", newScore.id);
        jsonScore.put("instrument", newScore.getInstrument().toString());
        jsonScore.put("tempo", Integer.toString(newScore.getTempo()));

        JSONArray jsonMeasures = new JSONArray();
        for (Measure measure : newScore.getMeasures()) {
            System.out.println("Processing measure with time signature: " + measure.getTimeSignature());

            JSONObject jsonMeasure = new JSONObject();
            JSONArray jsonChords = new JSONArray();

            Iterator<Map.Entry<Rational, Chord>> iterator = measure.chordIterator();
            while(iterator.hasNext()) {
                Map.Entry<Rational, Chord> entry = iterator.next();
                Rational offset = entry.getKey();
                Chord chord = entry.getValue();
                if (offset == null) {
                    System.out.println("Warning: Null offset found, skipping chord.");
                    continue;
                }
                System.out.println("Processing chord at offset: " + chord.getOffset());

                JSONObject jsonChord = new JSONObject();
                jsonChord.put("offset", chord.getValue().toString());
                jsonChord.put("value", chord.getValue().toString());
                jsonChord.put("dotted", Boolean.toString(chord.isDotted()));


                JSONArray jsonNotes = new JSONArray();
                for (int i = 0; i < 6; i++) {
                    if (i < chord.getNotes().size() && chord.getNotes().get(i) != null) {
                        Note note = chord.getNotes().get(i);
                        System.out.println("Adding note: " + note.getPitchClass() + " at string " + note.getString());

                        JSONObject jsonNote = new JSONObject();
                        jsonNote.put("pitchClass", note.getPitchClass().toString());
                        jsonNote.put("octave", Integer.toString(note.getOctave()));
                        jsonNote.put("string", Integer.toString(note.getString()));
                        jsonNote.put("frontTie", Boolean.toString(note.hasFrontTie()));
                        jsonNote.put("backTie", Boolean.toString(note.hasBackTie()));
                        jsonNotes.add(jsonNote);
                    } else {
                        jsonNotes.add("null");
                    }
                }

                jsonChord.put("notes", jsonNotes);
                jsonChords.add(jsonChord);
            }
            jsonMeasure.put("timeSignature", measure.getTimeSignatureString());


            if (jsonChords.isEmpty()) {
                System.out.println("Warning: No chords found in measure.");
            }

            jsonMeasure.put("chords", jsonChords);
            jsonMeasures.add(jsonMeasure);
        }

        if (jsonMeasures.isEmpty()) {
            System.out.println("Warning: No measures found in score.");
        }

        jsonScore.put("measures", jsonMeasures);

        try (FileWriter file = new FileWriter(filename)) {
            file.write(jsonScore.toJSONString());
            file.flush();
            System.out.println("Successfully wrote score to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveScores(ArrayList<Score> scores) { // deprecated
        JSONArray jsonScores = new JSONArray();

        for (Score score : scores) {
            JSONObject jsonScore = new JSONObject();

            jsonScore.put("uuid", score.id);
            jsonScore.put("instrument", score.getInstrument().toString());
            jsonScore.put("tempo", Integer.toString(score.getTempo()));

            JSONArray jsonMeasures = new JSONArray();

            for (Measure measure : score.getMeasures()) {
                JSONObject jsonMeasure = new JSONObject();


                JSONArray jsonChords = new JSONArray();
                Iterator<Map.Entry<Rational, Chord>> iterator = measure.chordIterator();
                while (iterator.hasNext()) {
                    Map.Entry<Rational, Chord> entry = iterator.next();
                    Rational offset = entry.getKey(); 
                    Chord chord = entry.getValue(); 
                    if (offset == null) {
                        System.out.println("Warning: Null offset found, skipping chord.");
                    continue;
                    }

                    JSONObject jsonChord = new JSONObject();

                    jsonChord.put("offset", entry.getKey().toString());
                    jsonChord.put("value", entry.getValue().getValue().toString());
                    jsonChord.put("dotted", Boolean.toString(chord.isDotted()));
                    
                    JSONArray jsonNotes = new JSONArray();
                    for (Note note : chord.getNotes()) {
                        if (note != null) {
                            JSONObject jsonNote = new JSONObject();
                           
                        jsonNote.put("pitchClass", note.getPitchClass().toString());
                        jsonNote.put("octave", Integer.toString(note.getOctave()));
                        jsonNote.put("string", Integer.toString(note.getString() + 1));
                        jsonNote.put("frontTie", Boolean.toString(note.hasFrontTie()));
                        jsonNote.put("backTie", Boolean.toString(note.hasBackTie()));
                        jsonNotes.add(jsonNote);
                        }
                    }

                    jsonChord.put("notes", jsonNotes);
                    jsonChords.add(jsonChord);
                }
                jsonMeasure.put("timeSignature", measure.getTimeSignatureString());

                jsonMeasure.put("chords", jsonChords);
                jsonMeasures.add(jsonMeasure);
            }

            jsonScore.put("measures", jsonMeasures);
            jsonScores.add(jsonScore);
        }

        try (FileWriter file = new FileWriter(SCORE_TEMP_FILE_NAME)) {
            file.write(jsonScores.toJSONString());
            file.flush();
            System.out.println("Successfully wrote all scores to scores.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject getScoreJSON(Score score) {
            JSONObject jsonScore = new JSONObject();

            jsonScore.put("uuid", score.id);
            jsonScore.put("instrument", score.getInstrument().toString());
            jsonScore.put("tempo", Integer.toString(score.getTempo()));

            JSONArray jsonMeasures = new JSONArray();
            for (Measure measure : score.getMeasures()) {
                JSONObject jsonMeasure = new JSONObject();


                JSONArray jsonChords = new JSONArray();
                Iterator<Map.Entry<Rational, Chord>> iterator = measure.chordIterator();
                while (iterator.hasNext()) {
                    Map.Entry<Rational, Chord> entry = iterator.next();
                    Rational offset = entry.getKey(); 
                    Chord chord = entry.getValue(); 
                    if (offset == null) {
                        System.out.println("Warning: Null offset found, skipping chord.");
                    continue;
                    }

                    JSONObject jsonChord = new JSONObject();

                    jsonChord.put("offset", entry.getKey().toString());
                    jsonChord.put("value", entry.getValue().getValue().toString());
                    jsonChord.put("dotted", Boolean.toString(chord.isDotted()));
                    
                    JSONArray jsonNotes = new JSONArray();
                    for (Note note : chord.getNotes()) {
                        if (note != null) {
                            JSONObject jsonNote = new JSONObject();
                           
                        jsonNote.put("pitchClass", note.getPitchClass().toString());
                        jsonNote.put("octave", Integer.toString(note.getOctave()));
                        jsonNote.put("string", Integer.toString(note.getString() + 1));
                        jsonNote.put("frontTie", Boolean.toString(note.hasFrontTie()));
                        jsonNote.put("backTie", Boolean.toString(note.hasBackTie()));
                        jsonNotes.add(jsonNote);
                        }
                    }

                    jsonChord.put("notes", jsonNotes);
                    jsonChords.add(jsonChord);
                }
                jsonMeasure.put("timeSignature", measure.getTimeSignatureString());

                jsonMeasure.put("chords", jsonChords);
                jsonMeasures.add(jsonMeasure);
            }

            jsonScore.put("measures", jsonMeasures);
         return jsonScore;
    }

    public static void main(String args[]) {
       
        DataWriter.saveUsers(DataLoader.getUsers());
        DataWriter.saveTeachers(DataLoader.getTeachers());
        DataWriter.savePlaylists(DataLoader.getAllPlaylists());
        DataWriter.saveSongs(DataLoader.getAllSongs());
        DataWriter.saveLessons();

        DataWriter.saveNewScore(DataLoader.getScoreFromID("a42d710f-afcb-4bce-bfd7-ecb43e6a5a89"), SCORE_TEMP_FILE_NAME);
    }
}