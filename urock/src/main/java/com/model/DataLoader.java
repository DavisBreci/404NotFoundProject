package com.model;

import java.util.ArrayList;

public class DataLoader {
    
    public ArrayList<User> getUsers() {
        return users;
    }

    public Score midiToScore(String filename, Instrument instrument) {
        return score;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }
}
