package com.model;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.time.LocalDate;

public class UserList {
    
    private static UserList userList;
    private ArrayList<User> users;

    private UserList() {
        users = DataLoader.getUsers();
        users.addAll(DataLoader.getTeachers());
    }

    public static UserList getInstance() {
        if(userList == null) {
            userList = new UserList();
        }
        return userList;
    }

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

    public boolean createUser(boolean teacher, String first, String last, String email, String user, String pass) {
        if(!User.isValidUsername(user) || !User.isValidPassword(pass))
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
    public ArrayList<User> getAllUsers(){
        return users;
    }

    public ArrayList<Teacher> getTeachers(){
        ArrayList<Teacher> teachers = new ArrayList<Teacher>();
        for(User user : users)
            if(user instanceof Teacher)
                teachers.add((Teacher) user);
        return teachers;
    }

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
