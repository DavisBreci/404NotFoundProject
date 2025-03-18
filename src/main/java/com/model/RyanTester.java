package com.model;

import java.util.ArrayList;

import com.model.User;

public class RyanTester {
    public static void main(String[] args) {
        Score dummyscore = DataLoader.getScoreFromID("3a6c83d2-2235-4fff-84dc-7ad6ec2dabf8");
        ArrayList<User> dummyusers = DataLoader.getUsers();
        ArrayList<Song> dummysongs = DataLoader.getSongs();
    }
}