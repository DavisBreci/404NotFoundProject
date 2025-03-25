package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class PlaylistList {
    
    private static PlaylistList playlistList;
    private ArrayList<Playlist> playlists;

    private PlaylistList() {
        playlists = DataLoader.getAllPlaylists();
    }

    public static PlaylistList getInstance() {
        if(playlistList == null) {
            playlistList = new PlaylistList();
        }
        return playlistList;
    }

    public Playlist getPlaylist(String title, String author, String description, ArrayList<Song> library, double length) {
        for(Playlist playlist : playlists) {
            boolean titleMatch = playlist.getTitle().equals(title);
            boolean authorMatch = ( author == null || playlist.getAuthor().equals(author));
            boolean descriptionMatch = (description == null || playlist.getDescription().equals(description));
            boolean libraryMatch = (library == null || playlist.getSongs().containsAll(library));

            if(titleMatch && authorMatch && descriptionMatch && libraryMatch) {
                return playlist;
            }
        }
        return null;
    }

    
    public Playlist getPlaylistByTitle(String title) {
        for( Playlist playlist : playlists) {
            if(playlist.getTitle().equals(title)) {
                return playlist;
            }
        }
        return null;
    }

    public Playlist getPlaylistByAuthor(String author) {
        for( Playlist playlist : playlists) {
            if(playlist.getAuthor().equals(author)) {
                return playlist;
            }
        }
        return null;
    }
    

    public Playlist createPlaylist(String title, String author, String description) {
        if(title.charAt(0) == '~') {
            return null;
        }
        Playlist toAdd = new Playlist(null, title, author, description, null);
        playlists.add(toAdd);
        return toAdd;
    }

    public void removePlaylist(String id) {
        playlists.removeIf(playlist -> playlist.getId().equals(id));
    }

   
}
