package model;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.time.LocalDate;

import com.model.*;

public class UserTest {
    @Test
    public void constructorTest1(){
        assertThrows(
            IllegalArgumentException.class,
            new ThrowingRunnable() {
                @Override
                public void run() throws Throwable {
                    User u = new User(null, null, null, null, null, null, 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN);
                }
            }
        );
    }
    @Test
    public void constructorTest2(){
        assertThrows(
            IllegalArgumentException.class,
            new ThrowingRunnable() {
                @Override
                public void run() throws Throwable {
                    User u = new User(null, "", "", "", null, null, 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN);
                }
            }
        );
    }
    @Test
    public void constructorTest3(){
        assertThrows(
            IllegalArgumentException.class,
            new ThrowingRunnable() {
                @Override
                public void run() throws Throwable {
                    User u = new User(null, "", "", "", "goodUsername", null, 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN);
                }
            }
        );
    }
    @Test
    public void constructorTest4(){
        User u = new User(null, "", "", "", "goodUsername", "GoodPass#1", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN);
        assertNotNull((Object)u);
    }
    // NOTE: it is not possible to ensure that a valid username is entered into the constructor,
    //       as the definiton of what is "valid" depends on the context of the function call.
    //       In the broader scope of the program, an invalid username can never be passed into
    //       the constructor since the checks are done within the UserList.createUser function
    //       and in DataLoader.
    @Test
    public void resetPasswordTest1(){
        User u = new User(null, "hello", "world", "email@email.email", "epicUsername", "myPassword#1", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN);
        u.resetPassword("fakeEmail", "GoodPass#1");
        assertNotEquals(u.getPassword(), "GoodPass#1");
    }
    @Test
    public void resetPasswordTest2(){
        User u = new User(null, "hello", "world", "email@email.email", "epicUsername", "myPassword#1", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN);
        u.resetPassword("email@email.email", "GoodPass#1");
        assertEquals(u.getPassword(), "GoodPass#1");
    }
    @Test
    public void resetPasswordTest3(){
        User u = new User(null, "hello", "world", "email@email.email", "epicUsername", "myPassword#1", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN);
        u.resetPassword(null, "GoodPass#1");
        assertNotEquals(u.getPassword(), "GoodPass#1");
    }
    @Test
    public void resetPasswordTest4(){
        User u = new User(null, "hello", "world", "email@email.email", "epicUsername", "myPassword#1", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN);
        u.resetPassword("email@email.email", null);
        assertNotEquals(u.getPassword(), null);
    }
    @Test
    public void resetPasswordTest5(){
        User u = new User(null, "hello", "world", "email@email.email", "epicUsername", "myPassword#1", 0, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN);
        u.resetPassword("email@email.email", "badpass");
        assertNotEquals(u.getPassword(), "badpass");
    }
    @Test
    public void isValidUsernameTest1(){
        assertFalse(User.isValidUsername(""));
    }
    @Test
    public void isValidUsernameTest2(){
        assertFalse(User.isValidUsername("hi"));
    }
    @Test
    public void isValidUsernameTest3(){
        assertFalse(User.isValidUsername(null));
    }
    @Test
    public void isValidUsernameTest4(){
        assertFalse(User.isValidUsername("sadlf;asdfkhsakjlhfklhsdakjlfhkashkedfkasldkfhakwlekfksahdlfksdkj"));
    }
    @Test
    public void isValidUsernameTest5(){
        assertFalse(User.isValidUsername("dbreci"));
    }
    @Test
    public void isValidUsernameTest6(){
        assertTrue(User.isValidUsername("uniqueUsername"));
    }
    @Test
    public void isValidPasswordTest1(){
        assertFalse(User.isValidPassword("s"));
    }
    @Test
    public void isValidPasswordTest2(){
        assertFalse(User.isValidPassword("SDFJLKSADJKBKJSDKFHDFLKJSJKDFLJKVSDFKJVBKJLSDFVKJHSDFLKJV#212E23"));
    }
    @Test
    public void isValidPasswordTest3(){
        assertFalse(User.isValidPassword("normalLengthNoSpecOrNum"));
    }
    @Test
    public void isValidPasswordTest4(){
        assertFalse(User.isValidPassword("32423423423"));
    }
    @Test
    public void isValidPasswordTest5(){
        assertFalse(User.isValidPassword("SpecialChar#"));
    }
    @Test
    public void isValidPasswordTest6(){
        assertFalse(User.isValidPassword(null));
    }
    @Test
    public void isValidPasswordTest7(){
        assertTrue(User.isValidPassword("ValidPassword&%123123"));
    }
    @Test
    public void updateStreakTest1(){
        User u = new User(null, "", "", "", "uniqueUsername#5", "ValidPassword#2", 12, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN);
        u.updateStreak();
        assertEquals(u.getStreak(), 0);
    }
    @Test
    public void updateStreakTest2(){
        User u = new User(null, "", "", "", "uniqueUsername#5", "ValidPassword#2", 12, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.now().minusDays(1));
        u.updateStreak();
        assertEquals(u.getStreak(), 13);
    }
    @Test
    public void updateStreakTest3(){
        User u = new User(null, "", "", "", "uniqueUsername#5", "ValidPassword#2", 12, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.now());
        u.updateStreak();
        assertEquals(u.getStreak(), 12);
    }
    @Test
    public void updateStreakTest4(){
        User u = new User(null, "", "", "", "uniqueUsername#5", "ValidPassword#2", 12, 0, new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN.now().minusDays(2));
        u.updateStreak();
        assertEquals(u.getStreak(), 0);
    }

}
