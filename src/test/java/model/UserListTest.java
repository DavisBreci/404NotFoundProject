package model;

import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.time.LocalDate;

import com.model.*;

public class UserListTest{
    @Test
    public void getInstanceTest1(){
        UserList ul = UserList.getInstance();
        assertNotNull(ul);
    }
    @Test
    public void getInstanceTest2(){
        UserList ul = UserList.getInstance();
        UserList ul2 = UserList.getInstance();
        assertEquals(ul, ul2);
    }
    @Test
    public void getInstanceTest3(){
        UserList ul = UserList.getInstance();
        UserList copy = ul;
        ul = UserList.getInstance();
        assertEquals(ul, copy);
    }
    @Test
    public void getInstanceTest4(){
        UserList ul = UserList.getInstance();
        UserList copy = ul;
        ul.getInstance();
        assertEquals(ul, copy);
    }
    @Test
    public void getUserTest1(){
        assertNull(UserList.getInstance().getUser(null, null));
    }
    @Test
    public void getUserTest2(){
        assertNull(UserList.getInstance().getUser("dbreci", null));
    }
    @Test
    public void getUserTest3(){
        assertNull(UserList.getInstance().getUser(null, "I<3mysister"));
    }
    @Test
    public void getUserTest4(){
        assertNotNull(UserList.getInstance().getUser("ffred", "I<3mysister"));
    }
    UserList ul;
    @Before
    public void initialize(){
        ul = UserList.getInstance();
    }
    // The createUser method is the primary reason we need not test the User constructor with invalid usernames
    @Test
    public void createUserTest1(){
        assertTrue(ul.createUser(false, "firstName", "lastName", "email@email.email", "uniqueUsername", "ValidPass#1"));
        assertNotNull(ul.getUser("uniqueUsername", "ValidPass#1"));
    }
    @Test
    public void createUserTest2(){
        assertFalse(ul.createUser(false, "","", null, "unique", "ValidPass#1"));
    }
    @Test
    public void createUserTest3(){
        assertFalse(ul.createUser(false, "", "", "", "unique", "badpass"));
    }
    @Test
    public void createUserTest4(){
        assertFalse(ul.createUser(false, "", "", "", "dbreci", "ValidPass#1"));
    }
    @Test
    public void createUserTest5(){
        assertFalse(ul.createUser(true, "", "", "", "dbreci", "ValidPass#1"));
    }
    @Test
    public void createUserTest6(){
        assertFalse(ul.createUser(true, "", "", "", "unique", "badpass"));
    }
    @Test
    public void createUserTest7(){
        assertFalse(ul.createUser(true, null, null, null, "unique", "GoodPass#3"));
    }
    @Test
    public void createUserTest8(){
        assertTrue(ul.createUser(true, "portia", "plante", "portiaportia@email.sc.edu", "portiaportia", "I<3myStudents!"));
        assertNotNull(ul.getUser("portiaportia", "I<3myStudents!"));
    }
    @Test
    public void createUserTest9(){
        assertFalse(ul.createUser(false, "valid", "valid", "valid", "includes spaces", "includes spac3$"));
    }
    @Test
    public void removeUserTest1(){
        assertFalse(ul.removeUser("asdkjlckasdjkfkj;"));
    }
    @Test
    public void removeUserTest2(){
        assertTrue(ul.contains("dbreci"));
        assertTrue(ul.removeUser("dbreci"));
        assertFalse(ul.contains("dbreci"));
    }
    @Test
    public void removeUserTest3(){
        assertFalse(ul.removeUser(null));
    }
}