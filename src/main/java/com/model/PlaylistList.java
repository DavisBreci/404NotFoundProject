package com.model;

import java.util.ArrayList;

public class PlaylistList {
    
    private static PlaylistList playlistList;
    private ArrayList<Playlist> playlists;

    private PlaylistList() {
        playlists = new ArrayList<>();
    }

    public static PlaylistList getInstance() {
        // if(playlistList == null) {
        //     playlistList = new ArrayList<>();
        // }

        return playlistList;
    }

    public Playlist getPlaylist(String title, String author, String description, ArrayList<Song> songs, double length) {
        return null;
    }

    public void addPlaylist(String title, String author, String description) {
        // Playlist newPlaylist = new Playlist();
        // playlists.add(newPlaylist);
    }

    public void removePlaylist(Playlist playlist) {
        playlists.remove(playlist);
    }
}
