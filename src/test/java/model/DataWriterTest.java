package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;




import org.junit.Assert;
import com.model.*;

public class DataWriterTest {

    private UserList userList = UserList.getInstance();
    private ArrayList<User> users = userList.getUsers();
    private ArrayList<Teacher> teachers = new ArrayList<>();
    private SongList songList = SongList.getInstance();
    private ArrayList<Song> songs = new ArrayList<>();
    private PlaylistList playlistList = PlaylistList.getInstance();
    private ArrayList<Playlist> playlists = new  ArrayList<>();
    private LessonList lessonList = LessonList.getInstance();
    private ArrayList<Lesson> lessons = new ArrayList<>();
    private ArrayList<Score> scores = new ArrayList<>();
    



    @Before
    public void setup() {
        users.clear();
        teachers.clear();
        songs.clear();
        playlists.clear();
        lessons.clear();
        DataWriter.saveUsers(users);
        DataWriter.saveTeachers(teachers);
        DataWriter.saveSongs(songs);
        DataWriter.savePlaylists(playlists);
        DataWriter.saveLessons(lessons);
        // Score score1 = DataLoader.getScoreFromID("a42d710f-afcb-4bce-bfd7-ecb43e6a5a89");
        // Score score2 = DataLoader.getScoreFromID("3a6c83d2-2235-4fff-84dc-7ad6ec2dabf8");
        // Score score3 = DataLoader.getScoreFromID("d3f280ff-4f4c-498e-a8c5-8406067be730");
        // Score score4 = DataLoader.getScoreFromID("82cd6a4e-adae-4fc4-b96c-f767de9e2cb8");
        // Score score5 = DataLoader.getScoreFromID("b5eca77c-d611-439f-89d6-44e3127e85b5");
        // Score score6 = DataLoader.getScoreFromID("7ca4352f-b2fb-4f15-8437-fe23dede5572");

        // scores.add(score1);
        // scores.add(score2);
        // scores.add(score3);
        // scores.add(score4);
        // scores.add(score5);
        // scores.add(score6);

    }

    @After 
    public void close() {
        userList.getInstance().getUsers().clear();
        DataWriter.saveUsers(users);
    }

    @Test
    public void testZeroUser() {
        users = DataLoader.getUsers();
        assertEquals(0, users.size());
    }

    @Test
    public void testOneUsers() {
        users.add(new User("Test", "User", "Name", "email", "username", "iHaTeMyBr0tHeR*", 2, 2, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN));
        DataWriter.saveUsers(users);
        assertEquals("username", DataLoader.getUsers().get(0).getUsername());
    }

    @Test
    public void testTwoUsers() {
        users.add(new User("Test", "User", "Name", "email", "username", "iHaTeMyBr0tHeR*", 2, 2, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN));
        users.add(new User("Test2", "User", "Name", "email", "username2", "iHaTeMyBr0tHeR*", 2, 2, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN));
        DataWriter.saveUsers(users);
        assertEquals("username2", DataLoader.getUsers().get(1).getUsername());
    }

    @Test
    public void testTwoUsers2() {
        users.add(new User("Test", "User", "Name", "email", "username", "iHaTeMyBr0tHeR*", 2, 2, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN));
        users.add(new User("Test", "User", "Name", "email", "username", "iHaTeMyBr0tHeR*", 2, 2, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN));
        DataWriter.saveUsers(users);
        assertEquals("username", DataLoader.getUsers().get(1).getUsername());
    }

