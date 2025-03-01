package com.model;

import java.util.ArrayList;

public class UserList {
    
    private static UserList userList;
    private ArrayList<User> users;

    private UserList() {

    }

    public static UserList getInstance() {

    }

    public User getUser(String username, String password) {
        
    }

    public User addUser(String firstname, String password) {

    }

    public boolean removeUser(User user) {
        return true;
    }
}
