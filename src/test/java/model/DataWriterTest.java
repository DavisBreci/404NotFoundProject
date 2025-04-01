package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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



    @Before
    public void setup() {
        users.clear();
        teachers.clear();
        DataWriter.saveUsers(users);
        DataWriter.saveTeachers(teachers);
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
    public void testGetUserJSON2() {
        User newUser =  new User("Test", "User",  "Name", "email", "username", "iHaTeMyBr0tHeR*", 2, 2, null, new ArrayList<Lesson>(), LocalDate.MIN);
        JSONObject jsonOutput = DataWriter.getUserJSON(newUser);
    }

    //Attempmting to check getUserJSON returns correctly for null lessons
    @Test
    public void testGetUserJSON3() {
        User newUser =  new User("Test", "User",  "Name", "email", "username", "iHaTeMyBr0tHeR*", 2, 2, new ArrayList<Playlist>(), null,  LocalDate.MIN);
        JSONObject jsonOutput = DataWriter.getUserJSON(newUser);
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

    @Test
    public void testOneClass() {
        ArrayList<ArrayList<User>> classes = new ArrayList<>();
        ArrayList<User> firstClass = new ArrayList<>();
        User student = new User(null, "User",  "Name", "email", "username", "iHaTeMyBr0tHeR*", 2, 2, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN);
        firstClass.add(student);
        classes.add(firstClass);
        Teacher teacher = new Teacher(null, "first", "last", "email", "teacher", "iHaTeMyBr0tHeR*", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN, classes,  new ArrayList<Lesson>());
        teachers.add(teacher);
    DataWriter.saveTeachers(teachers);
    ArrayList<Teacher> loadedTeachers = DataLoader.getTeachers();
    
    // 4. Proper assertions
    assertFalse("No teachers loaded", loadedTeachers.isEmpty());
    
    Teacher loadedTeacher = loadedTeachers.get(0);
    assertNotNull("Teacher classes null", loadedTeacher.getClasses());
    
    ArrayList<User> loadedClass = loadedTeacher.getClasses().get(0);
    assertNotNull("Class not loaded", loadedClass);
    
    User loadedStudent = loadedClass.get(0);
    assertNotNull("Student null", loadedStudent);
    
    assertEquals("Username mismatch", "username", loadedStudent.getUsername());
    assertEquals("Full name mismatch", "User Name", 
        loadedStudent.getFirstName() + " " + loadedStudent.getLastName());
    }    

    @Test
    public void testTwoClasses() {
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
        // assertEquals(secondUser, actualUser);
    }

    @Test
    public void testGetTeacherJSON() {
        Teacher teacher = new Teacher(null, "first", "last", "email", "teacher", "iHaTeMyBr0tHeR*", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN, new ArrayList<ArrayList<User>>(),  new ArrayList<Lesson>());
        JSONObject jsonOutput = DataWriter.getTeacherJSON(teacher);
        assertNotNull("jsonOutput null", jsonOutput);
    }

    @Test
    public void testZeroSongs() {

    }

    @Test
    public void testOneSong() {
        
    }

    @Test
    public void testTwoSongs() {
        
    }

    @Test
    public void testEmtySongSave() {
        
    }

    @Test
    public void testNullSongSave() {
        
    }

    @Test
    public void testGetSongJSON() {
        
    }

    @Test
    public void testZeroPlaylists() {
        
    }

    @Test
    public void testOnePlaylist() {
        
    }

    @Test
    public void testTwoPlaylists() {
        
    }

    @Test
    public void testEmptyPlaylist() {
        
    }

    @Test
    public void testNullPlaylist() {
        
    }

    @Test
    public void testGetPlaylistJSON() {
        
    }

    @Test
    public void testZeroLessons() {
        
    }

    @Test
    public void testOneLesson() {
        
    }

    @Test
    public void testTwoLessons() {
        
    }

    @Test
    public void testEmptyLessonSave() {
        
    }

    @Test
    public void testNullLessonSave() {
        
    }

    @Test
    public void testGetLessonJSON() {
        
    }

    @Test
    public void testZeroScores() {
        
    }

    @Test
    public void testOneScore() {
        
    }

    @Test
    public void testTwoScores() {
        
    }

    @Test
    public void testEmptyScore() {
        
    }

    @Test
    public void testNullScore() {
        
    }

    @Test
    public void testGetScoreJSON() {
        
    }
}
