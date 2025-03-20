package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class PlaylistList {
    
    private static PlaylistList playlistList;
    private ArrayList<Playlist> playlists;

    PlaylistList() {
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

    /**
     * @authro brenskrz
     * @return playlists
     */
    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void addPlaylist(String title, String author, String description) {
        playlists.add(new Playlist(null, title, author, description, new ArrayList<>()));
    }

    public void removePlaylist(Playlist playlist) {
        playlists.remove(playlist);
    }
}
