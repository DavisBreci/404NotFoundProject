package com.model;

import java.util.ArrayList;

/**
 * Class that manages the program's playlists
 */
public class PlaylistList {
    
    private static PlaylistList playlistList;
    private ArrayList<Playlist> playlists;

    /**
     * Pulls data from JSON files to construct the PlaylistList
     * @author brenskrz
     */
    private PlaylistList() {
        playlists = DataLoader.getAllPlaylists();
    }

    /**
     * Retrieves a reference to the PlaylistList singleton
     * @return the singleton
     * @author Davis Breci
     */
    public static PlaylistList getInstance() {
        if(playlistList == null) {
            playlistList = new PlaylistList();
        }
        return playlistList;
    }

    /**
     * Retrieves all playlists held by the PlaylistLists
     * @return the complete playlist collection
     */
    public ArrayList<Playlist> getAllPlaylists(){
        return playlists;
    }

    /**
     * Retrieves a playlist based on the given metadata
     * @param title the playlist's title
     * @param author the playlist's author
     * @param description the playlist's description
     * @param library the songs contained within the playlist
     * @param length the duration of the playlist (unused)
     * @return the playlist (if it exists)
     * @author brenskrz
     */
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

    /**
     * Retrieves a playlist by title alone
     * @param title the title to be searched for
     * @return the playlist (if it exxists)
     * @author brenskrz
     */    
    public Playlist getPlaylistByTitle(String title) {
        for( Playlist playlist : playlists) {
            if(playlist.getTitle().equals(title)) {
                return playlist;
            }
        }
        return null;
    }

    /**
     * Retrieves the first playlist in the list authored by the given author
     * @param author the playlist's creator
     * @return the playlist (if it exists)
     * @author brenskrz
     */
    public Playlist getPlaylistByAuthor(String author) {
        for( Playlist playlist : playlists) {
            if(playlist.getAuthor().equals(author)) {
                return playlist;
            }
        }
        return null;
    }
    
    /**
     * Creates a new playlist with the given attributes
     * @param title the playlist's title
     * @param author the playlist's creator
     * @param description a brief description of the playlist's contents
     * @return the newly created playlist
     * @author brenskrz
     */
    public Playlist createPlaylist(String title, String author, String description) {
        if(title.charAt(0) == '~') {
            return null;
        }
        Playlist toAdd = new Playlist(null, title, author, description, null);
        playlists.add(toAdd);
        return toAdd;
    }

    /**
     * Attempts to remove the playlist with the given uuid
     * @param id the uuid
     */
    public void removePlaylist(String id) {
        playlists.removeIf(playlist -> playlist.getId().equals(id));
    }

   
}
