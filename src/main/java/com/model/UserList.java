package com.model;

import java.util.ArrayList;

public class UserList {
    
    private static UserList userList;
    private ArrayList<User> users;

    private UserList() {
        users = new UserList();
    }

    public static UserList getInstance() {
        if(userList == null) {
            userList = new UserList();
        }

        return userList;
    }

    public User getUser(String username, String password) {
        return user;
    }

    public User addUser(String firstname, String password) {
        users.add(user);
        return user;
    }

    public boolean removeUser(User user) {
        return true;
    }
}