    @Test
    public void testTenUsers() {
        users.add(new User("Test", "User",  "Name", "email", "username", "iHaTeMyBr0tHeR*", 2, 2, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN));
        users.add(new User("Test2", "User", "Name", "email", "username2", "iHaTeMyBr0tHeR*", 2, 2, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN));
        users.add(new User("Test3", "User", "Name", "email", "username3", "iHaTeMyBr0tHeR*", 2, 2, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN));
        users.add(new User("Test4", "User", "Name", "email", "username4", "iHaTeMyBr0tHeR*", 2, 2, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN));
        users.add(new User("Test5", "User", "Name", "email", "username5", "iHaTeMyBr0tHeR*", 2, 2, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN));
        users.add(new User("Test6", "User", "Name", "email", "username6", "iHaTeMyBr0tHeR*", 2, 2, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN));
        users.add(new User("Test7", "User", "Name", "email", "username7", "iHaTeMyBr0tHeR*", 2, 2, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN));
        users.add(new User("Test8", "User", "Name", "email", "username8", "iHaTeMyBr0tHeR*", 2, 2, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN));
        users.add(new User("Test9", "User", "Name", "email", "username9", "iHaTeMyBr0tHeR*", 2, 2, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN));
        users.add(new User("Test10", "User", "Name", "email", "username10", "iHaTeMyBr0tHeR*", 2, 2, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN));
        DataWriter.saveUsers(users);
        assertEquals("username10", DataLoader.getUsers().get(9).getUsername());
    }

