package com.model;

import java.util.ArrayList;
import java.time.LocalDate;

public class UserList {
    
    private static UserList userList;
    private ArrayList<User> users;

    private UserList() {
        users = DataLoader.getUsers();
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

    public void createUser(String first, String last, String email, String user, String pass) {
        users.add(new User(null, first, last, email, user, pass, 0, 0,
                  new ArrayList<Playlist>(), new ArrayList<Lesson>(), LocalDate.MIN));
    }

    public boolean removeUser(User user) {
        if(users.contains(user)){
            users.remove(user);
            return true;
        }
        return false;
    }
    public ArrayList<User> getAllUsers(){
        return users;
    }
}
