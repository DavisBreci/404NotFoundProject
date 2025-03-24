package com.model;
import java.util.ArrayList;

public class SongList {
    
    private static SongList songList;
    private ArrayList<Song> songs;

    private SongList() {
        songs = DataLoader.getAllSongs();
    }

    public static SongList getInstance() {
        if(songList == null) {
            songList = new SongList();
        }
        return songList;
    }

    public ArrayList<Song> getSongList() {
        return songs;
    }

    public Song getSongByTitle(String name) {
        for(Song s : songs)
            if(s.getTitle().equals(name))
                return s;
        return null;
    }
    

    public ArrayList<Song> getSongsByDifficulty(DifficultyLevel dv) {
        ArrayList<Song> ret = new ArrayList<Song>();
        for(Song s : songs)
            if(s.getDifficultyLevel() == dv)
                ret.add(s);
        return ret;
    }

    public ArrayList<Song> getSongsByKey(Key k) {
        ArrayList<Song> ret = new ArrayList<Song>();
        for(Song s : songs)
            if(s.getKey() == k)
                ret.add(s);
        return ret;
    }

    public ArrayList<Song> getSongsByGenre(String genre) {
        ArrayList<Song> ret = new ArrayList<Song>();
        for(Song s : songs)
            if(s.getGenre() == genre)
                ret.add(s);
        return ret;
    }

    /**
     * @author Christopher Ferguson
     * Retrieves all songs by an artist
     * @param artist the artis whose songs you want
     * @return the songs by the artist
     */
    public ArrayList<Song> getSongsByArtist(String artist){
        ArrayList<Song> ret = new ArrayList<Song>();
        for(Song s : songs)
            if(s.getArtist() == artist)
                ret.add(s);
        return ret;
    }

    public void createSong(String title, String artist, String genre, Key key, DifficultyLevel difficulty,
                        Instrument instrument, Score score) {
        songs.add(new Song(null, title, artist, genre, key, difficulty, instrument, score));
    }

    public boolean add(Song s){
        return songs.add(s);
    }
}