    //Cannot test for an empty or null user for the IllegalArgumentException is thrown by the User constrcutor,
    // so these tests do run correctly but saveUsers does not ever run for an empty or null user
        @Test
        public void testEmptyUserSave() {        
            assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
                @Override
                public void run() throws Throwable {
                    users.add(new User("", "", "", "", "", "", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN));
                    DataWriter.saveUsers(users);
                }
            });
        }

    @Test
    public void testNullUserSave() {        
        assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                users.add(new User(null, null, null, null, null, null, 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN));
                DataWriter.saveUsers(users);
            }
        });
    }

    @Test
    public void testGetUserJSON() {
        User newUser =  new User("Test", "User",  "Name", "email", "username", "iHaTeMyBr0tHeR*", 2, 2, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN);
        JSONObject jsonOutput = DataWriter.getUserJSON(newUser);
        assertNotNull("Should not be null.", jsonOutput);
    }
    
    //Attempmting to check getUserJSON returns correctly for null playlists
    @Test
    public void testGetUserJSONPlaylist() {
        User newUser =  new User("Test", "User",  "Name", "email", "username", "iHaTeMyBr0tHeR*", 2, 2, null, new ArrayList<Lesson>(), LocalDate.MIN);
        JSONObject jsonOutput = DataWriter.getUserJSON(newUser);
        assertEquals("[]", jsonOutput.get("playlists").toString());
    }

    //Attempmting to check getUserJSON returns correctly for null lessons
    @Test
    public void testGetUserJSONLesson() {
        User newUser =  new User("Test", "User",  "Name", "email", "username", "iHaTeMyBr0tHeR*", 2, 2, new ArrayList<Playlist>(), null,  LocalDate.MIN);
        JSONObject jsonOutput = DataWriter.getUserJSON(newUser);
        assertNull(jsonOutput.get("lessons"));
    }

    @Test
    public void testZeroTeachers() {
        DataLoader.getTeachers();
        assertEquals(0, teachers.size());
    }

    @Test
    public void testOneTeacher() {
        teachers.add(new Teacher(null, "first", "last", "email", "teacher", "iHaTeMyBr0tHeR*", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN, new ArrayList<ArrayList<User>>(),  new ArrayList<Lesson>()));
        DataWriter.saveTeachers(teachers);
        assertEquals("teacher", DataLoader.getTeachers().get(0).getUsername());
    }

    @Test
    public void testTwoTeachers() {
        teachers.add(new Teacher(null, "first", "last", "email", "teacher", "iHaTeMyBr0tHeR*", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN, new ArrayList<ArrayList<User>>(),  new ArrayList<Lesson>()));
        teachers.add(new Teacher(null, "first", "last", "email", "teacher2", "iHaTeMyBr0tHeR*", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN, new ArrayList<ArrayList<User>>(),  new ArrayList<Lesson>()));
        DataWriter.saveTeachers(teachers);
        assertEquals("teacher2", DataLoader.getTeachers().get(1).getUsername());
    }

    @Test
    public void testTenTeachers() {
        teachers.add(new Teacher(null, "first", "last", "email", "teacher", "iHaTeMyBr0tHeR*", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN, new ArrayList<ArrayList<User>>(),  new ArrayList<Lesson>()));
        teachers.add(new Teacher(null, "first", "last", "email", "teacher2", "iHaTeMyBr0tHeR*", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN, new ArrayList<ArrayList<User>>(),  new ArrayList<Lesson>()));
        teachers.add(new Teacher(null, "first", "last", "email", "teacher3", "iHaTeMyBr0tHeR*", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN, new ArrayList<ArrayList<User>>(),  new ArrayList<Lesson>()));
        teachers.add(new Teacher(null, "first", "last", "email", "teacher4", "iHaTeMyBr0tHeR*", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN, new ArrayList<ArrayList<User>>(),  new ArrayList<Lesson>()));
        teachers.add(new Teacher(null, "first", "last", "email", "teacher5", "iHaTeMyBr0tHeR*", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN, new ArrayList<ArrayList<User>>(),  new ArrayList<Lesson>()));
        teachers.add(new Teacher(null, "first", "last", "email", "teacher6", "iHaTeMyBr0tHeR*", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN, new ArrayList<ArrayList<User>>(),  new ArrayList<Lesson>()));
        teachers.add(new Teacher(null, "first", "last", "email", "teacher7", "iHaTeMyBr0tHeR*", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN, new ArrayList<ArrayList<User>>(),  new ArrayList<Lesson>()));
        teachers.add(new Teacher(null, "first", "last", "email", "teacher8", "iHaTeMyBr0tHeR*", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN, new ArrayList<ArrayList<User>>(),  new ArrayList<Lesson>()));
        teachers.add(new Teacher(null, "first", "last", "email", "teacher9", "iHaTeMyBr0tHeR*", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN, new ArrayList<ArrayList<User>>(),  new ArrayList<Lesson>()));
        teachers.add(new Teacher(null, "first", "last", "email", "teacher10", "iHaTeMyBr0tHeR*", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN, new ArrayList<ArrayList<User>>(),  new ArrayList<Lesson>()));
        DataWriter.saveTeachers(teachers);
        assertEquals("teacher10", DataLoader.getTeachers().get(9).getUsername());
    }

    @Test
    public void testEmptyTeacherSave() {
        assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                teachers.add(new Teacher("", "", "", "", "", "", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN, new ArrayList<ArrayList<User>>(),  new ArrayList<Lesson>()));
                DataWriter.saveTeachers(teachers);
            }
        });
    }

    @Test
    public void testNullTeacherSave() {
        assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                teachers.add(new Teacher(null, null, null, null, null, null, 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN, new ArrayList<ArrayList<User>>(), new ArrayList<Lesson>()));
                DataWriter.saveTeachers(teachers);
            }
        });
    }

    //Test fails for 
    @Test
    public void testOneClassesStudent() {
        ArrayList<ArrayList<User>> classes = new ArrayList<>();
        ArrayList<User> firstClass = new ArrayList<>();
        User student = new User("404", "User",  "Name", "email", "username", "iHaTeMyBr0tHeR*", 2, 2, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN);
        firstClass.add(student);
        classes.add(firstClass);
        Teacher teacher = new Teacher(null, "first", "last", "email", "teacher", "iHaTeMyBr0tHeR*", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN, classes,  new ArrayList<Lesson>());
        teachers.add(teacher);
        DataWriter.saveTeachers(teachers);
        ArrayList<Teacher> loadedTeachers = DataLoader.getTeachers();

        assertEquals(1, loadedTeachers.size());
        Teacher loadedTeacher = loadedTeachers.get(0);
        assertEquals("first", loadedTeacher.getFirstName());
        assertEquals("last", loadedTeacher.getLastName());
        assertEquals("teacher", loadedTeacher.getUsername());
        ArrayList<ArrayList<User>> loadedClasses = loadedTeacher.getClasses();
        assertEquals(1, loadedClasses.size()); // Ensure one class exists
        assertEquals(1, loadedClasses.get(0).size()); // Ensure one student exists in the class
    
        User loadedStudent = loadedClasses.get(0).get(0); // Retrieve the student from the class
        assertEquals("404",  loadedStudent.id);
    }    

    // made these tests to check if classes are loading and writing correctly in teachers
    @Test
    public void testOneClassSize() {
        ArrayList<Teacher> teachers = new ArrayList<>();
        teachers = DataLoader.getTeachers();
        ArrayList<ArrayList<User>> classes = new ArrayList<>();
        ArrayList<User> firstClass = new ArrayList<>();
        firstClass.add(new User("Test", "User",  "Name", "email", "username", "iHaTeMyBr0tHeR*", 2, 2, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN));
        classes.add(firstClass);
        Teacher teacher = new Teacher(null, "first", "last", "email", "teacher", "iHaTeMyBr0tHeR*", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN, classes,  new ArrayList<Lesson>());
        teachers.add(teacher);
        DataWriter.saveTeachers(teachers);
        ArrayList<Teacher> loadedTeachers = DataLoader.getTeachers();
        assertFalse(loadedTeachers.isEmpty());
        Teacher loadedTeacher = loadedTeachers.get(0);
        assertEquals(1, loadedTeacher.getClasses().size());
    }
    
    @Test
    public void testTwoClassesSize() {
        ArrayList<Teacher> teachers = new ArrayList<>();
        teachers = DataLoader.getTeachers();
        ArrayList<ArrayList<User>> classes = new ArrayList<>();
        ArrayList<User> firstClass = new ArrayList<>();
        firstClass.add(new User("Test", "User",  "Name", "email", "username", "iHaTeMyBr0tHeR*", 2, 2, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN));
        ArrayList<User> secondClass = new ArrayList<>();
        User secondUser = new User("Test", "User",  "Name", "email", "username2", "iHaTeMyBr0tHeR*", 2, 2, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN);
        secondClass.add(secondUser);
        classes.add(firstClass);
        classes.add(secondClass);
        Teacher teacher = new Teacher(null, "first", "last", "email", "teacher", "iHaTeMyBr0tHeR*", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN, classes,  new ArrayList<Lesson>());
        teachers.add(teacher);
        DataWriter.saveTeachers(teachers);
        User actualUser = DataLoader.getTeachers().get(0).getClasses().get(1).get(0);
        ArrayList<Teacher> loadedTeachers = DataLoader.getTeachers();
        assertFalse(loadedTeachers.isEmpty());
        Teacher loadedTeacher = loadedTeachers.get(0);
        assertEquals(2, loadedTeacher.getClasses().size());
    }

    @Test
    public void testOneClassStudent() {
        ArrayList<Teacher> teachers = new ArrayList<>();
        teachers = DataLoader.getTeachers();
        ArrayList<ArrayList<User>> classes = new ArrayList<>();
        ArrayList<User> firstClass = new ArrayList<>();
        firstClass.add(new User("Test", "User",  "Name", "email", "username", "iHaTeMyBr0tHeR*", 2, 2, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN));
        classes.add(firstClass);
        Teacher teacher = new Teacher(null, "first", "last", "email", "teacher", "iHaTeMyBr0tHeR*", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN, classes,  new ArrayList<Lesson>());
        teachers.add(teacher);
        DataWriter.saveTeachers(teachers);
        ArrayList<Teacher> loadedTeachers = DataLoader.getTeachers();
        assertFalse(loadedTeachers.isEmpty());
        Teacher loadedTeacher = loadedTeachers.get(0);
        assertEquals(1, loadedTeacher.getClasses().get(0).size());
    }
    

    @Test
    public void testGetTeacherJSON() {
        Teacher teacher = new Teacher(null, "first", "last", "email", "teacher", "iHaTeMyBr0tHeR*", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN, new ArrayList<ArrayList<User>>(),  new ArrayList<Lesson>());
        JSONObject jsonOutput = DataWriter.getTeacherJSON(teacher);
        assertNotNull("jsonOutput null", jsonOutput);
    }

    @Test
    public void testTeacherGetJSONPlaylist() {
        Teacher newteacher = new Teacher(null, "first", "last", "email", "teacher", "iHaTeMyBr0tHeR*", 0, 0, null, new ArrayList<Lesson>(), LocalDate.MIN, new ArrayList<ArrayList<User>>(),  new ArrayList<Lesson>());
        JSONObject jsonOutput = DataWriter.getTeacherJSON(newteacher);
        assertNull(jsonOutput.get("playlists"));
    }

    @Test
    public void testTeacherGetJSONAssignedLesson() {
        Teacher newteacher = new Teacher(null, "first", "last", "email", "teacher", "iHaTeMyBr0tHeR*", 0, 0, new ArrayList<Playlist>(), null, LocalDate.MIN, new ArrayList<ArrayList<User>>(),  new ArrayList<Lesson>());
        JSONObject jsonOutput = DataWriter.getTeacherJSON(newteacher);
        assertEquals("[]", jsonOutput.get("assignedLessons").toString());

    }

    @Test
    public void testTeacherGetJSONClasses() {
        Teacher newteacher = new Teacher(null, "first", "last", "email", "teacher", "iHaTeMyBr0tHeR*", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN, null,  new ArrayList<Lesson>());
        JSONObject jsonOutput = DataWriter.getTeacherJSON(newteacher);
        assertEquals("[]", jsonOutput.get("classes").toString());

    }

    @Test
    public void testTeacherGetJSONLessons() {
        Teacher newteacher = new Teacher(null, "first", "last", "email", "teacher", "iHaTeMyBr0tHeR*", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN, new ArrayList<ArrayList<User>>(),  null);
        JSONObject jsonOutput = DataWriter.getTeacherJSON(newteacher);
        assertEquals("[]", jsonOutput.get("lessons").toString());

    }

    @Test
    public void testZeroSongs() {
        songs = DataLoader.getAllSongs();
        assertEquals(0, songs.size());
    }

    @Test
    public void testOneSong() {
        songs.add(new Song("Song", "testSong", "me", "rock", Key.CMAJOR_AMINOR , DifficultyLevel.BEGINNER, Instrument.DISTORTION_GUITAR, new Score(null, Instrument.ACOUSTIC_BASS, 10)));
        DataWriter.saveSongs(songs);
        assertEquals("testSong", DataLoader.getAllSongs().get(0).getTitle());
    }

    @Test
    public void testTwoSongs() {
        songs.add(new Song("Song", "testSong", "me", "rock", Key.CMAJOR_AMINOR , DifficultyLevel.BEGINNER, Instrument.DISTORTION_GUITAR, new Score(null, Instrument.ACOUSTIC_BASS, 10)));
        songs.add(new Song("Song", "testSong2", "me", "rock", Key.CMAJOR_AMINOR , DifficultyLevel.BEGINNER, Instrument.DISTORTION_GUITAR, new Score(null, Instrument.ACOUSTIC_BASS, 10)));
        DataWriter.saveSongs(songs);
        assertEquals("testSong2", DataLoader.getAllSongs().get(1).getTitle());
    }

    //COME BACK, logic maybe?
    @Test
    public void testEmtySongSave() {
        songs.add(new Song("", "", "", "", Key.CMAJOR_AMINOR , DifficultyLevel.BEGINNER, Instrument.DISTORTION_GUITAR, new Score(null, Instrument.ACOUSTIC_BASS, 10)));
        DataWriter.saveSongs(songs);
        assertEquals("", DataLoader.getAllSongs().get(0).getTitle());
    }

    @Test
    public void testNullSongSave() {
        assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                songs.add(new Song(null, null, null, null, null , null, null, null));
                DataWriter.saveSongs(songs);
            }
        });
    }

    @Test
    public void testGetSongJSON() {
        Song newSong = new Song("Song", "testSong", "me", "rock", Key.CMAJOR_AMINOR , DifficultyLevel.BEGINNER, Instrument.DISTORTION_GUITAR, new Score(null, Instrument.ACOUSTIC_BASS, 10));
        JSONObject jsonOutput = DataWriter.getSongJSON(newSong);
        assertNotNull( "jsonOutput is null.", jsonOutput);
    }

    //COME BACK
    @Test
    public void testGetSongJSONTitle() {
        Song newSong = new Song("Song", null, "me", "rock", Key.CMAJOR_AMINOR , DifficultyLevel.BEGINNER, Instrument.DISTORTION_GUITAR, new Score(null, Instrument.ACOUSTIC_BASS, 10));
        JSONObject jsonOutput = DataWriter.getSongJSON(newSong);
        assertNull( jsonOutput.get("artist"));

    }
    //COME BACK
    @Test
    public void testGetSongJSONArtist() {
        Song newSong = new Song("Song", "title", null, "rock", Key.CMAJOR_AMINOR , DifficultyLevel.BEGINNER, Instrument.DISTORTION_GUITAR, new Score(null, Instrument.ACOUSTIC_BASS, 10));
        JSONObject jsonOutput = DataWriter.getSongJSON(newSong);
        assertNull( jsonOutput.get("artist"));

    }

    @Test
    public void testZeroPlaylists() {
        playlists = DataLoader.getAllPlaylists();
        assertEquals(0, DataLoader.getAllPlaylists().size());
    }

    @Test
    public void testOnePlaylist() {
        playlists.add(new Playlist(null, "Brendan Playlist", "Brendan", "awesome", songs));
        DataWriter.savePlaylists(playlists);
        assertEquals("Brendan Playlist", DataLoader.getAllPlaylists().get(0).getTitle());
    }

    @Test
    public void testTwoPlaylists() {
        playlists.add(new Playlist(null, "Brendan Playlist", "Brendan", "awesome", songs));
        playlists.add(new Playlist(null, "Brendan Playlist Two", "Brendan", "awesome", songs));
        DataWriter.savePlaylists(playlists);
        assertEquals("Brendan Playlist Two", DataLoader.getAllPlaylists().get(1).getTitle());
    }

    @Test
    public void testEmptyPlaylist() {
        playlists.add(new Playlist("", "", "", "", songs));
        DataWriter.savePlaylists(playlists);
        assertEquals("", DataLoader.getAllPlaylists().get(0).getTitle());
    }

    @Test
    public void testNullPlaylist() {
        assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                playlists.add(new Playlist(null, null, null, null, songs));
                DataWriter.savePlaylists(playlists);
                            }
        });
        assertNotNull(DataLoader.getAllPlaylists());
    }

    @Test
    public void testGetPlaylistJSON() {
        Playlist newPlaylist = new Playlist(null, "Brendan Playlist", "Brendan", "awesome", songs);
        JSONObject jsonOutput = DataWriter.getPlaylistJSON(newPlaylist, null);
        assertNotNull("Should not be null.", jsonOutput);
    }

    @Test
    public void testGetPlaylistJSONTitle() {
        assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                Playlist playlist = new Playlist(null, null, "me", "awesome", songs);
                JSONObject jsonOutput = DataWriter.getPlaylistJSON(playlist, null);
                    }
        });
    }

    @Test
    public void testGetPlaylistJSONAuthor() {
        assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                Playlist playlist = new Playlist(null, "playlist", null, "awesome", songs);
                JSONObject jsonOutput = DataWriter.getPlaylistJSON(playlist, null);
                    }
        });
    }

    @Test
    public void testGetPlaylistJSONDescription() {
        Playlist playlist = new Playlist(null, "playlist", "me", null,  songs);
        JSONObject jsonOutput = DataWriter.getPlaylistJSON(playlist, null);
        assertNotNull(jsonOutput.get("description"));
    }

    @Test 
    public void testGetPlaylistJSONSongs() {
        Playlist playlist = new Playlist(null, "playlist", "me", "awesome",  null);
        JSONObject jsonOutput = DataWriter.getPlaylistJSON(playlist, null);
        assertNotNull(jsonOutput.get("songs"));

    }

    @Test
    public void testZeroLessons() {
        lessons = DataLoader.getAllLessons();
        assertEquals(0, DataLoader.getAllLessons().size());
    }

    //Bug found
    @Test
    public void testOneLesson() {
        lessons.add(new Lesson(null, songs, "Brendan lesson"));
        DataWriter.saveLessons(lessons);
        assertEquals("Brendan lesson", DataLoader.getAllLessons().get(0).getTitle());
    }

    //Bug found
    @Test
    public void testTwoLessons() {
        lessons.add(new Lesson(null, songs, "Brendan lesson two"));
        lessons.add(new Lesson(null, songs, "Brendan lesson two"));
        DataWriter.saveLessons(lessons);
        assertEquals("Brendan lesson two", DataLoader.getAllLessons().get(1).getTitle());

    }

    //Bug found
    @Test
    public void testEmptyLessonSave() {
        lessons.add(new Lesson("", songs, ""));
        DataWriter.saveLessons(lessons);
        assertNotNull(DataLoader.getAllLessons());
    }

    @Test
    public void testNullLessonSave() {
        assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                lessons.add(new Lesson(null, songs, null));
                DataWriter.saveLessons(lessons);
            }
        });
    }

    @Test
    public void testGetLessonJSON() {
        Lesson lesson = new Lesson(null, songs, "Brendan's lesson");
        JSONObject jsonOutput = DataWriter.getLessonJSON(lesson);
        assertNotNull(jsonOutput);
    }

    @Test 
    public void testGetLessonJSONSongs() {
        assertThrows(NullPointerException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                Lesson lesson = new Lesson(null, null, "Brendan's lesson");
                JSONObject jsonOutput = DataWriter.getLessonJSON(lesson);
                        }
        });

}

    @Test
    public void testGetLessonJSONTitle() {
        assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                Lesson lesson = new Lesson(null, songs, null);
                JSONObject jsonOutput = DataWriter.getLessonJSON(lesson);
                assertNotNull(jsonOutput.get("title"));
                    }
        });       

    }

    @Test
    public void testZeroScores() {
        scores = DataLoader.getAllScores("JSON/scores.json");
        assertEquals(0, scores.size());
    }

    @Test
    public void testOneScore() {
        scores.add( new Score(null, Instrument.ACOUSTIC_BASS, 10));
        DataWriter.saveScores(scores);
        assertEquals(Instrument.ACOUSTIC_BASS, DataLoader.getAllScores("JSON/scores.json").get(0).getInstrument());

    }

    @Test
    public void testTwoScores() {
        scores.add(new Score(null, Instrument.ACOUSTIC_BASS, 100));
        scores.add(new Score(null, Instrument.DISTORTION_GUITAR, 200));
        DataWriter.saveScores(scores);
        assertEquals(200, DataLoader.getAllScores("JSON/scores.json").get(1).getTempo());
    }

    @Test
    public void testEmptyScoreUUID() {
        scores.add(new Score("", Instrument.ACOUSTIC_BASS, 0));
        DataWriter.saveScores(scores);
        assertNotNull(DataLoader.getAllScores("JSON/scores.json").get(0).id);
    }

    @Test
    public void testScoreNullUUID() {
        scores.add(new Score(null, Instrument.ACOUSTIC_BASS, 0));
        DataWriter.saveScores(scores);
        assertNotNull(DataLoader.getAllScores("JSON/scores.json").get(0).id);

    }
    @Test
    public void testNullScoreInstrument() {
        assertThrows(NullPointerException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                scores.add(new Score(null, null, 10));
                DataWriter.saveScores(scores);
                }
        });
    }

    @Test
    public void testZeroScoreTempo() {
        scores.add(new Score("null", Instrument.ACOUSTIC_BASS, 0));
        DataWriter.saveScores(scores);
        assertEquals(0, DataLoader.getAllScores("JSON/scores.json").get(0).getTempo());
    }

    @Test
    public void testGetScoreJSON() {
        Score newScore = new Score(null, Instrument.ACOUSTIC_BASS, 50);
        JSONObject jsonOutput = DataWriter.getScoreJSON(newScore);
        assertNotNull(jsonOutput);
    }

    @Test
    public void testGetScoreJSONInstrument() {
        assertThrows(NullPointerException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                Score newScore = new Score(null, null, 50);
                JSONObject jsonOutput = DataWriter.getScoreJSON(newScore);
                                }
        });

    }

    @Test
    public void testGetScoreJSONTempo() {
        Score newScore = new Score(null, Instrument.ACOUSTIC_BASS, 0);
        JSONObject jsonOutput = DataWriter.getScoreJSON(newScore);
        assertNotNull(jsonOutput.get("tempo"));
    }

}
