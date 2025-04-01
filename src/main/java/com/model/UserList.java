package com.model;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.time.LocalDate;

/**
 * a UserList class that manages a list of users
 */
public class UserList {
    
    private static UserList userList;
    private ArrayList<User> users;

    /**
     * Constructor that loads the user list from DataLoader
     */
    private UserList() {
        users = DataLoader.getUsers();
        users.addAll(DataLoader.getTeachers());
    }

    /**
     * Singleton instance of UserList
     * @return the instance of user list
     */
    public static UserList getInstance() {
        if(userList == null) {
            userList = new UserList();
        }
        return userList;
    }

    /**
     * Gets the User by both username and password
     * @param username The users username
     * @param password The users password
     * @return the user object if the arguments match the user object
     */
    public User getUser(String username, String password) {
        for(User user : users)
            if(user.getUsername().equals(username) && user.getPassword().equals(password))
                return user;
        return null;
        
    }
    
    /**
     * Retrieves a user by their username alone
     * @author Christopher Ferguson
     * @param username the user's handle
     * @return the user or null
     */
    public User getUser(String username){
        for(User u : users)
            if(u.username.equals(username))
                return u;
        return null;
    }

    /**
     * Creates a new user and adds the new user 
     * @param teacher If the student is a teacher or not
     * @param first The User's first name
     * @param last The users' last name
     * @param email The user's email
     * @param user The User's username
     * @param pass The user's password
     * @return True if the creation was a success, false if not
     */
    public boolean createUser(boolean teacher, String first, String last, String email, String user, String pass) {
        if(first == null || last == null || email == null || !User.isValidUsername(user) || !User.isValidPassword(pass))
            return false;
        if(teacher)
            users.add(new Teacher(null, first, last, email, user, pass, 0, 0,
            new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN,
            new ArrayList<ArrayList<User>>(), new ArrayList<Lesson>()));
        else
            users.add(new User(null, first, last, email, user, pass, 0, 0,
                new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN));
        return true;
    }

    /**
     * Removes a user by their username
     * @param username The user's username
     * @return True if the removal was successful, false if not
     */
    public boolean removeUser(String username) {
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getUsername().equals(username)){
                users.remove(i);
                --i;
                return true;
            }
        }
        return false;
    }

    /**
     * Getter for users
     * @return the list of all users
     */
    public ArrayList<User> getAllUsers(){
        return users;
    }

    /**
     * Getter for teachers
     * @return the list of all teachers
     */
    public ArrayList<Teacher> getTeachers(){
        ArrayList<Teacher> teachers = new ArrayList<Teacher>();
        for(User user : users)
            if(user instanceof Teacher)
                teachers.add((Teacher) user);
        return teachers;
    }

    /**
     * Getter for students 
     * @return the list of users who are not teaches
     */
    public ArrayList<User> getUsers(){
        ArrayList<User> students = new ArrayList<User>();
        for(User user : users)
            if(!(user instanceof Teacher))
                students.add(user);
        return students;
    }
    /**
     * checks if a username already exists
     * @author Davis Breci
     * @param username String proposed username
     * @return boolean if someone already has the username
     */
    public boolean contains(String username){
        ArrayList<String> usernames = new ArrayList<String>();
        for(User u : users)
            usernames.add(u.getUsername());
        return usernames.contains(username);
    }
}
