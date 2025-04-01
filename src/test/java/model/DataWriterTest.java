package model;

import java.time.LocalDate;
import java.util.ArrayList;
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


    @Before
    public void setup() {
        users.clear();
        DataWriter.saveUsers(users);
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
    public void testMulitpleClasses() {
        
    }
}
