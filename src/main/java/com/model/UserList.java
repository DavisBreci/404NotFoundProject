package com.model;

import java.util.ArrayList;

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
                
    }

    public User addUser(String firstname, String password) {
        // users.add(user);
        return null;
    }

    public boolean removeUser(User user) {
        return true;
    }
}
